package com.synectiks.asset.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class OuEnvMappingMapperTest {

    private OuEnvMappingMapper ouEnvMappingMapper;

    @BeforeEach
    public void setUp() {
//        ouEnvMappingMapper = new OuEnvMappingMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(ouEnvMappingMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(ouEnvMappingMapper.fromId(null)).isNull();
    }
}
