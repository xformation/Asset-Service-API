package com.synectiks.asset.service;

import com.synectiks.asset.service.dto.InputDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.synectiks.asset.domain.Input}.
 */
public interface InputService {

    /**
     * Save a input.
     *
     * @param inputDTO the entity to save.
     * @return the persisted entity.
     */
    InputDTO save(InputDTO inputDTO);

    /**
     * Get all the inputs.
     *
     * @return the list of entities.
     */
    List<InputDTO> findAll();


    /**
     * Get the "id" input.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<InputDTO> findOne(Long id);

    /**
     * Delete the "id" input.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
