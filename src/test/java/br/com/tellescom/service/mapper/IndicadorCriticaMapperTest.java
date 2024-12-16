package br.com.tellescom.service.mapper;

import static br.com.tellescom.domain.IndicadorCriticaAsserts.*;
import static br.com.tellescom.domain.IndicadorCriticaTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IndicadorCriticaMapperTest {

    private IndicadorCriticaMapper indicadorCriticaMapper;

    @BeforeEach
    void setUp() {
        indicadorCriticaMapper = new IndicadorCriticaMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getIndicadorCriticaSample1();
        var actual = indicadorCriticaMapper.toEntity(indicadorCriticaMapper.toDto(expected));
        assertIndicadorCriticaAllPropertiesEquals(expected, actual);
    }
}
