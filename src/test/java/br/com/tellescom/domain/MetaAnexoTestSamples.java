package br.com.tellescom.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class MetaAnexoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static MetaAnexo getMetaAnexoSample1() {
        return new MetaAnexo().id(1L).nomeFisico("nomeFisico1").nomeOriginal("nomeOriginal1").extensao("extensao1").caminho("caminho1");
    }

    public static MetaAnexo getMetaAnexoSample2() {
        return new MetaAnexo().id(2L).nomeFisico("nomeFisico2").nomeOriginal("nomeOriginal2").extensao("extensao2").caminho("caminho2");
    }

    public static MetaAnexo getMetaAnexoRandomSampleGenerator() {
        return new MetaAnexo()
            .id(longCount.incrementAndGet())
            .nomeFisico(UUID.randomUUID().toString())
            .nomeOriginal(UUID.randomUUID().toString())
            .extensao(UUID.randomUUID().toString())
            .caminho(UUID.randomUUID().toString());
    }
}
