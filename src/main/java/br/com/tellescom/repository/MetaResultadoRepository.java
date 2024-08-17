package br.com.tellescom.repository;

import br.com.tellescom.domain.MetaResultado;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MetaResultado entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MetaResultadoRepository extends JpaRepository<MetaResultado, Long> {
    List<MetaResultado> findByMetaIdOrderByLancadoEmDesc(Long metaId);
}
