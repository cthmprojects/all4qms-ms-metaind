package br.com.tellescom.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link br.com.tellescom.domain.MetaRecurso} entity. This class is used
 * in {@link br.com.tellescom.web.rest.MetaRecursoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /meta-recursos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MetaRecursoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter recursoNome;

    private LongFilter metasId;

    private Boolean distinct;

    public MetaRecursoCriteria() {}

    public MetaRecursoCriteria(MetaRecursoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.recursoNome = other.recursoNome == null ? null : other.recursoNome.copy();
        this.metasId = other.metasId == null ? null : other.metasId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public MetaRecursoCriteria copy() {
        return new MetaRecursoCriteria(this);
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

    public StringFilter getRecursoNome() {
        return recursoNome;
    }

    public StringFilter recursoNome() {
        if (recursoNome == null) {
            recursoNome = new StringFilter();
        }
        return recursoNome;
    }

    public void setRecursoNome(StringFilter recursoNome) {
        this.recursoNome = recursoNome;
    }

    public LongFilter getMetasId() {
        return metasId;
    }

    public LongFilter metasId() {
        if (metasId == null) {
            metasId = new LongFilter();
        }
        return metasId;
    }

    public void setMetasId(LongFilter metasId) {
        this.metasId = metasId;
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
        final MetaRecursoCriteria that = (MetaRecursoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(recursoNome, that.recursoNome) &&
            Objects.equals(metasId, that.metasId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, recursoNome, metasId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MetaRecursoCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (recursoNome != null ? "recursoNome=" + recursoNome + ", " : "") +
            (metasId != null ? "metasId=" + metasId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
