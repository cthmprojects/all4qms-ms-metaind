package br.com.tellescom.service.dto;

import br.com.tellescom.domain.enumeration.EnumTemporal;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link br.com.tellescom.domain.Meta} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MetaDTO implements Serializable {

    private Long id;

    private String descricao;

    private String indicador;

    private String medicao;

    private String acao;

    private String avaliacaoResultado;

    private Integer idProcesso;

    private EnumTemporal monitoramento;

    private EnumTemporal periodo;

    private Set<MetaRecursoDTO> recursos = new HashSet<>();

    private MetaObjetivoDTO metaObjetivo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getIndicador() {
        return indicador;
    }

    public void setIndicador(String indicador) {
        this.indicador = indicador;
    }

    public String getMedicao() {
        return medicao;
    }

    public void setMedicao(String medicao) {
        this.medicao = medicao;
    }

    public String getAcao() {
        return acao;
    }

    public void setAcao(String acao) {
        this.acao = acao;
    }

    public String getAvaliacaoResultado() {
        return avaliacaoResultado;
    }

    public void setAvaliacaoResultado(String avaliacaoResultado) {
        this.avaliacaoResultado = avaliacaoResultado;
    }

    public Integer getIdProcesso() {
        return idProcesso;
    }

    public void setIdProcesso(Integer idProcesso) {
        this.idProcesso = idProcesso;
    }

    public EnumTemporal getMonitoramento() {
        return monitoramento;
    }

    public void setMonitoramento(EnumTemporal monitoramento) {
        this.monitoramento = monitoramento;
    }

    public EnumTemporal getPeriodo() {
        return periodo;
    }

    public void setPeriodo(EnumTemporal periodo) {
        this.periodo = periodo;
    }

    public Set<MetaRecursoDTO> getRecursos() {
        return recursos;
    }

    public void setRecursos(Set<MetaRecursoDTO> recursos) {
        this.recursos = recursos;
    }

    public MetaObjetivoDTO getMetaObjetivo() {
        return metaObjetivo;
    }

    public void setMetaObjetivo(MetaObjetivoDTO metaObjetivo) {
        this.metaObjetivo = metaObjetivo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MetaDTO)) {
            return false;
        }

        MetaDTO metaDTO = (MetaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, metaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MetaDTO{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            ", indicador='" + getIndicador() + "'" +
            ", medicao='" + getMedicao() + "'" +
            ", acao='" + getAcao() + "'" +
            ", avaliacaoResultado='" + getAvaliacaoResultado() + "'" +
            ", idProcesso=" + getIdProcesso() +
            ", monitoramento='" + getMonitoramento() + "'" +
            ", periodo='" + getPeriodo() + "'" +
            ", recursos=" + getRecursos() +
            ", metaObjetivo=" + getMetaObjetivo() +
            "}";
    }
}
