package com.synectiks.asset.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class OuEnvAcMappingMapperTest {

    private OuEnvAcMappingMapper ouEnvAcMappingMapper;

    @BeforeEach
    public void setUp() {
//        ouEnvAcMappingMapper = new OuEnvAcMappingMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(ouEnvAcMappingMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(ouEnvAcMappingMapper.fromId(null)).isNull();
    }
}
