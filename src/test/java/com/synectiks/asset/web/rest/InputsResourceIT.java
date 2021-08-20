package com.synectiks.asset.web.rest;

import com.synectiks.asset.AssetserviceApp;
import com.synectiks.asset.domain.Inputs;
import com.synectiks.asset.repository.InputsRepository;
import com.synectiks.asset.service.InputsService;
import com.synectiks.asset.service.dto.InputsDTO;
import com.synectiks.asset.service.mapper.InputsMapper;

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
 * Integration tests for the {@link InputsResource} REST controller.
 */
@SpringBootTest(classes = AssetserviceApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class InputsResourceIT {

    private static final String DEFAULT_ACCOUNT_ID = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_TENANT_ID = "AAAAAAAAAA";
    private static final String UPDATED_TENANT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_INPUT_SOURCE = "AAAAAAAAAA";
    private static final String UPDATED_INPUT_SOURCE = "BBBBBBBBBB";

    private static final String DEFAULT_INPUT_SOURCE_ID = "AAAAAAAAAA";
    private static final String UPDATED_INPUT_SOURCE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_REF_URL = "AAAAAAAAAA";
    private static final String UPDATED_REF_URL = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final byte[] DEFAULT_VIEW_JSON = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_VIEW_JSON = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_VIEW_JSON_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_VIEW_JSON_CONTENT_TYPE = "image/png";

    private static final Instant DEFAULT_CREATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    @Autowired
    private InputsRepository inputsRepository;

    @Autowired
    private InputsMapper inputsMapper;

    @Autowired
    private InputsService inputsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInputsMockMvc;

    private Inputs inputs;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Inputs createEntity(EntityManager em) {
        Inputs inputs = new Inputs()
            .accountId(DEFAULT_ACCOUNT_ID)
            .tenantId(DEFAULT_TENANT_ID)
            .inputSource(DEFAULT_INPUT_SOURCE)
            .inputSourceId(DEFAULT_INPUT_SOURCE_ID)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .status(DEFAULT_STATUS)
            .refUrl(DEFAULT_REF_URL)
            .type(DEFAULT_TYPE)
            .viewJson(DEFAULT_VIEW_JSON)
            .viewJsonContentType(DEFAULT_VIEW_JSON_CONTENT_TYPE)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedOn(DEFAULT_UPDATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .createdBy(DEFAULT_CREATED_BY);
        return inputs;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Inputs createUpdatedEntity(EntityManager em) {
        Inputs inputs = new Inputs()
            .accountId(UPDATED_ACCOUNT_ID)
            .tenantId(UPDATED_TENANT_ID)
            .inputSource(UPDATED_INPUT_SOURCE)
            .inputSourceId(UPDATED_INPUT_SOURCE_ID)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .status(UPDATED_STATUS)
            .refUrl(UPDATED_REF_URL)
            .type(UPDATED_TYPE)
            .viewJson(UPDATED_VIEW_JSON)
            .viewJsonContentType(UPDATED_VIEW_JSON_CONTENT_TYPE)
            .createdOn(UPDATED_CREATED_ON)
            .updatedOn(UPDATED_UPDATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .createdBy(UPDATED_CREATED_BY);
        return inputs;
    }

    @BeforeEach
    public void initTest() {
        inputs = createEntity(em);
    }

    @Test
    @Transactional
    public void createInputs() throws Exception {
        int databaseSizeBeforeCreate = inputsRepository.findAll().size();
        // Create the Inputs
        InputsDTO inputsDTO = inputsMapper.toDto(inputs);
        restInputsMockMvc.perform(post("/api/inputs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(inputsDTO)))
            .andExpect(status().isCreated());

        // Validate the Inputs in the database
        List<Inputs> inputsList = inputsRepository.findAll();
        assertThat(inputsList).hasSize(databaseSizeBeforeCreate + 1);
        Inputs testInputs = inputsList.get(inputsList.size() - 1);
        assertThat(testInputs.getAccountId()).isEqualTo(DEFAULT_ACCOUNT_ID);
        assertThat(testInputs.getTenantId()).isEqualTo(DEFAULT_TENANT_ID);
        assertThat(testInputs.getInputSource()).isEqualTo(DEFAULT_INPUT_SOURCE);
        assertThat(testInputs.getInputSourceId()).isEqualTo(DEFAULT_INPUT_SOURCE_ID);
        assertThat(testInputs.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testInputs.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testInputs.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testInputs.getRefUrl()).isEqualTo(DEFAULT_REF_URL);
        assertThat(testInputs.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testInputs.getViewJson()).isEqualTo(DEFAULT_VIEW_JSON);
        assertThat(testInputs.getViewJsonContentType()).isEqualTo(DEFAULT_VIEW_JSON_CONTENT_TYPE);
        assertThat(testInputs.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testInputs.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
        assertThat(testInputs.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testInputs.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
    }

    @Test
    @Transactional
    public void createInputsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = inputsRepository.findAll().size();

        // Create the Inputs with an existing ID
        inputs.setId(1L);
        InputsDTO inputsDTO = inputsMapper.toDto(inputs);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInputsMockMvc.perform(post("/api/inputs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(inputsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Inputs in the database
        List<Inputs> inputsList = inputsRepository.findAll();
        assertThat(inputsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllInputs() throws Exception {
        // Initialize the database
        inputsRepository.saveAndFlush(inputs);

        // Get all the inputsList
        restInputsMockMvc.perform(get("/api/inputs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inputs.getId().intValue())))
            .andExpect(jsonPath("$.[*].accountId").value(hasItem(DEFAULT_ACCOUNT_ID)))
            .andExpect(jsonPath("$.[*].tenantId").value(hasItem(DEFAULT_TENANT_ID)))
            .andExpect(jsonPath("$.[*].inputSource").value(hasItem(DEFAULT_INPUT_SOURCE)))
            .andExpect(jsonPath("$.[*].inputSourceId").value(hasItem(DEFAULT_INPUT_SOURCE_ID)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].refUrl").value(hasItem(DEFAULT_REF_URL)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].viewJsonContentType").value(hasItem(DEFAULT_VIEW_JSON_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].viewJson").value(hasItem(Base64Utils.encodeToString(DEFAULT_VIEW_JSON))))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)));
    }
    
    @Test
    @Transactional
    public void getInputs() throws Exception {
        // Initialize the database
        inputsRepository.saveAndFlush(inputs);

        // Get the inputs
        restInputsMockMvc.perform(get("/api/inputs/{id}", inputs.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(inputs.getId().intValue()))
            .andExpect(jsonPath("$.accountId").value(DEFAULT_ACCOUNT_ID))
            .andExpect(jsonPath("$.tenantId").value(DEFAULT_TENANT_ID))
            .andExpect(jsonPath("$.inputSource").value(DEFAULT_INPUT_SOURCE))
            .andExpect(jsonPath("$.inputSourceId").value(DEFAULT_INPUT_SOURCE_ID))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.refUrl").value(DEFAULT_REF_URL))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.viewJsonContentType").value(DEFAULT_VIEW_JSON_CONTENT_TYPE))
            .andExpect(jsonPath("$.viewJson").value(Base64Utils.encodeToString(DEFAULT_VIEW_JSON)))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY));
    }
    @Test
    @Transactional
    public void getNonExistingInputs() throws Exception {
        // Get the inputs
        restInputsMockMvc.perform(get("/api/inputs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInputs() throws Exception {
        // Initialize the database
        inputsRepository.saveAndFlush(inputs);

        int databaseSizeBeforeUpdate = inputsRepository.findAll().size();

        // Update the inputs
        Inputs updatedInputs = inputsRepository.findById(inputs.getId()).get();
        // Disconnect from session so that the updates on updatedInputs are not directly saved in db
        em.detach(updatedInputs);
        updatedInputs
            .accountId(UPDATED_ACCOUNT_ID)
            .tenantId(UPDATED_TENANT_ID)
            .inputSource(UPDATED_INPUT_SOURCE)
            .inputSourceId(UPDATED_INPUT_SOURCE_ID)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .status(UPDATED_STATUS)
            .refUrl(UPDATED_REF_URL)
            .type(UPDATED_TYPE)
            .viewJson(UPDATED_VIEW_JSON)
            .viewJsonContentType(UPDATED_VIEW_JSON_CONTENT_TYPE)
            .createdOn(UPDATED_CREATED_ON)
            .updatedOn(UPDATED_UPDATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .createdBy(UPDATED_CREATED_BY);
        InputsDTO inputsDTO = inputsMapper.toDto(updatedInputs);

        restInputsMockMvc.perform(put("/api/inputs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(inputsDTO)))
            .andExpect(status().isOk());

        // Validate the Inputs in the database
        List<Inputs> inputsList = inputsRepository.findAll();
        assertThat(inputsList).hasSize(databaseSizeBeforeUpdate);
        Inputs testInputs = inputsList.get(inputsList.size() - 1);
        assertThat(testInputs.getAccountId()).isEqualTo(UPDATED_ACCOUNT_ID);
        assertThat(testInputs.getTenantId()).isEqualTo(UPDATED_TENANT_ID);
        assertThat(testInputs.getInputSource()).isEqualTo(UPDATED_INPUT_SOURCE);
        assertThat(testInputs.getInputSourceId()).isEqualTo(UPDATED_INPUT_SOURCE_ID);
        assertThat(testInputs.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testInputs.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testInputs.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testInputs.getRefUrl()).isEqualTo(UPDATED_REF_URL);
        assertThat(testInputs.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testInputs.getViewJson()).isEqualTo(UPDATED_VIEW_JSON);
        assertThat(testInputs.getViewJsonContentType()).isEqualTo(UPDATED_VIEW_JSON_CONTENT_TYPE);
        assertThat(testInputs.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testInputs.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testInputs.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testInputs.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingInputs() throws Exception {
        int databaseSizeBeforeUpdate = inputsRepository.findAll().size();

        // Create the Inputs
        InputsDTO inputsDTO = inputsMapper.toDto(inputs);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInputsMockMvc.perform(put("/api/inputs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(inputsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Inputs in the database
        List<Inputs> inputsList = inputsRepository.findAll();
        assertThat(inputsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInputs() throws Exception {
        // Initialize the database
        inputsRepository.saveAndFlush(inputs);

        int databaseSizeBeforeDelete = inputsRepository.findAll().size();

        // Delete the inputs
        restInputsMockMvc.perform(delete("/api/inputs/{id}", inputs.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Inputs> inputsList = inputsRepository.findAll();
        assertThat(inputsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
