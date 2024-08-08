package br.com.tellescom.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link br.com.tellescom.domain.MetaResultado} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MetaResultadoDTO implements Serializable {

    private Long id;

    private Instant lancadoEm;

    private Boolean parcial;

    private Boolean metaAtingida;

    private Instant periodo;

    @Size(max = 4000)
    private String avaliacao;

    @Size(max = 4000)
    private String analise;

    private MetaDTO meta;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getLancadoEm() {
        return lancadoEm;
    }

    public void setLancadoEm(Instant lancadoEm) {
        this.lancadoEm = lancadoEm;
    }

    public Boolean getParcial() {
        return parcial;
    }

    public void setParcial(Boolean parcial) {
        this.parcial = parcial;
    }

    public Boolean getMetaAtingida() {
        return metaAtingida;
    }

    public void setMetaAtingida(Boolean metaAtingida) {
        this.metaAtingida = metaAtingida;
    }

    public Instant getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Instant periodo) {
        this.periodo = periodo;
    }

    public String getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(String avaliacao) {
        this.avaliacao = avaliacao;
    }

    public String getAnalise() {
        return analise;
    }

    public void setAnalise(String analise) {
        this.analise = analise;
    }

    public MetaDTO getMeta() {
        return meta;
    }

    public void setMeta(MetaDTO meta) {
        this.meta = meta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MetaResultadoDTO)) {
            return false;
        }

        MetaResultadoDTO metaResultadoDTO = (MetaResultadoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, metaResultadoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MetaResultadoDTO{" +
            "id=" + getId() +
            ", lancadoEm='" + getLancadoEm() + "'" +
            ", parcial='" + getParcial() + "'" +
            ", metaAtingida='" + getMetaAtingida() + "'" +
            ", periodo='" + getPeriodo() + "'" +
            ", avaliacao='" + getAvaliacao() + "'" +
            ", analise='" + getAnalise() + "'" +
            ", meta=" + getMeta() +
            "}";
    }
}
