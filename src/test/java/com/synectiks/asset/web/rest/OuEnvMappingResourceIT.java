package com.synectiks.asset.web.rest;

import com.synectiks.asset.AssetserviceApp;
import com.synectiks.asset.domain.OuEnvMapping;
import com.synectiks.asset.repository.OuEnvMappingRepository;
import com.synectiks.asset.service.OuEnvMappingService;
import com.synectiks.asset.service.dto.OuEnvMappingDTO;
import com.synectiks.asset.service.mapper.OuEnvMappingMapper;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link OuEnvMappingResource} REST controller.
 */
@SpringBootTest(classes = AssetserviceApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class OuEnvMappingResourceIT {

    @Autowired
    private OuEnvMappingRepository ouEnvMappingRepository;

    @Autowired
    private OuEnvMappingMapper ouEnvMappingMapper;

    @Autowired
    private OuEnvMappingService ouEnvMappingService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOuEnvMappingMockMvc;

    private OuEnvMapping ouEnvMapping;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OuEnvMapping createEntity(EntityManager em) {
        OuEnvMapping ouEnvMapping = new OuEnvMapping();
        return ouEnvMapping;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OuEnvMapping createUpdatedEntity(EntityManager em) {
        OuEnvMapping ouEnvMapping = new OuEnvMapping();
        return ouEnvMapping;
    }

    @BeforeEach
    public void initTest() {
        ouEnvMapping = createEntity(em);
    }

    @Test
    @Transactional
    public void createOuEnvMapping() throws Exception {
        int databaseSizeBeforeCreate = ouEnvMappingRepository.findAll().size();
        // Create the OuEnvMapping
        OuEnvMappingDTO ouEnvMappingDTO = ouEnvMappingMapper.toDto(ouEnvMapping);
        restOuEnvMappingMockMvc.perform(post("/api/ou-env-mappings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ouEnvMappingDTO)))
            .andExpect(status().isCreated());

        // Validate the OuEnvMapping in the database
        List<OuEnvMapping> ouEnvMappingList = ouEnvMappingRepository.findAll();
        assertThat(ouEnvMappingList).hasSize(databaseSizeBeforeCreate + 1);
        OuEnvMapping testOuEnvMapping = ouEnvMappingList.get(ouEnvMappingList.size() - 1);
    }

    @Test
    @Transactional
    public void createOuEnvMappingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ouEnvMappingRepository.findAll().size();

        // Create the OuEnvMapping with an existing ID
        ouEnvMapping.setId(1L);
        OuEnvMappingDTO ouEnvMappingDTO = ouEnvMappingMapper.toDto(ouEnvMapping);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOuEnvMappingMockMvc.perform(post("/api/ou-env-mappings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ouEnvMappingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OuEnvMapping in the database
        List<OuEnvMapping> ouEnvMappingList = ouEnvMappingRepository.findAll();
        assertThat(ouEnvMappingList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllOuEnvMappings() throws Exception {
        // Initialize the database
        ouEnvMappingRepository.saveAndFlush(ouEnvMapping);

        // Get all the ouEnvMappingList
        restOuEnvMappingMockMvc.perform(get("/api/ou-env-mappings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ouEnvMapping.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getOuEnvMapping() throws Exception {
        // Initialize the database
        ouEnvMappingRepository.saveAndFlush(ouEnvMapping);

        // Get the ouEnvMapping
        restOuEnvMappingMockMvc.perform(get("/api/ou-env-mappings/{id}", ouEnvMapping.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ouEnvMapping.getId().intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingOuEnvMapping() throws Exception {
        // Get the ouEnvMapping
        restOuEnvMappingMockMvc.perform(get("/api/ou-env-mappings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOuEnvMapping() throws Exception {
        // Initialize the database
        ouEnvMappingRepository.saveAndFlush(ouEnvMapping);

        int databaseSizeBeforeUpdate = ouEnvMappingRepository.findAll().size();

        // Update the ouEnvMapping
        OuEnvMapping updatedOuEnvMapping = ouEnvMappingRepository.findById(ouEnvMapping.getId()).get();
        // Disconnect from session so that the updates on updatedOuEnvMapping are not directly saved in db
        em.detach(updatedOuEnvMapping);
        OuEnvMappingDTO ouEnvMappingDTO = ouEnvMappingMapper.toDto(updatedOuEnvMapping);

        restOuEnvMappingMockMvc.perform(put("/api/ou-env-mappings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ouEnvMappingDTO)))
            .andExpect(status().isOk());

        // Validate the OuEnvMapping in the database
        List<OuEnvMapping> ouEnvMappingList = ouEnvMappingRepository.findAll();
        assertThat(ouEnvMappingList).hasSize(databaseSizeBeforeUpdate);
        OuEnvMapping testOuEnvMapping = ouEnvMappingList.get(ouEnvMappingList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingOuEnvMapping() throws Exception {
        int databaseSizeBeforeUpdate = ouEnvMappingRepository.findAll().size();

        // Create the OuEnvMapping
        OuEnvMappingDTO ouEnvMappingDTO = ouEnvMappingMapper.toDto(ouEnvMapping);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOuEnvMappingMockMvc.perform(put("/api/ou-env-mappings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ouEnvMappingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OuEnvMapping in the database
        List<OuEnvMapping> ouEnvMappingList = ouEnvMappingRepository.findAll();
        assertThat(ouEnvMappingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOuEnvMapping() throws Exception {
        // Initialize the database
        ouEnvMappingRepository.saveAndFlush(ouEnvMapping);

        int databaseSizeBeforeDelete = ouEnvMappingRepository.findAll().size();

        // Delete the ouEnvMapping
        restOuEnvMappingMockMvc.perform(delete("/api/ou-env-mappings/{id}", ouEnvMapping.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OuEnvMapping> ouEnvMappingList = ouEnvMappingRepository.findAll();
        assertThat(ouEnvMappingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
