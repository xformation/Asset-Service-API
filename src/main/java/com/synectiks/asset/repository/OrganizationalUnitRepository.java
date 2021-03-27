package com.synectiks.asset.repository;

import com.synectiks.asset.domain.OrganizationalUnit;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the OrganizationalUnit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrganizationalUnitRepository extends JpaRepository<OrganizationalUnit, Long> {
}
