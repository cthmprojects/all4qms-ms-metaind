package br.com.tellescom.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class IndicadorCriticaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static IndicadorCritica getIndicadorCriticaSample1() {
        return new IndicadorCritica()
            .id(1L)
            .idIndicadorMeta(1L)
            .idPlano(1L)
            .analiseCritica("analiseCritica1")
            .observacao("observacao1")
            .mes(1)
            .ano(1)
            .criadoPor(1L)
            .atualizadoPor(1L);
    }

    public static IndicadorCritica getIndicadorCriticaSample2() {
        return new IndicadorCritica()
            .id(2L)
            .idIndicadorMeta(2L)
            .idPlano(2L)
            .analiseCritica("analiseCritica2")
            .observacao("observacao2")
            .mes(2)
            .ano(2)
            .criadoPor(2L)
            .atualizadoPor(2L);
    }

    public static IndicadorCritica getIndicadorCriticaRandomSampleGenerator() {
        return new IndicadorCritica()
            .id(longCount.incrementAndGet())
            .idIndicadorMeta(longCount.incrementAndGet())
            .idPlano(longCount.incrementAndGet())
            .analiseCritica(UUID.randomUUID().toString())
            .observacao(UUID.randomUUID().toString())
            .mes(intCount.incrementAndGet())
            .ano(intCount.incrementAndGet())
            .criadoPor(longCount.incrementAndGet())
            .atualizadoPor(longCount.incrementAndGet());
    }
}
