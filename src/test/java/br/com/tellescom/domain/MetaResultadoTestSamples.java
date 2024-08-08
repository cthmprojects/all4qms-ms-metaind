package br.com.tellescom.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class MetaResultadoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static MetaResultado getMetaResultadoSample1() {
        return new MetaResultado().id(1L).avaliacao("avaliacao1").analise("analise1");
    }

    public static MetaResultado getMetaResultadoSample2() {
        return new MetaResultado().id(2L).avaliacao("avaliacao2").analise("analise2");
    }

    public static MetaResultado getMetaResultadoRandomSampleGenerator() {
        return new MetaResultado()
            .id(longCount.incrementAndGet())
            .avaliacao(UUID.randomUUID().toString())
            .analise(UUID.randomUUID().toString());
    }
}
