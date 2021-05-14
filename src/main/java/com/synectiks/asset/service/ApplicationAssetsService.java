package com.synectiks.asset.service;

import com.synectiks.asset.service.dto.ApplicationAssetsDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.synectiks.asset.domain.ApplicationAssets}.
 */
public interface ApplicationAssetsService {

    /**
     * Save a applicationAssets.
     *
     * @param applicationAssetsDTO the entity to save.
     * @return the persisted entity.
     */
    ApplicationAssetsDTO save(ApplicationAssetsDTO applicationAssetsDTO);

    /**
     * Get all the applicationAssets.
     *
     * @return the list of entities.
     */
    List<ApplicationAssetsDTO> findAll();


    /**
     * Get the "id" applicationAssets.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ApplicationAssetsDTO> findOne(Long id);

    /**
     * Delete the "id" applicationAssets.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
