package com.synectiks.asset.repository;

import com.synectiks.asset.domain.InputConfig;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the InputConfig entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InputConfigRepository extends JpaRepository<InputConfig, Long> {
}
