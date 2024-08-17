package br.com.tellescom.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A MetaObjetivo.
 */
@Entity
@Table(name = MetaObjetivo.TABLE)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MetaObjetivo implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String TABLE = "meta_objetivo";
    public static final String SEQUENCE = TABLE + "_id_seq";

    @Id
    @SequenceGenerator(name = SEQUENCE, sequenceName = SEQUENCE, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(name = "politica_sgq")
    private String politicaSGQ;

    @Column(name = "desdobramento_sgq")
    private String desdobramentoSGQ;

    @Column(name = "objetivo_sgq")
    private String objetivoSGQ;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "metaObjetivo")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "resultados", "recursos", "metaObjetivo" }, allowSetters = true)
    private Set<Meta> metas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public MetaObjetivo id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPoliticaSGQ() {
        return this.politicaSGQ;
    }

    public MetaObjetivo politicaSGQ(String politicaSGQ) {
        this.setPoliticaSGQ(politicaSGQ);
        return this;
    }

    public void setPoliticaSGQ(String politicaSGQ) {
        this.politicaSGQ = politicaSGQ;
    }

    public String getDesdobramentoSGQ() {
        return this.desdobramentoSGQ;
    }

    public MetaObjetivo desdobramentoSGQ(String desdobramentoSGQ) {
        this.setDesdobramentoSGQ(desdobramentoSGQ);
        return this;
    }

    public void setDesdobramentoSGQ(String desdobramentoSGQ) {
        this.desdobramentoSGQ = desdobramentoSGQ;
    }

    public String getObjetivoSGQ() {
        return this.objetivoSGQ;
    }

    public MetaObjetivo objetivoSGQ(String objetivoSGQ) {
        this.setObjetivoSGQ(objetivoSGQ);
        return this;
    }

    public void setObjetivoSGQ(String objetivoSGQ) {
        this.objetivoSGQ = objetivoSGQ;
    }

    public Set<Meta> getMetas() {
        return this.metas;
    }

    public void setMetas(Set<Meta> metas) {
        if (this.metas != null) {
            this.metas.forEach(i -> i.setMetaObjetivo(null));
        }
        if (metas != null) {
            metas.forEach(i -> i.setMetaObjetivo(this));
        }
        this.metas = metas;
    }

    public MetaObjetivo metas(Set<Meta> metas) {
        this.setMetas(metas);
        return this;
    }

    public MetaObjetivo addMetas(Meta meta) {
        this.metas.add(meta);
        meta.setMetaObjetivo(this);
        return this;
    }

    public MetaObjetivo removeMetas(Meta meta) {
        this.metas.remove(meta);
        meta.setMetaObjetivo(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MetaObjetivo)) {
            return false;
        }
        return getId() != null && getId().equals(((MetaObjetivo) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MetaObjetivo{" +
            "id=" + getId() +
            ", politicaSGQ='" + getPoliticaSGQ() + "'" +
            ", desdobramentoSGQ='" + getDesdobramentoSGQ() + "'" +
            ", objetivoSGQ='" + getObjetivoSGQ() + "'" +
            "}";
    }
}
