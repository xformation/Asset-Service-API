package com.synectiks.asset.web.rest;

import com.synectiks.asset.AssetserviceApp;
import com.synectiks.asset.domain.CloudAsset;
import com.synectiks.asset.repository.CloudAssetRepository;
import com.synectiks.asset.service.CloudAssetService;
import com.synectiks.asset.service.dto.CloudAssetDTO;
import com.synectiks.asset.service.mapper.CloudAssetMapper;

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
 * Integration tests for the {@link CloudAssetResource} REST controller.
 */
@SpringBootTest(classes = AssetserviceApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CloudAssetResourceIT {

    private static final String DEFAULT_ACCOUNT_ID = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

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
    private CloudAssetRepository cloudAssetRepository;

    @Autowired
    private CloudAssetMapper cloudAssetMapper;

    @Autowired
    private CloudAssetService cloudAssetService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCloudAssetMockMvc;

    private CloudAsset cloudAsset;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CloudAsset createEntity(EntityManager em) {
        CloudAsset cloudAsset = new CloudAsset()
            .accountId(DEFAULT_ACCOUNT_ID)
            .type(DEFAULT_TYPE)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .sourceJsonRef(DEFAULT_SOURCE_JSON_REF)
            .sourceJson(DEFAULT_SOURCE_JSON)
            .sourceJsonContentType(DEFAULT_SOURCE_JSON_CONTENT_TYPE)
            .status(DEFAULT_STATUS)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedOn(DEFAULT_UPDATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .createdBy(DEFAULT_CREATED_BY);
        return cloudAsset;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CloudAsset createUpdatedEntity(EntityManager em) {
        CloudAsset cloudAsset = new CloudAsset()
            .accountId(UPDATED_ACCOUNT_ID)
            .type(UPDATED_TYPE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .sourceJsonRef(UPDATED_SOURCE_JSON_REF)
            .sourceJson(UPDATED_SOURCE_JSON)
            .sourceJsonContentType(UPDATED_SOURCE_JSON_CONTENT_TYPE)
            .status(UPDATED_STATUS)
            .createdOn(UPDATED_CREATED_ON)
            .updatedOn(UPDATED_UPDATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .createdBy(UPDATED_CREATED_BY);
        return cloudAsset;
    }

    @BeforeEach
    public void initTest() {
        cloudAsset = createEntity(em);
    }

    @Test
    @Transactional
    public void createCloudAsset() throws Exception {
        int databaseSizeBeforeCreate = cloudAssetRepository.findAll().size();
        // Create the CloudAsset
        CloudAssetDTO cloudAssetDTO = cloudAssetMapper.toDto(cloudAsset);
        restCloudAssetMockMvc.perform(post("/api/cloud-assets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cloudAssetDTO)))
            .andExpect(status().isCreated());

        // Validate the CloudAsset in the database
        List<CloudAsset> cloudAssetList = cloudAssetRepository.findAll();
        assertThat(cloudAssetList).hasSize(databaseSizeBeforeCreate + 1);
        CloudAsset testCloudAsset = cloudAssetList.get(cloudAssetList.size() - 1);
        assertThat(testCloudAsset.getAccountId()).isEqualTo(DEFAULT_ACCOUNT_ID);
        assertThat(testCloudAsset.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testCloudAsset.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCloudAsset.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCloudAsset.getSourceJsonRef()).isEqualTo(DEFAULT_SOURCE_JSON_REF);
        assertThat(testCloudAsset.getSourceJson()).isEqualTo(DEFAULT_SOURCE_JSON);
        assertThat(testCloudAsset.getSourceJsonContentType()).isEqualTo(DEFAULT_SOURCE_JSON_CONTENT_TYPE);
        assertThat(testCloudAsset.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testCloudAsset.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testCloudAsset.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
        assertThat(testCloudAsset.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testCloudAsset.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
    }

    @Test
    @Transactional
    public void createCloudAssetWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cloudAssetRepository.findAll().size();

        // Create the CloudAsset with an existing ID
        cloudAsset.setId(1L);
        CloudAssetDTO cloudAssetDTO = cloudAssetMapper.toDto(cloudAsset);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCloudAssetMockMvc.perform(post("/api/cloud-assets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cloudAssetDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CloudAsset in the database
        List<CloudAsset> cloudAssetList = cloudAssetRepository.findAll();
        assertThat(cloudAssetList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCloudAssets() throws Exception {
        // Initialize the database
        cloudAssetRepository.saveAndFlush(cloudAsset);

        // Get all the cloudAssetList
        restCloudAssetMockMvc.perform(get("/api/cloud-assets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cloudAsset.getId().intValue())))
            .andExpect(jsonPath("$.[*].accountId").value(hasItem(DEFAULT_ACCOUNT_ID)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
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
    public void getCloudAsset() throws Exception {
        // Initialize the database
        cloudAssetRepository.saveAndFlush(cloudAsset);

        // Get the cloudAsset
        restCloudAssetMockMvc.perform(get("/api/cloud-assets/{id}", cloudAsset.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cloudAsset.getId().intValue()))
            .andExpect(jsonPath("$.accountId").value(DEFAULT_ACCOUNT_ID))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
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
    public void getNonExistingCloudAsset() throws Exception {
        // Get the cloudAsset
        restCloudAssetMockMvc.perform(get("/api/cloud-assets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCloudAsset() throws Exception {
        // Initialize the database
        cloudAssetRepository.saveAndFlush(cloudAsset);

        int databaseSizeBeforeUpdate = cloudAssetRepository.findAll().size();

        // Update the cloudAsset
        CloudAsset updatedCloudAsset = cloudAssetRepository.findById(cloudAsset.getId()).get();
        // Disconnect from session so that the updates on updatedCloudAsset are not directly saved in db
        em.detach(updatedCloudAsset);
        updatedCloudAsset
            .accountId(UPDATED_ACCOUNT_ID)
            .type(UPDATED_TYPE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .sourceJsonRef(UPDATED_SOURCE_JSON_REF)
            .sourceJson(UPDATED_SOURCE_JSON)
            .sourceJsonContentType(UPDATED_SOURCE_JSON_CONTENT_TYPE)
            .status(UPDATED_STATUS)
            .createdOn(UPDATED_CREATED_ON)
            .updatedOn(UPDATED_UPDATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .createdBy(UPDATED_CREATED_BY);
        CloudAssetDTO cloudAssetDTO = cloudAssetMapper.toDto(updatedCloudAsset);

        restCloudAssetMockMvc.perform(put("/api/cloud-assets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cloudAssetDTO)))
            .andExpect(status().isOk());

        // Validate the CloudAsset in the database
        List<CloudAsset> cloudAssetList = cloudAssetRepository.findAll();
        assertThat(cloudAssetList).hasSize(databaseSizeBeforeUpdate);
        CloudAsset testCloudAsset = cloudAssetList.get(cloudAssetList.size() - 1);
        assertThat(testCloudAsset.getAccountId()).isEqualTo(UPDATED_ACCOUNT_ID);
        assertThat(testCloudAsset.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testCloudAsset.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCloudAsset.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCloudAsset.getSourceJsonRef()).isEqualTo(UPDATED_SOURCE_JSON_REF);
        assertThat(testCloudAsset.getSourceJson()).isEqualTo(UPDATED_SOURCE_JSON);
        assertThat(testCloudAsset.getSourceJsonContentType()).isEqualTo(UPDATED_SOURCE_JSON_CONTENT_TYPE);
        assertThat(testCloudAsset.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testCloudAsset.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testCloudAsset.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testCloudAsset.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testCloudAsset.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingCloudAsset() throws Exception {
        int databaseSizeBeforeUpdate = cloudAssetRepository.findAll().size();

        // Create the CloudAsset
        CloudAssetDTO cloudAssetDTO = cloudAssetMapper.toDto(cloudAsset);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCloudAssetMockMvc.perform(put("/api/cloud-assets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cloudAssetDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CloudAsset in the database
        List<CloudAsset> cloudAssetList = cloudAssetRepository.findAll();
        assertThat(cloudAssetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCloudAsset() throws Exception {
        // Initialize the database
        cloudAssetRepository.saveAndFlush(cloudAsset);

        int databaseSizeBeforeDelete = cloudAssetRepository.findAll().size();

        // Delete the cloudAsset
        restCloudAssetMockMvc.perform(delete("/api/cloud-assets/{id}", cloudAsset.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CloudAsset> cloudAssetList = cloudAssetRepository.findAll();
        assertThat(cloudAssetList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
