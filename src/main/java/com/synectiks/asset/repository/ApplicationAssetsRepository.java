package com.synectiks.asset.repository;

import com.synectiks.asset.domain.ApplicationAssets;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ApplicationAssets entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApplicationAssetsRepository extends JpaRepository<ApplicationAssets, Long> {
}
