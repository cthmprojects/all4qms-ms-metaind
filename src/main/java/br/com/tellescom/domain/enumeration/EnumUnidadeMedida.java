package br.com.tellescom.domain.enumeration;

/**
 * The EnumUnidadeMedida enumeration.
 */
public enum EnumUnidadeMedida {
    PERCENTUAL("Percentual"),
    MONETARIO("Monetário"),
    UNITARIO("Unitário"),
    DECIMAL("Decimal");

    private final String value;

    EnumUnidadeMedida(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
