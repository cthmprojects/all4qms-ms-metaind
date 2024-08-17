package br.com.tellescom.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class MetaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Meta getMetaSample1() {
        return new Meta()
            .id(1L)
            .descricao("descricao1")
            .indicador("indicador1")
            .medicao("medicao1")
            .acao("acao1")
            .avaliacaoResultado("avaliacaoResultado1")
            .idProcesso(1);
    }

    public static Meta getMetaSample2() {
        return new Meta()
            .id(2L)
            .descricao("descricao2")
            .indicador("indicador2")
            .medicao("medicao2")
            .acao("acao2")
            .avaliacaoResultado("avaliacaoResultado2")
            .idProcesso(2);
    }

    public static Meta getMetaRandomSampleGenerator() {
        return new Meta()
            .id(longCount.incrementAndGet())
            .descricao(UUID.randomUUID().toString())
            .indicador(UUID.randomUUID().toString())
            .medicao(UUID.randomUUID().toString())
            .acao(UUID.randomUUID().toString())
            .avaliacaoResultado(UUID.randomUUID().toString())
            .idProcesso(intCount.incrementAndGet());
    }
}
