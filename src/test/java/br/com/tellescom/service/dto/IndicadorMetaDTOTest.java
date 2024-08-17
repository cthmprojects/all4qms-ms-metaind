package br.com.tellescom.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.tellescom.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class IndicadorMetaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(IndicadorMetaDTO.class);
        IndicadorMetaDTO indicadorMetaDTO1 = new IndicadorMetaDTO();
        indicadorMetaDTO1.setId(1L);
        IndicadorMetaDTO indicadorMetaDTO2 = new IndicadorMetaDTO();
        assertThat(indicadorMetaDTO1).isNotEqualTo(indicadorMetaDTO2);
        indicadorMetaDTO2.setId(indicadorMetaDTO1.getId());
        assertThat(indicadorMetaDTO1).isEqualTo(indicadorMetaDTO2);
        indicadorMetaDTO2.setId(2L);
        assertThat(indicadorMetaDTO1).isNotEqualTo(indicadorMetaDTO2);
        indicadorMetaDTO1.setId(null);
        assertThat(indicadorMetaDTO1).isNotEqualTo(indicadorMetaDTO2);
    }
}
