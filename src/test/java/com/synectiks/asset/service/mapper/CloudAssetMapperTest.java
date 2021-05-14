package com.synectiks.asset.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CloudAssetMapperTest {

    private CloudAssetMapper cloudAssetMapper;

    @BeforeEach
    public void setUp() {
//        cloudAssetMapper = new CloudAssetMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cloudAssetMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cloudAssetMapper.fromId(null)).isNull();
    }
}
