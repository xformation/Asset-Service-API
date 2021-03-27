package com.synectiks.asset.repository;

import com.synectiks.asset.domain.OuEnvAcMapping;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the OuEnvAcMapping entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OuEnvAcMappingRepository extends JpaRepository<OuEnvAcMapping, Long> {
}
