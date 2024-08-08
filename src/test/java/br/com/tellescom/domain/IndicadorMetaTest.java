package br.com.tellescom.domain;

import static br.com.tellescom.domain.IndicadorMetaTestSamples.*;
import static br.com.tellescom.domain.IndicadorTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import br.com.tellescom.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class IndicadorMetaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IndicadorMeta.class);
        IndicadorMeta indicadorMeta1 = getIndicadorMetaSample1();
        IndicadorMeta indicadorMeta2 = new IndicadorMeta();
        assertThat(indicadorMeta1).isNotEqualTo(indicadorMeta2);

        indicadorMeta2.setId(indicadorMeta1.getId());
        assertThat(indicadorMeta1).isEqualTo(indicadorMeta2);

        indicadorMeta2 = getIndicadorMetaSample2();
        assertThat(indicadorMeta1).isNotEqualTo(indicadorMeta2);
    }

    @Test
    void indicadorTest() {
        IndicadorMeta indicadorMeta = getIndicadorMetaRandomSampleGenerator();
        Indicador indicadorBack = getIndicadorRandomSampleGenerator();

        indicadorMeta.setIndicador(indicadorBack);
        assertThat(indicadorMeta.getIndicador()).isEqualTo(indicadorBack);

        indicadorMeta.indicador(null);
        assertThat(indicadorMeta.getIndicador()).isNull();
    }
}
