package com.papra.magic.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.papra.magic.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EshterakDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EshterakDTO.class);
        EshterakDTO eshterakDTO1 = new EshterakDTO();
        eshterakDTO1.setId(1L);
        EshterakDTO eshterakDTO2 = new EshterakDTO();
        assertThat(eshterakDTO1).isNotEqualTo(eshterakDTO2);
        eshterakDTO2.setId(eshterakDTO1.getId());
        assertThat(eshterakDTO1).isEqualTo(eshterakDTO2);
        eshterakDTO2.setId(2L);
        assertThat(eshterakDTO1).isNotEqualTo(eshterakDTO2);
        eshterakDTO1.setId(null);
        assertThat(eshterakDTO1).isNotEqualTo(eshterakDTO2);
    }
}
