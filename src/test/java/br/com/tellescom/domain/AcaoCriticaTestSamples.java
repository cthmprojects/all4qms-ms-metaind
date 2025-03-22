package br.com.tellescom.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AcaoCriticaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static AcaoCritica getAcaoCriticaSample1() {
        return new AcaoCritica()
            .id(1L)
            .acaoCritica("acaoCritica1")
            .nomeResponsavel("nomeResponsavel1")
            .idResponsavel(1L)
            .instrucaoAcao("instrucaoAcao1");
    }

    public static AcaoCritica getAcaoCriticaSample2() {
        return new AcaoCritica()
            .id(2L)
            .acaoCritica("acaoCritica2")
            .nomeResponsavel("nomeResponsavel2")
            .idResponsavel(2L)
            .instrucaoAcao("instrucaoAcao2");
    }

    public static AcaoCritica getAcaoCriticaRandomSampleGenerator() {
        return new AcaoCritica()
            .id(longCount.incrementAndGet())
            .acaoCritica(UUID.randomUUID().toString())
            .nomeResponsavel(UUID.randomUUID().toString())
            .idResponsavel(longCount.incrementAndGet())
            .instrucaoAcao(UUID.randomUUID().toString());
    }
}
