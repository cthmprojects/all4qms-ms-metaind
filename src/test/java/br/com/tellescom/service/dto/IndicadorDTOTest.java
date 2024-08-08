package br.com.tellescom.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.tellescom.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class IndicadorDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(IndicadorDTO.class);
        IndicadorDTO indicadorDTO1 = new IndicadorDTO();
        indicadorDTO1.setId(1L);
        IndicadorDTO indicadorDTO2 = new IndicadorDTO();
        assertThat(indicadorDTO1).isNotEqualTo(indicadorDTO2);
        indicadorDTO2.setId(indicadorDTO1.getId());
        assertThat(indicadorDTO1).isEqualTo(indicadorDTO2);
        indicadorDTO2.setId(2L);
        assertThat(indicadorDTO1).isNotEqualTo(indicadorDTO2);
        indicadorDTO1.setId(null);
        assertThat(indicadorDTO1).isNotEqualTo(indicadorDTO2);
    }
}
