package com.synectiks.asset.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AssetDetailsMapperTest {

    private AssetDetailsMapper assetDetailsMapper;

    @BeforeEach
    public void setUp() {
//        assetDetailsMapper = new AssetDetailsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(assetDetailsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(assetDetailsMapper.fromId(null)).isNull();
    }
}
