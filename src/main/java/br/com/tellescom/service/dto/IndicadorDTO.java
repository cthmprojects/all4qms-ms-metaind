package br.com.tellescom.service.dto;

import br.com.tellescom.domain.enumeration.EnumTendencia;
import br.com.tellescom.domain.enumeration.EnumUnidadeMedida;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link br.com.tellescom.domain.Indicador} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class IndicadorDTO implements Serializable {

    private Long id;

    private String codigoIndicador;

    private String nomeIndicador;

    private String descricaoIndicador;

    private EnumUnidadeMedida unidade;

    private EnumTendencia tendencia;

    private Integer idProcesso;

    private Integer idMetaIndicador;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigoIndicador() {
        return codigoIndicador;
    }

    public void setCodigoIndicador(String codigoIndicador) {
        this.codigoIndicador = codigoIndicador;
    }

    public String getNomeIndicador() {
        return nomeIndicador;
    }

    public void setNomeIndicador(String nomeIndicador) {
        this.nomeIndicador = nomeIndicador;
    }

    public String getDescricaoIndicador() {
        return descricaoIndicador;
    }

    public void setDescricaoIndicador(String descricaoIndicador) {
        this.descricaoIndicador = descricaoIndicador;
    }

    public EnumUnidadeMedida getUnidade() {
        return unidade;
    }

    public void setUnidade(EnumUnidadeMedida unidade) {
        this.unidade = unidade;
    }

    public EnumTendencia getTendencia() {
        return tendencia;
    }

    public void setTendencia(EnumTendencia tendencia) {
        this.tendencia = tendencia;
    }

    public Integer getIdProcesso() {
        return idProcesso;
    }

    public void setIdProcesso(Integer idProcesso) {
        this.idProcesso = idProcesso;
    }

    public Integer getIdMetaIndicador() {
        return idMetaIndicador;
    }

    public void setIdMetaIndicador(Integer idMetaIndicador) {
        this.idMetaIndicador = idMetaIndicador;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IndicadorDTO)) {
            return false;
        }

        IndicadorDTO indicadorDTO = (IndicadorDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, indicadorDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IndicadorDTO{" +
            "id=" + getId() +
            ", codigoIndicador='" + getCodigoIndicador() + "'" +
            ", nomeIndicador='" + getNomeIndicador() + "'" +
            ", descricaoIndicador='" + getDescricaoIndicador() + "'" +
            ", unidade='" + getUnidade() + "'" +
            ", tendencia='" + getTendencia() + "'" +
            ", idProcesso=" + getIdProcesso() +
            ", idMetaIndicador=" + getIdMetaIndicador() +
            "}";
    }
}
