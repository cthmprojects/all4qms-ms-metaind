package br.com.tellescom.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class MetaObjetivoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static MetaObjetivo getMetaObjetivoSample1() {
        return new MetaObjetivo().id(1L).politicaSGQ("politicaSGQ1").desdobramentoSGQ("desdobramentoSGQ1").objetivoSGQ("objetivoSGQ1");
    }

    public static MetaObjetivo getMetaObjetivoSample2() {
        return new MetaObjetivo().id(2L).politicaSGQ("politicaSGQ2").desdobramentoSGQ("desdobramentoSGQ2").objetivoSGQ("objetivoSGQ2");
    }

    public static MetaObjetivo getMetaObjetivoRandomSampleGenerator() {
        return new MetaObjetivo()
            .id(longCount.incrementAndGet())
            .politicaSGQ(UUID.randomUUID().toString())
            .desdobramentoSGQ(UUID.randomUUID().toString())
            .objetivoSGQ(UUID.randomUUID().toString());
    }
}
