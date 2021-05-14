package com.synectiks.asset.repository;

import com.synectiks.asset.domain.CloudAsset;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CloudAsset entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CloudAssetRepository extends JpaRepository<CloudAsset, Long> {
}
