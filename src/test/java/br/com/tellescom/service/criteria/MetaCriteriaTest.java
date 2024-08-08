package br.com.tellescom.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class MetaCriteriaTest {

    @Test
    void newMetaCriteriaHasAllFiltersNullTest() {
        var metaCriteria = new MetaCriteria();
        assertThat(metaCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void metaCriteriaFluentMethodsCreatesFiltersTest() {
        var metaCriteria = new MetaCriteria();

        setAllFilters(metaCriteria);

        assertThat(metaCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void metaCriteriaCopyCreatesNullFilterTest() {
        var metaCriteria = new MetaCriteria();
        var copy = metaCriteria.copy();

        assertThat(metaCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(metaCriteria)
        );
    }

    @Test
    void metaCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var metaCriteria = new MetaCriteria();
        setAllFilters(metaCriteria);

        var copy = metaCriteria.copy();

        assertThat(metaCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(metaCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var metaCriteria = new MetaCriteria();

        assertThat(metaCriteria).hasToString("MetaCriteria{}");
    }

    private static void setAllFilters(MetaCriteria metaCriteria) {
        metaCriteria.id();
        metaCriteria.descricao();
        metaCriteria.indicador();
        metaCriteria.medicao();
        metaCriteria.acao();
        metaCriteria.avaliacaoResultado();
        metaCriteria.idProcesso();
        metaCriteria.monitoramento();
        metaCriteria.periodo();
        metaCriteria.resultadosId();
        metaCriteria.recursosId();
        metaCriteria.metaObjetivoId();
        metaCriteria.distinct();
    }

    private static Condition<MetaCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getDescricao()) &&
                condition.apply(criteria.getIndicador()) &&
                condition.apply(criteria.getMedicao()) &&
                condition.apply(criteria.getAcao()) &&
                condition.apply(criteria.getAvaliacaoResultado()) &&
                condition.apply(criteria.getIdProcesso()) &&
                condition.apply(criteria.getMonitoramento()) &&
                condition.apply(criteria.getPeriodo()) &&
                condition.apply(criteria.getResultadosId()) &&
                condition.apply(criteria.getRecursosId()) &&
                condition.apply(criteria.getMetaObjetivoId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<MetaCriteria> copyFiltersAre(MetaCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getDescricao(), copy.getDescricao()) &&
                condition.apply(criteria.getIndicador(), copy.getIndicador()) &&
                condition.apply(criteria.getMedicao(), copy.getMedicao()) &&
                condition.apply(criteria.getAcao(), copy.getAcao()) &&
                condition.apply(criteria.getAvaliacaoResultado(), copy.getAvaliacaoResultado()) &&
                condition.apply(criteria.getIdProcesso(), copy.getIdProcesso()) &&
                condition.apply(criteria.getMonitoramento(), copy.getMonitoramento()) &&
                condition.apply(criteria.getPeriodo(), copy.getPeriodo()) &&
                condition.apply(criteria.getResultadosId(), copy.getResultadosId()) &&
                condition.apply(criteria.getRecursosId(), copy.getRecursosId()) &&
                condition.apply(criteria.getMetaObjetivoId(), copy.getMetaObjetivoId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
