package br.com.tellescom.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.tellescom.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MetaAnexoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MetaAnexoDTO.class);
        MetaAnexoDTO metaAnexoDTO1 = new MetaAnexoDTO();
        metaAnexoDTO1.setId(1L);
        MetaAnexoDTO metaAnexoDTO2 = new MetaAnexoDTO();
        assertThat(metaAnexoDTO1).isNotEqualTo(metaAnexoDTO2);
        metaAnexoDTO2.setId(metaAnexoDTO1.getId());
        assertThat(metaAnexoDTO1).isEqualTo(metaAnexoDTO2);
        metaAnexoDTO2.setId(2L);
        assertThat(metaAnexoDTO1).isNotEqualTo(metaAnexoDTO2);
        metaAnexoDTO1.setId(null);
        assertThat(metaAnexoDTO1).isNotEqualTo(metaAnexoDTO2);
    }
}
