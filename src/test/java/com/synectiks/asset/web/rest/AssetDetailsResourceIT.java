package com.synectiks.asset.web.rest;

import com.synectiks.asset.AssetserviceApp;
import com.synectiks.asset.domain.AssetDetails;
import com.synectiks.asset.repository.AssetDetailsRepository;
import com.synectiks.asset.service.AssetDetailsService;
import com.synectiks.asset.service.dto.AssetDetailsDTO;
import com.synectiks.asset.service.mapper.AssetDetailsMapper;

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
 * Integration tests for the {@link AssetDetailsResource} REST controller.
 */
@SpringBootTest(classes = AssetserviceApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AssetDetailsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_TOTAL_INSTANCES = 1;
    private static final Integer UPDATED_TOTAL_INSTANCES = 2;

    private static final String DEFAULT_VIEW_JSON_REF = "AAAAAAAAAA";
    private static final String UPDATED_VIEW_JSON_REF = "BBBBBBBBBB";

    private static final byte[] DEFAULT_VIEW_JSON = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_VIEW_JSON = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_VIEW_JSON_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_VIEW_JSON_CONTENT_TYPE = "image/png";

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
    private AssetDetailsRepository assetDetailsRepository;

    @Autowired
    private AssetDetailsMapper assetDetailsMapper;

    @Autowired
    private AssetDetailsService assetDetailsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAssetDetailsMockMvc;

    private AssetDetails assetDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AssetDetails createEntity(EntityManager em) {
        AssetDetails assetDetails = new AssetDetails()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .totalInstances(DEFAULT_TOTAL_INSTANCES)
            .viewJsonRef(DEFAULT_VIEW_JSON_REF)
            .viewJson(DEFAULT_VIEW_JSON)
            .viewJsonContentType(DEFAULT_VIEW_JSON_CONTENT_TYPE)
            .sourceJsonRef(DEFAULT_SOURCE_JSON_REF)
            .sourceJson(DEFAULT_SOURCE_JSON)
            .sourceJsonContentType(DEFAULT_SOURCE_JSON_CONTENT_TYPE)
            .status(DEFAULT_STATUS)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedOn(DEFAULT_UPDATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .createdBy(DEFAULT_CREATED_BY);
        return assetDetails;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AssetDetails createUpdatedEntity(EntityManager em) {
        AssetDetails assetDetails = new AssetDetails()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .totalInstances(UPDATED_TOTAL_INSTANCES)
            .viewJsonRef(UPDATED_VIEW_JSON_REF)
            .viewJson(UPDATED_VIEW_JSON)
            .viewJsonContentType(UPDATED_VIEW_JSON_CONTENT_TYPE)
            .sourceJsonRef(UPDATED_SOURCE_JSON_REF)
            .sourceJson(UPDATED_SOURCE_JSON)
            .sourceJsonContentType(UPDATED_SOURCE_JSON_CONTENT_TYPE)
            .status(UPDATED_STATUS)
            .createdOn(UPDATED_CREATED_ON)
            .updatedOn(UPDATED_UPDATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .createdBy(UPDATED_CREATED_BY);
        return assetDetails;
    }

    @BeforeEach
    public void initTest() {
        assetDetails = createEntity(em);
    }

    @Test
    @Transactional
    public void createAssetDetails() throws Exception {
        int databaseSizeBeforeCreate = assetDetailsRepository.findAll().size();
        // Create the AssetDetails
        AssetDetailsDTO assetDetailsDTO = assetDetailsMapper.toDto(assetDetails);
        restAssetDetailsMockMvc.perform(post("/api/asset-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(assetDetailsDTO)))
            .andExpect(status().isCreated());

        // Validate the AssetDetails in the database
        List<AssetDetails> assetDetailsList = assetDetailsRepository.findAll();
        assertThat(assetDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        AssetDetails testAssetDetails = assetDetailsList.get(assetDetailsList.size() - 1);
        assertThat(testAssetDetails.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAssetDetails.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAssetDetails.getTotalInstances()).isEqualTo(DEFAULT_TOTAL_INSTANCES);
        assertThat(testAssetDetails.getViewJsonRef()).isEqualTo(DEFAULT_VIEW_JSON_REF);
        assertThat(testAssetDetails.getViewJson()).isEqualTo(DEFAULT_VIEW_JSON);
        assertThat(testAssetDetails.getViewJsonContentType()).isEqualTo(DEFAULT_VIEW_JSON_CONTENT_TYPE);
        assertThat(testAssetDetails.getSourceJsonRef()).isEqualTo(DEFAULT_SOURCE_JSON_REF);
        assertThat(testAssetDetails.getSourceJson()).isEqualTo(DEFAULT_SOURCE_JSON);
        assertThat(testAssetDetails.getSourceJsonContentType()).isEqualTo(DEFAULT_SOURCE_JSON_CONTENT_TYPE);
        assertThat(testAssetDetails.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testAssetDetails.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testAssetDetails.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
        assertThat(testAssetDetails.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testAssetDetails.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
    }

    @Test
    @Transactional
    public void createAssetDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = assetDetailsRepository.findAll().size();

        // Create the AssetDetails with an existing ID
        assetDetails.setId(1L);
        AssetDetailsDTO assetDetailsDTO = assetDetailsMapper.toDto(assetDetails);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAssetDetailsMockMvc.perform(post("/api/asset-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(assetDetailsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AssetDetails in the database
        List<AssetDetails> assetDetailsList = assetDetailsRepository.findAll();
        assertThat(assetDetailsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAssetDetails() throws Exception {
        // Initialize the database
        assetDetailsRepository.saveAndFlush(assetDetails);

        // Get all the assetDetailsList
        restAssetDetailsMockMvc.perform(get("/api/asset-details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(assetDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].totalInstances").value(hasItem(DEFAULT_TOTAL_INSTANCES)))
            .andExpect(jsonPath("$.[*].viewJsonRef").value(hasItem(DEFAULT_VIEW_JSON_REF)))
            .andExpect(jsonPath("$.[*].viewJsonContentType").value(hasItem(DEFAULT_VIEW_JSON_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].viewJson").value(hasItem(Base64Utils.encodeToString(DEFAULT_VIEW_JSON))))
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
    public void getAssetDetails() throws Exception {
        // Initialize the database
        assetDetailsRepository.saveAndFlush(assetDetails);

        // Get the assetDetails
        restAssetDetailsMockMvc.perform(get("/api/asset-details/{id}", assetDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(assetDetails.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.totalInstances").value(DEFAULT_TOTAL_INSTANCES))
            .andExpect(jsonPath("$.viewJsonRef").value(DEFAULT_VIEW_JSON_REF))
            .andExpect(jsonPath("$.viewJsonContentType").value(DEFAULT_VIEW_JSON_CONTENT_TYPE))
            .andExpect(jsonPath("$.viewJson").value(Base64Utils.encodeToString(DEFAULT_VIEW_JSON)))
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
    public void getNonExistingAssetDetails() throws Exception {
        // Get the assetDetails
        restAssetDetailsMockMvc.perform(get("/api/asset-details/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAssetDetails() throws Exception {
        // Initialize the database
        assetDetailsRepository.saveAndFlush(assetDetails);

        int databaseSizeBeforeUpdate = assetDetailsRepository.findAll().size();

        // Update the assetDetails
        AssetDetails updatedAssetDetails = assetDetailsRepository.findById(assetDetails.getId()).get();
        // Disconnect from session so that the updates on updatedAssetDetails are not directly saved in db
        em.detach(updatedAssetDetails);
        updatedAssetDetails
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .totalInstances(UPDATED_TOTAL_INSTANCES)
            .viewJsonRef(UPDATED_VIEW_JSON_REF)
            .viewJson(UPDATED_VIEW_JSON)
            .viewJsonContentType(UPDATED_VIEW_JSON_CONTENT_TYPE)
            .sourceJsonRef(UPDATED_SOURCE_JSON_REF)
            .sourceJson(UPDATED_SOURCE_JSON)
            .sourceJsonContentType(UPDATED_SOURCE_JSON_CONTENT_TYPE)
            .status(UPDATED_STATUS)
            .createdOn(UPDATED_CREATED_ON)
            .updatedOn(UPDATED_UPDATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .createdBy(UPDATED_CREATED_BY);
        AssetDetailsDTO assetDetailsDTO = assetDetailsMapper.toDto(updatedAssetDetails);

        restAssetDetailsMockMvc.perform(put("/api/asset-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(assetDetailsDTO)))
            .andExpect(status().isOk());

        // Validate the AssetDetails in the database
        List<AssetDetails> assetDetailsList = assetDetailsRepository.findAll();
        assertThat(assetDetailsList).hasSize(databaseSizeBeforeUpdate);
        AssetDetails testAssetDetails = assetDetailsList.get(assetDetailsList.size() - 1);
        assertThat(testAssetDetails.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAssetDetails.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAssetDetails.getTotalInstances()).isEqualTo(UPDATED_TOTAL_INSTANCES);
        assertThat(testAssetDetails.getViewJsonRef()).isEqualTo(UPDATED_VIEW_JSON_REF);
        assertThat(testAssetDetails.getViewJson()).isEqualTo(UPDATED_VIEW_JSON);
        assertThat(testAssetDetails.getViewJsonContentType()).isEqualTo(UPDATED_VIEW_JSON_CONTENT_TYPE);
        assertThat(testAssetDetails.getSourceJsonRef()).isEqualTo(UPDATED_SOURCE_JSON_REF);
        assertThat(testAssetDetails.getSourceJson()).isEqualTo(UPDATED_SOURCE_JSON);
        assertThat(testAssetDetails.getSourceJsonContentType()).isEqualTo(UPDATED_SOURCE_JSON_CONTENT_TYPE);
        assertThat(testAssetDetails.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testAssetDetails.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testAssetDetails.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testAssetDetails.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testAssetDetails.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingAssetDetails() throws Exception {
        int databaseSizeBeforeUpdate = assetDetailsRepository.findAll().size();

        // Create the AssetDetails
        AssetDetailsDTO assetDetailsDTO = assetDetailsMapper.toDto(assetDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAssetDetailsMockMvc.perform(put("/api/asset-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(assetDetailsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AssetDetails in the database
        List<AssetDetails> assetDetailsList = assetDetailsRepository.findAll();
        assertThat(assetDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAssetDetails() throws Exception {
        // Initialize the database
        assetDetailsRepository.saveAndFlush(assetDetails);

        int databaseSizeBeforeDelete = assetDetailsRepository.findAll().size();

        // Delete the assetDetails
        restAssetDetailsMockMvc.perform(delete("/api/asset-details/{id}", assetDetails.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AssetDetails> assetDetailsList = assetDetailsRepository.findAll();
        assertThat(assetDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
