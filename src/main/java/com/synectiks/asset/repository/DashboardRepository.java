package com.synectiks.asset.repository;

import com.synectiks.asset.domain.Dashboard;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Dashboard entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DashboardRepository extends JpaRepository<Dashboard, Long> {
}
