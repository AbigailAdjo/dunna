package com.nawoagency.dunna.web.rest;

import com.nawoagency.dunna.DunnaApp;
import com.nawoagency.dunna.domain.Region;
import com.nawoagency.dunna.repository.RegionRepository;
import com.nawoagency.dunna.repository.search.RegionSearchRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link RegionResource} REST controller.
 */
@SpringBootTest(classes = DunnaApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class RegionResourceIT {

    private static final String DEFAULT_REGION_NAME = "AAAAAAAAAA";
    private static final String UPDATED_REGION_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_REGION_CODE = "AAAAAAAAAA";
    private static final String UPDATED_REGION_CODE = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    @Autowired
    private RegionRepository regionRepository;

    /**
     * This repository is mocked in the com.nawoagency.dunna.repository.search test package.
     *
     * @see com.nawoagency.dunna.repository.search.RegionSearchRepositoryMockConfiguration
     */
    @Autowired
    private RegionSearchRepository mockRegionSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRegionMockMvc;

    private Region region;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Region createEntity(EntityManager em) {
        Region region = new Region()
            .regionName(DEFAULT_REGION_NAME)
            .regionCode(DEFAULT_REGION_CODE)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .createdBy(DEFAULT_CREATED_BY)
            .updatedBy(DEFAULT_UPDATED_BY);
        return region;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Region createUpdatedEntity(EntityManager em) {
        Region region = new Region()
            .regionName(UPDATED_REGION_NAME)
            .regionCode(UPDATED_REGION_CODE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .createdBy(UPDATED_CREATED_BY)
            .updatedBy(UPDATED_UPDATED_BY);
        return region;
    }

    @BeforeEach
    public void initTest() {
        region = createEntity(em);
    }

    @Test
    @Transactional
    public void createRegion() throws Exception {
        int databaseSizeBeforeCreate = regionRepository.findAll().size();
        // Create the Region
        restRegionMockMvc.perform(post("/api/regions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(region)))
            .andExpect(status().isCreated());

        // Validate the Region in the database
        List<Region> regionList = regionRepository.findAll();
        assertThat(regionList).hasSize(databaseSizeBeforeCreate + 1);
        Region testRegion = regionList.get(regionList.size() - 1);
        assertThat(testRegion.getRegionName()).isEqualTo(DEFAULT_REGION_NAME);
        assertThat(testRegion.getRegionCode()).isEqualTo(DEFAULT_REGION_CODE);
        assertThat(testRegion.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testRegion.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testRegion.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testRegion.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);

        // Validate the Region in Elasticsearch
        verify(mockRegionSearchRepository, times(1)).save(testRegion);
    }

    @Test
    @Transactional
    public void createRegionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = regionRepository.findAll().size();

        // Create the Region with an existing ID
        region.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRegionMockMvc.perform(post("/api/regions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(region)))
            .andExpect(status().isBadRequest());

        // Validate the Region in the database
        List<Region> regionList = regionRepository.findAll();
        assertThat(regionList).hasSize(databaseSizeBeforeCreate);

        // Validate the Region in Elasticsearch
        verify(mockRegionSearchRepository, times(0)).save(region);
    }


    @Test
    @Transactional
    public void checkRegionNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = regionRepository.findAll().size();
        // set the field null
        region.setRegionName(null);

        // Create the Region, which fails.


        restRegionMockMvc.perform(post("/api/regions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(region)))
            .andExpect(status().isBadRequest());

        List<Region> regionList = regionRepository.findAll();
        assertThat(regionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRegionCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = regionRepository.findAll().size();
        // set the field null
        region.setRegionCode(null);

        // Create the Region, which fails.


        restRegionMockMvc.perform(post("/api/regions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(region)))
            .andExpect(status().isBadRequest());

        List<Region> regionList = regionRepository.findAll();
        assertThat(regionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRegions() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList
        restRegionMockMvc.perform(get("/api/regions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(region.getId().intValue())))
            .andExpect(jsonPath("$.[*].regionName").value(hasItem(DEFAULT_REGION_NAME)))
            .andExpect(jsonPath("$.[*].regionCode").value(hasItem(DEFAULT_REGION_CODE)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)));
    }
    
    @Test
    @Transactional
    public void getRegion() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get the region
        restRegionMockMvc.perform(get("/api/regions/{id}", region.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(region.getId().intValue()))
            .andExpect(jsonPath("$.regionName").value(DEFAULT_REGION_NAME))
            .andExpect(jsonPath("$.regionCode").value(DEFAULT_REGION_CODE))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY));
    }
    @Test
    @Transactional
    public void getNonExistingRegion() throws Exception {
        // Get the region
        restRegionMockMvc.perform(get("/api/regions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRegion() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        int databaseSizeBeforeUpdate = regionRepository.findAll().size();

        // Update the region
        Region updatedRegion = regionRepository.findById(region.getId()).get();
        // Disconnect from session so that the updates on updatedRegion are not directly saved in db
        em.detach(updatedRegion);
        updatedRegion
            .regionName(UPDATED_REGION_NAME)
            .regionCode(UPDATED_REGION_CODE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .createdBy(UPDATED_CREATED_BY)
            .updatedBy(UPDATED_UPDATED_BY);

        restRegionMockMvc.perform(put("/api/regions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedRegion)))
            .andExpect(status().isOk());

        // Validate the Region in the database
        List<Region> regionList = regionRepository.findAll();
        assertThat(regionList).hasSize(databaseSizeBeforeUpdate);
        Region testRegion = regionList.get(regionList.size() - 1);
        assertThat(testRegion.getRegionName()).isEqualTo(UPDATED_REGION_NAME);
        assertThat(testRegion.getRegionCode()).isEqualTo(UPDATED_REGION_CODE);
        assertThat(testRegion.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testRegion.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testRegion.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testRegion.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);

        // Validate the Region in Elasticsearch
        verify(mockRegionSearchRepository, times(1)).save(testRegion);
    }

    @Test
    @Transactional
    public void updateNonExistingRegion() throws Exception {
        int databaseSizeBeforeUpdate = regionRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRegionMockMvc.perform(put("/api/regions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(region)))
            .andExpect(status().isBadRequest());

        // Validate the Region in the database
        List<Region> regionList = regionRepository.findAll();
        assertThat(regionList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Region in Elasticsearch
        verify(mockRegionSearchRepository, times(0)).save(region);
    }

    @Test
    @Transactional
    public void deleteRegion() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        int databaseSizeBeforeDelete = regionRepository.findAll().size();

        // Delete the region
        restRegionMockMvc.perform(delete("/api/regions/{id}", region.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Region> regionList = regionRepository.findAll();
        assertThat(regionList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Region in Elasticsearch
        verify(mockRegionSearchRepository, times(1)).deleteById(region.getId());
    }

    @Test
    @Transactional
    public void searchRegion() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        regionRepository.saveAndFlush(region);
        when(mockRegionSearchRepository.search(queryStringQuery("id:" + region.getId())))
            .thenReturn(Collections.singletonList(region));

        // Search the region
        restRegionMockMvc.perform(get("/api/_search/regions?query=id:" + region.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(region.getId().intValue())))
            .andExpect(jsonPath("$.[*].regionName").value(hasItem(DEFAULT_REGION_NAME)))
            .andExpect(jsonPath("$.[*].regionCode").value(hasItem(DEFAULT_REGION_CODE)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)));
    }
}
