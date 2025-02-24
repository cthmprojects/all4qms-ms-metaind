package br.com.tellescom.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Meta.
 */
@Entity
@Table(name = Meta.TABLE)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Meta implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String TABLE = "meta";
    public static final String SEQUENCE = TABLE + "_id_seq";

    @Id
    @SequenceGenerator(name = SEQUENCE, sequenceName = SEQUENCE, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "monitoramento_controle")
    private String monitoramentoControle;

    @Column(name = "descricao_monitoramento_controle")
    private String descricaoMonitoramentoControle;

    @Column(name = "frequencia")
    private String frequencia;

    @Column(name = "acao")
    private String acao;

    @Column(name = "avaliacao_resultado")
    private String avaliacaoResultado;

    @Column(name = "id_processo")
    private Integer idProcesso;

    @Column(name = "monitoramento")
    private String monitoramento;

    @Column(name = "periodo")
    private String periodo;

    @Column(name = "fl_ativo")
    private Integer flAtivo;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "meta")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = {"anexos", "meta"}, allowSetters = true)
    private Set<MetaResultado> resultados = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_meta__recursos",
        joinColumns = @JoinColumn(name = "meta_id"),
        inverseJoinColumns = @JoinColumn(name = "recursos_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = {"metas"}, allowSetters = true)
    private Set<MetaRecurso> recursos = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = {"metas"}, allowSetters = true)
    private MetaObjetivo metaObjetivo;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Meta id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public Meta descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getMonitoramentoControle() {
        return this.monitoramentoControle;
    }

    public Meta monitoramentoControle(String monitoramentoControle) {
        this.setMonitoramentoControle(monitoramentoControle);
        return this;
    }

    public void setMonitoramentoControle(String monitoramentoControle) {
        this.monitoramentoControle = monitoramentoControle;
    }

    public String getDescricaoMonitoramentoControle() {
        return this.descricaoMonitoramentoControle;
    }

    public Meta descricaoMonitoramentoControle(String descricaoMonitoramentoControle) {
        this.setDescricaoMonitoramentoControle(descricaoMonitoramentoControle);
        return this;
    }

    public void setDescricaoMonitoramentoControle(String descricaoMonitoramentoControle) {
        this.descricaoMonitoramentoControle = descricaoMonitoramentoControle;
    }

    public String getFrequencia() {
        return frequencia;
    }

    public Meta frequencia(String frequencia) {
        this.setFrequencia(frequencia);
        return this;
    }

    public void setFrequencia(String frequencia) {
        this.frequencia = frequencia;
    }

    public String getAcao() {
        return this.acao;
    }

    public Meta acao(String acao) {
        this.setAcao(acao);
        return this;
    }

    public void setAcao(String acao) {
        this.acao = acao;
    }

    public String getAvaliacaoResultado() {
        return this.avaliacaoResultado;
    }

    public Meta avaliacaoResultado(String avaliacaoResultado) {
        this.setAvaliacaoResultado(avaliacaoResultado);
        return this;
    }

    public void setAvaliacaoResultado(String avaliacaoResultado) {
        this.avaliacaoResultado = avaliacaoResultado;
    }

    public Integer getIdProcesso() {
        return this.idProcesso;
    }

    public Meta idProcesso(Integer idProcesso) {
        this.setIdProcesso(idProcesso);
        return this;
    }

    public void setIdProcesso(Integer idProcesso) {
        this.idProcesso = idProcesso;
    }

    public String getMonitoramento() {
        return this.monitoramento;
    }

    public Meta monitoramento(String monitoramento) {
        this.setMonitoramento(monitoramento);
        return this;
    }

    public void setMonitoramento(String monitoramento) {
        this.monitoramento = monitoramento;
    }

    public String getPeriodo() {
        return this.periodo;
    }

    public Meta periodo(String periodo) {
        this.setPeriodo(periodo);
        return this;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public Set<MetaResultado> getResultados() {
        return this.resultados;
    }

    public void setResultados(Set<MetaResultado> metaResultados) {
        if (this.resultados != null) {
            this.resultados.forEach(i -> i.setMeta(null));
        }
        if (metaResultados != null) {
            metaResultados.forEach(i -> i.setMeta(this));
        }
        this.resultados = metaResultados;
    }

    public Meta resultados(Set<MetaResultado> metaResultados) {
        this.setResultados(metaResultados);
        return this;
    }

    public Meta addResultados(MetaResultado metaResultado) {
        this.resultados.add(metaResultado);
        metaResultado.setMeta(this);
        return this;
    }

    public Meta removeResultados(MetaResultado metaResultado) {
        this.resultados.remove(metaResultado);
        metaResultado.setMeta(null);
        return this;
    }

    public Set<MetaRecurso> getRecursos() {
        return this.recursos;
    }

    public void setRecursos(Set<MetaRecurso> metaRecursos) {
        this.recursos = metaRecursos;
    }

    public Meta recursos(Set<MetaRecurso> metaRecursos) {
        this.setRecursos(metaRecursos);
        return this;
    }

    public Meta addRecursos(MetaRecurso metaRecurso) {
        this.recursos.add(metaRecurso);
        return this;
    }

    public Meta removeRecursos(MetaRecurso metaRecurso) {
        this.recursos.remove(metaRecurso);
        return this;
    }

    public MetaObjetivo getMetaObjetivo() {
        return this.metaObjetivo;
    }

    public void setMetaObjetivo(MetaObjetivo metaObjetivo) {
        this.metaObjetivo = metaObjetivo;
    }

    public Meta metaObjetivo(MetaObjetivo metaObjetivo) {
        this.setMetaObjetivo(metaObjetivo);
        return this;
    }

    public Integer getFlAtivo() {
        return flAtivo;
    }

    public Meta flAtivo(Integer flAtivo){
        this.setFlAtivo(flAtivo);
        return this;
    }

    public void setFlAtivo(Integer flAtivo) {
        this.flAtivo = flAtivo;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Meta)) {
            return false;
        }
        return getId() != null && getId().equals(((Meta) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Meta{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            ", monitoramentoControle='" + getMonitoramentoControle() + "'" +
            ", descricaoMonitoramentoControle='" + getDescricaoMonitoramentoControle() + "'" +
            ", frequencia='" + getFrequencia() + "'" +
            ", acao='" + getAcao() + "'" +
            ", avaliacaoResultado='" + getAvaliacaoResultado() + "'" +
            ", idProcesso=" + getIdProcesso() +
            ", monitoramento='" + getMonitoramento() + "'" +
            ", periodo='" + getPeriodo() + "'" +
            ", flAtivo=" + getFlAtivo() +
            "}";
    }
}
