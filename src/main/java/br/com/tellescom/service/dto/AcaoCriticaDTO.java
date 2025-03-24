package br.com.tellescom.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link br.com.tellescom.domain.AcaoCritica} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AcaoCriticaDTO implements Serializable {

    private Long id;

    @Size(max = 4000)
    private String acaoCritica;

    private String nomeResponsavel;

    private Long idResponsavel;

    private LocalDate dataAcao;

    @Size(max = 4000)
    private String instrucaoAcao;

    @NotNull
    private Long idIndicadorCritica;

    private ZonedDateTime criadoEm;

    private ZonedDateTime atualizadoEm;

    private Boolean isNotificado;

    private IndicadorCriticaDTO indicadorCritica;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAcaoCritica() {
        return acaoCritica;
    }

    public void setAcaoCritica(String acaoCritica) {
        this.acaoCritica = acaoCritica;
    }

    public String getNomeResponsavel() {
        return nomeResponsavel;
    }

    public void setNomeResponsavel(String nomeResponsavel) {
        this.nomeResponsavel = nomeResponsavel;
    }

    public Long getIdResponsavel() {
        return idResponsavel;
    }

    public void setIdResponsavel(Long idResponsavel) {
        this.idResponsavel = idResponsavel;
    }

    public LocalDate getDataAcao() {
        return dataAcao;
    }

    public void setDataAcao(LocalDate dataAcao) {
        this.dataAcao = dataAcao;
    }

    public String getInstrucaoAcao() {
        return instrucaoAcao;
    }

    public void setInstrucaoAcao(String instrucaoAcao) {
        this.instrucaoAcao = instrucaoAcao;
    }

    public Long getIdIndicadorCritica() {
        return idIndicadorCritica;
    }

    public void setIdIndicadorCritica(Long idIndicadorCritica) {
        this.idIndicadorCritica = idIndicadorCritica;
    }

    public ZonedDateTime getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(ZonedDateTime criadoEm) {
        this.criadoEm = criadoEm;
    }

    public ZonedDateTime getAtualizadoEm() {
        return atualizadoEm;
    }

    public void setAtualizadoEm(ZonedDateTime atualizadoEm) {
        this.atualizadoEm = atualizadoEm;
    }

    public Boolean getIsNotificado() {
        return isNotificado;
    }

    public void setIsNotificado(Boolean isNotificado) {
        this.isNotificado = isNotificado;
    }

    public IndicadorCriticaDTO getIndicadorCritica() {
        return indicadorCritica;
    }

    public void setIndicadorCritica(IndicadorCriticaDTO indicadorCritica) {
        this.indicadorCritica = indicadorCritica;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AcaoCriticaDTO)) {
            return false;
        }

        AcaoCriticaDTO acaoCriticaDTO = (AcaoCriticaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, acaoCriticaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AcaoCriticaDTO{" +
            "id=" + getId() +
            ", acaoCritica='" + getAcaoCritica() + "'" +
            ", nomeResponsavel='" + getNomeResponsavel() + "'" +
            ", idResponsavel=" + getIdResponsavel() +
            ", dataAcao='" + getDataAcao() + "'" +
            ", instrucaoAcao='" + getInstrucaoAcao() + "'" +
            ", idIndicadorCritica=" + getIdIndicadorCritica() +
            ", criadoEm='" + getCriadoEm() + "'" +
            ", atualizadoEm='" + getAtualizadoEm() + "'" +
            ", isNotificado='" + getIsNotificado() + "'" +
            ", indicadorCritica=" + getIndicadorCritica() +
            "}";
    }
}
