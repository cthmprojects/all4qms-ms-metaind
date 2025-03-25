package br.com.tellescom.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.tellescom.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AcaoCriticaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AcaoCriticaDTO.class);
        AcaoCriticaDTO acaoCriticaDTO1 = new AcaoCriticaDTO();
        acaoCriticaDTO1.setId(1L);
        AcaoCriticaDTO acaoCriticaDTO2 = new AcaoCriticaDTO();
        assertThat(acaoCriticaDTO1).isNotEqualTo(acaoCriticaDTO2);
        acaoCriticaDTO2.setId(acaoCriticaDTO1.getId());
        assertThat(acaoCriticaDTO1).isEqualTo(acaoCriticaDTO2);
        acaoCriticaDTO2.setId(2L);
        assertThat(acaoCriticaDTO1).isNotEqualTo(acaoCriticaDTO2);
        acaoCriticaDTO1.setId(null);
        assertThat(acaoCriticaDTO1).isNotEqualTo(acaoCriticaDTO2);
    }
}
