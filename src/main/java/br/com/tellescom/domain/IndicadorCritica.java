package br.com.tellescom.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A IndicadorCritica.
 */
@Entity
@Table(name = IndicadorCritica.TABLE)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class IndicadorCritica implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String TABLE = "indicador_critica";
    public static final String SEQUENCE = TABLE + "_id_seq";

    @SequenceGenerator(name = SEQUENCE, sequenceName = SEQUENCE, allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(name = "id_indicador_meta")
    private Long idIndicadorMeta;

    @Column(name = "id_plano")
    private Long idPlano;

    @Size(max = 4000)
    @Column(name = "analise_critica", length = 4000)
    private String analiseCritica;

    @Size(max = 4000)
    @Column(name = "observacao", length = 4000)
    private String observacao;

    @Column(name = "mes")
    private Integer mes;

    @Column(name = "ano")
    private Integer ano;

    @Column(name = "criado_por")
    private Long criadoPor;

    @Column(name = "criado_em")
    private Instant criadoEm;

    @Column(name = "atualizado_por")
    private Long atualizadoPor;

    @Column(name = "atualizado_em")
    private Instant atualizadoEm;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public IndicadorCritica id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdIndicadorMeta() {
        return this.idIndicadorMeta;
    }

    public IndicadorCritica idIndicadorMeta(Long idIndicadorMeta) {
        this.setIdIndicadorMeta(idIndicadorMeta);
        return this;
    }

    public void setIdIndicadorMeta(Long idIndicadorMeta) {
        this.idIndicadorMeta = idIndicadorMeta;
    }

    public Long getIdPlano() {
        return this.idPlano;
    }

    public IndicadorCritica idPlano(Long idPlano) {
        this.setIdPlano(idPlano);
        return this;
    }

    public void setIdPlano(Long idPlano) {
        this.idPlano = idPlano;
    }

    public String getAnaliseCritica() {
        return this.analiseCritica;
    }

    public IndicadorCritica analiseCritica(String analiseCritica) {
        this.setAnaliseCritica(analiseCritica);
        return this;
    }

    public void setAnaliseCritica(String analiseCritica) {
        this.analiseCritica = analiseCritica;
    }

    public String getObservacao() {
        return this.observacao;
    }

    public IndicadorCritica observacao(String observacao) {
        this.setObservacao(observacao);
        return this;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Integer getMes() {
        return this.mes;
    }

    public IndicadorCritica mes(Integer mes) {
        this.setMes(mes);
        return this;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public Integer getAno() {
        return this.ano;
    }

    public IndicadorCritica ano(Integer ano) {
        this.setAno(ano);
        return this;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public Long getCriadoPor() {
        return this.criadoPor;
    }

    public IndicadorCritica criadoPor(Long criadoPor) {
        this.setCriadoPor(criadoPor);
        return this;
    }

    public void setCriadoPor(Long criadoPor) {
        this.criadoPor = criadoPor;
    }

    public Instant getCriadoEm() {
        return this.criadoEm;
    }

    public IndicadorCritica criadoEm(Instant criadoEm) {
        this.setCriadoEm(criadoEm);
        return this;
    }

    public void setCriadoEm(Instant criadoEm) {
        this.criadoEm = criadoEm;
    }

    public Long getAtualizadoPor() {
        return this.atualizadoPor;
    }

    public IndicadorCritica atualizadoPor(Long atualizadoPor) {
        this.setAtualizadoPor(atualizadoPor);
        return this;
    }

    public void setAtualizadoPor(Long atualizadoPor) {
        this.atualizadoPor = atualizadoPor;
    }

    public Instant getAtualizadoEm() {
        return this.atualizadoEm;
    }

    public IndicadorCritica atualizadoEm(Instant atualizadoEm) {
        this.setAtualizadoEm(atualizadoEm);
        return this;
    }

    public void setAtualizadoEm(Instant atualizadoEm) {
        this.atualizadoEm = atualizadoEm;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IndicadorCritica)) {
            return false;
        }
        return getId() != null && getId().equals(((IndicadorCritica) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IndicadorCritica{" +
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
