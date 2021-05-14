package com.synectiks.asset.service;

import com.synectiks.asset.service.dto.InputsDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.synectiks.asset.domain.Inputs}.
 */
public interface InputsService {

    /**
     * Save a inputs.
     *
     * @param inputsDTO the entity to save.
     * @return the persisted entity.
     */
    InputsDTO save(InputsDTO inputsDTO);

    /**
     * Get all the inputs.
     *
     * @return the list of entities.
     */
    List<InputsDTO> findAll();


    /**
     * Get the "id" inputs.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<InputsDTO> findOne(Long id);

    /**
     * Delete the "id" inputs.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
