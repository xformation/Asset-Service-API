package com.synectiks.asset.repository;

import com.synectiks.asset.domain.OuEnvMapping;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the OuEnvMapping entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OuEnvMappingRepository extends JpaRepository<OuEnvMapping, Long> {
}
