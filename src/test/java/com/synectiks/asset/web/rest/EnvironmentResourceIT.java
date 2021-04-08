package com.synectiks.asset.web.rest;

import com.synectiks.asset.AssetserviceApp;
import com.synectiks.asset.domain.Environment;
import com.synectiks.asset.repository.EnvironmentRepository;
import com.synectiks.asset.service.EnvironmentService;
import com.synectiks.asset.service.dto.EnvironmentDTO;
import com.synectiks.asset.service.mapper.EnvironmentMapper;

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
 * Integration tests for the {@link EnvironmentResource} REST controller.
 */
@SpringBootTest(classes = AssetserviceApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class EnvironmentResourceIT {

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

    private static final String DEFAULT_SCOPES = "AAAAAAAAAA";
    private static final String UPDATED_SCOPES = "BBBBBBBBBB";

    private static final String DEFAULT_AUTH_URL = "AAAAAAAAAA";
    private static final String UPDATED_AUTH_URL = "BBBBBBBBBB";

    private static final String DEFAULT_TOKEN_URL = "AAAAAAAAAA";
    private static final String UPDATED_TOKEN_URL = "BBBBBBBBBB";

    private static final String DEFAULT_API_URL = "AAAAAAAAAA";
    private static final String UPDATED_API_URL = "BBBBBBBBBB";

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final byte[] DEFAULT_JSON_DATA = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_JSON_DATA = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_JSON_DATA_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_JSON_DATA_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    @Autowired
    private EnvironmentRepository environmentRepository;

    @Autowired
    private EnvironmentMapper environmentMapper;

    @Autowired
    private EnvironmentService environmentService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEnvironmentMockMvc;

    private Environment environment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Environment createEntity(EntityManager em) {
        Environment environment = new Environment()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedOn(DEFAULT_UPDATED_ON)
            .status(DEFAULT_STATUS)
            .scopes(DEFAULT_SCOPES)
            .authUrl(DEFAULT_AUTH_URL)
            .tokenUrl(DEFAULT_TOKEN_URL)
            .apiUrl(DEFAULT_API_URL)
            .updatedBy(DEFAULT_UPDATED_BY)
            .createdBy(DEFAULT_CREATED_BY)
            .jsonData(DEFAULT_JSON_DATA)
            .jsonDataContentType(DEFAULT_JSON_DATA_CONTENT_TYPE)
            .type(DEFAULT_TYPE);
        return environment;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Environment createUpdatedEntity(EntityManager em) {
        Environment environment = new Environment()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .createdOn(UPDATED_CREATED_ON)
            .updatedOn(UPDATED_UPDATED_ON)
            .status(UPDATED_STATUS)
            .scopes(UPDATED_SCOPES)
            .authUrl(UPDATED_AUTH_URL)
            .tokenUrl(UPDATED_TOKEN_URL)
            .apiUrl(UPDATED_API_URL)
            .updatedBy(UPDATED_UPDATED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .jsonData(UPDATED_JSON_DATA)
            .jsonDataContentType(UPDATED_JSON_DATA_CONTENT_TYPE)
            .type(UPDATED_TYPE);
        return environment;
    }

    @BeforeEach
    public void initTest() {
        environment = createEntity(em);
    }

    @Test
    @Transactional
    public void createEnvironment() throws Exception {
        int databaseSizeBeforeCreate = environmentRepository.findAll().size();
        // Create the Environment
        EnvironmentDTO environmentDTO = environmentMapper.toDto(environment);
        restEnvironmentMockMvc.perform(post("/api/environments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(environmentDTO)))
            .andExpect(status().isCreated());

        // Validate the Environment in the database
        List<Environment> environmentList = environmentRepository.findAll();
        assertThat(environmentList).hasSize(databaseSizeBeforeCreate + 1);
        Environment testEnvironment = environmentList.get(environmentList.size() - 1);
        assertThat(testEnvironment.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testEnvironment.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testEnvironment.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testEnvironment.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
        assertThat(testEnvironment.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testEnvironment.getScopes()).isEqualTo(DEFAULT_SCOPES);
        assertThat(testEnvironment.getAuthUrl()).isEqualTo(DEFAULT_AUTH_URL);
        assertThat(testEnvironment.getTokenUrl()).isEqualTo(DEFAULT_TOKEN_URL);
        assertThat(testEnvironment.getApiUrl()).isEqualTo(DEFAULT_API_URL);
        assertThat(testEnvironment.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testEnvironment.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testEnvironment.getJsonData()).isEqualTo(DEFAULT_JSON_DATA);
        assertThat(testEnvironment.getJsonDataContentType()).isEqualTo(DEFAULT_JSON_DATA_CONTENT_TYPE);
        assertThat(testEnvironment.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createEnvironmentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = environmentRepository.findAll().size();

        // Create the Environment with an existing ID
        environment.setId(1L);
        EnvironmentDTO environmentDTO = environmentMapper.toDto(environment);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEnvironmentMockMvc.perform(post("/api/environments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(environmentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Environment in the database
        List<Environment> environmentList = environmentRepository.findAll();
        assertThat(environmentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEnvironments() throws Exception {
        // Initialize the database
        environmentRepository.saveAndFlush(environment);

        // Get all the environmentList
        restEnvironmentMockMvc.perform(get("/api/environments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(environment.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].scopes").value(hasItem(DEFAULT_SCOPES)))
            .andExpect(jsonPath("$.[*].authUrl").value(hasItem(DEFAULT_AUTH_URL)))
            .andExpect(jsonPath("$.[*].tokenUrl").value(hasItem(DEFAULT_TOKEN_URL)))
            .andExpect(jsonPath("$.[*].apiUrl").value(hasItem(DEFAULT_API_URL)))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].jsonDataContentType").value(hasItem(DEFAULT_JSON_DATA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].jsonData").value(hasItem(Base64Utils.encodeToString(DEFAULT_JSON_DATA))))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)));
    }
    
    @Test
    @Transactional
    public void getEnvironment() throws Exception {
        // Initialize the database
        environmentRepository.saveAndFlush(environment);

        // Get the environment
        restEnvironmentMockMvc.perform(get("/api/environments/{id}", environment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(environment.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.scopes").value(DEFAULT_SCOPES))
            .andExpect(jsonPath("$.authUrl").value(DEFAULT_AUTH_URL))
            .andExpect(jsonPath("$.tokenUrl").value(DEFAULT_TOKEN_URL))
            .andExpect(jsonPath("$.apiUrl").value(DEFAULT_API_URL))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.jsonDataContentType").value(DEFAULT_JSON_DATA_CONTENT_TYPE))
            .andExpect(jsonPath("$.jsonData").value(Base64Utils.encodeToString(DEFAULT_JSON_DATA)))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE));
    }
    @Test
    @Transactional
    public void getNonExistingEnvironment() throws Exception {
        // Get the environment
        restEnvironmentMockMvc.perform(get("/api/environments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEnvironment() throws Exception {
        // Initialize the database
        environmentRepository.saveAndFlush(environment);

        int databaseSizeBeforeUpdate = environmentRepository.findAll().size();

        // Update the environment
        Environment updatedEnvironment = environmentRepository.findById(environment.getId()).get();
        // Disconnect from session so that the updates on updatedEnvironment are not directly saved in db
        em.detach(updatedEnvironment);
        updatedEnvironment
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .createdOn(UPDATED_CREATED_ON)
            .updatedOn(UPDATED_UPDATED_ON)
            .status(UPDATED_STATUS)
            .scopes(UPDATED_SCOPES)
            .authUrl(UPDATED_AUTH_URL)
            .tokenUrl(UPDATED_TOKEN_URL)
            .apiUrl(UPDATED_API_URL)
            .updatedBy(UPDATED_UPDATED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .jsonData(UPDATED_JSON_DATA)
            .jsonDataContentType(UPDATED_JSON_DATA_CONTENT_TYPE)
            .type(UPDATED_TYPE);
        EnvironmentDTO environmentDTO = environmentMapper.toDto(updatedEnvironment);

        restEnvironmentMockMvc.perform(put("/api/environments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(environmentDTO)))
            .andExpect(status().isOk());

        // Validate the Environment in the database
        List<Environment> environmentList = environmentRepository.findAll();
        assertThat(environmentList).hasSize(databaseSizeBeforeUpdate);
        Environment testEnvironment = environmentList.get(environmentList.size() - 1);
        assertThat(testEnvironment.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEnvironment.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testEnvironment.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testEnvironment.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testEnvironment.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testEnvironment.getScopes()).isEqualTo(UPDATED_SCOPES);
        assertThat(testEnvironment.getAuthUrl()).isEqualTo(UPDATED_AUTH_URL);
        assertThat(testEnvironment.getTokenUrl()).isEqualTo(UPDATED_TOKEN_URL);
        assertThat(testEnvironment.getApiUrl()).isEqualTo(UPDATED_API_URL);
        assertThat(testEnvironment.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testEnvironment.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testEnvironment.getJsonData()).isEqualTo(UPDATED_JSON_DATA);
        assertThat(testEnvironment.getJsonDataContentType()).isEqualTo(UPDATED_JSON_DATA_CONTENT_TYPE);
        assertThat(testEnvironment.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingEnvironment() throws Exception {
        int databaseSizeBeforeUpdate = environmentRepository.findAll().size();

        // Create the Environment
        EnvironmentDTO environmentDTO = environmentMapper.toDto(environment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEnvironmentMockMvc.perform(put("/api/environments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(environmentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Environment in the database
        List<Environment> environmentList = environmentRepository.findAll();
        assertThat(environmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEnvironment() throws Exception {
        // Initialize the database
        environmentRepository.saveAndFlush(environment);

        int databaseSizeBeforeDelete = environmentRepository.findAll().size();

        // Delete the environment
        restEnvironmentMockMvc.perform(delete("/api/environments/{id}", environment.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Environment> environmentList = environmentRepository.findAll();
        assertThat(environmentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
