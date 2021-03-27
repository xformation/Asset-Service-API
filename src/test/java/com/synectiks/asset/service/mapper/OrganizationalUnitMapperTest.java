package com.synectiks.asset.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class OrganizationalUnitMapperTest {

    private OrganizationalUnitMapper organizationalUnitMapper;

    @BeforeEach
    public void setUp() {
//        organizationalUnitMapper = new OrganizationalUnitMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(organizationalUnitMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(organizationalUnitMapper.fromId(null)).isNull();
    }
}
