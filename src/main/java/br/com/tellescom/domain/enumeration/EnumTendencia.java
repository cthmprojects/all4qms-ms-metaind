package br.com.tellescom.domain.enumeration;

/**
 * The EnumTendencia enumeration.
 */
public enum EnumTendencia {
    MAIOR("Maior_Melhor"),
    MENOR("Menor_Melhor"),
    ESTABILIZAR("Estabilizar");

    private final String value;

    EnumTendencia(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
