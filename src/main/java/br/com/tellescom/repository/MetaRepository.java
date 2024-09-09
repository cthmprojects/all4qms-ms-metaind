package br.com.tellescom.repository;

import br.com.tellescom.domain.Meta;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Meta entity.
 *
 * When extending this class, extend MetaRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface MetaRepository extends MetaRepositoryWithBagRelationships, JpaRepository<Meta, Long>, JpaSpecificationExecutor<Meta> {
    default Optional<Meta> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findById(id));
    }

    default List<Meta> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAll());
    }

    default Page<Meta> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAll(pageable));
    }

    List<Meta> findAllByMetaObjetivoId(Long id);
}
