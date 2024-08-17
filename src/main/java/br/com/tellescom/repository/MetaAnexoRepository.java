package br.com.tellescom.repository;

import br.com.tellescom.domain.MetaAnexo;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MetaAnexo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MetaAnexoRepository extends JpaRepository<MetaAnexo, Long> {
    List<MetaAnexo> findAllByMetaResultadoId(Long id);
}
