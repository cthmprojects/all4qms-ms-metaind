package br.com.tellescom.repository;

import br.com.tellescom.domain.response.MetaResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MetaResponseRepository extends JpaRepository<MetaResponse, Long> {
    @Query(
        value = "SELECT " +
            "m.id, " +
            "m.meta_objetivo_id, " +
            "mr.id as meta_resultado_id, " +
            "m.descricao, " +
            "mr.avaliacao, " +
            "mr.analise, " +
            "mr.parcial, " +
            "mr.meta_atingida, " +
            "mr.lancado_em " +
            "FROM meta m " +
            "LEFT JOIN meta_resultado mr ON " +
            "mr.meta_id = m.id AND " +
            "mr.lancado_em = ( SELECT MAX(lancado_em) FROM meta_resultado WHERE meta_id = m.id ) " +
            "WHERE ((:mes IS NULL AND :ano IS NULL) OR  (mr.lancado_em IS NOT NULL " +
            "AND (COALESCE(TO_CHAR(mr.lancado_em, 'MM'), '00') = COALESCE(LPAD(:mes, 2, '0'), TO_CHAR(mr.lancado_em, 'MM')) or mr.lancado_em IS NULL ) " +
            "AND (COALESCE(TO_CHAR(mr.lancado_em, 'YYYY'), '0000') = COALESCE(:ano, TO_CHAR(mr.lancado_em, 'YYYY'))  or mr.lancado_em IS NULL ))) " +
            "AND ((:parcial IS NULL AND :metaAtingida IS NULL) OR (mr.id IS NOT NULL " +
            "AND (COALESCE(:parcial, mr.parcial) = mr.parcial or mr.parcial is NULL )   " +
            "AND (COALESCE(:metaAtingida, mr.meta_atingida) = mr.meta_atingida or mr.meta_atingida is NULL))) " +
            "AND (COALESCE(:idProcesso, -1) = -1 OR m.id_processo = :idProcesso)   " +
            "AND (:pesquisa IS NULL " +
            "OR m.descricao ILIKE '%' || :pesquisa || '%' " +
            "OR COALESCE(mr.avaliacao, '') ILIKE '%' || :pesquisa || '%' " +
            "OR COALESCE(mr.analise, '') ILIKE '%' || :pesquisa || '%') " +
            "ORDER BY m.id DESC;",
        nativeQuery = true
    )
    Page<MetaResponse> getAllMetaByFilter(
        @Param("ano") String ano,
        @Param("mes") String mes,
        @Param("idProcesso") Long idProcesso,
        @Param("pesquisa") String pesquisa,
        @Param("metaAtingida") Boolean metaAtingida,
        @Param("parcial") Boolean metaParcial,
        Pageable pageable
    );

}
