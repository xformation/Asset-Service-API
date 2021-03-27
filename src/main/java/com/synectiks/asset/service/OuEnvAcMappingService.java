package com.synectiks.asset.service;

import com.synectiks.asset.service.dto.OuEnvAcMappingDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.synectiks.asset.domain.OuEnvAcMapping}.
 */
public interface OuEnvAcMappingService {

    /**
     * Save a ouEnvAcMapping.
     *
     * @param ouEnvAcMappingDTO the entity to save.
     * @return the persisted entity.
     */
    OuEnvAcMappingDTO save(OuEnvAcMappingDTO ouEnvAcMappingDTO);

    /**
     * Get all the ouEnvAcMappings.
     *
     * @return the list of entities.
     */
    List<OuEnvAcMappingDTO> findAll();


    /**
     * Get the "id" ouEnvAcMapping.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OuEnvAcMappingDTO> findOne(Long id);

    /**
     * Delete the "id" ouEnvAcMapping.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
