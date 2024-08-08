package br.com.tellescom.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A MetaRecurso.
 */
@Entity
@Table(name = "meta_recurso")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MetaRecurso implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "recurso_nome")
    private String recursoNome;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "recursos")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "resultados", "recursos", "metaObjetivo" }, allowSetters = true)
    private Set<Meta> metas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public MetaRecurso id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRecursoNome() {
        return this.recursoNome;
    }

    public MetaRecurso recursoNome(String recursoNome) {
        this.setRecursoNome(recursoNome);
        return this;
    }

    public void setRecursoNome(String recursoNome) {
        this.recursoNome = recursoNome;
    }

    public Set<Meta> getMetas() {
        return this.metas;
    }

    public void setMetas(Set<Meta> metas) {
        if (this.metas != null) {
            this.metas.forEach(i -> i.removeRecursos(this));
        }
        if (metas != null) {
            metas.forEach(i -> i.addRecursos(this));
        }
        this.metas = metas;
    }

    public MetaRecurso metas(Set<Meta> metas) {
        this.setMetas(metas);
        return this;
    }

    public MetaRecurso addMetas(Meta meta) {
        this.metas.add(meta);
        meta.getRecursos().add(this);
        return this;
    }

    public MetaRecurso removeMetas(Meta meta) {
        this.metas.remove(meta);
        meta.getRecursos().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MetaRecurso)) {
            return false;
        }
        return getId() != null && getId().equals(((MetaRecurso) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MetaRecurso{" +
            "id=" + getId() +
            ", recursoNome='" + getRecursoNome() + "'" +
            "}";
    }
}
