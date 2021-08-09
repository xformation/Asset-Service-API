package com.synectiks.asset.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.synectiks.asset.web.rest.TestUtil;

public class InputConfigTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InputConfig.class);
        InputConfig inputConfig1 = new InputConfig();
        inputConfig1.setId(1L);
        InputConfig inputConfig2 = new InputConfig();
        inputConfig2.setId(inputConfig1.getId());
        assertThat(inputConfig1).isEqualTo(inputConfig2);
        inputConfig2.setId(2L);
        assertThat(inputConfig1).isNotEqualTo(inputConfig2);
        inputConfig1.setId(null);
        assertThat(inputConfig1).isNotEqualTo(inputConfig2);
    }
}
