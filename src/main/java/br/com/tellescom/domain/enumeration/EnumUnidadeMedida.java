package br.com.tellescom.domain.enumeration;

import br.com.tellescom.domain.response.EnumItemResponse;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    public static List<EnumItemResponse> toEnumItemResponseList() {
        return Arrays
            .stream(values())
            .map(value -> EnumItemResponse.builder().cod(value.ordinal()).nome(value.name()).valor(value.getValue()).build())
            .collect(Collectors.toList());
    }
}
