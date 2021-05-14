package com.synectiks.asset.web.rest;

import com.synectiks.asset.AssetserviceApp;
import com.synectiks.asset.domain.Accounts;
import com.synectiks.asset.repository.AccountsRepository;
import com.synectiks.asset.service.AccountsService;
import com.synectiks.asset.service.dto.AccountsDTO;
import com.synectiks.asset.service.mapper.AccountsMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AccountsResource} REST controller.
 */
@SpringBootTest(classes = AssetserviceApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AccountsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOUNT_ID = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_TENANT_ID = "AAAAAAAAAA";
    private static final String UPDATED_TENANT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_ACCESS_KEY = "AAAAAAAAAA";
    private static final String UPDATED_ACCESS_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_SECRET_KEY = "AAAAAAAAAA";
    private static final String UPDATED_SECRET_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_REGION = "AAAAAAAAAA";
    private static final String UPDATED_REGION = "BBBBBBBBBB";

    private static final String DEFAULT_BUCKET = "AAAAAAAAAA";
    private static final String UPDATED_BUCKET = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final String DEFAULT_CLOUD_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_CLOUD_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_SOURCE_JSON_REF = "AAAAAAAAAA";
    private static final String UPDATED_SOURCE_JSON_REF = "BBBBBBBBBB";

    private static final byte[] DEFAULT_SOURCE_JSON = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_SOURCE_JSON = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_SOURCE_JSON_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_SOURCE_JSON_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    @Autowired
    private AccountsRepository accountsRepository;

    @Autowired
    private AccountsMapper accountsMapper;

    @Autowired
    private AccountsService accountsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAccountsMockMvc;

    private Accounts accounts;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Accounts createEntity(EntityManager em) {
        Accounts accounts = new Accounts()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .accountId(DEFAULT_ACCOUNT_ID)
            .tenantId(DEFAULT_TENANT_ID)
            .accessKey(DEFAULT_ACCESS_KEY)
            .secretKey(DEFAULT_SECRET_KEY)
            .region(DEFAULT_REGION)
            .bucket(DEFAULT_BUCKET)
            .email(DEFAULT_EMAIL)
            .password(DEFAULT_PASSWORD)
            .cloudType(DEFAULT_CLOUD_TYPE)
            .sourceJsonRef(DEFAULT_SOURCE_JSON_REF)
            .sourceJson(DEFAULT_SOURCE_JSON)
            .sourceJsonContentType(DEFAULT_SOURCE_JSON_CONTENT_TYPE)
            .status(DEFAULT_STATUS)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedOn(DEFAULT_UPDATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .createdBy(DEFAULT_CREATED_BY);
        return accounts;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Accounts createUpdatedEntity(EntityManager em) {
        Accounts accounts = new Accounts()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .accountId(UPDATED_ACCOUNT_ID)
            .tenantId(UPDATED_TENANT_ID)
            .accessKey(UPDATED_ACCESS_KEY)
            .secretKey(UPDATED_SECRET_KEY)
            .region(UPDATED_REGION)
            .bucket(UPDATED_BUCKET)
            .email(UPDATED_EMAIL)
            .password(UPDATED_PASSWORD)
            .cloudType(UPDATED_CLOUD_TYPE)
            .sourceJsonRef(UPDATED_SOURCE_JSON_REF)
            .sourceJson(UPDATED_SOURCE_JSON)
            .sourceJsonContentType(UPDATED_SOURCE_JSON_CONTENT_TYPE)
            .status(UPDATED_STATUS)
            .createdOn(UPDATED_CREATED_ON)
            .updatedOn(UPDATED_UPDATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .createdBy(UPDATED_CREATED_BY);
        return accounts;
    }

    @BeforeEach
    public void initTest() {
        accounts = createEntity(em);
    }

    @Test
    @Transactional
    public void createAccounts() throws Exception {
        int databaseSizeBeforeCreate = accountsRepository.findAll().size();
        // Create the Accounts
        AccountsDTO accountsDTO = accountsMapper.toDto(accounts);
        restAccountsMockMvc.perform(post("/api/accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(accountsDTO)))
            .andExpect(status().isCreated());

        // Validate the Accounts in the database
        List<Accounts> accountsList = accountsRepository.findAll();
        assertThat(accountsList).hasSize(databaseSizeBeforeCreate + 1);
        Accounts testAccounts = accountsList.get(accountsList.size() - 1);
        assertThat(testAccounts.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAccounts.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAccounts.getAccountId()).isEqualTo(DEFAULT_ACCOUNT_ID);
        assertThat(testAccounts.getTenantId()).isEqualTo(DEFAULT_TENANT_ID);
        assertThat(testAccounts.getAccessKey()).isEqualTo(DEFAULT_ACCESS_KEY);
        assertThat(testAccounts.getSecretKey()).isEqualTo(DEFAULT_SECRET_KEY);
        assertThat(testAccounts.getRegion()).isEqualTo(DEFAULT_REGION);
        assertThat(testAccounts.getBucket()).isEqualTo(DEFAULT_BUCKET);
        assertThat(testAccounts.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testAccounts.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testAccounts.getCloudType()).isEqualTo(DEFAULT_CLOUD_TYPE);
        assertThat(testAccounts.getSourceJsonRef()).isEqualTo(DEFAULT_SOURCE_JSON_REF);
        assertThat(testAccounts.getSourceJson()).isEqualTo(DEFAULT_SOURCE_JSON);
        assertThat(testAccounts.getSourceJsonContentType()).isEqualTo(DEFAULT_SOURCE_JSON_CONTENT_TYPE);
        assertThat(testAccounts.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testAccounts.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testAccounts.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
        assertThat(testAccounts.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testAccounts.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
    }

    @Test
    @Transactional
    public void createAccountsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = accountsRepository.findAll().size();

        // Create the Accounts with an existing ID
        accounts.setId(1L);
        AccountsDTO accountsDTO = accountsMapper.toDto(accounts);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAccountsMockMvc.perform(post("/api/accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(accountsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Accounts in the database
        List<Accounts> accountsList = accountsRepository.findAll();
        assertThat(accountsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAccounts() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList
        restAccountsMockMvc.perform(get("/api/accounts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(accounts.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].accountId").value(hasItem(DEFAULT_ACCOUNT_ID)))
            .andExpect(jsonPath("$.[*].tenantId").value(hasItem(DEFAULT_TENANT_ID)))
            .andExpect(jsonPath("$.[*].accessKey").value(hasItem(DEFAULT_ACCESS_KEY)))
            .andExpect(jsonPath("$.[*].secretKey").value(hasItem(DEFAULT_SECRET_KEY)))
            .andExpect(jsonPath("$.[*].region").value(hasItem(DEFAULT_REGION)))
            .andExpect(jsonPath("$.[*].bucket").value(hasItem(DEFAULT_BUCKET)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)))
            .andExpect(jsonPath("$.[*].cloudType").value(hasItem(DEFAULT_CLOUD_TYPE)))
            .andExpect(jsonPath("$.[*].sourceJsonRef").value(hasItem(DEFAULT_SOURCE_JSON_REF)))
            .andExpect(jsonPath("$.[*].sourceJsonContentType").value(hasItem(DEFAULT_SOURCE_JSON_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].sourceJson").value(hasItem(Base64Utils.encodeToString(DEFAULT_SOURCE_JSON))))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)));
    }
    
    @Test
    @Transactional
    public void getAccounts() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get the accounts
        restAccountsMockMvc.perform(get("/api/accounts/{id}", accounts.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(accounts.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.accountId").value(DEFAULT_ACCOUNT_ID))
            .andExpect(jsonPath("$.tenantId").value(DEFAULT_TENANT_ID))
            .andExpect(jsonPath("$.accessKey").value(DEFAULT_ACCESS_KEY))
            .andExpect(jsonPath("$.secretKey").value(DEFAULT_SECRET_KEY))
            .andExpect(jsonPath("$.region").value(DEFAULT_REGION))
            .andExpect(jsonPath("$.bucket").value(DEFAULT_BUCKET))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD))
            .andExpect(jsonPath("$.cloudType").value(DEFAULT_CLOUD_TYPE))
            .andExpect(jsonPath("$.sourceJsonRef").value(DEFAULT_SOURCE_JSON_REF))
            .andExpect(jsonPath("$.sourceJsonContentType").value(DEFAULT_SOURCE_JSON_CONTENT_TYPE))
            .andExpect(jsonPath("$.sourceJson").value(Base64Utils.encodeToString(DEFAULT_SOURCE_JSON)))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY));
    }
    @Test
    @Transactional
    public void getNonExistingAccounts() throws Exception {
        // Get the accounts
        restAccountsMockMvc.perform(get("/api/accounts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAccounts() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        int databaseSizeBeforeUpdate = accountsRepository.findAll().size();

        // Update the accounts
        Accounts updatedAccounts = accountsRepository.findById(accounts.getId()).get();
        // Disconnect from session so that the updates on updatedAccounts are not directly saved in db
        em.detach(updatedAccounts);
        updatedAccounts
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .accountId(UPDATED_ACCOUNT_ID)
            .tenantId(UPDATED_TENANT_ID)
            .accessKey(UPDATED_ACCESS_KEY)
            .secretKey(UPDATED_SECRET_KEY)
            .region(UPDATED_REGION)
            .bucket(UPDATED_BUCKET)
            .email(UPDATED_EMAIL)
            .password(UPDATED_PASSWORD)
            .cloudType(UPDATED_CLOUD_TYPE)
            .sourceJsonRef(UPDATED_SOURCE_JSON_REF)
            .sourceJson(UPDATED_SOURCE_JSON)
            .sourceJsonContentType(UPDATED_SOURCE_JSON_CONTENT_TYPE)
            .status(UPDATED_STATUS)
            .createdOn(UPDATED_CREATED_ON)
            .updatedOn(UPDATED_UPDATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .createdBy(UPDATED_CREATED_BY);
        AccountsDTO accountsDTO = accountsMapper.toDto(updatedAccounts);

        restAccountsMockMvc.perform(put("/api/accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(accountsDTO)))
            .andExpect(status().isOk());

        // Validate the Accounts in the database
        List<Accounts> accountsList = accountsRepository.findAll();
        assertThat(accountsList).hasSize(databaseSizeBeforeUpdate);
        Accounts testAccounts = accountsList.get(accountsList.size() - 1);
        assertThat(testAccounts.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAccounts.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAccounts.getAccountId()).isEqualTo(UPDATED_ACCOUNT_ID);
        assertThat(testAccounts.getTenantId()).isEqualTo(UPDATED_TENANT_ID);
        assertThat(testAccounts.getAccessKey()).isEqualTo(UPDATED_ACCESS_KEY);
        assertThat(testAccounts.getSecretKey()).isEqualTo(UPDATED_SECRET_KEY);
        assertThat(testAccounts.getRegion()).isEqualTo(UPDATED_REGION);
        assertThat(testAccounts.getBucket()).isEqualTo(UPDATED_BUCKET);
        assertThat(testAccounts.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testAccounts.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testAccounts.getCloudType()).isEqualTo(UPDATED_CLOUD_TYPE);
        assertThat(testAccounts.getSourceJsonRef()).isEqualTo(UPDATED_SOURCE_JSON_REF);
        assertThat(testAccounts.getSourceJson()).isEqualTo(UPDATED_SOURCE_JSON);
        assertThat(testAccounts.getSourceJsonContentType()).isEqualTo(UPDATED_SOURCE_JSON_CONTENT_TYPE);
        assertThat(testAccounts.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testAccounts.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testAccounts.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testAccounts.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testAccounts.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingAccounts() throws Exception {
        int databaseSizeBeforeUpdate = accountsRepository.findAll().size();

        // Create the Accounts
        AccountsDTO accountsDTO = accountsMapper.toDto(accounts);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAccountsMockMvc.perform(put("/api/accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(accountsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Accounts in the database
        List<Accounts> accountsList = accountsRepository.findAll();
        assertThat(accountsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAccounts() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        int databaseSizeBeforeDelete = accountsRepository.findAll().size();

        // Delete the accounts
        restAccountsMockMvc.perform(delete("/api/accounts/{id}", accounts.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Accounts> accountsList = accountsRepository.findAll();
        assertThat(accountsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
