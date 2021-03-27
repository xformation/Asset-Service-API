package com.synectiks.asset.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.synectiks.asset.web.rest.TestUtil;

public class OuEnvAcMappingDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OuEnvAcMappingDTO.class);
        OuEnvAcMappingDTO ouEnvAcMappingDTO1 = new OuEnvAcMappingDTO();
        ouEnvAcMappingDTO1.setId(1L);
        OuEnvAcMappingDTO ouEnvAcMappingDTO2 = new OuEnvAcMappingDTO();
        assertThat(ouEnvAcMappingDTO1).isNotEqualTo(ouEnvAcMappingDTO2);
        ouEnvAcMappingDTO2.setId(ouEnvAcMappingDTO1.getId());
        assertThat(ouEnvAcMappingDTO1).isEqualTo(ouEnvAcMappingDTO2);
        ouEnvAcMappingDTO2.setId(2L);
        assertThat(ouEnvAcMappingDTO1).isNotEqualTo(ouEnvAcMappingDTO2);
        ouEnvAcMappingDTO1.setId(null);
        assertThat(ouEnvAcMappingDTO1).isNotEqualTo(ouEnvAcMappingDTO2);
    }
}
