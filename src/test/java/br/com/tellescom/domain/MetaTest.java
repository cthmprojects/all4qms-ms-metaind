package br.com.tellescom.domain;

import static br.com.tellescom.domain.MetaObjetivoTestSamples.*;
import static br.com.tellescom.domain.MetaRecursoTestSamples.*;
import static br.com.tellescom.domain.MetaResultadoTestSamples.*;
import static br.com.tellescom.domain.MetaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import br.com.tellescom.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class MetaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Meta.class);
        Meta meta1 = getMetaSample1();
        Meta meta2 = new Meta();
        assertThat(meta1).isNotEqualTo(meta2);

        meta2.setId(meta1.getId());
        assertThat(meta1).isEqualTo(meta2);

        meta2 = getMetaSample2();
        assertThat(meta1).isNotEqualTo(meta2);
    }

    @Test
    void resultadosTest() {
        Meta meta = getMetaRandomSampleGenerator();
        MetaResultado metaResultadoBack = getMetaResultadoRandomSampleGenerator();

        meta.addResultados(metaResultadoBack);
        assertThat(meta.getResultados()).containsOnly(metaResultadoBack);
        assertThat(metaResultadoBack.getMeta()).isEqualTo(meta);

        meta.removeResultados(metaResultadoBack);
        assertThat(meta.getResultados()).doesNotContain(metaResultadoBack);
        assertThat(metaResultadoBack.getMeta()).isNull();

        meta.resultados(new HashSet<>(Set.of(metaResultadoBack)));
        assertThat(meta.getResultados()).containsOnly(metaResultadoBack);
        assertThat(metaResultadoBack.getMeta()).isEqualTo(meta);

        meta.setResultados(new HashSet<>());
        assertThat(meta.getResultados()).doesNotContain(metaResultadoBack);
        assertThat(metaResultadoBack.getMeta()).isNull();
    }

    @Test
    void recursosTest() {
        Meta meta = getMetaRandomSampleGenerator();
        MetaRecurso metaRecursoBack = getMetaRecursoRandomSampleGenerator();

        meta.addRecursos(metaRecursoBack);
        assertThat(meta.getRecursos()).containsOnly(metaRecursoBack);

        meta.removeRecursos(metaRecursoBack);
        assertThat(meta.getRecursos()).doesNotContain(metaRecursoBack);

        meta.recursos(new HashSet<>(Set.of(metaRecursoBack)));
        assertThat(meta.getRecursos()).containsOnly(metaRecursoBack);

        meta.setRecursos(new HashSet<>());
        assertThat(meta.getRecursos()).doesNotContain(metaRecursoBack);
    }

    @Test
    void metaObjetivoTest() {
        Meta meta = getMetaRandomSampleGenerator();
        MetaObjetivo metaObjetivoBack = getMetaObjetivoRandomSampleGenerator();

        meta.setMetaObjetivo(metaObjetivoBack);
        assertThat(meta.getMetaObjetivo()).isEqualTo(metaObjetivoBack);

        meta.metaObjetivo(null);
        assertThat(meta.getMetaObjetivo()).isNull();
    }
}
