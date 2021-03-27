package com.synectiks.asset.web.rest;

import com.synectiks.asset.AssetserviceApp;
import com.synectiks.asset.domain.OuEnvAcMapping;
import com.synectiks.asset.repository.OuEnvAcMappingRepository;
import com.synectiks.asset.service.OuEnvAcMappingService;
import com.synectiks.asset.service.dto.OuEnvAcMappingDTO;
import com.synectiks.asset.service.mapper.OuEnvAcMappingMapper;

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
 * Integration tests for the {@link OuEnvAcMappingResource} REST controller.
 */
@SpringBootTest(classes = AssetserviceApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class OuEnvAcMappingResourceIT {

    @Autowired
    private OuEnvAcMappingRepository ouEnvAcMappingRepository;

    @Autowired
    private OuEnvAcMappingMapper ouEnvAcMappingMapper;

    @Autowired
    private OuEnvAcMappingService ouEnvAcMappingService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOuEnvAcMappingMockMvc;

    private OuEnvAcMapping ouEnvAcMapping;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OuEnvAcMapping createEntity(EntityManager em) {
        OuEnvAcMapping ouEnvAcMapping = new OuEnvAcMapping();
        return ouEnvAcMapping;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OuEnvAcMapping createUpdatedEntity(EntityManager em) {
        OuEnvAcMapping ouEnvAcMapping = new OuEnvAcMapping();
        return ouEnvAcMapping;
    }

    @BeforeEach
    public void initTest() {
        ouEnvAcMapping = createEntity(em);
    }

    @Test
    @Transactional
    public void createOuEnvAcMapping() throws Exception {
        int databaseSizeBeforeCreate = ouEnvAcMappingRepository.findAll().size();
        // Create the OuEnvAcMapping
        OuEnvAcMappingDTO ouEnvAcMappingDTO = ouEnvAcMappingMapper.toDto(ouEnvAcMapping);
        restOuEnvAcMappingMockMvc.perform(post("/api/ou-env-ac-mappings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ouEnvAcMappingDTO)))
            .andExpect(status().isCreated());

        // Validate the OuEnvAcMapping in the database
        List<OuEnvAcMapping> ouEnvAcMappingList = ouEnvAcMappingRepository.findAll();
        assertThat(ouEnvAcMappingList).hasSize(databaseSizeBeforeCreate + 1);
        OuEnvAcMapping testOuEnvAcMapping = ouEnvAcMappingList.get(ouEnvAcMappingList.size() - 1);
    }

    @Test
    @Transactional
    public void createOuEnvAcMappingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ouEnvAcMappingRepository.findAll().size();

        // Create the OuEnvAcMapping with an existing ID
        ouEnvAcMapping.setId(1L);
        OuEnvAcMappingDTO ouEnvAcMappingDTO = ouEnvAcMappingMapper.toDto(ouEnvAcMapping);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOuEnvAcMappingMockMvc.perform(post("/api/ou-env-ac-mappings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ouEnvAcMappingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OuEnvAcMapping in the database
        List<OuEnvAcMapping> ouEnvAcMappingList = ouEnvAcMappingRepository.findAll();
        assertThat(ouEnvAcMappingList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllOuEnvAcMappings() throws Exception {
        // Initialize the database
        ouEnvAcMappingRepository.saveAndFlush(ouEnvAcMapping);

        // Get all the ouEnvAcMappingList
        restOuEnvAcMappingMockMvc.perform(get("/api/ou-env-ac-mappings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ouEnvAcMapping.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getOuEnvAcMapping() throws Exception {
        // Initialize the database
        ouEnvAcMappingRepository.saveAndFlush(ouEnvAcMapping);

        // Get the ouEnvAcMapping
        restOuEnvAcMappingMockMvc.perform(get("/api/ou-env-ac-mappings/{id}", ouEnvAcMapping.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ouEnvAcMapping.getId().intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingOuEnvAcMapping() throws Exception {
        // Get the ouEnvAcMapping
        restOuEnvAcMappingMockMvc.perform(get("/api/ou-env-ac-mappings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOuEnvAcMapping() throws Exception {
        // Initialize the database
        ouEnvAcMappingRepository.saveAndFlush(ouEnvAcMapping);

        int databaseSizeBeforeUpdate = ouEnvAcMappingRepository.findAll().size();

        // Update the ouEnvAcMapping
        OuEnvAcMapping updatedOuEnvAcMapping = ouEnvAcMappingRepository.findById(ouEnvAcMapping.getId()).get();
        // Disconnect from session so that the updates on updatedOuEnvAcMapping are not directly saved in db
        em.detach(updatedOuEnvAcMapping);
        OuEnvAcMappingDTO ouEnvAcMappingDTO = ouEnvAcMappingMapper.toDto(updatedOuEnvAcMapping);

        restOuEnvAcMappingMockMvc.perform(put("/api/ou-env-ac-mappings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ouEnvAcMappingDTO)))
            .andExpect(status().isOk());

        // Validate the OuEnvAcMapping in the database
        List<OuEnvAcMapping> ouEnvAcMappingList = ouEnvAcMappingRepository.findAll();
        assertThat(ouEnvAcMappingList).hasSize(databaseSizeBeforeUpdate);
        OuEnvAcMapping testOuEnvAcMapping = ouEnvAcMappingList.get(ouEnvAcMappingList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingOuEnvAcMapping() throws Exception {
        int databaseSizeBeforeUpdate = ouEnvAcMappingRepository.findAll().size();

        // Create the OuEnvAcMapping
        OuEnvAcMappingDTO ouEnvAcMappingDTO = ouEnvAcMappingMapper.toDto(ouEnvAcMapping);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOuEnvAcMappingMockMvc.perform(put("/api/ou-env-ac-mappings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ouEnvAcMappingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OuEnvAcMapping in the database
        List<OuEnvAcMapping> ouEnvAcMappingList = ouEnvAcMappingRepository.findAll();
        assertThat(ouEnvAcMappingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOuEnvAcMapping() throws Exception {
        // Initialize the database
        ouEnvAcMappingRepository.saveAndFlush(ouEnvAcMapping);

        int databaseSizeBeforeDelete = ouEnvAcMappingRepository.findAll().size();

        // Delete the ouEnvAcMapping
        restOuEnvAcMappingMockMvc.perform(delete("/api/ou-env-ac-mappings/{id}", ouEnvAcMapping.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OuEnvAcMapping> ouEnvAcMappingList = ouEnvAcMappingRepository.findAll();
        assertThat(ouEnvAcMappingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
