package com.synectiks.asset.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class EnvAccountMapperTest {

    private EnvAccountMapper envAccountMapper;

    @BeforeEach
    public void setUp() {
//        envAccountMapper = new EnvAccountMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(envAccountMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(envAccountMapper.fromId(null)).isNull();
    }
}
