package com.synectiks.asset.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.synectiks.asset.web.rest.TestUtil;

public class EnvironmentDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EnvironmentDTO.class);
        EnvironmentDTO environmentDTO1 = new EnvironmentDTO();
        environmentDTO1.setId(1L);
        EnvironmentDTO environmentDTO2 = new EnvironmentDTO();
        assertThat(environmentDTO1).isNotEqualTo(environmentDTO2);
        environmentDTO2.setId(environmentDTO1.getId());
        assertThat(environmentDTO1).isEqualTo(environmentDTO2);
        environmentDTO2.setId(2L);
        assertThat(environmentDTO1).isNotEqualTo(environmentDTO2);
        environmentDTO1.setId(null);
        assertThat(environmentDTO1).isNotEqualTo(environmentDTO2);
    }
}
