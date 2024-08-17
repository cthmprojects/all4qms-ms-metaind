package br.com.tellescom.domain.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EnumItemResponse {

    private int cod;
    private String nome;
    private String valor;
}
