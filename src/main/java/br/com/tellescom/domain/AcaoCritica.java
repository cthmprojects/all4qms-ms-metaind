package br.com.tellescom.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AcaoCritica.
 */
@Entity
@Table(name = "acao_critica")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AcaoCritica implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Size(max = 4000)
    @Column(name = "acao_critica", length = 4000)
    private String acaoCritica;

    @Column(name = "nome_responsavel")
    private String nomeResponsavel;

    @Column(name = "id_responsavel")
    private Long idResponsavel;

    @Column(name = "data_acao")
    private LocalDate dataAcao;

    @Size(max = 4000)
    @Column(name = "instrucao_acao", length = 4000)
    private String instrucaoAcao;

    @Column(name = "criado_em")
    private ZonedDateTime criadoEm;

    @Column(name = "atualizado_em")
    private ZonedDateTime atualizadoEm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "acaoCriticas" }, allowSetters = true)
    private IndicadorCritica indicadorCritica;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AcaoCritica id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAcaoCritica() {
        return this.acaoCritica;
    }

    public AcaoCritica acaoCritica(String acaoCritica) {
        this.setAcaoCritica(acaoCritica);
        return this;
    }

    public void setAcaoCritica(String acaoCritica) {
        this.acaoCritica = acaoCritica;
    }

    public String getNomeResponsavel() {
        return this.nomeResponsavel;
    }

    public AcaoCritica nomeResponsavel(String nomeResponsavel) {
        this.setNomeResponsavel(nomeResponsavel);
        return this;
    }

    public void setNomeResponsavel(String nomeResponsavel) {
        this.nomeResponsavel = nomeResponsavel;
    }

    public Long getIdResponsavel() {
        return this.idResponsavel;
    }

    public AcaoCritica idResponsavel(Long idResponsavel) {
        this.setIdResponsavel(idResponsavel);
        return this;
    }

    public void setIdResponsavel(Long idResponsavel) {
        this.idResponsavel = idResponsavel;
    }

    public LocalDate getDataAcao() {
        return this.dataAcao;
    }

    public AcaoCritica dataAcao(LocalDate dataAcao) {
        this.setDataAcao(dataAcao);
        return this;
    }

    public void setDataAcao(LocalDate dataAcao) {
        this.dataAcao = dataAcao;
    }

    public String getInstrucaoAcao() {
        return this.instrucaoAcao;
    }

    public AcaoCritica instrucaoAcao(String instrucaoAcao) {
        this.setInstrucaoAcao(instrucaoAcao);
        return this;
    }

    public void setInstrucaoAcao(String instrucaoAcao) {
        this.instrucaoAcao = instrucaoAcao;
    }

    public ZonedDateTime getCriadoEm() {
        return this.criadoEm;
    }

    public AcaoCritica criadoEm(ZonedDateTime criadoEm) {
        this.setCriadoEm(criadoEm);
        return this;
    }

    public void setCriadoEm(ZonedDateTime criadoEm) {
        this.criadoEm = criadoEm;
    }

    public ZonedDateTime getAtualizadoEm() {
        return this.atualizadoEm;
    }

    public AcaoCritica atualizadoEm(ZonedDateTime atualizadoEm) {
        this.setAtualizadoEm(atualizadoEm);
        return this;
    }

    public void setAtualizadoEm(ZonedDateTime atualizadoEm) {
        this.atualizadoEm = atualizadoEm;
    }

    public IndicadorCritica getIndicadorCritica() {
        return this.indicadorCritica;
    }

    public void setIndicadorCritica(IndicadorCritica indicadorCritica) {
        this.indicadorCritica = indicadorCritica;
    }

    public AcaoCritica indicadorCritica(IndicadorCritica indicadorCritica) {
        this.setIndicadorCritica(indicadorCritica);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AcaoCritica)) {
            return false;
        }
        return getId() != null && getId().equals(((AcaoCritica) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AcaoCritica{" +
            "id=" + getId() +
            ", acaoCritica='" + getAcaoCritica() + "'" +
            ", nomeResponsavel='" + getNomeResponsavel() + "'" +
            ", idResponsavel=" + getIdResponsavel() +
            ", dataAcao='" + getDataAcao() + "'" +
            ", instrucaoAcao='" + getInstrucaoAcao() + "'" +
            ", criadoEm='" + getCriadoEm() + "'" +
            ", atualizadoEm='" + getAtualizadoEm() + "'" +
            "}";
    }
}
