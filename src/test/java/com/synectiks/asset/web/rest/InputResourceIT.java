package com.synectiks.asset.web.rest;

import com.synectiks.asset.AssetserviceApp;
import com.synectiks.asset.domain.Input;
import com.synectiks.asset.repository.InputRepository;
import com.synectiks.asset.service.InputService;
import com.synectiks.asset.service.dto.InputDTO;
import com.synectiks.asset.service.mapper.InputMapper;

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
 * Integration tests for the {@link InputResource} REST controller.
 */
@SpringBootTest(classes = AssetserviceApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class InputResourceIT {

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

    private static final String DEFAULT_SOURCE = "AAAAAAAAAA";
    private static final String UPDATED_SOURCE = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final String DEFAULT_DATABASE = "AAAAAAAAAA";
    private static final String UPDATED_DATABASE = "BBBBBBBBBB";

    private static final byte[] DEFAULT_INPUT_DATA = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_INPUT_DATA = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_INPUT_DATA_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_INPUT_DATA_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_ACCESS = "AAAAAAAAAA";
    private static final String UPDATED_ACCESS = "BBBBBBBBBB";

    private static final String DEFAULT_BASIC_AUTH = "AAAAAAAAAA";
    private static final String UPDATED_BASIC_AUTH = "BBBBBBBBBB";

    private static final String DEFAULT_BASIC_AUTH_USER = "AAAAAAAAAA";
    private static final String UPDATED_BASIC_AUTH_USER = "BBBBBBBBBB";

    private static final String DEFAULT_BASIC_AUTH_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_BASIC_AUTH_PASSWORD = "BBBBBBBBBB";

    private static final String DEFAULT_WITH_CREDENTIALS = "AAAAAAAAAA";
    private static final String UPDATED_WITH_CREDENTIALS = "BBBBBBBBBB";

    private static final String DEFAULT_SECURE_INPUT_DATA = "AAAAAAAAAA";
    private static final String UPDATED_SECURE_INPUT_DATA = "BBBBBBBBBB";

    @Autowired
    private InputRepository inputRepository;

    @Autowired
    private InputMapper inputMapper;

    @Autowired
    private InputService inputService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInputMockMvc;

    private Input input;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Input createEntity(EntityManager em) {
        Input input = new Input()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedOn(DEFAULT_UPDATED_ON)
            .status(DEFAULT_STATUS)
            .source(DEFAULT_SOURCE)
            .type(DEFAULT_TYPE)
            .url(DEFAULT_URL)
            .userId(DEFAULT_USER_ID)
            .password(DEFAULT_PASSWORD)
            .database(DEFAULT_DATABASE)
            .inputData(DEFAULT_INPUT_DATA)
            .inputDataContentType(DEFAULT_INPUT_DATA_CONTENT_TYPE)
            .access(DEFAULT_ACCESS)
            .basicAuth(DEFAULT_BASIC_AUTH)
            .basicAuthUser(DEFAULT_BASIC_AUTH_USER)
            .basicAuthPassword(DEFAULT_BASIC_AUTH_PASSWORD)
            .withCredentials(DEFAULT_WITH_CREDENTIALS)
            .secureInputData(DEFAULT_SECURE_INPUT_DATA);
        return input;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Input createUpdatedEntity(EntityManager em) {
        Input input = new Input()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .createdOn(UPDATED_CREATED_ON)
            .updatedOn(UPDATED_UPDATED_ON)
            .status(UPDATED_STATUS)
            .source(UPDATED_SOURCE)
            .type(UPDATED_TYPE)
            .url(UPDATED_URL)
            .userId(UPDATED_USER_ID)
            .password(UPDATED_PASSWORD)
            .database(UPDATED_DATABASE)
            .inputData(UPDATED_INPUT_DATA)
            .inputDataContentType(UPDATED_INPUT_DATA_CONTENT_TYPE)
            .access(UPDATED_ACCESS)
            .basicAuth(UPDATED_BASIC_AUTH)
            .basicAuthUser(UPDATED_BASIC_AUTH_USER)
            .basicAuthPassword(UPDATED_BASIC_AUTH_PASSWORD)
            .withCredentials(UPDATED_WITH_CREDENTIALS)
            .secureInputData(UPDATED_SECURE_INPUT_DATA);
        return input;
    }

    @BeforeEach
    public void initTest() {
        input = createEntity(em);
    }

    @Test
    @Transactional
    public void createInput() throws Exception {
        int databaseSizeBeforeCreate = inputRepository.findAll().size();
        // Create the Input
        InputDTO inputDTO = inputMapper.toDto(input);
        restInputMockMvc.perform(post("/api/inputs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(inputDTO)))
            .andExpect(status().isCreated());

        // Validate the Input in the database
        List<Input> inputList = inputRepository.findAll();
        assertThat(inputList).hasSize(databaseSizeBeforeCreate + 1);
        Input testInput = inputList.get(inputList.size() - 1);
        assertThat(testInput.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testInput.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testInput.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testInput.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
        assertThat(testInput.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testInput.getSource()).isEqualTo(DEFAULT_SOURCE);
        assertThat(testInput.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testInput.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testInput.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testInput.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testInput.getDatabase()).isEqualTo(DEFAULT_DATABASE);
        assertThat(testInput.getInputData()).isEqualTo(DEFAULT_INPUT_DATA);
        assertThat(testInput.getInputDataContentType()).isEqualTo(DEFAULT_INPUT_DATA_CONTENT_TYPE);
        assertThat(testInput.getAccess()).isEqualTo(DEFAULT_ACCESS);
        assertThat(testInput.getBasicAuth()).isEqualTo(DEFAULT_BASIC_AUTH);
        assertThat(testInput.getBasicAuthUser()).isEqualTo(DEFAULT_BASIC_AUTH_USER);
        assertThat(testInput.getBasicAuthPassword()).isEqualTo(DEFAULT_BASIC_AUTH_PASSWORD);
        assertThat(testInput.getWithCredentials()).isEqualTo(DEFAULT_WITH_CREDENTIALS);
        assertThat(testInput.getSecureInputData()).isEqualTo(DEFAULT_SECURE_INPUT_DATA);
    }

    @Test
    @Transactional
    public void createInputWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = inputRepository.findAll().size();

        // Create the Input with an existing ID
        input.setId(1L);
        InputDTO inputDTO = inputMapper.toDto(input);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInputMockMvc.perform(post("/api/inputs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(inputDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Input in the database
        List<Input> inputList = inputRepository.findAll();
        assertThat(inputList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllInputs() throws Exception {
        // Initialize the database
        inputRepository.saveAndFlush(input);

        // Get all the inputList
        restInputMockMvc.perform(get("/api/inputs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(input.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL)))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)))
            .andExpect(jsonPath("$.[*].database").value(hasItem(DEFAULT_DATABASE)))
            .andExpect(jsonPath("$.[*].inputDataContentType").value(hasItem(DEFAULT_INPUT_DATA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].inputData").value(hasItem(Base64Utils.encodeToString(DEFAULT_INPUT_DATA))))
            .andExpect(jsonPath("$.[*].access").value(hasItem(DEFAULT_ACCESS)))
            .andExpect(jsonPath("$.[*].basicAuth").value(hasItem(DEFAULT_BASIC_AUTH)))
            .andExpect(jsonPath("$.[*].basicAuthUser").value(hasItem(DEFAULT_BASIC_AUTH_USER)))
            .andExpect(jsonPath("$.[*].basicAuthPassword").value(hasItem(DEFAULT_BASIC_AUTH_PASSWORD)))
            .andExpect(jsonPath("$.[*].withCredentials").value(hasItem(DEFAULT_WITH_CREDENTIALS)))
            .andExpect(jsonPath("$.[*].secureInputData").value(hasItem(DEFAULT_SECURE_INPUT_DATA)));
    }
    
    @Test
    @Transactional
    public void getInput() throws Exception {
        // Initialize the database
        inputRepository.saveAndFlush(input);

        // Get the input
        restInputMockMvc.perform(get("/api/inputs/{id}", input.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(input.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.source").value(DEFAULT_SOURCE))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD))
            .andExpect(jsonPath("$.database").value(DEFAULT_DATABASE))
            .andExpect(jsonPath("$.inputDataContentType").value(DEFAULT_INPUT_DATA_CONTENT_TYPE))
            .andExpect(jsonPath("$.inputData").value(Base64Utils.encodeToString(DEFAULT_INPUT_DATA)))
            .andExpect(jsonPath("$.access").value(DEFAULT_ACCESS))
            .andExpect(jsonPath("$.basicAuth").value(DEFAULT_BASIC_AUTH))
            .andExpect(jsonPath("$.basicAuthUser").value(DEFAULT_BASIC_AUTH_USER))
            .andExpect(jsonPath("$.basicAuthPassword").value(DEFAULT_BASIC_AUTH_PASSWORD))
            .andExpect(jsonPath("$.withCredentials").value(DEFAULT_WITH_CREDENTIALS))
            .andExpect(jsonPath("$.secureInputData").value(DEFAULT_SECURE_INPUT_DATA));
    }
    @Test
    @Transactional
    public void getNonExistingInput() throws Exception {
        // Get the input
        restInputMockMvc.perform(get("/api/inputs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInput() throws Exception {
        // Initialize the database
        inputRepository.saveAndFlush(input);

        int databaseSizeBeforeUpdate = inputRepository.findAll().size();

        // Update the input
        Input updatedInput = inputRepository.findById(input.getId()).get();
        // Disconnect from session so that the updates on updatedInput are not directly saved in db
        em.detach(updatedInput);
        updatedInput
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .createdOn(UPDATED_CREATED_ON)
            .updatedOn(UPDATED_UPDATED_ON)
            .status(UPDATED_STATUS)
            .source(UPDATED_SOURCE)
            .type(UPDATED_TYPE)
            .url(UPDATED_URL)
            .userId(UPDATED_USER_ID)
            .password(UPDATED_PASSWORD)
            .database(UPDATED_DATABASE)
            .inputData(UPDATED_INPUT_DATA)
            .inputDataContentType(UPDATED_INPUT_DATA_CONTENT_TYPE)
            .access(UPDATED_ACCESS)
            .basicAuth(UPDATED_BASIC_AUTH)
            .basicAuthUser(UPDATED_BASIC_AUTH_USER)
            .basicAuthPassword(UPDATED_BASIC_AUTH_PASSWORD)
            .withCredentials(UPDATED_WITH_CREDENTIALS)
            .secureInputData(UPDATED_SECURE_INPUT_DATA);
        InputDTO inputDTO = inputMapper.toDto(updatedInput);

        restInputMockMvc.perform(put("/api/inputs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(inputDTO)))
            .andExpect(status().isOk());

        // Validate the Input in the database
        List<Input> inputList = inputRepository.findAll();
        assertThat(inputList).hasSize(databaseSizeBeforeUpdate);
        Input testInput = inputList.get(inputList.size() - 1);
        assertThat(testInput.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testInput.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testInput.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testInput.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testInput.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testInput.getSource()).isEqualTo(UPDATED_SOURCE);
        assertThat(testInput.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testInput.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testInput.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testInput.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testInput.getDatabase()).isEqualTo(UPDATED_DATABASE);
        assertThat(testInput.getInputData()).isEqualTo(UPDATED_INPUT_DATA);
        assertThat(testInput.getInputDataContentType()).isEqualTo(UPDATED_INPUT_DATA_CONTENT_TYPE);
        assertThat(testInput.getAccess()).isEqualTo(UPDATED_ACCESS);
        assertThat(testInput.getBasicAuth()).isEqualTo(UPDATED_BASIC_AUTH);
        assertThat(testInput.getBasicAuthUser()).isEqualTo(UPDATED_BASIC_AUTH_USER);
        assertThat(testInput.getBasicAuthPassword()).isEqualTo(UPDATED_BASIC_AUTH_PASSWORD);
        assertThat(testInput.getWithCredentials()).isEqualTo(UPDATED_WITH_CREDENTIALS);
        assertThat(testInput.getSecureInputData()).isEqualTo(UPDATED_SECURE_INPUT_DATA);
    }

    @Test
    @Transactional
    public void updateNonExistingInput() throws Exception {
        int databaseSizeBeforeUpdate = inputRepository.findAll().size();

        // Create the Input
        InputDTO inputDTO = inputMapper.toDto(input);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInputMockMvc.perform(put("/api/inputs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(inputDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Input in the database
        List<Input> inputList = inputRepository.findAll();
        assertThat(inputList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInput() throws Exception {
        // Initialize the database
        inputRepository.saveAndFlush(input);

        int databaseSizeBeforeDelete = inputRepository.findAll().size();

        // Delete the input
        restInputMockMvc.perform(delete("/api/inputs/{id}", input.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Input> inputList = inputRepository.findAll();
        assertThat(inputList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
