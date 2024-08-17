package br.com.tellescom.domain.response;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.io.Serializable;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class MetaResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    private Long idMeta;

    @Column(name = "meta_objetivo_id")
    private Long idMetaObjetivo;

    @Column(name = "meta_resultado_id")
    private Long idMetaResultado;

    private String descricao;

    private String avaliacao;

    private String analise;

    private Boolean parcial;

    @Column(name = "meta_atingida")
    private Boolean metaAtingida;

    @Column(name = "lancado_em")
    private Instant lancadoEm;
}
