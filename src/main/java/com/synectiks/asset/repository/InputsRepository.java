package com.synectiks.asset.repository;

import com.synectiks.asset.domain.Inputs;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Inputs entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InputsRepository extends JpaRepository<Inputs, Long> {
}
