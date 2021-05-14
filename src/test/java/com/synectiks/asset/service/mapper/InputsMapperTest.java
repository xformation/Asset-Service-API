package com.synectiks.asset.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class InputsMapperTest {

    private InputsMapper inputsMapper;

    @BeforeEach
    public void setUp() {
//        inputsMapper = new InputsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(inputsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(inputsMapper.fromId(null)).isNull();
    }
}
