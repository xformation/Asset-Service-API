package com.synectiks.asset.web.rest;

import com.synectiks.asset.AssetserviceApp;
import com.synectiks.asset.domain.OrganizationalUnit;
import com.synectiks.asset.repository.OrganizationalUnitRepository;
import com.synectiks.asset.service.OrganizationalUnitService;
import com.synectiks.asset.service.dto.OrganizationalUnitDTO;
import com.synectiks.asset.service.mapper.OrganizationalUnitMapper;

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
 * Integration tests for the {@link OrganizationalUnitResource} REST controller.
 */
@SpringBootTest(classes = AssetserviceApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class OrganizationalUnitResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

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
    private OrganizationalUnitRepository organizationalUnitRepository;

    @Autowired
    private OrganizationalUnitMapper organizationalUnitMapper;

    @Autowired
    private OrganizationalUnitService organizationalUnitService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrganizationalUnitMockMvc;

    private OrganizationalUnit organizationalUnit;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrganizationalUnit createEntity(EntityManager em) {
        OrganizationalUnit organizationalUnit = new OrganizationalUnit()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .status(DEFAULT_STATUS)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedOn(DEFAULT_UPDATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .createdBy(DEFAULT_CREATED_BY);
        return organizationalUnit;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrganizationalUnit createUpdatedEntity(EntityManager em) {
        OrganizationalUnit organizationalUnit = new OrganizationalUnit()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .status(UPDATED_STATUS)
            .createdOn(UPDATED_CREATED_ON)
            .updatedOn(UPDATED_UPDATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .createdBy(UPDATED_CREATED_BY);
        return organizationalUnit;
    }

    @BeforeEach
    public void initTest() {
        organizationalUnit = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrganizationalUnit() throws Exception {
        int databaseSizeBeforeCreate = organizationalUnitRepository.findAll().size();
        // Create the OrganizationalUnit
        OrganizationalUnitDTO organizationalUnitDTO = organizationalUnitMapper.toDto(organizationalUnit);
        restOrganizationalUnitMockMvc.perform(post("/api/organizational-units")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(organizationalUnitDTO)))
            .andExpect(status().isCreated());

        // Validate the OrganizationalUnit in the database
        List<OrganizationalUnit> organizationalUnitList = organizationalUnitRepository.findAll();
        assertThat(organizationalUnitList).hasSize(databaseSizeBeforeCreate + 1);
        OrganizationalUnit testOrganizationalUnit = organizationalUnitList.get(organizationalUnitList.size() - 1);
        assertThat(testOrganizationalUnit.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testOrganizationalUnit.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testOrganizationalUnit.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testOrganizationalUnit.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testOrganizationalUnit.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
        assertThat(testOrganizationalUnit.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testOrganizationalUnit.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
    }

    @Test
    @Transactional
    public void createOrganizationalUnitWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = organizationalUnitRepository.findAll().size();

        // Create the OrganizationalUnit with an existing ID
        organizationalUnit.setId(1L);
        OrganizationalUnitDTO organizationalUnitDTO = organizationalUnitMapper.toDto(organizationalUnit);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrganizationalUnitMockMvc.perform(post("/api/organizational-units")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(organizationalUnitDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OrganizationalUnit in the database
        List<OrganizationalUnit> organizationalUnitList = organizationalUnitRepository.findAll();
        assertThat(organizationalUnitList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllOrganizationalUnits() throws Exception {
        // Initialize the database
        organizationalUnitRepository.saveAndFlush(organizationalUnit);

        // Get all the organizationalUnitList
        restOrganizationalUnitMockMvc.perform(get("/api/organizational-units?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(organizationalUnit.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)));
    }
    
    @Test
    @Transactional
    public void getOrganizationalUnit() throws Exception {
        // Initialize the database
        organizationalUnitRepository.saveAndFlush(organizationalUnit);

        // Get the organizationalUnit
        restOrganizationalUnitMockMvc.perform(get("/api/organizational-units/{id}", organizationalUnit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(organizationalUnit.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY));
    }
    @Test
    @Transactional
    public void getNonExistingOrganizationalUnit() throws Exception {
        // Get the organizationalUnit
        restOrganizationalUnitMockMvc.perform(get("/api/organizational-units/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrganizationalUnit() throws Exception {
        // Initialize the database
        organizationalUnitRepository.saveAndFlush(organizationalUnit);

        int databaseSizeBeforeUpdate = organizationalUnitRepository.findAll().size();

        // Update the organizationalUnit
        OrganizationalUnit updatedOrganizationalUnit = organizationalUnitRepository.findById(organizationalUnit.getId()).get();
        // Disconnect from session so that the updates on updatedOrganizationalUnit are not directly saved in db
        em.detach(updatedOrganizationalUnit);
        updatedOrganizationalUnit
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .status(UPDATED_STATUS)
            .createdOn(UPDATED_CREATED_ON)
            .updatedOn(UPDATED_UPDATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .createdBy(UPDATED_CREATED_BY);
        OrganizationalUnitDTO organizationalUnitDTO = organizationalUnitMapper.toDto(updatedOrganizationalUnit);

        restOrganizationalUnitMockMvc.perform(put("/api/organizational-units")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(organizationalUnitDTO)))
            .andExpect(status().isOk());

        // Validate the OrganizationalUnit in the database
        List<OrganizationalUnit> organizationalUnitList = organizationalUnitRepository.findAll();
        assertThat(organizationalUnitList).hasSize(databaseSizeBeforeUpdate);
        OrganizationalUnit testOrganizationalUnit = organizationalUnitList.get(organizationalUnitList.size() - 1);
        assertThat(testOrganizationalUnit.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOrganizationalUnit.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testOrganizationalUnit.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testOrganizationalUnit.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testOrganizationalUnit.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testOrganizationalUnit.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testOrganizationalUnit.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingOrganizationalUnit() throws Exception {
        int databaseSizeBeforeUpdate = organizationalUnitRepository.findAll().size();

        // Create the OrganizationalUnit
        OrganizationalUnitDTO organizationalUnitDTO = organizationalUnitMapper.toDto(organizationalUnit);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrganizationalUnitMockMvc.perform(put("/api/organizational-units")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(organizationalUnitDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OrganizationalUnit in the database
        List<OrganizationalUnit> organizationalUnitList = organizationalUnitRepository.findAll();
        assertThat(organizationalUnitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOrganizationalUnit() throws Exception {
        // Initialize the database
        organizationalUnitRepository.saveAndFlush(organizationalUnit);

        int databaseSizeBeforeDelete = organizationalUnitRepository.findAll().size();

        // Delete the organizationalUnit
        restOrganizationalUnitMockMvc.perform(delete("/api/organizational-units/{id}", organizationalUnit.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OrganizationalUnit> organizationalUnitList = organizationalUnitRepository.findAll();
        assertThat(organizationalUnitList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
