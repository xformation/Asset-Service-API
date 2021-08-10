package com.synectiks.asset.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class InputConfigMapperTest {

    private InputConfigMapper inputConfigMapper;

    @BeforeEach
    public void setUp() {
//        inputConfigMapper = new InputConfigMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(inputConfigMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(inputConfigMapper.fromId(null)).isNull();
    }
}
