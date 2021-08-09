package com.synectiks.asset.web.rest;

import com.synectiks.asset.AssetserviceApp;
import com.synectiks.asset.domain.ApplicationAssets;
import com.synectiks.asset.repository.ApplicationAssetsRepository;
import com.synectiks.asset.service.ApplicationAssetsService;
import com.synectiks.asset.service.dto.ApplicationAssetsDTO;
import com.synectiks.asset.service.mapper.ApplicationAssetsMapper;

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
 * Integration tests for the {@link ApplicationAssetsResource} REST controller.
 */
@SpringBootTest(classes = AssetserviceApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ApplicationAssetsResourceIT {

    private static final String DEFAULT_TENANT_ID = "AAAAAAAAAA";
    private static final String UPDATED_TENANT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_DASHBOARD_UUID = "AAAAAAAAAA";
    private static final String UPDATED_DASHBOARD_UUID = "BBBBBBBBBB";

    private static final String DEFAULT_FILE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FILE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CLOUD_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_CLOUD_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_ELEMENT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_ELEMENT_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_ELEMENT_SUB_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_ELEMENT_SUB_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_INPUT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_INPUT_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_DASHBOARD_NATURE = "AAAAAAAAAA";
    private static final String UPDATED_DASHBOARD_NATURE = "BBBBBBBBBB";

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
    private ApplicationAssetsRepository applicationAssetsRepository;

    @Autowired
    private ApplicationAssetsMapper applicationAssetsMapper;

    @Autowired
    private ApplicationAssetsService applicationAssetsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restApplicationAssetsMockMvc;

    private ApplicationAssets applicationAssets;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApplicationAssets createEntity(EntityManager em) {
        ApplicationAssets applicationAssets = new ApplicationAssets()
            .tenantId(DEFAULT_TENANT_ID)
            .dashboardUuid(DEFAULT_DASHBOARD_UUID)
            .fileName(DEFAULT_FILE_NAME)
            .cloudType(DEFAULT_CLOUD_TYPE)
            .elementType(DEFAULT_ELEMENT_TYPE)
            .elementSubType(DEFAULT_ELEMENT_SUB_TYPE)
            .inputType(DEFAULT_INPUT_TYPE)
            .dashboardNature(DEFAULT_DASHBOARD_NATURE)
            .status(DEFAULT_STATUS)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedOn(DEFAULT_UPDATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .createdBy(DEFAULT_CREATED_BY);
        return applicationAssets;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApplicationAssets createUpdatedEntity(EntityManager em) {
        ApplicationAssets applicationAssets = new ApplicationAssets()
            .tenantId(UPDATED_TENANT_ID)
            .dashboardUuid(UPDATED_DASHBOARD_UUID)
            .fileName(UPDATED_FILE_NAME)
            .cloudType(UPDATED_CLOUD_TYPE)
            .elementType(UPDATED_ELEMENT_TYPE)
            .elementSubType(UPDATED_ELEMENT_SUB_TYPE)
            .inputType(UPDATED_INPUT_TYPE)
            .dashboardNature(UPDATED_DASHBOARD_NATURE)
            .status(UPDATED_STATUS)
            .createdOn(UPDATED_CREATED_ON)
            .updatedOn(UPDATED_UPDATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .createdBy(UPDATED_CREATED_BY);
        return applicationAssets;
    }

    @BeforeEach
    public void initTest() {
        applicationAssets = createEntity(em);
    }

    @Test
    @Transactional
    public void createApplicationAssets() throws Exception {
        int databaseSizeBeforeCreate = applicationAssetsRepository.findAll().size();
        // Create the ApplicationAssets
        ApplicationAssetsDTO applicationAssetsDTO = applicationAssetsMapper.toDto(applicationAssets);
        restApplicationAssetsMockMvc.perform(post("/api/application-assets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicationAssetsDTO)))
            .andExpect(status().isCreated());

        // Validate the ApplicationAssets in the database
        List<ApplicationAssets> applicationAssetsList = applicationAssetsRepository.findAll();
        assertThat(applicationAssetsList).hasSize(databaseSizeBeforeCreate + 1);
        ApplicationAssets testApplicationAssets = applicationAssetsList.get(applicationAssetsList.size() - 1);
        assertThat(testApplicationAssets.getTenantId()).isEqualTo(DEFAULT_TENANT_ID);
        assertThat(testApplicationAssets.getDashboardUuid()).isEqualTo(DEFAULT_DASHBOARD_UUID);
        assertThat(testApplicationAssets.getFileName()).isEqualTo(DEFAULT_FILE_NAME);
        assertThat(testApplicationAssets.getCloudType()).isEqualTo(DEFAULT_CLOUD_TYPE);
        assertThat(testApplicationAssets.getElementType()).isEqualTo(DEFAULT_ELEMENT_TYPE);
        assertThat(testApplicationAssets.getElementSubType()).isEqualTo(DEFAULT_ELEMENT_SUB_TYPE);
        assertThat(testApplicationAssets.getInputType()).isEqualTo(DEFAULT_INPUT_TYPE);
        assertThat(testApplicationAssets.getDashboardNature()).isEqualTo(DEFAULT_DASHBOARD_NATURE);
        assertThat(testApplicationAssets.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testApplicationAssets.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testApplicationAssets.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
        assertThat(testApplicationAssets.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testApplicationAssets.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
    }

    @Test
    @Transactional
    public void createApplicationAssetsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = applicationAssetsRepository.findAll().size();

        // Create the ApplicationAssets with an existing ID
        applicationAssets.setId(1L);
        ApplicationAssetsDTO applicationAssetsDTO = applicationAssetsMapper.toDto(applicationAssets);

        // An entity with an existing ID cannot be created, so this API call must fail
        restApplicationAssetsMockMvc.perform(post("/api/application-assets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicationAssetsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ApplicationAssets in the database
        List<ApplicationAssets> applicationAssetsList = applicationAssetsRepository.findAll();
        assertThat(applicationAssetsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllApplicationAssets() throws Exception {
        // Initialize the database
        applicationAssetsRepository.saveAndFlush(applicationAssets);

        // Get all the applicationAssetsList
        restApplicationAssetsMockMvc.perform(get("/api/application-assets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applicationAssets.getId().intValue())))
            .andExpect(jsonPath("$.[*].tenantId").value(hasItem(DEFAULT_TENANT_ID)))
            .andExpect(jsonPath("$.[*].dashboardUuid").value(hasItem(DEFAULT_DASHBOARD_UUID)))
            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME)))
            .andExpect(jsonPath("$.[*].cloudType").value(hasItem(DEFAULT_CLOUD_TYPE)))
            .andExpect(jsonPath("$.[*].elementType").value(hasItem(DEFAULT_ELEMENT_TYPE)))
            .andExpect(jsonPath("$.[*].elementSubType").value(hasItem(DEFAULT_ELEMENT_SUB_TYPE)))
            .andExpect(jsonPath("$.[*].inputType").value(hasItem(DEFAULT_INPUT_TYPE)))
            .andExpect(jsonPath("$.[*].dashboardNature").value(hasItem(DEFAULT_DASHBOARD_NATURE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)));
    }
    
    @Test
    @Transactional
    public void getApplicationAssets() throws Exception {
        // Initialize the database
        applicationAssetsRepository.saveAndFlush(applicationAssets);

        // Get the applicationAssets
        restApplicationAssetsMockMvc.perform(get("/api/application-assets/{id}", applicationAssets.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(applicationAssets.getId().intValue()))
            .andExpect(jsonPath("$.tenantId").value(DEFAULT_TENANT_ID))
            .andExpect(jsonPath("$.dashboardUuid").value(DEFAULT_DASHBOARD_UUID))
            .andExpect(jsonPath("$.fileName").value(DEFAULT_FILE_NAME))
            .andExpect(jsonPath("$.cloudType").value(DEFAULT_CLOUD_TYPE))
            .andExpect(jsonPath("$.elementType").value(DEFAULT_ELEMENT_TYPE))
            .andExpect(jsonPath("$.elementSubType").value(DEFAULT_ELEMENT_SUB_TYPE))
            .andExpect(jsonPath("$.inputType").value(DEFAULT_INPUT_TYPE))
            .andExpect(jsonPath("$.dashboardNature").value(DEFAULT_DASHBOARD_NATURE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY));
    }
    @Test
    @Transactional
    public void getNonExistingApplicationAssets() throws Exception {
        // Get the applicationAssets
        restApplicationAssetsMockMvc.perform(get("/api/application-assets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateApplicationAssets() throws Exception {
        // Initialize the database
        applicationAssetsRepository.saveAndFlush(applicationAssets);

        int databaseSizeBeforeUpdate = applicationAssetsRepository.findAll().size();

        // Update the applicationAssets
        ApplicationAssets updatedApplicationAssets = applicationAssetsRepository.findById(applicationAssets.getId()).get();
        // Disconnect from session so that the updates on updatedApplicationAssets are not directly saved in db
        em.detach(updatedApplicationAssets);
        updatedApplicationAssets
            .tenantId(UPDATED_TENANT_ID)
            .dashboardUuid(UPDATED_DASHBOARD_UUID)
            .fileName(UPDATED_FILE_NAME)
            .cloudType(UPDATED_CLOUD_TYPE)
            .elementType(UPDATED_ELEMENT_TYPE)
            .elementSubType(UPDATED_ELEMENT_SUB_TYPE)
            .inputType(UPDATED_INPUT_TYPE)
            .dashboardNature(UPDATED_DASHBOARD_NATURE)
            .status(UPDATED_STATUS)
            .createdOn(UPDATED_CREATED_ON)
            .updatedOn(UPDATED_UPDATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .createdBy(UPDATED_CREATED_BY);
        ApplicationAssetsDTO applicationAssetsDTO = applicationAssetsMapper.toDto(updatedApplicationAssets);

        restApplicationAssetsMockMvc.perform(put("/api/application-assets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicationAssetsDTO)))
            .andExpect(status().isOk());

        // Validate the ApplicationAssets in the database
        List<ApplicationAssets> applicationAssetsList = applicationAssetsRepository.findAll();
        assertThat(applicationAssetsList).hasSize(databaseSizeBeforeUpdate);
        ApplicationAssets testApplicationAssets = applicationAssetsList.get(applicationAssetsList.size() - 1);
        assertThat(testApplicationAssets.getTenantId()).isEqualTo(UPDATED_TENANT_ID);
        assertThat(testApplicationAssets.getDashboardUuid()).isEqualTo(UPDATED_DASHBOARD_UUID);
        assertThat(testApplicationAssets.getFileName()).isEqualTo(UPDATED_FILE_NAME);
        assertThat(testApplicationAssets.getCloudType()).isEqualTo(UPDATED_CLOUD_TYPE);
        assertThat(testApplicationAssets.getElementType()).isEqualTo(UPDATED_ELEMENT_TYPE);
        assertThat(testApplicationAssets.getElementSubType()).isEqualTo(UPDATED_ELEMENT_SUB_TYPE);
        assertThat(testApplicationAssets.getInputType()).isEqualTo(UPDATED_INPUT_TYPE);
        assertThat(testApplicationAssets.getDashboardNature()).isEqualTo(UPDATED_DASHBOARD_NATURE);
        assertThat(testApplicationAssets.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testApplicationAssets.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testApplicationAssets.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testApplicationAssets.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testApplicationAssets.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingApplicationAssets() throws Exception {
        int databaseSizeBeforeUpdate = applicationAssetsRepository.findAll().size();

        // Create the ApplicationAssets
        ApplicationAssetsDTO applicationAssetsDTO = applicationAssetsMapper.toDto(applicationAssets);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApplicationAssetsMockMvc.perform(put("/api/application-assets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicationAssetsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ApplicationAssets in the database
        List<ApplicationAssets> applicationAssetsList = applicationAssetsRepository.findAll();
        assertThat(applicationAssetsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteApplicationAssets() throws Exception {
        // Initialize the database
        applicationAssetsRepository.saveAndFlush(applicationAssets);

        int databaseSizeBeforeDelete = applicationAssetsRepository.findAll().size();

        // Delete the applicationAssets
        restApplicationAssetsMockMvc.perform(delete("/api/application-assets/{id}", applicationAssets.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ApplicationAssets> applicationAssetsList = applicationAssetsRepository.findAll();
        assertThat(applicationAssetsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
