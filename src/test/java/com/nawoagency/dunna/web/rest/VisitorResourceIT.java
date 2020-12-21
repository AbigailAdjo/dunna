package com.nawoagency.dunna.web.rest;

import com.nawoagency.dunna.DunnaApp;
import com.nawoagency.dunna.domain.Visitor;
import com.nawoagency.dunna.repository.VisitorRepository;
import com.nawoagency.dunna.repository.search.VisitorSearchRepository;

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
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.nawoagency.dunna.domain.enumeration.Gender;
import com.nawoagency.dunna.domain.enumeration.IdcardType;
/**
 * Integration tests for the {@link VisitorResource} REST controller.
 */
@SpringBootTest(classes = DunnaApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class VisitorResourceIT {

    private static final Gender DEFAULT_GENDER_TYPE = Gender.MALE;
    private static final Gender UPDATED_GENDER_TYPE = Gender.FEMALE;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LASTNAME = "AAAAAAAAAA";
    private static final String UPDATED_LASTNAME = "BBBBBBBBBB";

    private static final IdcardType DEFAULT_IDCARD_TYPE = IdcardType.Idcard;
    private static final IdcardType UPDATED_IDCARD_TYPE = IdcardType.Passport;

    private static final String DEFAULT_CARD_EXPIRING_DATE = "AAAAAAAAAA";
    private static final String UPDATED_CARD_EXPIRING_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_BIRTH_DATE = "AAAAAAAAAA";
    private static final String UPDATED_BIRTH_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_CARD_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_CARD_NUMBER = "BBBBBBBBBB";

    private static final Integer DEFAULT_CARD_COUNTRY = 1;
    private static final Integer UPDATED_CARD_COUNTRY = 2;

    private static final Integer DEFAULT_NATIONALITY = 1;
    private static final Integer UPDATED_NATIONALITY = 2;

    @Autowired
    private VisitorRepository visitorRepository;

    /**
     * This repository is mocked in the com.nawoagency.dunna.repository.search test package.
     *
     * @see com.nawoagency.dunna.repository.search.VisitorSearchRepositoryMockConfiguration
     */
    @Autowired
    private VisitorSearchRepository mockVisitorSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVisitorMockMvc;

    private Visitor visitor;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Visitor createEntity(EntityManager em) {
        Visitor visitor = new Visitor()
            .genderType(DEFAULT_GENDER_TYPE)
            .name(DEFAULT_NAME)
            .lastname(DEFAULT_LASTNAME)
            .idcardType(DEFAULT_IDCARD_TYPE)
            .cardExpiringDate(DEFAULT_CARD_EXPIRING_DATE)
            .birthDate(DEFAULT_BIRTH_DATE)
            .cardNumber(DEFAULT_CARD_NUMBER)
            .cardCountry(DEFAULT_CARD_COUNTRY)
            .nationality(DEFAULT_NATIONALITY);
        return visitor;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Visitor createUpdatedEntity(EntityManager em) {
        Visitor visitor = new Visitor()
            .genderType(UPDATED_GENDER_TYPE)
            .name(UPDATED_NAME)
            .lastname(UPDATED_LASTNAME)
            .idcardType(UPDATED_IDCARD_TYPE)
            .cardExpiringDate(UPDATED_CARD_EXPIRING_DATE)
            .birthDate(UPDATED_BIRTH_DATE)
            .cardNumber(UPDATED_CARD_NUMBER)
            .cardCountry(UPDATED_CARD_COUNTRY)
            .nationality(UPDATED_NATIONALITY);
        return visitor;
    }

    @BeforeEach
    public void initTest() {
        visitor = createEntity(em);
    }

    @Test
    @Transactional
    public void createVisitor() throws Exception {
        int databaseSizeBeforeCreate = visitorRepository.findAll().size();
        // Create the Visitor
        restVisitorMockMvc.perform(post("/api/visitors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(visitor)))
            .andExpect(status().isCreated());

        // Validate the Visitor in the database
        List<Visitor> visitorList = visitorRepository.findAll();
        assertThat(visitorList).hasSize(databaseSizeBeforeCreate + 1);
        Visitor testVisitor = visitorList.get(visitorList.size() - 1);
        assertThat(testVisitor.getGenderType()).isEqualTo(DEFAULT_GENDER_TYPE);
        assertThat(testVisitor.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testVisitor.getLastname()).isEqualTo(DEFAULT_LASTNAME);
        assertThat(testVisitor.getIdcardType()).isEqualTo(DEFAULT_IDCARD_TYPE);
        assertThat(testVisitor.getCardExpiringDate()).isEqualTo(DEFAULT_CARD_EXPIRING_DATE);
        assertThat(testVisitor.getBirthDate()).isEqualTo(DEFAULT_BIRTH_DATE);
        assertThat(testVisitor.getCardNumber()).isEqualTo(DEFAULT_CARD_NUMBER);
        assertThat(testVisitor.getCardCountry()).isEqualTo(DEFAULT_CARD_COUNTRY);
        assertThat(testVisitor.getNationality()).isEqualTo(DEFAULT_NATIONALITY);

        // Validate the Visitor in Elasticsearch
        verify(mockVisitorSearchRepository, times(1)).save(testVisitor);
    }

    @Test
    @Transactional
    public void createVisitorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = visitorRepository.findAll().size();

        // Create the Visitor with an existing ID
        visitor.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVisitorMockMvc.perform(post("/api/visitors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(visitor)))
            .andExpect(status().isBadRequest());

        // Validate the Visitor in the database
        List<Visitor> visitorList = visitorRepository.findAll();
        assertThat(visitorList).hasSize(databaseSizeBeforeCreate);

        // Validate the Visitor in Elasticsearch
        verify(mockVisitorSearchRepository, times(0)).save(visitor);
    }


    @Test
    @Transactional
    public void getAllVisitors() throws Exception {
        // Initialize the database
        visitorRepository.saveAndFlush(visitor);

        // Get all the visitorList
        restVisitorMockMvc.perform(get("/api/visitors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(visitor.getId().intValue())))
            .andExpect(jsonPath("$.[*].genderType").value(hasItem(DEFAULT_GENDER_TYPE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].lastname").value(hasItem(DEFAULT_LASTNAME)))
            .andExpect(jsonPath("$.[*].idcardType").value(hasItem(DEFAULT_IDCARD_TYPE.toString())))
            .andExpect(jsonPath("$.[*].cardExpiringDate").value(hasItem(DEFAULT_CARD_EXPIRING_DATE)))
            .andExpect(jsonPath("$.[*].birthDate").value(hasItem(DEFAULT_BIRTH_DATE)))
            .andExpect(jsonPath("$.[*].cardNumber").value(hasItem(DEFAULT_CARD_NUMBER)))
            .andExpect(jsonPath("$.[*].cardCountry").value(hasItem(DEFAULT_CARD_COUNTRY)))
            .andExpect(jsonPath("$.[*].nationality").value(hasItem(DEFAULT_NATIONALITY)));
    }
    
    @Test
    @Transactional
    public void getVisitor() throws Exception {
        // Initialize the database
        visitorRepository.saveAndFlush(visitor);

        // Get the visitor
        restVisitorMockMvc.perform(get("/api/visitors/{id}", visitor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(visitor.getId().intValue()))
            .andExpect(jsonPath("$.genderType").value(DEFAULT_GENDER_TYPE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.lastname").value(DEFAULT_LASTNAME))
            .andExpect(jsonPath("$.idcardType").value(DEFAULT_IDCARD_TYPE.toString()))
            .andExpect(jsonPath("$.cardExpiringDate").value(DEFAULT_CARD_EXPIRING_DATE))
            .andExpect(jsonPath("$.birthDate").value(DEFAULT_BIRTH_DATE))
            .andExpect(jsonPath("$.cardNumber").value(DEFAULT_CARD_NUMBER))
            .andExpect(jsonPath("$.cardCountry").value(DEFAULT_CARD_COUNTRY))
            .andExpect(jsonPath("$.nationality").value(DEFAULT_NATIONALITY));
    }
    @Test
    @Transactional
    public void getNonExistingVisitor() throws Exception {
        // Get the visitor
        restVisitorMockMvc.perform(get("/api/visitors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVisitor() throws Exception {
        // Initialize the database
        visitorRepository.saveAndFlush(visitor);

        int databaseSizeBeforeUpdate = visitorRepository.findAll().size();

        // Update the visitor
        Visitor updatedVisitor = visitorRepository.findById(visitor.getId()).get();
        // Disconnect from session so that the updates on updatedVisitor are not directly saved in db
        em.detach(updatedVisitor);
        updatedVisitor
            .genderType(UPDATED_GENDER_TYPE)
            .name(UPDATED_NAME)
            .lastname(UPDATED_LASTNAME)
            .idcardType(UPDATED_IDCARD_TYPE)
            .cardExpiringDate(UPDATED_CARD_EXPIRING_DATE)
            .birthDate(UPDATED_BIRTH_DATE)
            .cardNumber(UPDATED_CARD_NUMBER)
            .cardCountry(UPDATED_CARD_COUNTRY)
            .nationality(UPDATED_NATIONALITY);

        restVisitorMockMvc.perform(put("/api/visitors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedVisitor)))
            .andExpect(status().isOk());

        // Validate the Visitor in the database
        List<Visitor> visitorList = visitorRepository.findAll();
        assertThat(visitorList).hasSize(databaseSizeBeforeUpdate);
        Visitor testVisitor = visitorList.get(visitorList.size() - 1);
        assertThat(testVisitor.getGenderType()).isEqualTo(UPDATED_GENDER_TYPE);
        assertThat(testVisitor.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testVisitor.getLastname()).isEqualTo(UPDATED_LASTNAME);
        assertThat(testVisitor.getIdcardType()).isEqualTo(UPDATED_IDCARD_TYPE);
        assertThat(testVisitor.getCardExpiringDate()).isEqualTo(UPDATED_CARD_EXPIRING_DATE);
        assertThat(testVisitor.getBirthDate()).isEqualTo(UPDATED_BIRTH_DATE);
        assertThat(testVisitor.getCardNumber()).isEqualTo(UPDATED_CARD_NUMBER);
        assertThat(testVisitor.getCardCountry()).isEqualTo(UPDATED_CARD_COUNTRY);
        assertThat(testVisitor.getNationality()).isEqualTo(UPDATED_NATIONALITY);

        // Validate the Visitor in Elasticsearch
        verify(mockVisitorSearchRepository, times(1)).save(testVisitor);
    }

    @Test
    @Transactional
    public void updateNonExistingVisitor() throws Exception {
        int databaseSizeBeforeUpdate = visitorRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVisitorMockMvc.perform(put("/api/visitors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(visitor)))
            .andExpect(status().isBadRequest());

        // Validate the Visitor in the database
        List<Visitor> visitorList = visitorRepository.findAll();
        assertThat(visitorList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Visitor in Elasticsearch
        verify(mockVisitorSearchRepository, times(0)).save(visitor);
    }

    @Test
    @Transactional
    public void deleteVisitor() throws Exception {
        // Initialize the database
        visitorRepository.saveAndFlush(visitor);

        int databaseSizeBeforeDelete = visitorRepository.findAll().size();

        // Delete the visitor
        restVisitorMockMvc.perform(delete("/api/visitors/{id}", visitor.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Visitor> visitorList = visitorRepository.findAll();
        assertThat(visitorList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Visitor in Elasticsearch
        verify(mockVisitorSearchRepository, times(1)).deleteById(visitor.getId());
    }

    @Test
    @Transactional
    public void searchVisitor() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        visitorRepository.saveAndFlush(visitor);
        when(mockVisitorSearchRepository.search(queryStringQuery("id:" + visitor.getId())))
            .thenReturn(Collections.singletonList(visitor));

        // Search the visitor
        restVisitorMockMvc.perform(get("/api/_search/visitors?query=id:" + visitor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(visitor.getId().intValue())))
            .andExpect(jsonPath("$.[*].genderType").value(hasItem(DEFAULT_GENDER_TYPE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].lastname").value(hasItem(DEFAULT_LASTNAME)))
            .andExpect(jsonPath("$.[*].idcardType").value(hasItem(DEFAULT_IDCARD_TYPE.toString())))
            .andExpect(jsonPath("$.[*].cardExpiringDate").value(hasItem(DEFAULT_CARD_EXPIRING_DATE)))
            .andExpect(jsonPath("$.[*].birthDate").value(hasItem(DEFAULT_BIRTH_DATE)))
            .andExpect(jsonPath("$.[*].cardNumber").value(hasItem(DEFAULT_CARD_NUMBER)))
            .andExpect(jsonPath("$.[*].cardCountry").value(hasItem(DEFAULT_CARD_COUNTRY)))
            .andExpect(jsonPath("$.[*].nationality").value(hasItem(DEFAULT_NATIONALITY)));
    }
}
