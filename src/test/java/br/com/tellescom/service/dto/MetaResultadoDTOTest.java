package br.com.tellescom.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.tellescom.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MetaResultadoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MetaResultadoDTO.class);
        MetaResultadoDTO metaResultadoDTO1 = new MetaResultadoDTO();
        metaResultadoDTO1.setId(1L);
        MetaResultadoDTO metaResultadoDTO2 = new MetaResultadoDTO();
        assertThat(metaResultadoDTO1).isNotEqualTo(metaResultadoDTO2);
        metaResultadoDTO2.setId(metaResultadoDTO1.getId());
        assertThat(metaResultadoDTO1).isEqualTo(metaResultadoDTO2);
        metaResultadoDTO2.setId(2L);
        assertThat(metaResultadoDTO1).isNotEqualTo(metaResultadoDTO2);
        metaResultadoDTO1.setId(null);
        assertThat(metaResultadoDTO1).isNotEqualTo(metaResultadoDTO2);
    }
}
