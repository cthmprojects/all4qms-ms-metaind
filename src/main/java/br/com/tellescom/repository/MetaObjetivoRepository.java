package br.com.tellescom.repository;

import br.com.tellescom.domain.MetaObjetivo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MetaObjetivo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MetaObjetivoRepository extends JpaRepository<MetaObjetivo, Long> {}
