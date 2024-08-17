package br.com.tellescom.service.criteria;

import br.com.tellescom.domain.enumeration.EnumTemporal;
import br.com.tellescom.domain.enumeration.EnumTemporal;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link br.com.tellescom.domain.Meta} entity. This class is used
 * in {@link br.com.tellescom.web.rest.MetaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /metas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MetaCriteria implements Serializable, Criteria {

    /**
     * Class for filtering EnumTemporal
     */
    public static class EnumTemporalFilter extends Filter<EnumTemporal> {

        public EnumTemporalFilter() {}

        public EnumTemporalFilter(EnumTemporalFilter filter) {
            super(filter);
        }

        @Override
        public EnumTemporalFilter copy() {
            return new EnumTemporalFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter descricao;

    private StringFilter indicador;

    private StringFilter medicao;

    private StringFilter acao;

    private StringFilter avaliacaoResultado;

    private IntegerFilter idProcesso;

    private EnumTemporalFilter monitoramento;

    private EnumTemporalFilter periodo;

    private LongFilter resultadosId;

    private LongFilter recursosId;

    private LongFilter metaObjetivoId;

    private Boolean distinct;

    public MetaCriteria() {}

    public MetaCriteria(MetaCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.descricao = other.descricao == null ? null : other.descricao.copy();
        this.indicador = other.indicador == null ? null : other.indicador.copy();
        this.medicao = other.medicao == null ? null : other.medicao.copy();
        this.acao = other.acao == null ? null : other.acao.copy();
        this.avaliacaoResultado = other.avaliacaoResultado == null ? null : other.avaliacaoResultado.copy();
        this.idProcesso = other.idProcesso == null ? null : other.idProcesso.copy();
        this.monitoramento = other.monitoramento == null ? null : other.monitoramento.copy();
        this.periodo = other.periodo == null ? null : other.periodo.copy();
        this.resultadosId = other.resultadosId == null ? null : other.resultadosId.copy();
        this.recursosId = other.recursosId == null ? null : other.recursosId.copy();
        this.metaObjetivoId = other.metaObjetivoId == null ? null : other.metaObjetivoId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public MetaCriteria copy() {
        return new MetaCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getDescricao() {
        return descricao;
    }

    public StringFilter descricao() {
        if (descricao == null) {
            descricao = new StringFilter();
        }
        return descricao;
    }

    public void setDescricao(StringFilter descricao) {
        this.descricao = descricao;
    }

    public StringFilter getIndicador() {
        return indicador;
    }

    public StringFilter indicador() {
        if (indicador == null) {
            indicador = new StringFilter();
        }
        return indicador;
    }

    public void setIndicador(StringFilter indicador) {
        this.indicador = indicador;
    }

    public StringFilter getMedicao() {
        return medicao;
    }

    public StringFilter medicao() {
        if (medicao == null) {
            medicao = new StringFilter();
        }
        return medicao;
    }

    public void setMedicao(StringFilter medicao) {
        this.medicao = medicao;
    }

    public StringFilter getAcao() {
        return acao;
    }

    public StringFilter acao() {
        if (acao == null) {
            acao = new StringFilter();
        }
        return acao;
    }

    public void setAcao(StringFilter acao) {
        this.acao = acao;
    }

    public StringFilter getAvaliacaoResultado() {
        return avaliacaoResultado;
    }

    public StringFilter avaliacaoResultado() {
        if (avaliacaoResultado == null) {
            avaliacaoResultado = new StringFilter();
        }
        return avaliacaoResultado;
    }

    public void setAvaliacaoResultado(StringFilter avaliacaoResultado) {
        this.avaliacaoResultado = avaliacaoResultado;
    }

    public IntegerFilter getIdProcesso() {
        return idProcesso;
    }

    public IntegerFilter idProcesso() {
        if (idProcesso == null) {
            idProcesso = new IntegerFilter();
        }
        return idProcesso;
    }

    public void setIdProcesso(IntegerFilter idProcesso) {
        this.idProcesso = idProcesso;
    }

    public EnumTemporalFilter getMonitoramento() {
        return monitoramento;
    }

    public EnumTemporalFilter monitoramento() {
        if (monitoramento == null) {
            monitoramento = new EnumTemporalFilter();
        }
        return monitoramento;
    }

    public void setMonitoramento(EnumTemporalFilter monitoramento) {
        this.monitoramento = monitoramento;
    }

    public EnumTemporalFilter getPeriodo() {
        return periodo;
    }

    public EnumTemporalFilter periodo() {
        if (periodo == null) {
            periodo = new EnumTemporalFilter();
        }
        return periodo;
    }

    public void setPeriodo(EnumTemporalFilter periodo) {
        this.periodo = periodo;
    }

    public LongFilter getResultadosId() {
        return resultadosId;
    }

    public LongFilter resultadosId() {
        if (resultadosId == null) {
            resultadosId = new LongFilter();
        }
        return resultadosId;
    }

    public void setResultadosId(LongFilter resultadosId) {
        this.resultadosId = resultadosId;
    }

    public LongFilter getRecursosId() {
        return recursosId;
    }

    public LongFilter recursosId() {
        if (recursosId == null) {
            recursosId = new LongFilter();
        }
        return recursosId;
    }

    public void setRecursosId(LongFilter recursosId) {
        this.recursosId = recursosId;
    }

    public LongFilter getMetaObjetivoId() {
        return metaObjetivoId;
    }

    public LongFilter metaObjetivoId() {
        if (metaObjetivoId == null) {
            metaObjetivoId = new LongFilter();
        }
        return metaObjetivoId;
    }

    public void setMetaObjetivoId(LongFilter metaObjetivoId) {
        this.metaObjetivoId = metaObjetivoId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MetaCriteria that = (MetaCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(descricao, that.descricao) &&
            Objects.equals(indicador, that.indicador) &&
            Objects.equals(medicao, that.medicao) &&
            Objects.equals(acao, that.acao) &&
            Objects.equals(avaliacaoResultado, that.avaliacaoResultado) &&
            Objects.equals(idProcesso, that.idProcesso) &&
            Objects.equals(monitoramento, that.monitoramento) &&
            Objects.equals(periodo, that.periodo) &&
            Objects.equals(resultadosId, that.resultadosId) &&
            Objects.equals(recursosId, that.recursosId) &&
            Objects.equals(metaObjetivoId, that.metaObjetivoId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            descricao,
            indicador,
            medicao,
            acao,
            avaliacaoResultado,
            idProcesso,
            monitoramento,
            periodo,
            resultadosId,
            recursosId,
            metaObjetivoId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MetaCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (descricao != null ? "descricao=" + descricao + ", " : "") +
            (indicador != null ? "indicador=" + indicador + ", " : "") +
            (medicao != null ? "medicao=" + medicao + ", " : "") +
            (acao != null ? "acao=" + acao + ", " : "") +
            (avaliacaoResultado != null ? "avaliacaoResultado=" + avaliacaoResultado + ", " : "") +
            (idProcesso != null ? "idProcesso=" + idProcesso + ", " : "") +
            (monitoramento != null ? "monitoramento=" + monitoramento + ", " : "") +
            (periodo != null ? "periodo=" + periodo + ", " : "") +
            (resultadosId != null ? "resultadosId=" + resultadosId + ", " : "") +
            (recursosId != null ? "recursosId=" + recursosId + ", " : "") +
            (metaObjetivoId != null ? "metaObjetivoId=" + metaObjetivoId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
