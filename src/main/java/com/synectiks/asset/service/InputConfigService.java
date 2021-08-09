package com.synectiks.asset.service;

import com.synectiks.asset.service.dto.InputConfigDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.synectiks.asset.domain.InputConfig}.
 */
public interface InputConfigService {

    /**
     * Save a inputConfig.
     *
     * @param inputConfigDTO the entity to save.
     * @return the persisted entity.
     */
    InputConfigDTO save(InputConfigDTO inputConfigDTO);

    /**
     * Get all the inputConfigs.
     *
     * @return the list of entities.
     */
    List<InputConfigDTO> findAll();


    /**
     * Get the "id" inputConfig.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<InputConfigDTO> findOne(Long id);

    /**
     * Delete the "id" inputConfig.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
