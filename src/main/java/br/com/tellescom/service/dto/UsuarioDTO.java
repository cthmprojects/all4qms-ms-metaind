package br.com.tellescom.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UsuarioDTO implements Serializable {

    private Long id;
    private String nome;
    private String email;
}
