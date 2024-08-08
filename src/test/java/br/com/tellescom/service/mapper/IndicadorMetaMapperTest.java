package br.com.tellescom.service.mapper;

import static br.com.tellescom.domain.IndicadorMetaAsserts.*;
import static br.com.tellescom.domain.IndicadorMetaTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IndicadorMetaMapperTest {

    private IndicadorMetaMapper indicadorMetaMapper;

    @BeforeEach
    void setUp() {
        indicadorMetaMapper = new IndicadorMetaMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getIndicadorMetaSample1();
        var actual = indicadorMetaMapper.toEntity(indicadorMetaMapper.toDto(expected));
        assertIndicadorMetaAllPropertiesEquals(expected, actual);
    }
}
