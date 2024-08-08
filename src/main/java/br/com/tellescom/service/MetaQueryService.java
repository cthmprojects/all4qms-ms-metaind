package br.com.tellescom.service;

import br.com.tellescom.domain.*; // for static metamodels
import br.com.tellescom.domain.Meta;
import br.com.tellescom.repository.MetaRepository;
import br.com.tellescom.service.criteria.MetaCriteria;
import br.com.tellescom.service.dto.MetaDTO;
import br.com.tellescom.service.mapper.MetaMapper;
import jakarta.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Meta} entities in the database.
 * The main input is a {@link MetaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link MetaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MetaQueryService extends QueryService<Meta> {

    private static final Logger log = LoggerFactory.getLogger(MetaQueryService.class);

    private final MetaRepository metaRepository;

    private final MetaMapper metaMapper;

    public MetaQueryService(MetaRepository metaRepository, MetaMapper metaMapper) {
        this.metaRepository = metaRepository;
        this.metaMapper = metaMapper;
    }

    /**
     * Return a {@link Page} of {@link MetaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MetaDTO> findByCriteria(MetaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Meta> specification = createSpecification(criteria);
        return metaRepository.fetchBagRelationships(metaRepository.findAll(specification, page)).map(metaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MetaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Meta> specification = createSpecification(criteria);
        return metaRepository.count(specification);
    }

    /**
     * Function to convert {@link MetaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Meta> createSpecification(MetaCriteria criteria) {
        Specification<Meta> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Meta_.id));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), Meta_.descricao));
            }
            if (criteria.getIndicador() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIndicador(), Meta_.indicador));
            }
            if (criteria.getMedicao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMedicao(), Meta_.medicao));
            }
            if (criteria.getAcao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAcao(), Meta_.acao));
            }
            if (criteria.getAvaliacaoResultado() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAvaliacaoResultado(), Meta_.avaliacaoResultado));
            }
            if (criteria.getIdProcesso() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIdProcesso(), Meta_.idProcesso));
            }
            if (criteria.getMonitoramento() != null) {
                specification = specification.and(buildSpecification(criteria.getMonitoramento(), Meta_.monitoramento));
            }
            if (criteria.getPeriodo() != null) {
                specification = specification.and(buildSpecification(criteria.getPeriodo(), Meta_.periodo));
            }
            if (criteria.getResultadosId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getResultadosId(),
                        root -> root.join(Meta_.resultados, JoinType.LEFT).get(MetaResultado_.id)
                    )
                );
            }
            if (criteria.getRecursosId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getRecursosId(), root -> root.join(Meta_.recursos, JoinType.LEFT).get(MetaRecurso_.id))
                );
            }
            if (criteria.getMetaObjetivoId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getMetaObjetivoId(),
                        root -> root.join(Meta_.metaObjetivo, JoinType.LEFT).get(MetaObjetivo_.id)
                    )
                );
            }
        }
        return specification;
    }
}
