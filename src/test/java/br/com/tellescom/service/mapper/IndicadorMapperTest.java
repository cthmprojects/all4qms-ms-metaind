package br.com.tellescom.service.mapper;

import static br.com.tellescom.domain.IndicadorAsserts.*;
import static br.com.tellescom.domain.IndicadorTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IndicadorMapperTest {

    private IndicadorMapper indicadorMapper;

    @BeforeEach
    void setUp() {
        indicadorMapper = new IndicadorMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getIndicadorSample1();
        var actual = indicadorMapper.toEntity(indicadorMapper.toDto(expected));
        assertIndicadorAllPropertiesEquals(expected, actual);
    }
}
