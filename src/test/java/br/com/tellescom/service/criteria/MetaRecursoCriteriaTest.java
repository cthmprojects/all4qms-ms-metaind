package br.com.tellescom.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class MetaRecursoCriteriaTest {

    @Test
    void newMetaRecursoCriteriaHasAllFiltersNullTest() {
        var metaRecursoCriteria = new MetaRecursoCriteria();
        assertThat(metaRecursoCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void metaRecursoCriteriaFluentMethodsCreatesFiltersTest() {
        var metaRecursoCriteria = new MetaRecursoCriteria();

        setAllFilters(metaRecursoCriteria);

        assertThat(metaRecursoCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void metaRecursoCriteriaCopyCreatesNullFilterTest() {
        var metaRecursoCriteria = new MetaRecursoCriteria();
        var copy = metaRecursoCriteria.copy();

        assertThat(metaRecursoCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(metaRecursoCriteria)
        );
    }

    @Test
    void metaRecursoCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var metaRecursoCriteria = new MetaRecursoCriteria();
        setAllFilters(metaRecursoCriteria);

        var copy = metaRecursoCriteria.copy();

        assertThat(metaRecursoCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(metaRecursoCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var metaRecursoCriteria = new MetaRecursoCriteria();

        assertThat(metaRecursoCriteria).hasToString("MetaRecursoCriteria{}");
    }

    private static void setAllFilters(MetaRecursoCriteria metaRecursoCriteria) {
        metaRecursoCriteria.id();
        metaRecursoCriteria.recursoNome();
        metaRecursoCriteria.metasId();
        metaRecursoCriteria.distinct();
    }

    private static Condition<MetaRecursoCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getRecursoNome()) &&
                condition.apply(criteria.getMetasId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<MetaRecursoCriteria> copyFiltersAre(MetaRecursoCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getRecursoNome(), copy.getRecursoNome()) &&
                condition.apply(criteria.getMetasId(), copy.getMetasId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
