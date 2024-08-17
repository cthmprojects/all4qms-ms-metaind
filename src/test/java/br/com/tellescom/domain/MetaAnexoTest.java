package br.com.tellescom.domain;

import static br.com.tellescom.domain.MetaAnexoTestSamples.*;
import static br.com.tellescom.domain.MetaResultadoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import br.com.tellescom.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MetaAnexoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MetaAnexo.class);
        MetaAnexo metaAnexo1 = getMetaAnexoSample1();
        MetaAnexo metaAnexo2 = new MetaAnexo();
        assertThat(metaAnexo1).isNotEqualTo(metaAnexo2);

        metaAnexo2.setId(metaAnexo1.getId());
        assertThat(metaAnexo1).isEqualTo(metaAnexo2);

        metaAnexo2 = getMetaAnexoSample2();
        assertThat(metaAnexo1).isNotEqualTo(metaAnexo2);
    }

    @Test
    void metaResultadoTest() throws Exception {
        MetaAnexo metaAnexo = getMetaAnexoRandomSampleGenerator();
        MetaResultado metaResultadoBack = getMetaResultadoRandomSampleGenerator();

        metaAnexo.setMetaResultado(metaResultadoBack);
        assertThat(metaAnexo.getMetaResultado()).isEqualTo(metaResultadoBack);

        metaAnexo.metaResultado(null);
        assertThat(metaAnexo.getMetaResultado()).isNull();
    }
}
