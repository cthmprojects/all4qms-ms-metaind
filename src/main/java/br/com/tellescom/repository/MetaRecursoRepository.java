package br.com.tellescom.repository;

import br.com.tellescom.domain.MetaRecurso;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MetaRecurso entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MetaRecursoRepository extends JpaRepository<MetaRecurso, Long>, JpaSpecificationExecutor<MetaRecurso> {}
