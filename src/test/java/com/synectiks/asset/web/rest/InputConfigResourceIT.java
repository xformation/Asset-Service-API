package com.synectiks.asset.web.rest;

import com.synectiks.asset.AssetserviceApp;
import com.synectiks.asset.domain.InputConfig;
import com.synectiks.asset.repository.InputConfigRepository;
import com.synectiks.asset.service.InputConfigService;
import com.synectiks.asset.service.dto.InputConfigDTO;
import com.synectiks.asset.service.mapper.InputConfigMapper;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link InputConfigResource} REST controller.
 */
@SpringBootTest(classes = AssetserviceApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class InputConfigResourceIT {

    private static final String DEFAULT_INPUT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_INPUT_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_TENANT_ID = "AAAAAAAAAA";
    private static final String UPDATED_TENANT_ID = "BBBBBBBBBB";

    private static final byte[] DEFAULT_VIEW_JSON = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_VIEW_JSON = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_VIEW_JSON_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_VIEW_JSON_CONTENT_TYPE = "image/png";

    @Autowired
    private InputConfigRepository inputConfigRepository;

    @Autowired
    private InputConfigMapper inputConfigMapper;

    @Autowired
    private InputConfigService inputConfigService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInputConfigMockMvc;

    private InputConfig inputConfig;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InputConfig createEntity(EntityManager em) {
        InputConfig inputConfig = new InputConfig()
            .inputType(DEFAULT_INPUT_TYPE)
            .status(DEFAULT_STATUS)
            .tenantId(DEFAULT_TENANT_ID)
            .viewJson(DEFAULT_VIEW_JSON)
            .viewJsonContentType(DEFAULT_VIEW_JSON_CONTENT_TYPE);
        return inputConfig;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InputConfig createUpdatedEntity(EntityManager em) {
        InputConfig inputConfig = new InputConfig()
            .inputType(UPDATED_INPUT_TYPE)
            .status(UPDATED_STATUS)
            .tenantId(UPDATED_TENANT_ID)
            .viewJson(UPDATED_VIEW_JSON)
            .viewJsonContentType(UPDATED_VIEW_JSON_CONTENT_TYPE);
        return inputConfig;
    }

    @BeforeEach
    public void initTest() {
        inputConfig = createEntity(em);
    }

    @Test
    @Transactional
    public void createInputConfig() throws Exception {
        int databaseSizeBeforeCreate = inputConfigRepository.findAll().size();
        // Create the InputConfig
        InputConfigDTO inputConfigDTO = inputConfigMapper.toDto(inputConfig);
        restInputConfigMockMvc.perform(post("/api/input-configs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(inputConfigDTO)))
            .andExpect(status().isCreated());

        // Validate the InputConfig in the database
        List<InputConfig> inputConfigList = inputConfigRepository.findAll();
        assertThat(inputConfigList).hasSize(databaseSizeBeforeCreate + 1);
        InputConfig testInputConfig = inputConfigList.get(inputConfigList.size() - 1);
        assertThat(testInputConfig.getInputType()).isEqualTo(DEFAULT_INPUT_TYPE);
        assertThat(testInputConfig.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testInputConfig.getTenantId()).isEqualTo(DEFAULT_TENANT_ID);
        assertThat(testInputConfig.getViewJson()).isEqualTo(DEFAULT_VIEW_JSON);
        assertThat(testInputConfig.getViewJsonContentType()).isEqualTo(DEFAULT_VIEW_JSON_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createInputConfigWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = inputConfigRepository.findAll().size();

        // Create the InputConfig with an existing ID
        inputConfig.setId(1L);
        InputConfigDTO inputConfigDTO = inputConfigMapper.toDto(inputConfig);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInputConfigMockMvc.perform(post("/api/input-configs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(inputConfigDTO)))
            .andExpect(status().isBadRequest());

        // Validate the InputConfig in the database
        List<InputConfig> inputConfigList = inputConfigRepository.findAll();
        assertThat(inputConfigList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllInputConfigs() throws Exception {
        // Initialize the database
        inputConfigRepository.saveAndFlush(inputConfig);

        // Get all the inputConfigList
        restInputConfigMockMvc.perform(get("/api/input-configs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inputConfig.getId().intValue())))
            .andExpect(jsonPath("$.[*].inputType").value(hasItem(DEFAULT_INPUT_TYPE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].tenantId").value(hasItem(DEFAULT_TENANT_ID)))
            .andExpect(jsonPath("$.[*].viewJsonContentType").value(hasItem(DEFAULT_VIEW_JSON_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].viewJson").value(hasItem(Base64Utils.encodeToString(DEFAULT_VIEW_JSON))));
    }
    
    @Test
    @Transactional
    public void getInputConfig() throws Exception {
        // Initialize the database
        inputConfigRepository.saveAndFlush(inputConfig);

        // Get the inputConfig
        restInputConfigMockMvc.perform(get("/api/input-configs/{id}", inputConfig.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(inputConfig.getId().intValue()))
            .andExpect(jsonPath("$.inputType").value(DEFAULT_INPUT_TYPE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.tenantId").value(DEFAULT_TENANT_ID))
            .andExpect(jsonPath("$.viewJsonContentType").value(DEFAULT_VIEW_JSON_CONTENT_TYPE))
            .andExpect(jsonPath("$.viewJson").value(Base64Utils.encodeToString(DEFAULT_VIEW_JSON)));
    }
    @Test
    @Transactional
    public void getNonExistingInputConfig() throws Exception {
        // Get the inputConfig
        restInputConfigMockMvc.perform(get("/api/input-configs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInputConfig() throws Exception {
        // Initialize the database
        inputConfigRepository.saveAndFlush(inputConfig);

        int databaseSizeBeforeUpdate = inputConfigRepository.findAll().size();

        // Update the inputConfig
        InputConfig updatedInputConfig = inputConfigRepository.findById(inputConfig.getId()).get();
        // Disconnect from session so that the updates on updatedInputConfig are not directly saved in db
        em.detach(updatedInputConfig);
        updatedInputConfig
            .inputType(UPDATED_INPUT_TYPE)
            .status(UPDATED_STATUS)
            .tenantId(UPDATED_TENANT_ID)
            .viewJson(UPDATED_VIEW_JSON)
            .viewJsonContentType(UPDATED_VIEW_JSON_CONTENT_TYPE);
        InputConfigDTO inputConfigDTO = inputConfigMapper.toDto(updatedInputConfig);

        restInputConfigMockMvc.perform(put("/api/input-configs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(inputConfigDTO)))
            .andExpect(status().isOk());

        // Validate the InputConfig in the database
        List<InputConfig> inputConfigList = inputConfigRepository.findAll();
        assertThat(inputConfigList).hasSize(databaseSizeBeforeUpdate);
        InputConfig testInputConfig = inputConfigList.get(inputConfigList.size() - 1);
        assertThat(testInputConfig.getInputType()).isEqualTo(UPDATED_INPUT_TYPE);
        assertThat(testInputConfig.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testInputConfig.getTenantId()).isEqualTo(UPDATED_TENANT_ID);
        assertThat(testInputConfig.getViewJson()).isEqualTo(UPDATED_VIEW_JSON);
        assertThat(testInputConfig.getViewJsonContentType()).isEqualTo(UPDATED_VIEW_JSON_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingInputConfig() throws Exception {
        int databaseSizeBeforeUpdate = inputConfigRepository.findAll().size();

        // Create the InputConfig
        InputConfigDTO inputConfigDTO = inputConfigMapper.toDto(inputConfig);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInputConfigMockMvc.perform(put("/api/input-configs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(inputConfigDTO)))
            .andExpect(status().isBadRequest());

        // Validate the InputConfig in the database
        List<InputConfig> inputConfigList = inputConfigRepository.findAll();
        assertThat(inputConfigList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInputConfig() throws Exception {
        // Initialize the database
        inputConfigRepository.saveAndFlush(inputConfig);

        int databaseSizeBeforeDelete = inputConfigRepository.findAll().size();

        // Delete the inputConfig
        restInputConfigMockMvc.perform(delete("/api/input-configs/{id}", inputConfig.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<InputConfig> inputConfigList = inputConfigRepository.findAll();
        assertThat(inputConfigList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
