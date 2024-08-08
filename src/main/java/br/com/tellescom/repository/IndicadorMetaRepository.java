package br.com.tellescom.repository;

import br.com.tellescom.domain.IndicadorMeta;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the IndicadorMeta entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IndicadorMetaRepository extends JpaRepository<IndicadorMeta, Long> {}
