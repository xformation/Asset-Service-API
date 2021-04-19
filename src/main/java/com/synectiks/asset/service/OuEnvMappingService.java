package com.synectiks.asset.service;

import com.synectiks.asset.service.dto.OuEnvMappingDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.synectiks.asset.domain.OuEnvMapping}.
 */
public interface OuEnvMappingService {

    /**
     * Save a ouEnvMapping.
     *
     * @param ouEnvMappingDTO the entity to save.
     * @return the persisted entity.
     */
    OuEnvMappingDTO save(OuEnvMappingDTO ouEnvMappingDTO);

    /**
     * Get all the ouEnvMappings.
     *
     * @return the list of entities.
     */
    List<OuEnvMappingDTO> findAll();


    /**
     * Get the "id" ouEnvMapping.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OuEnvMappingDTO> findOne(Long id);

    /**
     * Delete the "id" ouEnvMapping.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
