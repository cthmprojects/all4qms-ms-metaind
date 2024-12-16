package br.com.tellescom.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.tellescom.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class IndicadorCriticaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(IndicadorCriticaDTO.class);
        IndicadorCriticaDTO indicadorCriticaDTO1 = new IndicadorCriticaDTO();
        indicadorCriticaDTO1.setId(1L);
        IndicadorCriticaDTO indicadorCriticaDTO2 = new IndicadorCriticaDTO();
        assertThat(indicadorCriticaDTO1).isNotEqualTo(indicadorCriticaDTO2);
        indicadorCriticaDTO2.setId(indicadorCriticaDTO1.getId());
        assertThat(indicadorCriticaDTO1).isEqualTo(indicadorCriticaDTO2);
        indicadorCriticaDTO2.setId(2L);
        assertThat(indicadorCriticaDTO1).isNotEqualTo(indicadorCriticaDTO2);
        indicadorCriticaDTO1.setId(null);
        assertThat(indicadorCriticaDTO1).isNotEqualTo(indicadorCriticaDTO2);
    }
}
