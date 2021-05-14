package com.synectiks.asset.repository;

import com.synectiks.asset.domain.AssetDetails;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AssetDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AssetDetailsRepository extends JpaRepository<AssetDetails, Long> {
}
