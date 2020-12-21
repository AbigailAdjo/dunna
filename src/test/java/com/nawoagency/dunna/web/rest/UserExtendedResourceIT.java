package com.nawoagency.dunna.web.rest;

import com.nawoagency.dunna.DunnaApp;
import com.nawoagency.dunna.domain.UserExtended;
import com.nawoagency.dunna.repository.UserExtendedRepository;
import com.nawoagency.dunna.repository.search.UserExtendedSearchRepository;

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
import org.springframework.util.Base64Utils;
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

import com.nawoagency.dunna.domain.enumeration.Gender;
/**
 * Integration tests for the {@link UserExtendedResource} REST controller.
 */
@SpringBootTest(classes = DunnaApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class UserExtendedResourceIT {

    private static final Gender DEFAULT_GENDER_TYPE = Gender.MALE;
    private static final Gender UPDATED_GENDER_TYPE = Gender.FEMALE;

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_RESPONSIBLE = false;
    private static final Boolean UPDATED_RESPONSIBLE = true;

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final byte[] DEFAULT_AVATAR = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_AVATAR = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_AVATAR_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_AVATAR_CONTENT_TYPE = "image/png";

    private static final Instant DEFAULT_LAST_LOGIN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_LOGIN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private UserExtendedRepository userExtendedRepository;

    /**
     * This repository is mocked in the com.nawoagency.dunna.repository.search test package.
     *
     * @see com.nawoagency.dunna.repository.search.UserExtendedSearchRepositoryMockConfiguration
     */
    @Autowired
    private UserExtendedSearchRepository mockUserExtendedSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserExtendedMockMvc;

    private UserExtended userExtended;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserExtended createEntity(EntityManager em) {
        UserExtended userExtended = new UserExtended()
            .genderType(DEFAULT_GENDER_TYPE)
            .phone(DEFAULT_PHONE)
            .responsible(DEFAULT_RESPONSIBLE)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .createdBy(DEFAULT_CREATED_BY)
            .updatedBy(DEFAULT_UPDATED_BY)
            .avatar(DEFAULT_AVATAR)
            .avatarContentType(DEFAULT_AVATAR_CONTENT_TYPE)
            .lastLogin(DEFAULT_LAST_LOGIN);
        return userExtended;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserExtended createUpdatedEntity(EntityManager em) {
        UserExtended userExtended = new UserExtended()
            .genderType(UPDATED_GENDER_TYPE)
            .phone(UPDATED_PHONE)
            .responsible(UPDATED_RESPONSIBLE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .createdBy(UPDATED_CREATED_BY)
            .updatedBy(UPDATED_UPDATED_BY)
            .avatar(UPDATED_AVATAR)
            .avatarContentType(UPDATED_AVATAR_CONTENT_TYPE)
            .lastLogin(UPDATED_LAST_LOGIN);
        return userExtended;
    }

    @BeforeEach
    public void initTest() {
        userExtended = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserExtended() throws Exception {
        int databaseSizeBeforeCreate = userExtendedRepository.findAll().size();
        // Create the UserExtended
        restUserExtendedMockMvc.perform(post("/api/user-extendeds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userExtended)))
            .andExpect(status().isCreated());

        // Validate the UserExtended in the database
        List<UserExtended> userExtendedList = userExtendedRepository.findAll();
        assertThat(userExtendedList).hasSize(databaseSizeBeforeCreate + 1);
        UserExtended testUserExtended = userExtendedList.get(userExtendedList.size() - 1);
        assertThat(testUserExtended.getGenderType()).isEqualTo(DEFAULT_GENDER_TYPE);
        assertThat(testUserExtended.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testUserExtended.isResponsible()).isEqualTo(DEFAULT_RESPONSIBLE);
        assertThat(testUserExtended.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testUserExtended.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testUserExtended.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testUserExtended.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testUserExtended.getAvatar()).isEqualTo(DEFAULT_AVATAR);
        assertThat(testUserExtended.getAvatarContentType()).isEqualTo(DEFAULT_AVATAR_CONTENT_TYPE);
        assertThat(testUserExtended.getLastLogin()).isEqualTo(DEFAULT_LAST_LOGIN);

        // Validate the UserExtended in Elasticsearch
        verify(mockUserExtendedSearchRepository, times(1)).save(testUserExtended);
    }

    @Test
    @Transactional
    public void createUserExtendedWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userExtendedRepository.findAll().size();

        // Create the UserExtended with an existing ID
        userExtended.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserExtendedMockMvc.perform(post("/api/user-extendeds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userExtended)))
            .andExpect(status().isBadRequest());

        // Validate the UserExtended in the database
        List<UserExtended> userExtendedList = userExtendedRepository.findAll();
        assertThat(userExtendedList).hasSize(databaseSizeBeforeCreate);

        // Validate the UserExtended in Elasticsearch
        verify(mockUserExtendedSearchRepository, times(0)).save(userExtended);
    }


    @Test
    @Transactional
    public void checkPhoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = userExtendedRepository.findAll().size();
        // set the field null
        userExtended.setPhone(null);

        // Create the UserExtended, which fails.


        restUserExtendedMockMvc.perform(post("/api/user-extendeds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userExtended)))
            .andExpect(status().isBadRequest());

        List<UserExtended> userExtendedList = userExtendedRepository.findAll();
        assertThat(userExtendedList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUserExtendeds() throws Exception {
        // Initialize the database
        userExtendedRepository.saveAndFlush(userExtended);

        // Get all the userExtendedList
        restUserExtendedMockMvc.perform(get("/api/user-extendeds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userExtended.getId().intValue())))
            .andExpect(jsonPath("$.[*].genderType").value(hasItem(DEFAULT_GENDER_TYPE.toString())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].responsible").value(hasItem(DEFAULT_RESPONSIBLE.booleanValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].avatarContentType").value(hasItem(DEFAULT_AVATAR_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].avatar").value(hasItem(Base64Utils.encodeToString(DEFAULT_AVATAR))))
            .andExpect(jsonPath("$.[*].lastLogin").value(hasItem(DEFAULT_LAST_LOGIN.toString())));
    }
    
    @Test
    @Transactional
    public void getUserExtended() throws Exception {
        // Initialize the database
        userExtendedRepository.saveAndFlush(userExtended);

        // Get the userExtended
        restUserExtendedMockMvc.perform(get("/api/user-extendeds/{id}", userExtended.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userExtended.getId().intValue()))
            .andExpect(jsonPath("$.genderType").value(DEFAULT_GENDER_TYPE.toString()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.responsible").value(DEFAULT_RESPONSIBLE.booleanValue()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.avatarContentType").value(DEFAULT_AVATAR_CONTENT_TYPE))
            .andExpect(jsonPath("$.avatar").value(Base64Utils.encodeToString(DEFAULT_AVATAR)))
            .andExpect(jsonPath("$.lastLogin").value(DEFAULT_LAST_LOGIN.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingUserExtended() throws Exception {
        // Get the userExtended
        restUserExtendedMockMvc.perform(get("/api/user-extendeds/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserExtended() throws Exception {
        // Initialize the database
        userExtendedRepository.saveAndFlush(userExtended);

        int databaseSizeBeforeUpdate = userExtendedRepository.findAll().size();

        // Update the userExtended
        UserExtended updatedUserExtended = userExtendedRepository.findById(userExtended.getId()).get();
        // Disconnect from session so that the updates on updatedUserExtended are not directly saved in db
        em.detach(updatedUserExtended);
        updatedUserExtended
            .genderType(UPDATED_GENDER_TYPE)
            .phone(UPDATED_PHONE)
            .responsible(UPDATED_RESPONSIBLE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .createdBy(UPDATED_CREATED_BY)
            .updatedBy(UPDATED_UPDATED_BY)
            .avatar(UPDATED_AVATAR)
            .avatarContentType(UPDATED_AVATAR_CONTENT_TYPE)
            .lastLogin(UPDATED_LAST_LOGIN);

        restUserExtendedMockMvc.perform(put("/api/user-extendeds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedUserExtended)))
            .andExpect(status().isOk());

        // Validate the UserExtended in the database
        List<UserExtended> userExtendedList = userExtendedRepository.findAll();
        assertThat(userExtendedList).hasSize(databaseSizeBeforeUpdate);
        UserExtended testUserExtended = userExtendedList.get(userExtendedList.size() - 1);
        assertThat(testUserExtended.getGenderType()).isEqualTo(UPDATED_GENDER_TYPE);
        assertThat(testUserExtended.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testUserExtended.isResponsible()).isEqualTo(UPDATED_RESPONSIBLE);
        assertThat(testUserExtended.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testUserExtended.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testUserExtended.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testUserExtended.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testUserExtended.getAvatar()).isEqualTo(UPDATED_AVATAR);
        assertThat(testUserExtended.getAvatarContentType()).isEqualTo(UPDATED_AVATAR_CONTENT_TYPE);
        assertThat(testUserExtended.getLastLogin()).isEqualTo(UPDATED_LAST_LOGIN);

        // Validate the UserExtended in Elasticsearch
        verify(mockUserExtendedSearchRepository, times(1)).save(testUserExtended);
    }

    @Test
    @Transactional
    public void updateNonExistingUserExtended() throws Exception {
        int databaseSizeBeforeUpdate = userExtendedRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserExtendedMockMvc.perform(put("/api/user-extendeds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userExtended)))
            .andExpect(status().isBadRequest());

        // Validate the UserExtended in the database
        List<UserExtended> userExtendedList = userExtendedRepository.findAll();
        assertThat(userExtendedList).hasSize(databaseSizeBeforeUpdate);

        // Validate the UserExtended in Elasticsearch
        verify(mockUserExtendedSearchRepository, times(0)).save(userExtended);
    }

    @Test
    @Transactional
    public void deleteUserExtended() throws Exception {
        // Initialize the database
        userExtendedRepository.saveAndFlush(userExtended);

        int databaseSizeBeforeDelete = userExtendedRepository.findAll().size();

        // Delete the userExtended
        restUserExtendedMockMvc.perform(delete("/api/user-extendeds/{id}", userExtended.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserExtended> userExtendedList = userExtendedRepository.findAll();
        assertThat(userExtendedList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the UserExtended in Elasticsearch
        verify(mockUserExtendedSearchRepository, times(1)).deleteById(userExtended.getId());
    }

    @Test
    @Transactional
    public void searchUserExtended() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        userExtendedRepository.saveAndFlush(userExtended);
        when(mockUserExtendedSearchRepository.search(queryStringQuery("id:" + userExtended.getId())))
            .thenReturn(Collections.singletonList(userExtended));

        // Search the userExtended
        restUserExtendedMockMvc.perform(get("/api/_search/user-extendeds?query=id:" + userExtended.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userExtended.getId().intValue())))
            .andExpect(jsonPath("$.[*].genderType").value(hasItem(DEFAULT_GENDER_TYPE.toString())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].responsible").value(hasItem(DEFAULT_RESPONSIBLE.booleanValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].avatarContentType").value(hasItem(DEFAULT_AVATAR_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].avatar").value(hasItem(Base64Utils.encodeToString(DEFAULT_AVATAR))))
            .andExpect(jsonPath("$.[*].lastLogin").value(hasItem(DEFAULT_LAST_LOGIN.toString())));
    }
}
