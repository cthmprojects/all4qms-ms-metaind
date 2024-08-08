package br.com.tellescom.repository;

import br.com.tellescom.domain.MetaAnexo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MetaAnexo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MetaAnexoRepository extends JpaRepository<MetaAnexo, Long> {}
