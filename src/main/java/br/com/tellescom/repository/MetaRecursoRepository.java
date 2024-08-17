package br.com.tellescom.repository;

import br.com.tellescom.domain.MetaRecurso;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MetaRecurso entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MetaRecursoRepository extends JpaRepository<MetaRecurso, Long>, JpaSpecificationExecutor<MetaRecurso> {
    @Query("SELECT mr FROM MetaRecurso mr " + "WHERE (:recursoNome IS NULL OR mr.recursoNome ILIKE %:recursoNome%)")
    Page<MetaRecurso> findByRecursoNomeLike(@Param("recursoNome") String recursoNome, Pageable pageable);
}
