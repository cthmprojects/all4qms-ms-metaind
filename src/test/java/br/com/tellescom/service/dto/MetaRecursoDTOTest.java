package br.com.tellescom.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.tellescom.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MetaRecursoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MetaRecursoDTO.class);
        MetaRecursoDTO metaRecursoDTO1 = new MetaRecursoDTO();
        metaRecursoDTO1.setId(1L);
        MetaRecursoDTO metaRecursoDTO2 = new MetaRecursoDTO();
        assertThat(metaRecursoDTO1).isNotEqualTo(metaRecursoDTO2);
        metaRecursoDTO2.setId(metaRecursoDTO1.getId());
        assertThat(metaRecursoDTO1).isEqualTo(metaRecursoDTO2);
        metaRecursoDTO2.setId(2L);
        assertThat(metaRecursoDTO1).isNotEqualTo(metaRecursoDTO2);
        metaRecursoDTO1.setId(null);
        assertThat(metaRecursoDTO1).isNotEqualTo(metaRecursoDTO2);
    }
}
