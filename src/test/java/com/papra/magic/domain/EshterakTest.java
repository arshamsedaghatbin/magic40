package com.papra.magic.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.papra.magic.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EshterakTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Eshterak.class);
        Eshterak eshterak1 = new Eshterak();
        eshterak1.setId(1L);
        Eshterak eshterak2 = new Eshterak();
        eshterak2.setId(eshterak1.getId());
        assertThat(eshterak1).isEqualTo(eshterak2);
        eshterak2.setId(2L);
        assertThat(eshterak1).isNotEqualTo(eshterak2);
        eshterak1.setId(null);
        assertThat(eshterak1).isNotEqualTo(eshterak2);
    }
}
