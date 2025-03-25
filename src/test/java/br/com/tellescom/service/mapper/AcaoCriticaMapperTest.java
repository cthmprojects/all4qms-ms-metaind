package br.com.tellescom.service.mapper;

import static br.com.tellescom.domain.AcaoCriticaAsserts.*;
import static br.com.tellescom.domain.AcaoCriticaTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AcaoCriticaMapperTest {

    private AcaoCriticaMapper acaoCriticaMapper;

    @BeforeEach
    void setUp() {
        acaoCriticaMapper = new AcaoCriticaMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getAcaoCriticaSample1();
        var actual = acaoCriticaMapper.toEntity(acaoCriticaMapper.toDto(expected));
        assertAcaoCriticaAllPropertiesEquals(expected, actual);
    }
}
