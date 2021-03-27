package com.synectiks.asset.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class EnvironmentMapperTest {

    private EnvironmentMapper environmentMapper;

    @BeforeEach
    public void setUp() {
//        environmentMapper = new EnvironmentMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(environmentMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(environmentMapper.fromId(null)).isNull();
    }
}
