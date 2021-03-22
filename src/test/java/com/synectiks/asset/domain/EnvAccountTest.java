package com.synectiks.asset.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.synectiks.asset.web.rest.TestUtil;

public class EnvAccountTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EnvAccount.class);
        EnvAccount envAccount1 = new EnvAccount();
        envAccount1.setId(1L);
        EnvAccount envAccount2 = new EnvAccount();
        envAccount2.setId(envAccount1.getId());
        assertThat(envAccount1).isEqualTo(envAccount2);
        envAccount2.setId(2L);
        assertThat(envAccount1).isNotEqualTo(envAccount2);
        envAccount1.setId(null);
        assertThat(envAccount1).isNotEqualTo(envAccount2);
    }
}
