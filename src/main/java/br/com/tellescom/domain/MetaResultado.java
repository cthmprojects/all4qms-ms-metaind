package br.com.tellescom.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A MetaResultado.
 */
@Entity
@Table(name = MetaResultado.TABLE)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MetaResultado implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String TABLE = "meta_resultado";
    public static final String SEQUENCE = TABLE + "_id_seq";

    @Id
    @SequenceGenerator(name = SEQUENCE, sequenceName = SEQUENCE, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(name = "lancado_em")
    private Instant lancadoEm;

    @Column(name = "parcial")
    private Boolean parcial;

    @Column(name = "meta_atingida")
    private Boolean metaAtingida;

    @Column(name = "periodo")
    private Instant periodo;

    @Size(max = 4000)
    @Column(name = "avaliacao", length = 4000)
    private String avaliacao;

    @Size(max = 4000)
    @Column(name = "analise", length = 4000)
    private String analise;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "metaResultado")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "metaResultado" }, allowSetters = true)
    private Set<MetaAnexo> anexos = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "resultados", "recursos", "metaObjetivo" }, allowSetters = true)
    private Meta meta;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public MetaResultado id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getLancadoEm() {
        return this.lancadoEm;
    }

    public MetaResultado lancadoEm(Instant lancadoEm) {
        this.setLancadoEm(lancadoEm);
        return this;
    }

    public void setLancadoEm(Instant lancadoEm) {
        this.lancadoEm = lancadoEm;
    }

    public Boolean getParcial() {
        return this.parcial;
    }

    public MetaResultado parcial(Boolean parcial) {
        this.setParcial(parcial);
        return this;
    }

    public void setParcial(Boolean parcial) {
        this.parcial = parcial;
    }

    public Boolean getMetaAtingida() {
        return this.metaAtingida;
    }

    public MetaResultado metaAtingida(Boolean metaAtingida) {
        this.setMetaAtingida(metaAtingida);
        return this;
    }

    public void setMetaAtingida(Boolean metaAtingida) {
        this.metaAtingida = metaAtingida;
    }

    public Instant getPeriodo() {
        return this.periodo;
    }

    public MetaResultado periodo(Instant periodo) {
        this.setPeriodo(periodo);
        return this;
    }

    public void setPeriodo(Instant periodo) {
        this.periodo = periodo;
    }

    public String getAvaliacao() {
        return this.avaliacao;
    }

    public MetaResultado avaliacao(String avaliacao) {
        this.setAvaliacao(avaliacao);
        return this;
    }

    public void setAvaliacao(String avaliacao) {
        this.avaliacao = avaliacao;
    }

    public String getAnalise() {
        return this.analise;
    }

    public MetaResultado analise(String analise) {
        this.setAnalise(analise);
        return this;
    }

    public void setAnalise(String analise) {
        this.analise = analise;
    }

    public Set<MetaAnexo> getAnexos() {
        return this.anexos;
    }

    public void setAnexos(Set<MetaAnexo> metaAnexos) {
        if (this.anexos != null) {
            this.anexos.forEach(i -> i.setMetaResultado(null));
        }
        if (metaAnexos != null) {
            metaAnexos.forEach(i -> i.setMetaResultado(this));
        }
        this.anexos = metaAnexos;
    }

    public MetaResultado anexos(Set<MetaAnexo> metaAnexos) {
        this.setAnexos(metaAnexos);
        return this;
    }

    public MetaResultado addAnexos(MetaAnexo metaAnexo) {
        this.anexos.add(metaAnexo);
        metaAnexo.setMetaResultado(this);
        return this;
    }

    public MetaResultado removeAnexos(MetaAnexo metaAnexo) {
        this.anexos.remove(metaAnexo);
        metaAnexo.setMetaResultado(null);
        return this;
    }

    public Meta getMeta() {
        return this.meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public MetaResultado meta(Meta meta) {
        this.setMeta(meta);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MetaResultado)) {
            return false;
        }
        return getId() != null && getId().equals(((MetaResultado) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MetaResultado{" +
            "id=" + getId() +
            ", lancadoEm='" + getLancadoEm() + "'" +
            ", parcial='" + getParcial() + "'" +
            ", metaAtingida='" + getMetaAtingida() + "'" +
            ", periodo='" + getPeriodo() + "'" +
            ", avaliacao='" + getAvaliacao() + "'" +
            ", analise='" + getAnalise() + "'" +
            "}";
    }
}
