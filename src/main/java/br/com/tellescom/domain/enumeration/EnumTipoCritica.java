package br.com.tellescom.domain.enumeration;

import br.com.tellescom.domain.response.EnumItemResponse;

import java.util.Arrays;
import java.util.List;

public enum EnumTipoCritica {

    CRIAR("Criar"),
    ATUALIZAR("Atualizar"),
    CANCELAR("Cancelar");

    private final String value;

    EnumTipoCritica(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static List<EnumItemResponse> toEnumItemResponseList() {
        return Arrays
            .stream(values())
            .map(value -> EnumItemResponse.builder().cod(value.ordinal()).nome(value.name()).valor(value.getValue()).build())
            .toList();
    }
}
