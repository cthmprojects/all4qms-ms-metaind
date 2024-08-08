package br.com.tellescom.domain;

import static br.com.tellescom.domain.MetaObjetivoTestSamples.*;
import static br.com.tellescom.domain.MetaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import br.com.tellescom.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class MetaObjetivoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MetaObjetivo.class);
        MetaObjetivo metaObjetivo1 = getMetaObjetivoSample1();
        MetaObjetivo metaObjetivo2 = new MetaObjetivo();
        assertThat(metaObjetivo1).isNotEqualTo(metaObjetivo2);

        metaObjetivo2.setId(metaObjetivo1.getId());
        assertThat(metaObjetivo1).isEqualTo(metaObjetivo2);

        metaObjetivo2 = getMetaObjetivoSample2();
        assertThat(metaObjetivo1).isNotEqualTo(metaObjetivo2);
    }

    @Test
    void metasTest() {
        MetaObjetivo metaObjetivo = getMetaObjetivoRandomSampleGenerator();
        Meta metaBack = getMetaRandomSampleGenerator();

        metaObjetivo.addMetas(metaBack);
        assertThat(metaObjetivo.getMetas()).containsOnly(metaBack);
        assertThat(metaBack.getMetaObjetivo()).isEqualTo(metaObjetivo);

        metaObjetivo.removeMetas(metaBack);
        assertThat(metaObjetivo.getMetas()).doesNotContain(metaBack);
        assertThat(metaBack.getMetaObjetivo()).isNull();

        metaObjetivo.metas(new HashSet<>(Set.of(metaBack)));
        assertThat(metaObjetivo.getMetas()).containsOnly(metaBack);
        assertThat(metaBack.getMetaObjetivo()).isEqualTo(metaObjetivo);

        metaObjetivo.setMetas(new HashSet<>());
        assertThat(metaObjetivo.getMetas()).doesNotContain(metaBack);
        assertThat(metaBack.getMetaObjetivo()).isNull();
    }
}
