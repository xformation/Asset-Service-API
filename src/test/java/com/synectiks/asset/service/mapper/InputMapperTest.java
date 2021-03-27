package com.synectiks.asset.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class InputMapperTest {

    private InputMapper inputMapper;

    @BeforeEach
    public void setUp() {
//        inputMapper = new InputMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(inputMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(inputMapper.fromId(null)).isNull();
    }
}
