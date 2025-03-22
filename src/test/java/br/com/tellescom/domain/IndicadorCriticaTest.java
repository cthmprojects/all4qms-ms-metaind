package br.com.tellescom.domain;

import static br.com.tellescom.domain.AcaoCriticaTestSamples.*;
import static br.com.tellescom.domain.IndicadorCriticaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import br.com.tellescom.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class IndicadorCriticaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IndicadorCritica.class);
        IndicadorCritica indicadorCritica1 = getIndicadorCriticaSample1();
        IndicadorCritica indicadorCritica2 = new IndicadorCritica();
        assertThat(indicadorCritica1).isNotEqualTo(indicadorCritica2);

        indicadorCritica2.setId(indicadorCritica1.getId());
        assertThat(indicadorCritica1).isEqualTo(indicadorCritica2);

        indicadorCritica2 = getIndicadorCriticaSample2();
        assertThat(indicadorCritica1).isNotEqualTo(indicadorCritica2);
    }

    @Test
    void acaoCriticaTest() {
        IndicadorCritica indicadorCritica = getIndicadorCriticaRandomSampleGenerator();
        AcaoCritica acaoCriticaBack = getAcaoCriticaRandomSampleGenerator();

        indicadorCritica.addAcaoCritica(acaoCriticaBack);
        assertThat(indicadorCritica.getAcaoCriticas()).containsOnly(acaoCriticaBack);
        assertThat(acaoCriticaBack.getIndicadorCritica()).isEqualTo(indicadorCritica);

        indicadorCritica.removeAcaoCritica(acaoCriticaBack);
        assertThat(indicadorCritica.getAcaoCriticas()).doesNotContain(acaoCriticaBack);
        assertThat(acaoCriticaBack.getIndicadorCritica()).isNull();

        indicadorCritica.acaoCriticas(new HashSet<>(Set.of(acaoCriticaBack)));
        assertThat(indicadorCritica.getAcaoCriticas()).containsOnly(acaoCriticaBack);
        assertThat(acaoCriticaBack.getIndicadorCritica()).isEqualTo(indicadorCritica);

        indicadorCritica.setAcaoCriticas(new HashSet<>());
        assertThat(indicadorCritica.getAcaoCriticas()).doesNotContain(acaoCriticaBack);
        assertThat(acaoCriticaBack.getIndicadorCritica()).isNull();
    }
}
