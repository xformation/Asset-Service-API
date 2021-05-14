package com.synectiks.asset.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.synectiks.asset.web.rest.TestUtil;

public class InputsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Inputs.class);
        Inputs inputs1 = new Inputs();
        inputs1.setId(1L);
        Inputs inputs2 = new Inputs();
        inputs2.setId(inputs1.getId());
        assertThat(inputs1).isEqualTo(inputs2);
        inputs2.setId(2L);
        assertThat(inputs1).isNotEqualTo(inputs2);
        inputs1.setId(null);
        assertThat(inputs1).isNotEqualTo(inputs2);
    }
}
