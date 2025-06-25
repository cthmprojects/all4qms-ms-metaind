package br.com.tellescom.domain.response;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "meta")
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

    @Column(name = "monitoramento_controle")
    private String indicadorControle;

    private Boolean parcial;

    @Column(name = "meta_atingida")
    private Boolean metaAtingida;

    @Column(name = "resultado_final")
    private Boolean resultadoFinal;

    @Column(name = "lancado_em")
    private Instant lancadoEm;

    @Column(name = "fl_ativo")
    private Integer flAtivo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MetaResponse)) return false;
        MetaResponse that = (MetaResponse) o;
        return Objects.equals(idMeta, that.idMeta);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idMeta);
    }
}
