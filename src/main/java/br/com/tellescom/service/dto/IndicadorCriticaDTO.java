package br.com.tellescom.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link br.com.tellescom.domain.IndicadorCritica} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class IndicadorCriticaDTO implements Serializable {

    private Long id;

    private Long idIndicadorMeta;

    private Long idPlano;

    @Size(max = 4000)
    private String analiseCritica;

    @Size(max = 4000)
    private String observacao;

    private Integer mes;

    private Integer ano;

    private Long criadoPor;

    private Instant criadoEm;

    private Long atualizadoPor;

    private Instant atualizadoEm;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdIndicadorMeta() {
        return idIndicadorMeta;
    }

    public void setIdIndicadorMeta(Long idIndicadorMeta) {
        this.idIndicadorMeta = idIndicadorMeta;
    }

    public Long getIdPlano() {
        return idPlano;
    }

    public void setIdPlano(Long idPlano) {
        this.idPlano = idPlano;
    }

    public String getAnaliseCritica() {
        return analiseCritica;
    }

    public void setAnaliseCritica(String analiseCritica) {
        this.analiseCritica = analiseCritica;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public Long getCriadoPor() {
        return criadoPor;
    }

    public void setCriadoPor(Long criadoPor) {
        this.criadoPor = criadoPor;
    }

    public Instant getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(Instant criadoEm) {
        this.criadoEm = criadoEm;
    }

    public Long getAtualizadoPor() {
        return atualizadoPor;
    }

    public void setAtualizadoPor(Long atualizadoPor) {
        this.atualizadoPor = atualizadoPor;
    }

    public Instant getAtualizadoEm() {
        return atualizadoEm;
    }

    public void setAtualizadoEm(Instant atualizadoEm) {
        this.atualizadoEm = atualizadoEm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IndicadorCriticaDTO)) {
            return false;
        }

        IndicadorCriticaDTO indicadorCriticaDTO = (IndicadorCriticaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, indicadorCriticaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IndicadorCriticaDTO{" +
            "id=" + getId() +
            ", idIndicadorMeta=" + getIdIndicadorMeta() +
            ", idPlano=" + getIdPlano() +
            ", analiseCritica='" + getAnaliseCritica() + "'" +
            ", observacao='" + getObservacao() + "'" +
            ", mes=" + getMes() +
            ", ano=" + getAno() +
            ", criadoPor=" + getCriadoPor() +
            ", criadoEm='" + getCriadoEm() + "'" +
            ", atualizadoPor=" + getAtualizadoPor() +
            ", atualizadoEm='" + getAtualizadoEm() + "'" +
            "}";
    }
}
