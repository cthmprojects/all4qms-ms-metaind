package br.com.tellescom.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.tellescom.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MetaObjetivoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MetaObjetivoDTO.class);
        MetaObjetivoDTO metaObjetivoDTO1 = new MetaObjetivoDTO();
        metaObjetivoDTO1.setId(1L);
        MetaObjetivoDTO metaObjetivoDTO2 = new MetaObjetivoDTO();
        assertThat(metaObjetivoDTO1).isNotEqualTo(metaObjetivoDTO2);
        metaObjetivoDTO2.setId(metaObjetivoDTO1.getId());
        assertThat(metaObjetivoDTO1).isEqualTo(metaObjetivoDTO2);
        metaObjetivoDTO2.setId(2L);
        assertThat(metaObjetivoDTO1).isNotEqualTo(metaObjetivoDTO2);
        metaObjetivoDTO1.setId(null);
        assertThat(metaObjetivoDTO1).isNotEqualTo(metaObjetivoDTO2);
    }
}
