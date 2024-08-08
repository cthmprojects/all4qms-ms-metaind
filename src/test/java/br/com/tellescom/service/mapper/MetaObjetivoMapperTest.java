package br.com.tellescom.service.mapper;

import static br.com.tellescom.domain.MetaObjetivoAsserts.*;
import static br.com.tellescom.domain.MetaObjetivoTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MetaObjetivoMapperTest {

    private MetaObjetivoMapper metaObjetivoMapper;

    @BeforeEach
    void setUp() {
        metaObjetivoMapper = new MetaObjetivoMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getMetaObjetivoSample1();
        var actual = metaObjetivoMapper.toEntity(metaObjetivoMapper.toDto(expected));
        assertMetaObjetivoAllPropertiesEquals(expected, actual);
    }
}
