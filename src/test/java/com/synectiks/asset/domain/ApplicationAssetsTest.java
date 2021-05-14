package com.synectiks.asset.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.synectiks.asset.web.rest.TestUtil;

public class ApplicationAssetsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApplicationAssets.class);
        ApplicationAssets applicationAssets1 = new ApplicationAssets();
        applicationAssets1.setId(1L);
        ApplicationAssets applicationAssets2 = new ApplicationAssets();
        applicationAssets2.setId(applicationAssets1.getId());
        assertThat(applicationAssets1).isEqualTo(applicationAssets2);
        applicationAssets2.setId(2L);
        assertThat(applicationAssets1).isNotEqualTo(applicationAssets2);
        applicationAssets1.setId(null);
        assertThat(applicationAssets1).isNotEqualTo(applicationAssets2);
    }
}
