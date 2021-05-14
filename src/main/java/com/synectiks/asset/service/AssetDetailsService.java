package com.synectiks.asset.service;

import com.synectiks.asset.service.dto.AssetDetailsDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.synectiks.asset.domain.AssetDetails}.
 */
public interface AssetDetailsService {

    /**
     * Save a assetDetails.
     *
     * @param assetDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    AssetDetailsDTO save(AssetDetailsDTO assetDetailsDTO);

    /**
     * Get all the assetDetails.
     *
     * @return the list of entities.
     */
    List<AssetDetailsDTO> findAll();


    /**
     * Get the "id" assetDetails.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AssetDetailsDTO> findOne(Long id);

    /**
     * Delete the "id" assetDetails.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
