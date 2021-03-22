package com.synectiks.asset.web.rest;

import com.synectiks.asset.AssetserviceApp;
import com.synectiks.asset.domain.EnvAccount;
import com.synectiks.asset.repository.EnvAccountRepository;
import com.synectiks.asset.service.EnvAccountService;
import com.synectiks.asset.service.dto.EnvAccountDTO;
import com.synectiks.asset.service.mapper.EnvAccountMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link EnvAccountResource} REST controller.
 */
@SpringBootTest(classes = AssetserviceApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class EnvAccountResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_CLIENT_ID = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_CLIENT_SECRET = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_SECRET = "BBBBBBBBBB";

    private static final String DEFAULT_USER_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_USER_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    @Autowired
    private EnvAccountRepository envAccountRepository;

    @Autowired
    private EnvAccountMapper envAccountMapper;

    @Autowired
    private EnvAccountService envAccountService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEnvAccountMockMvc;

    private EnvAccount envAccount;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EnvAccount createEntity(EntityManager em) {
        EnvAccount envAccount = new EnvAccount()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedOn(DEFAULT_UPDATED_ON)
            .status(DEFAULT_STATUS)
            .clientId(DEFAULT_CLIENT_ID)
            .clientSecret(DEFAULT_CLIENT_SECRET)
            .userType(DEFAULT_USER_TYPE)
            .email(DEFAULT_EMAIL)
            .password(DEFAULT_PASSWORD);
        return envAccount;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EnvAccount createUpdatedEntity(EntityManager em) {
        EnvAccount envAccount = new EnvAccount()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .createdOn(UPDATED_CREATED_ON)
            .updatedOn(UPDATED_UPDATED_ON)
            .status(UPDATED_STATUS)
            .clientId(UPDATED_CLIENT_ID)
            .clientSecret(UPDATED_CLIENT_SECRET)
            .userType(UPDATED_USER_TYPE)
            .email(UPDATED_EMAIL)
            .password(UPDATED_PASSWORD);
        return envAccount;
    }

    @BeforeEach
    public void initTest() {
        envAccount = createEntity(em);
    }

    @Test
    @Transactional
    public void createEnvAccount() throws Exception {
        int databaseSizeBeforeCreate = envAccountRepository.findAll().size();
        // Create the EnvAccount
        EnvAccountDTO envAccountDTO = envAccountMapper.toDto(envAccount);
        restEnvAccountMockMvc.perform(post("/api/env-accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(envAccountDTO)))
            .andExpect(status().isCreated());

        // Validate the EnvAccount in the database
        List<EnvAccount> envAccountList = envAccountRepository.findAll();
        assertThat(envAccountList).hasSize(databaseSizeBeforeCreate + 1);
        EnvAccount testEnvAccount = envAccountList.get(envAccountList.size() - 1);
        assertThat(testEnvAccount.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testEnvAccount.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testEnvAccount.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testEnvAccount.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
        assertThat(testEnvAccount.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testEnvAccount.getClientId()).isEqualTo(DEFAULT_CLIENT_ID);
        assertThat(testEnvAccount.getClientSecret()).isEqualTo(DEFAULT_CLIENT_SECRET);
        assertThat(testEnvAccount.getUserType()).isEqualTo(DEFAULT_USER_TYPE);
        assertThat(testEnvAccount.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testEnvAccount.getPassword()).isEqualTo(DEFAULT_PASSWORD);
    }

    @Test
    @Transactional
    public void createEnvAccountWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = envAccountRepository.findAll().size();

        // Create the EnvAccount with an existing ID
        envAccount.setId(1L);
        EnvAccountDTO envAccountDTO = envAccountMapper.toDto(envAccount);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEnvAccountMockMvc.perform(post("/api/env-accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(envAccountDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EnvAccount in the database
        List<EnvAccount> envAccountList = envAccountRepository.findAll();
        assertThat(envAccountList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEnvAccounts() throws Exception {
        // Initialize the database
        envAccountRepository.saveAndFlush(envAccount);

        // Get all the envAccountList
        restEnvAccountMockMvc.perform(get("/api/env-accounts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(envAccount.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].clientId").value(hasItem(DEFAULT_CLIENT_ID)))
            .andExpect(jsonPath("$.[*].clientSecret").value(hasItem(DEFAULT_CLIENT_SECRET)))
            .andExpect(jsonPath("$.[*].userType").value(hasItem(DEFAULT_USER_TYPE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)));
    }
    
    @Test
    @Transactional
    public void getEnvAccount() throws Exception {
        // Initialize the database
        envAccountRepository.saveAndFlush(envAccount);

        // Get the envAccount
        restEnvAccountMockMvc.perform(get("/api/env-accounts/{id}", envAccount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(envAccount.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.clientId").value(DEFAULT_CLIENT_ID))
            .andExpect(jsonPath("$.clientSecret").value(DEFAULT_CLIENT_SECRET))
            .andExpect(jsonPath("$.userType").value(DEFAULT_USER_TYPE))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD));
    }
    @Test
    @Transactional
    public void getNonExistingEnvAccount() throws Exception {
        // Get the envAccount
        restEnvAccountMockMvc.perform(get("/api/env-accounts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEnvAccount() throws Exception {
        // Initialize the database
        envAccountRepository.saveAndFlush(envAccount);

        int databaseSizeBeforeUpdate = envAccountRepository.findAll().size();

        // Update the envAccount
        EnvAccount updatedEnvAccount = envAccountRepository.findById(envAccount.getId()).get();
        // Disconnect from session so that the updates on updatedEnvAccount are not directly saved in db
        em.detach(updatedEnvAccount);
        updatedEnvAccount
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .createdOn(UPDATED_CREATED_ON)
            .updatedOn(UPDATED_UPDATED_ON)
            .status(UPDATED_STATUS)
            .clientId(UPDATED_CLIENT_ID)
            .clientSecret(UPDATED_CLIENT_SECRET)
            .userType(UPDATED_USER_TYPE)
            .email(UPDATED_EMAIL)
            .password(UPDATED_PASSWORD);
        EnvAccountDTO envAccountDTO = envAccountMapper.toDto(updatedEnvAccount);

        restEnvAccountMockMvc.perform(put("/api/env-accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(envAccountDTO)))
            .andExpect(status().isOk());

        // Validate the EnvAccount in the database
        List<EnvAccount> envAccountList = envAccountRepository.findAll();
        assertThat(envAccountList).hasSize(databaseSizeBeforeUpdate);
        EnvAccount testEnvAccount = envAccountList.get(envAccountList.size() - 1);
        assertThat(testEnvAccount.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEnvAccount.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testEnvAccount.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testEnvAccount.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testEnvAccount.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testEnvAccount.getClientId()).isEqualTo(UPDATED_CLIENT_ID);
        assertThat(testEnvAccount.getClientSecret()).isEqualTo(UPDATED_CLIENT_SECRET);
        assertThat(testEnvAccount.getUserType()).isEqualTo(UPDATED_USER_TYPE);
        assertThat(testEnvAccount.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testEnvAccount.getPassword()).isEqualTo(UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    public void updateNonExistingEnvAccount() throws Exception {
        int databaseSizeBeforeUpdate = envAccountRepository.findAll().size();

        // Create the EnvAccount
        EnvAccountDTO envAccountDTO = envAccountMapper.toDto(envAccount);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEnvAccountMockMvc.perform(put("/api/env-accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(envAccountDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EnvAccount in the database
        List<EnvAccount> envAccountList = envAccountRepository.findAll();
        assertThat(envAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEnvAccount() throws Exception {
        // Initialize the database
        envAccountRepository.saveAndFlush(envAccount);

        int databaseSizeBeforeDelete = envAccountRepository.findAll().size();

        // Delete the envAccount
        restEnvAccountMockMvc.perform(delete("/api/env-accounts/{id}", envAccount.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EnvAccount> envAccountList = envAccountRepository.findAll();
        assertThat(envAccountList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
