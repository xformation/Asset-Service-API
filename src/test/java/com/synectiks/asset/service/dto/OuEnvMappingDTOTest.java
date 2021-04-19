package com.synectiks.asset.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.synectiks.asset.web.rest.TestUtil;

public class OuEnvMappingDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OuEnvMappingDTO.class);
        OuEnvMappingDTO ouEnvMappingDTO1 = new OuEnvMappingDTO();
        ouEnvMappingDTO1.setId(1L);
        OuEnvMappingDTO ouEnvMappingDTO2 = new OuEnvMappingDTO();
        assertThat(ouEnvMappingDTO1).isNotEqualTo(ouEnvMappingDTO2);
        ouEnvMappingDTO2.setId(ouEnvMappingDTO1.getId());
        assertThat(ouEnvMappingDTO1).isEqualTo(ouEnvMappingDTO2);
        ouEnvMappingDTO2.setId(2L);
        assertThat(ouEnvMappingDTO1).isNotEqualTo(ouEnvMappingDTO2);
        ouEnvMappingDTO1.setId(null);
        assertThat(ouEnvMappingDTO1).isNotEqualTo(ouEnvMappingDTO2);
    }
}
