package br.com.tellescom.service;

import br.com.tellescom.domain.MetaRecurso;
import br.com.tellescom.repository.MetaRecursoRepository;
import br.com.tellescom.service.dto.MetaRecursoDTO;
import br.com.tellescom.service.mapper.MetaRecursoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link br.com.tellescom.domain.MetaRecurso}.
 */
@Service
@Transactional
public class MetaRecursoService {

    private static final Logger log = LoggerFactory.getLogger(MetaRecursoService.class);

    private final MetaRecursoRepository metaRecursoRepository;

    private final MetaRecursoMapper metaRecursoMapper;

    public MetaRecursoService(MetaRecursoRepository metaRecursoRepository, MetaRecursoMapper metaRecursoMapper) {
        this.metaRecursoRepository = metaRecursoRepository;
        this.metaRecursoMapper = metaRecursoMapper;
    }

    /**
     * Save a metaRecurso.
     *
     * @param metaRecursoDTO the entity to save.
     * @return the persisted entity.
     */
    public MetaRecursoDTO save(MetaRecursoDTO metaRecursoDTO) {
        log.debug("Request to save MetaRecurso : {}", metaRecursoDTO);
        MetaRecurso metaRecurso = metaRecursoMapper.toEntity(metaRecursoDTO);
        metaRecurso = metaRecursoRepository.save(metaRecurso);
        return metaRecursoMapper.toDto(metaRecurso);
    }

    /**
     * Update a metaRecurso.
     *
     * @param metaRecursoDTO the entity to save.
     * @return the persisted entity.
     */
    public MetaRecursoDTO update(MetaRecursoDTO metaRecursoDTO) {
        log.debug("Request to update MetaRecurso : {}", metaRecursoDTO);
        MetaRecurso metaRecurso = metaRecursoMapper.toEntity(metaRecursoDTO);
        metaRecurso = metaRecursoRepository.save(metaRecurso);
        return metaRecursoMapper.toDto(metaRecurso);
    }

    /**
     * Partially update a metaRecurso.
     *
     * @param metaRecursoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<MetaRecursoDTO> partialUpdate(MetaRecursoDTO metaRecursoDTO) {
        log.debug("Request to partially update MetaRecurso : {}", metaRecursoDTO);

        return metaRecursoRepository
            .findById(metaRecursoDTO.getId())
            .map(existingMetaRecurso -> {
                metaRecursoMapper.partialUpdate(existingMetaRecurso, metaRecursoDTO);

                return existingMetaRecurso;
            })
            .map(metaRecursoRepository::save)
            .map(metaRecursoMapper::toDto);
    }

    /**
     * Get one metaRecurso by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MetaRecursoDTO> findOne(Long id) {
        log.debug("Request to get MetaRecurso : {}", id);
        return metaRecursoRepository.findById(id).map(metaRecursoMapper::toDto);
    }

    /**
     * Delete the metaRecurso by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MetaRecurso : {}", id);
        metaRecursoRepository.deleteById(id);
    }
}
