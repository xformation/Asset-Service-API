package com.synectiks.asset.service;

import com.synectiks.asset.service.dto.CloudAssetDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.synectiks.asset.domain.CloudAsset}.
 */
public interface CloudAssetService {

    /**
     * Save a cloudAsset.
     *
     * @param cloudAssetDTO the entity to save.
     * @return the persisted entity.
     */
    CloudAssetDTO save(CloudAssetDTO cloudAssetDTO);

    /**
     * Get all the cloudAssets.
     *
     * @return the list of entities.
     */
    List<CloudAssetDTO> findAll();


    /**
     * Get the "id" cloudAsset.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CloudAssetDTO> findOne(Long id);

    /**
     * Delete the "id" cloudAsset.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
