package br.com.tellescom.service.criteria;

import br.com.tellescom.domain.enumeration.EnumTemporal;
import br.com.tellescom.domain.enumeration.EnumTemporal;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
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
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.descricao = other.optionalDescricao().map(StringFilter::copy).orElse(null);
        this.indicador = other.optionalIndicador().map(StringFilter::copy).orElse(null);
        this.medicao = other.optionalMedicao().map(StringFilter::copy).orElse(null);
        this.acao = other.optionalAcao().map(StringFilter::copy).orElse(null);
        this.avaliacaoResultado = other.optionalAvaliacaoResultado().map(StringFilter::copy).orElse(null);
        this.idProcesso = other.optionalIdProcesso().map(IntegerFilter::copy).orElse(null);
        this.monitoramento = other.optionalMonitoramento().map(EnumTemporalFilter::copy).orElse(null);
        this.periodo = other.optionalPeriodo().map(EnumTemporalFilter::copy).orElse(null);
        this.resultadosId = other.optionalResultadosId().map(LongFilter::copy).orElse(null);
        this.recursosId = other.optionalRecursosId().map(LongFilter::copy).orElse(null);
        this.metaObjetivoId = other.optionalMetaObjetivoId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public MetaCriteria copy() {
        return new MetaCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public Optional<LongFilter> optionalId() {
        return Optional.ofNullable(id);
    }

    public LongFilter id() {
        if (id == null) {
            setId(new LongFilter());
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getDescricao() {
        return descricao;
    }

    public Optional<StringFilter> optionalDescricao() {
        return Optional.ofNullable(descricao);
    }

    public StringFilter descricao() {
        if (descricao == null) {
            setDescricao(new StringFilter());
        }
        return descricao;
    }

    public void setDescricao(StringFilter descricao) {
        this.descricao = descricao;
    }

    public StringFilter getIndicador() {
        return indicador;
    }

    public Optional<StringFilter> optionalIndicador() {
        return Optional.ofNullable(indicador);
    }

    public StringFilter indicador() {
        if (indicador == null) {
            setIndicador(new StringFilter());
        }
        return indicador;
    }

    public void setIndicador(StringFilter indicador) {
        this.indicador = indicador;
    }

    public StringFilter getMedicao() {
        return medicao;
    }

    public Optional<StringFilter> optionalMedicao() {
        return Optional.ofNullable(medicao);
    }

    public StringFilter medicao() {
        if (medicao == null) {
            setMedicao(new StringFilter());
        }
        return medicao;
    }

    public void setMedicao(StringFilter medicao) {
        this.medicao = medicao;
    }

    public StringFilter getAcao() {
        return acao;
    }

    public Optional<StringFilter> optionalAcao() {
        return Optional.ofNullable(acao);
    }

    public StringFilter acao() {
        if (acao == null) {
            setAcao(new StringFilter());
        }
        return acao;
    }

    public void setAcao(StringFilter acao) {
        this.acao = acao;
    }

    public StringFilter getAvaliacaoResultado() {
        return avaliacaoResultado;
    }

    public Optional<StringFilter> optionalAvaliacaoResultado() {
        return Optional.ofNullable(avaliacaoResultado);
    }

    public StringFilter avaliacaoResultado() {
        if (avaliacaoResultado == null) {
            setAvaliacaoResultado(new StringFilter());
        }
        return avaliacaoResultado;
    }

    public void setAvaliacaoResultado(StringFilter avaliacaoResultado) {
        this.avaliacaoResultado = avaliacaoResultado;
    }

    public IntegerFilter getIdProcesso() {
        return idProcesso;
    }

    public Optional<IntegerFilter> optionalIdProcesso() {
        return Optional.ofNullable(idProcesso);
    }

    public IntegerFilter idProcesso() {
        if (idProcesso == null) {
            setIdProcesso(new IntegerFilter());
        }
        return idProcesso;
    }

    public void setIdProcesso(IntegerFilter idProcesso) {
        this.idProcesso = idProcesso;
    }

    public EnumTemporalFilter getMonitoramento() {
        return monitoramento;
    }

    public Optional<EnumTemporalFilter> optionalMonitoramento() {
        return Optional.ofNullable(monitoramento);
    }

    public EnumTemporalFilter monitoramento() {
        if (monitoramento == null) {
            setMonitoramento(new EnumTemporalFilter());
        }
        return monitoramento;
    }

    public void setMonitoramento(EnumTemporalFilter monitoramento) {
        this.monitoramento = monitoramento;
    }

    public EnumTemporalFilter getPeriodo() {
        return periodo;
    }

    public Optional<EnumTemporalFilter> optionalPeriodo() {
        return Optional.ofNullable(periodo);
    }

    public EnumTemporalFilter periodo() {
        if (periodo == null) {
            setPeriodo(new EnumTemporalFilter());
        }
        return periodo;
    }

    public void setPeriodo(EnumTemporalFilter periodo) {
        this.periodo = periodo;
    }

    public LongFilter getResultadosId() {
        return resultadosId;
    }

    public Optional<LongFilter> optionalResultadosId() {
        return Optional.ofNullable(resultadosId);
    }

    public LongFilter resultadosId() {
        if (resultadosId == null) {
            setResultadosId(new LongFilter());
        }
        return resultadosId;
    }

    public void setResultadosId(LongFilter resultadosId) {
        this.resultadosId = resultadosId;
    }

    public LongFilter getRecursosId() {
        return recursosId;
    }

    public Optional<LongFilter> optionalRecursosId() {
        return Optional.ofNullable(recursosId);
    }

    public LongFilter recursosId() {
        if (recursosId == null) {
            setRecursosId(new LongFilter());
        }
        return recursosId;
    }

    public void setRecursosId(LongFilter recursosId) {
        this.recursosId = recursosId;
    }

    public LongFilter getMetaObjetivoId() {
        return metaObjetivoId;
    }

    public Optional<LongFilter> optionalMetaObjetivoId() {
        return Optional.ofNullable(metaObjetivoId);
    }

    public LongFilter metaObjetivoId() {
        if (metaObjetivoId == null) {
            setMetaObjetivoId(new LongFilter());
        }
        return metaObjetivoId;
    }

    public void setMetaObjetivoId(LongFilter metaObjetivoId) {
        this.metaObjetivoId = metaObjetivoId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public Optional<Boolean> optionalDistinct() {
        return Optional.ofNullable(distinct);
    }

    public Boolean distinct() {
        if (distinct == null) {
            setDistinct(true);
        }
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
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalDescricao().map(f -> "descricao=" + f + ", ").orElse("") +
            optionalIndicador().map(f -> "indicador=" + f + ", ").orElse("") +
            optionalMedicao().map(f -> "medicao=" + f + ", ").orElse("") +
            optionalAcao().map(f -> "acao=" + f + ", ").orElse("") +
            optionalAvaliacaoResultado().map(f -> "avaliacaoResultado=" + f + ", ").orElse("") +
            optionalIdProcesso().map(f -> "idProcesso=" + f + ", ").orElse("") +
            optionalMonitoramento().map(f -> "monitoramento=" + f + ", ").orElse("") +
            optionalPeriodo().map(f -> "periodo=" + f + ", ").orElse("") +
            optionalResultadosId().map(f -> "resultadosId=" + f + ", ").orElse("") +
            optionalRecursosId().map(f -> "recursosId=" + f + ", ").orElse("") +
            optionalMetaObjetivoId().map(f -> "metaObjetivoId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
