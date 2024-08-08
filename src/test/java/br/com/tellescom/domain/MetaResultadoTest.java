package br.com.tellescom.domain;

import static br.com.tellescom.domain.MetaAnexoTestSamples.*;
import static br.com.tellescom.domain.MetaResultadoTestSamples.*;
import static br.com.tellescom.domain.MetaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import br.com.tellescom.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class MetaResultadoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MetaResultado.class);
        MetaResultado metaResultado1 = getMetaResultadoSample1();
        MetaResultado metaResultado2 = new MetaResultado();
        assertThat(metaResultado1).isNotEqualTo(metaResultado2);

        metaResultado2.setId(metaResultado1.getId());
        assertThat(metaResultado1).isEqualTo(metaResultado2);

        metaResultado2 = getMetaResultadoSample2();
        assertThat(metaResultado1).isNotEqualTo(metaResultado2);
    }

    @Test
    void anexosTest() {
        MetaResultado metaResultado = getMetaResultadoRandomSampleGenerator();
        MetaAnexo metaAnexoBack = getMetaAnexoRandomSampleGenerator();

        metaResultado.addAnexos(metaAnexoBack);
        assertThat(metaResultado.getAnexos()).containsOnly(metaAnexoBack);
        assertThat(metaAnexoBack.getMetaResultado()).isEqualTo(metaResultado);

        metaResultado.removeAnexos(metaAnexoBack);
        assertThat(metaResultado.getAnexos()).doesNotContain(metaAnexoBack);
        assertThat(metaAnexoBack.getMetaResultado()).isNull();

        metaResultado.anexos(new HashSet<>(Set.of(metaAnexoBack)));
        assertThat(metaResultado.getAnexos()).containsOnly(metaAnexoBack);
        assertThat(metaAnexoBack.getMetaResultado()).isEqualTo(metaResultado);

        metaResultado.setAnexos(new HashSet<>());
        assertThat(metaResultado.getAnexos()).doesNotContain(metaAnexoBack);
        assertThat(metaAnexoBack.getMetaResultado()).isNull();
    }

    @Test
    void metaTest() {
        MetaResultado metaResultado = getMetaResultadoRandomSampleGenerator();
        Meta metaBack = getMetaRandomSampleGenerator();

        metaResultado.setMeta(metaBack);
        assertThat(metaResultado.getMeta()).isEqualTo(metaBack);

        metaResultado.meta(null);
        assertThat(metaResultado.getMeta()).isNull();
    }
}
