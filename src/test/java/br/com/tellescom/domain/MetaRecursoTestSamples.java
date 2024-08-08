package br.com.tellescom.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class MetaRecursoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static MetaRecurso getMetaRecursoSample1() {
        return new MetaRecurso().id(1L).recursoNome("recursoNome1");
    }

    public static MetaRecurso getMetaRecursoSample2() {
        return new MetaRecurso().id(2L).recursoNome("recursoNome2");
    }

    public static MetaRecurso getMetaRecursoRandomSampleGenerator() {
        return new MetaRecurso().id(longCount.incrementAndGet()).recursoNome(UUID.randomUUID().toString());
    }
}
