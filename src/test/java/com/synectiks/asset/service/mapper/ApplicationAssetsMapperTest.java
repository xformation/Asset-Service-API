package com.synectiks.asset.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ApplicationAssetsMapperTest {

    private ApplicationAssetsMapper applicationAssetsMapper;

    @BeforeEach
    public void setUp() {
//        applicationAssetsMapper = new ApplicationAssetsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(applicationAssetsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(applicationAssetsMapper.fromId(null)).isNull();
    }
}
