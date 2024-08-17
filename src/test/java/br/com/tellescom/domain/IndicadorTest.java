package br.com.tellescom.domain;

import static br.com.tellescom.domain.IndicadorMetaTestSamples.*;
import static br.com.tellescom.domain.IndicadorTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import br.com.tellescom.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class IndicadorTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Indicador.class);
        Indicador indicador1 = getIndicadorSample1();
        Indicador indicador2 = new Indicador();
        assertThat(indicador1).isNotEqualTo(indicador2);

        indicador2.setId(indicador1.getId());
        assertThat(indicador1).isEqualTo(indicador2);

        indicador2 = getIndicadorSample2();
        assertThat(indicador1).isNotEqualTo(indicador2);
    }

    @Test
    void indicadorMetaTest() throws Exception {
        Indicador indicador = getIndicadorRandomSampleGenerator();
        IndicadorMeta indicadorMetaBack = getIndicadorMetaRandomSampleGenerator();

        indicador.addIndicadorMeta(indicadorMetaBack);
        assertThat(indicador.getIndicadorMetas()).containsOnly(indicadorMetaBack);
        assertThat(indicadorMetaBack.getIndicador()).isEqualTo(indicador);

        indicador.removeIndicadorMeta(indicadorMetaBack);
        assertThat(indicador.getIndicadorMetas()).doesNotContain(indicadorMetaBack);
        assertThat(indicadorMetaBack.getIndicador()).isNull();

        indicador.indicadorMetas(new HashSet<>(Set.of(indicadorMetaBack)));
        assertThat(indicador.getIndicadorMetas()).containsOnly(indicadorMetaBack);
        assertThat(indicadorMetaBack.getIndicador()).isEqualTo(indicador);

        indicador.setIndicadorMetas(new HashSet<>());
        assertThat(indicador.getIndicadorMetas()).doesNotContain(indicadorMetaBack);
        assertThat(indicadorMetaBack.getIndicador()).isNull();
    }
}
