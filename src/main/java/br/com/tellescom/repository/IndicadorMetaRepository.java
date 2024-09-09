package br.com.tellescom.repository;

import br.com.tellescom.domain.IndicadorMeta;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data JPA repository for the IndicadorMeta entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IndicadorMetaRepository extends JpaRepository<IndicadorMeta, Long> {

    @Query("SELECT im FROM IndicadorMeta im WHERE im.indicador.id = :indicador AND im.anoIndicador = :anoIndicador")
    IndicadorMeta findByIndicadorAndAnoIndicador(int indicador, String anoIndicador);


    @Query(value = "SELECT im.*" +
        " FROM public.indicador_meta as im" +
        " JOIN" +
        " (SELECT id as id_origem FROM public.indicador as i WHERE i.id_processo = :processo) as ind" +
        " ON im.id = ind.id_origem " +
        "WHERE " +
        "im.indicador_id = :indicador AND im.ano_indicador = :anoIndicador", nativeQuery = true)
    Optional<IndicadorMeta> findByIndicadorAndAnoIndicadorAndProcesso(int indicador, int processo, String anoIndicador);

}
