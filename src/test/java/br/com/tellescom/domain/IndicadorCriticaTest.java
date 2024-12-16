package br.com.tellescom.domain;

import static br.com.tellescom.domain.IndicadorCriticaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import br.com.tellescom.web.rest.TestUtil;
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
}
