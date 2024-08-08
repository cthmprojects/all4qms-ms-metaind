package br.com.tellescom.service.mapper;

import static br.com.tellescom.domain.MetaResultadoAsserts.*;
import static br.com.tellescom.domain.MetaResultadoTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MetaResultadoMapperTest {

    private MetaResultadoMapper metaResultadoMapper;

    @BeforeEach
    void setUp() {
        metaResultadoMapper = new MetaResultadoMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getMetaResultadoSample1();
        var actual = metaResultadoMapper.toEntity(metaResultadoMapper.toDto(expected));
        assertMetaResultadoAllPropertiesEquals(expected, actual);
    }
}
