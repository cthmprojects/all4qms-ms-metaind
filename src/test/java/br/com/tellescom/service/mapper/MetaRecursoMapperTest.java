package br.com.tellescom.service.mapper;

import static br.com.tellescom.domain.MetaRecursoAsserts.*;
import static br.com.tellescom.domain.MetaRecursoTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MetaRecursoMapperTest {

    private MetaRecursoMapper metaRecursoMapper;

    @BeforeEach
    void setUp() {
        metaRecursoMapper = new MetaRecursoMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getMetaRecursoSample1();
        var actual = metaRecursoMapper.toEntity(metaRecursoMapper.toDto(expected));
        assertMetaRecursoAllPropertiesEquals(expected, actual);
    }
}
