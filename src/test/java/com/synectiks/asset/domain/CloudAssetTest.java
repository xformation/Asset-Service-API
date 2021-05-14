package com.synectiks.asset.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.synectiks.asset.web.rest.TestUtil;

public class CloudAssetTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CloudAsset.class);
        CloudAsset cloudAsset1 = new CloudAsset();
        cloudAsset1.setId(1L);
        CloudAsset cloudAsset2 = new CloudAsset();
        cloudAsset2.setId(cloudAsset1.getId());
        assertThat(cloudAsset1).isEqualTo(cloudAsset2);
        cloudAsset2.setId(2L);
        assertThat(cloudAsset1).isNotEqualTo(cloudAsset2);
        cloudAsset1.setId(null);
        assertThat(cloudAsset1).isNotEqualTo(cloudAsset2);
    }
}
