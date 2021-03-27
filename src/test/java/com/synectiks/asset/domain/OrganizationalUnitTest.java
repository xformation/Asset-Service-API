package com.synectiks.asset.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.synectiks.asset.web.rest.TestUtil;

public class OrganizationalUnitTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrganizationalUnit.class);
        OrganizationalUnit organizationalUnit1 = new OrganizationalUnit();
        organizationalUnit1.setId(1L);
        OrganizationalUnit organizationalUnit2 = new OrganizationalUnit();
        organizationalUnit2.setId(organizationalUnit1.getId());
        assertThat(organizationalUnit1).isEqualTo(organizationalUnit2);
        organizationalUnit2.setId(2L);
        assertThat(organizationalUnit1).isNotEqualTo(organizationalUnit2);
        organizationalUnit1.setId(null);
        assertThat(organizationalUnit1).isNotEqualTo(organizationalUnit2);
    }
}
