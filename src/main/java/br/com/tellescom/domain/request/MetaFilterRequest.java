package br.com.tellescom.domain.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MetaFilterRequest {

    private Long idProcesso;
    private String ano;
    private String mes;
    private String situacao; // F = Finalizado; P = Parcial
    private String pesquisa;
}
