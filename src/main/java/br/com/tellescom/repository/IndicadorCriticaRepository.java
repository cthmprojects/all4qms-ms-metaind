package br.com.tellescom.repository;

import br.com.tellescom.domain.IndicadorCritica;
import br.com.tellescom.service.dto.IndicadorCriticaDTO;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * Spring Data JPA repository for the IndicadorCritica entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IndicadorCriticaRepository extends JpaRepository<IndicadorCritica, Long> {
    List<IndicadorCritica> findAllByIdIndicadorMeta(Long id);

    IndicadorCritica findByIdIndicadorMetaAndMesAndAno(Long id, Integer mes, Integer ano);
}
