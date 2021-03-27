package com.synectiks.asset.service;

import com.synectiks.asset.service.dto.OrganizationalUnitDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.synectiks.asset.domain.OrganizationalUnit}.
 */
public interface OrganizationalUnitService {

    /**
     * Save a organizationalUnit.
     *
     * @param organizationalUnitDTO the entity to save.
     * @return the persisted entity.
     */
    OrganizationalUnitDTO save(OrganizationalUnitDTO organizationalUnitDTO);

    /**
     * Get all the organizationalUnits.
     *
     * @return the list of entities.
     */
    List<OrganizationalUnitDTO> findAll();


    /**
     * Get the "id" organizationalUnit.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OrganizationalUnitDTO> findOne(Long id);

    /**
     * Delete the "id" organizationalUnit.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
