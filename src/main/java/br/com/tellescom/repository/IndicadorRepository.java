package br.com.tellescom.repository;

import br.com.tellescom.domain.Indicador;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Indicador entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IndicadorRepository extends JpaRepository<Indicador, Long> {}
