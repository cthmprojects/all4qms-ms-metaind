package br.com.tellescom.repository;

import br.com.tellescom.domain.AcaoCritica;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * Spring Data JPA repository for the AcaoCritica entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AcaoCriticaRepository extends JpaRepository<AcaoCritica, Long> {
    @Query(value = "select * from acao_critica ac where ac.indicador_critica_id = :id", nativeQuery = true)
    Set<AcaoCritica> findAllByIndicadorCritica(Long id);
}
