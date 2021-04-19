package com.synectiks.asset.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.synectiks.asset.web.rest.TestUtil;

public class OuEnvMappingTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OuEnvMapping.class);
        OuEnvMapping ouEnvMapping1 = new OuEnvMapping();
        ouEnvMapping1.setId(1L);
        OuEnvMapping ouEnvMapping2 = new OuEnvMapping();
        ouEnvMapping2.setId(ouEnvMapping1.getId());
        assertThat(ouEnvMapping1).isEqualTo(ouEnvMapping2);
        ouEnvMapping2.setId(2L);
        assertThat(ouEnvMapping1).isNotEqualTo(ouEnvMapping2);
        ouEnvMapping1.setId(null);
        assertThat(ouEnvMapping1).isNotEqualTo(ouEnvMapping2);
    }
}
