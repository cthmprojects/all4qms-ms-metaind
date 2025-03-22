package br.com.tellescom.domain;

import static br.com.tellescom.domain.AcaoCriticaTestSamples.*;
import static br.com.tellescom.domain.IndicadorCriticaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import br.com.tellescom.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AcaoCriticaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AcaoCritica.class);
        AcaoCritica acaoCritica1 = getAcaoCriticaSample1();
        AcaoCritica acaoCritica2 = new AcaoCritica();
        assertThat(acaoCritica1).isNotEqualTo(acaoCritica2);

        acaoCritica2.setId(acaoCritica1.getId());
        assertThat(acaoCritica1).isEqualTo(acaoCritica2);

        acaoCritica2 = getAcaoCriticaSample2();
        assertThat(acaoCritica1).isNotEqualTo(acaoCritica2);
    }

    @Test
    void indicadorCriticaTest() {
        AcaoCritica acaoCritica = getAcaoCriticaRandomSampleGenerator();
        IndicadorCritica indicadorCriticaBack = getIndicadorCriticaRandomSampleGenerator();

        acaoCritica.setIndicadorCritica(indicadorCriticaBack);
        assertThat(acaoCritica.getIndicadorCritica()).isEqualTo(indicadorCriticaBack);

        acaoCritica.indicadorCritica(null);
        assertThat(acaoCritica.getIndicadorCritica()).isNull();
    }
}
