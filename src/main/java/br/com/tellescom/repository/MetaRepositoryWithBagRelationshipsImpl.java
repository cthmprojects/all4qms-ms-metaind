package br.com.tellescom.repository;

import br.com.tellescom.domain.Meta;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class MetaRepositoryWithBagRelationshipsImpl implements MetaRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String METAS_PARAMETER = "metas";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Meta> fetchBagRelationships(Optional<Meta> meta) {
        return meta.map(this::fetchRecursos);
    }

    @Override
    public Page<Meta> fetchBagRelationships(Page<Meta> metas) {
        return new PageImpl<>(fetchBagRelationships(metas.getContent()), metas.getPageable(), metas.getTotalElements());
    }

    @Override
    public List<Meta> fetchBagRelationships(List<Meta> metas) {
        return Optional.of(metas).map(this::fetchRecursos).orElse(Collections.emptyList());
    }

    Meta fetchRecursos(Meta result) {
        return entityManager
            .createQuery("select meta from Meta meta left join fetch meta.recursos where meta.id = :id", Meta.class)
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Meta> fetchRecursos(List<Meta> metas) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, metas.size()).forEach(index -> order.put(metas.get(index).getId(), index));
        List<Meta> result = entityManager
            .createQuery("select meta from Meta meta left join fetch meta.recursos where meta in :metas", Meta.class)
            .setParameter(METAS_PARAMETER, metas)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
