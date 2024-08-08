package br.com.tellescom.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class IndicadorTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Indicador getIndicadorSample1() {
        return new Indicador()
            .id(1L)
            .codigoIndicador("codigoIndicador1")
            .nomeIndicador("nomeIndicador1")
            .descricaoIndicador("descricaoIndicador1")
            .idProcesso(1)
            .idMetaIndicador(1);
    }

    public static Indicador getIndicadorSample2() {
        return new Indicador()
            .id(2L)
            .codigoIndicador("codigoIndicador2")
            .nomeIndicador("nomeIndicador2")
            .descricaoIndicador("descricaoIndicador2")
            .idProcesso(2)
            .idMetaIndicador(2);
    }

    public static Indicador getIndicadorRandomSampleGenerator() {
        return new Indicador()
            .id(longCount.incrementAndGet())
            .codigoIndicador(UUID.randomUUID().toString())
            .nomeIndicador(UUID.randomUUID().toString())
            .descricaoIndicador(UUID.randomUUID().toString())
            .idProcesso(intCount.incrementAndGet())
            .idMetaIndicador(intCount.incrementAndGet());
    }
}
