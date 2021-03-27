package com.synectiks.asset.repository;

import com.synectiks.asset.domain.EnvAccount;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the EnvAccount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EnvAccountRepository extends JpaRepository<EnvAccount, Long> {
}
