package br.com.tellescom.domain.enumeration;

/**
 * The EnumTemporal enumeration.
 */
public enum EnumTemporal {
    MENSAL("Mensal"),
    BIMESTRAL("Bimestral"),
    TRIMESTRAL("Trimestral"),
    QUADRIMESTRAL("Quadrimestral"),
    SEMESTRAL("Semestral"),
    ANUAL("Anual");

    private final String value;

    EnumTemporal(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
