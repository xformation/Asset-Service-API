package com.synectiks.asset.service;

import com.synectiks.asset.service.dto.EnvAccountDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.synectiks.asset.domain.EnvAccount}.
 */
public interface EnvAccountService {
  /**
   * Save a envAccount.
   *
   * @param envAccountDTO the entity to save.
   * @return the persisted entity.
   */
  EnvAccountDTO save(EnvAccountDTO envAccountDTO);

  /**
   * Partially updates a envAccount.
   *
   * @param envAccountDTO the entity to update partially.
   * @return the persisted entity.
   */
  Optional<EnvAccountDTO> partialUpdate(EnvAccountDTO envAccountDTO);

  /**
   * Get all the envAccounts.
   *
   * @return the list of entities.
   */
  List<EnvAccountDTO> findAll();

  /**
   * Get the "id" envAccount.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  Optional<EnvAccountDTO> findOne(Long id);

  /**
   * Delete the "id" envAccount.
   *
   * @param id the id of the entity.
   */
  void delete(Long id);
}
