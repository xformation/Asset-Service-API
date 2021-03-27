package com.synectiks.asset.web.rest;

import com.synectiks.asset.AssetserviceApp;
import com.synectiks.asset.domain.Dashboard;
import com.synectiks.asset.repository.DashboardRepository;
import com.synectiks.asset.service.DashboardService;
import com.synectiks.asset.service.dto.DashboardDTO;
import com.synectiks.asset.service.mapper.DashboardMapper;

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
 * Integration tests for the {@link DashboardResource} REST controller.
 */
@SpringBootTest(classes = AssetserviceApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class DashboardResourceIT {

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

    private static final String DEFAULT_IS_MONITORED = "AAA";
    private static final String UPDATED_IS_MONITORED = "BBB";

    private static final byte[] DEFAULT_JSON_DATA = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_JSON_DATA = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_JSON_DATA_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_JSON_DATA_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    @Autowired
    private DashboardRepository dashboardRepository;

    @Autowired
    private DashboardMapper dashboardMapper;

    @Autowired
    private DashboardService dashboardService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDashboardMockMvc;

    private Dashboard dashboard;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Dashboard createEntity(EntityManager em) {
        Dashboard dashboard = new Dashboard()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedOn(DEFAULT_UPDATED_ON)
            .status(DEFAULT_STATUS)
            .isMonitored(DEFAULT_IS_MONITORED)
            .jsonData(DEFAULT_JSON_DATA)
            .jsonDataContentType(DEFAULT_JSON_DATA_CONTENT_TYPE)
            .createdBy(DEFAULT_CREATED_BY)
            .updatedBy(DEFAULT_UPDATED_BY);
        return dashboard;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Dashboard createUpdatedEntity(EntityManager em) {
        Dashboard dashboard = new Dashboard()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .createdOn(UPDATED_CREATED_ON)
            .updatedOn(UPDATED_UPDATED_ON)
            .status(UPDATED_STATUS)
            .isMonitored(UPDATED_IS_MONITORED)
            .jsonData(UPDATED_JSON_DATA)
            .jsonDataContentType(UPDATED_JSON_DATA_CONTENT_TYPE)
            .createdBy(UPDATED_CREATED_BY)
            .updatedBy(UPDATED_UPDATED_BY);
        return dashboard;
    }

    @BeforeEach
    public void initTest() {
        dashboard = createEntity(em);
    }

    @Test
    @Transactional
    public void createDashboard() throws Exception {
        int databaseSizeBeforeCreate = dashboardRepository.findAll().size();
        // Create the Dashboard
        DashboardDTO dashboardDTO = dashboardMapper.toDto(dashboard);
        restDashboardMockMvc.perform(post("/api/dashboards")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dashboardDTO)))
            .andExpect(status().isCreated());

        // Validate the Dashboard in the database
        List<Dashboard> dashboardList = dashboardRepository.findAll();
        assertThat(dashboardList).hasSize(databaseSizeBeforeCreate + 1);
        Dashboard testDashboard = dashboardList.get(dashboardList.size() - 1);
        assertThat(testDashboard.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDashboard.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testDashboard.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testDashboard.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
        assertThat(testDashboard.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testDashboard.getIsMonitored()).isEqualTo(DEFAULT_IS_MONITORED);
        assertThat(testDashboard.getJsonData()).isEqualTo(DEFAULT_JSON_DATA);
        assertThat(testDashboard.getJsonDataContentType()).isEqualTo(DEFAULT_JSON_DATA_CONTENT_TYPE);
        assertThat(testDashboard.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testDashboard.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
    }

    @Test
    @Transactional
    public void createDashboardWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dashboardRepository.findAll().size();

        // Create the Dashboard with an existing ID
        dashboard.setId(1L);
        DashboardDTO dashboardDTO = dashboardMapper.toDto(dashboard);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDashboardMockMvc.perform(post("/api/dashboards")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dashboardDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Dashboard in the database
        List<Dashboard> dashboardList = dashboardRepository.findAll();
        assertThat(dashboardList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDashboards() throws Exception {
        // Initialize the database
        dashboardRepository.saveAndFlush(dashboard);

        // Get all the dashboardList
        restDashboardMockMvc.perform(get("/api/dashboards?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dashboard.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].isMonitored").value(hasItem(DEFAULT_IS_MONITORED)))
            .andExpect(jsonPath("$.[*].jsonDataContentType").value(hasItem(DEFAULT_JSON_DATA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].jsonData").value(hasItem(Base64Utils.encodeToString(DEFAULT_JSON_DATA))))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)));
    }
    
    @Test
    @Transactional
    public void getDashboard() throws Exception {
        // Initialize the database
        dashboardRepository.saveAndFlush(dashboard);

        // Get the dashboard
        restDashboardMockMvc.perform(get("/api/dashboards/{id}", dashboard.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dashboard.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.isMonitored").value(DEFAULT_IS_MONITORED))
            .andExpect(jsonPath("$.jsonDataContentType").value(DEFAULT_JSON_DATA_CONTENT_TYPE))
            .andExpect(jsonPath("$.jsonData").value(Base64Utils.encodeToString(DEFAULT_JSON_DATA)))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY));
    }
    @Test
    @Transactional
    public void getNonExistingDashboard() throws Exception {
        // Get the dashboard
        restDashboardMockMvc.perform(get("/api/dashboards/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDashboard() throws Exception {
        // Initialize the database
        dashboardRepository.saveAndFlush(dashboard);

        int databaseSizeBeforeUpdate = dashboardRepository.findAll().size();

        // Update the dashboard
        Dashboard updatedDashboard = dashboardRepository.findById(dashboard.getId()).get();
        // Disconnect from session so that the updates on updatedDashboard are not directly saved in db
        em.detach(updatedDashboard);
        updatedDashboard
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .createdOn(UPDATED_CREATED_ON)
            .updatedOn(UPDATED_UPDATED_ON)
            .status(UPDATED_STATUS)
            .isMonitored(UPDATED_IS_MONITORED)
            .jsonData(UPDATED_JSON_DATA)
            .jsonDataContentType(UPDATED_JSON_DATA_CONTENT_TYPE)
            .createdBy(UPDATED_CREATED_BY)
            .updatedBy(UPDATED_UPDATED_BY);
        DashboardDTO dashboardDTO = dashboardMapper.toDto(updatedDashboard);

        restDashboardMockMvc.perform(put("/api/dashboards")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dashboardDTO)))
            .andExpect(status().isOk());

        // Validate the Dashboard in the database
        List<Dashboard> dashboardList = dashboardRepository.findAll();
        assertThat(dashboardList).hasSize(databaseSizeBeforeUpdate);
        Dashboard testDashboard = dashboardList.get(dashboardList.size() - 1);
        assertThat(testDashboard.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDashboard.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDashboard.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testDashboard.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testDashboard.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testDashboard.getIsMonitored()).isEqualTo(UPDATED_IS_MONITORED);
        assertThat(testDashboard.getJsonData()).isEqualTo(UPDATED_JSON_DATA);
        assertThat(testDashboard.getJsonDataContentType()).isEqualTo(UPDATED_JSON_DATA_CONTENT_TYPE);
        assertThat(testDashboard.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testDashboard.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingDashboard() throws Exception {
        int databaseSizeBeforeUpdate = dashboardRepository.findAll().size();

        // Create the Dashboard
        DashboardDTO dashboardDTO = dashboardMapper.toDto(dashboard);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDashboardMockMvc.perform(put("/api/dashboards")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dashboardDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Dashboard in the database
        List<Dashboard> dashboardList = dashboardRepository.findAll();
        assertThat(dashboardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDashboard() throws Exception {
        // Initialize the database
        dashboardRepository.saveAndFlush(dashboard);

        int databaseSizeBeforeDelete = dashboardRepository.findAll().size();

        // Delete the dashboard
        restDashboardMockMvc.perform(delete("/api/dashboards/{id}", dashboard.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Dashboard> dashboardList = dashboardRepository.findAll();
        assertThat(dashboardList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
