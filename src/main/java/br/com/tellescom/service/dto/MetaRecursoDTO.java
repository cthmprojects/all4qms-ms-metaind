package br.com.tellescom.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link br.com.tellescom.domain.MetaRecurso} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MetaRecursoDTO implements Serializable {

    private Long id;

    private String recursoNome;

    private Set<MetaDTO> metas = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRecursoNome() {
        return recursoNome;
    }

    public void setRecursoNome(String recursoNome) {
        this.recursoNome = recursoNome;
    }

    public Set<MetaDTO> getMetas() {
        return metas;
    }

    public void setMetas(Set<MetaDTO> metas) {
        this.metas = metas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MetaRecursoDTO)) {
            return false;
        }

        MetaRecursoDTO metaRecursoDTO = (MetaRecursoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, metaRecursoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MetaRecursoDTO{" +
            "id=" + getId() +
            ", recursoNome='" + getRecursoNome() + "'" +
            ", metas=" + getMetas() +
            "}";
    }
}
