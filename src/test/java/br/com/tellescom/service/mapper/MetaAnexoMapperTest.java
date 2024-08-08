package br.com.tellescom.service.mapper;

import static br.com.tellescom.domain.MetaAnexoAsserts.*;
import static br.com.tellescom.domain.MetaAnexoTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MetaAnexoMapperTest {

    private MetaAnexoMapper metaAnexoMapper;

    @BeforeEach
    void setUp() {
        metaAnexoMapper = new MetaAnexoMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getMetaAnexoSample1();
        var actual = metaAnexoMapper.toEntity(metaAnexoMapper.toDto(expected));
        assertMetaAnexoAllPropertiesEquals(expected, actual);
    }
}
