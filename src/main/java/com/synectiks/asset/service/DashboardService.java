package com.synectiks.asset.service;

import com.synectiks.asset.service.dto.DashboardDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.synectiks.asset.domain.Dashboard}.
 */
public interface DashboardService {

    /**
     * Save a dashboard.
     *
     * @param dashboardDTO the entity to save.
     * @return the persisted entity.
     */
    DashboardDTO save(DashboardDTO dashboardDTO);

    /**
     * Get all the dashboards.
     *
     * @return the list of entities.
     */
    List<DashboardDTO> findAll();


    /**
     * Get the "id" dashboard.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DashboardDTO> findOne(Long id);

    /**
     * Delete the "id" dashboard.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
