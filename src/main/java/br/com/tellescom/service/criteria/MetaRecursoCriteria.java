package br.com.tellescom.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
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
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.recursoNome = other.optionalRecursoNome().map(StringFilter::copy).orElse(null);
        this.metasId = other.optionalMetasId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public MetaRecursoCriteria copy() {
        return new MetaRecursoCriteria(this);
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

    public StringFilter getRecursoNome() {
        return recursoNome;
    }

    public Optional<StringFilter> optionalRecursoNome() {
        return Optional.ofNullable(recursoNome);
    }

    public StringFilter recursoNome() {
        if (recursoNome == null) {
            setRecursoNome(new StringFilter());
        }
        return recursoNome;
    }

    public void setRecursoNome(StringFilter recursoNome) {
        this.recursoNome = recursoNome;
    }

    public LongFilter getMetasId() {
        return metasId;
    }

    public Optional<LongFilter> optionalMetasId() {
        return Optional.ofNullable(metasId);
    }

    public LongFilter metasId() {
        if (metasId == null) {
            setMetasId(new LongFilter());
        }
        return metasId;
    }

    public void setMetasId(LongFilter metasId) {
        this.metasId = metasId;
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
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalRecursoNome().map(f -> "recursoNome=" + f + ", ").orElse("") +
            optionalMetasId().map(f -> "metasId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
