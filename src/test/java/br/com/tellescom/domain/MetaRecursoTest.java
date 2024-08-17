package br.com.tellescom.domain;

import static br.com.tellescom.domain.MetaRecursoTestSamples.*;
import static br.com.tellescom.domain.MetaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import br.com.tellescom.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class MetaRecursoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MetaRecurso.class);
        MetaRecurso metaRecurso1 = getMetaRecursoSample1();
        MetaRecurso metaRecurso2 = new MetaRecurso();
        assertThat(metaRecurso1).isNotEqualTo(metaRecurso2);

        metaRecurso2.setId(metaRecurso1.getId());
        assertThat(metaRecurso1).isEqualTo(metaRecurso2);

        metaRecurso2 = getMetaRecursoSample2();
        assertThat(metaRecurso1).isNotEqualTo(metaRecurso2);
    }

    @Test
    void metasTest() throws Exception {
        MetaRecurso metaRecurso = getMetaRecursoRandomSampleGenerator();
        Meta metaBack = getMetaRandomSampleGenerator();

        metaRecurso.addMetas(metaBack);
        assertThat(metaRecurso.getMetas()).containsOnly(metaBack);
        assertThat(metaBack.getRecursos()).containsOnly(metaRecurso);

        metaRecurso.removeMetas(metaBack);
        assertThat(metaRecurso.getMetas()).doesNotContain(metaBack);
        assertThat(metaBack.getRecursos()).doesNotContain(metaRecurso);

        metaRecurso.metas(new HashSet<>(Set.of(metaBack)));
        assertThat(metaRecurso.getMetas()).containsOnly(metaBack);
        assertThat(metaBack.getRecursos()).containsOnly(metaRecurso);

        metaRecurso.setMetas(new HashSet<>());
        assertThat(metaRecurso.getMetas()).doesNotContain(metaBack);
        assertThat(metaBack.getRecursos()).doesNotContain(metaRecurso);
    }
}
