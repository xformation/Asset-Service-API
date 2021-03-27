package com.synectiks.asset.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.synectiks.asset.web.rest.TestUtil;

public class OuEnvAcMappingTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OuEnvAcMapping.class);
        OuEnvAcMapping ouEnvAcMapping1 = new OuEnvAcMapping();
        ouEnvAcMapping1.setId(1L);
        OuEnvAcMapping ouEnvAcMapping2 = new OuEnvAcMapping();
        ouEnvAcMapping2.setId(ouEnvAcMapping1.getId());
        assertThat(ouEnvAcMapping1).isEqualTo(ouEnvAcMapping2);
        ouEnvAcMapping2.setId(2L);
        assertThat(ouEnvAcMapping1).isNotEqualTo(ouEnvAcMapping2);
        ouEnvAcMapping1.setId(null);
        assertThat(ouEnvAcMapping1).isNotEqualTo(ouEnvAcMapping2);
    }
}
