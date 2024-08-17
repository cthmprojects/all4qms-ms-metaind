package br.com.tellescom.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class IndicadorMetaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static IndicadorMeta getIndicadorMetaSample1() {
        return new IndicadorMeta().id(1L).anoIndicador("anoIndicador1");
    }

    public static IndicadorMeta getIndicadorMetaSample2() {
        return new IndicadorMeta().id(2L).anoIndicador("anoIndicador2");
    }

    public static IndicadorMeta getIndicadorMetaRandomSampleGenerator() {
        return new IndicadorMeta().id(longCount.incrementAndGet()).anoIndicador(UUID.randomUUID().toString());
    }
}
