package com.synectiks.asset.service;

import com.synectiks.asset.service.dto.EnvironmentDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.synectiks.asset.domain.Environment}.
 */
public interface EnvironmentService {

    /**
     * Save a environment.
     *
     * @param environmentDTO the entity to save.
     * @return the persisted entity.
     */
    EnvironmentDTO save(EnvironmentDTO environmentDTO);

    /**
     * Get all the environments.
     *
     * @return the list of entities.
     */
    List<EnvironmentDTO> findAll();


    /**
     * Get the "id" environment.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EnvironmentDTO> findOne(Long id);

    /**
     * Delete the "id" environment.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
