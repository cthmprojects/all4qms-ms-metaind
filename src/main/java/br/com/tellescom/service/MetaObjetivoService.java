package br.com.tellescom.service;

import br.com.tellescom.domain.MetaObjetivo;
import br.com.tellescom.repository.MetaObjetivoRepository;
import br.com.tellescom.service.dto.MetaObjetivoDTO;
import br.com.tellescom.service.mapper.MetaObjetivoMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link br.com.tellescom.domain.MetaObjetivo}.
 */
@Service
@Transactional
public class MetaObjetivoService {

    private static final Logger log = LoggerFactory.getLogger(MetaObjetivoService.class);

    private final MetaObjetivoRepository metaObjetivoRepository;

    private final MetaObjetivoMapper metaObjetivoMapper;

    public MetaObjetivoService(MetaObjetivoRepository metaObjetivoRepository, MetaObjetivoMapper metaObjetivoMapper) {
        this.metaObjetivoRepository = metaObjetivoRepository;
        this.metaObjetivoMapper = metaObjetivoMapper;
    }

    /**
     * Save a metaObjetivo.
     *
     * @param metaObjetivoDTO the entity to save.
     * @return the persisted entity.
     */
    public MetaObjetivoDTO save(MetaObjetivoDTO metaObjetivoDTO) {
        log.debug("Request to save MetaObjetivo : {}", metaObjetivoDTO);
        MetaObjetivo metaObjetivo = metaObjetivoMapper.toEntity(metaObjetivoDTO);
        metaObjetivo = metaObjetivoRepository.save(metaObjetivo);
        return metaObjetivoMapper.toDto(metaObjetivo);
    }

    /**
     * Update a metaObjetivo.
     *
     * @param metaObjetivoDTO the entity to save.
     * @return the persisted entity.
     */
    public MetaObjetivoDTO update(MetaObjetivoDTO metaObjetivoDTO) {
        log.debug("Request to update MetaObjetivo : {}", metaObjetivoDTO);
        MetaObjetivo metaObjetivo = metaObjetivoMapper.toEntity(metaObjetivoDTO);
        metaObjetivo = metaObjetivoRepository.save(metaObjetivo);
        return metaObjetivoMapper.toDto(metaObjetivo);
    }

    /**
     * Partially update a metaObjetivo.
     *
     * @param metaObjetivoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<MetaObjetivoDTO> partialUpdate(MetaObjetivoDTO metaObjetivoDTO) {
        log.debug("Request to partially update MetaObjetivo : {}", metaObjetivoDTO);

        return metaObjetivoRepository
            .findById(metaObjetivoDTO.getId())
            .map(existingMetaObjetivo -> {
                metaObjetivoMapper.partialUpdate(existingMetaObjetivo, metaObjetivoDTO);

                return existingMetaObjetivo;
            })
            .map(metaObjetivoRepository::save)
            .map(metaObjetivoMapper::toDto);
    }

    /**
     * Get all the metaObjetivos.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<MetaObjetivoDTO> findAll() {
        log.debug("Request to get all MetaObjetivos");
        return metaObjetivoRepository.findAll().stream().map(metaObjetivoMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one metaObjetivo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MetaObjetivoDTO> findOne(Long id) {
        log.debug("Request to get MetaObjetivo : {}", id);
        return metaObjetivoRepository.findById(id).map(metaObjetivoMapper::toDto);
    }

    /**
     * Delete the metaObjetivo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MetaObjetivo : {}", id);
        metaObjetivoRepository.deleteById(id);
    }
}
