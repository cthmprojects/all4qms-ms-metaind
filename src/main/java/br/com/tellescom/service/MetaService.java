package br.com.tellescom.service;

import br.com.tellescom.domain.Meta;
import br.com.tellescom.repository.MetaRepository;
import br.com.tellescom.service.dto.MetaDTO;
import br.com.tellescom.service.mapper.MetaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link br.com.tellescom.domain.Meta}.
 */
@Service
@Transactional
public class MetaService {

    private static final Logger log = LoggerFactory.getLogger(MetaService.class);

    private final MetaRepository metaRepository;

    private final MetaMapper metaMapper;

    public MetaService(MetaRepository metaRepository, MetaMapper metaMapper) {
        this.metaRepository = metaRepository;
        this.metaMapper = metaMapper;
    }

    /**
     * Save a meta.
     *
     * @param metaDTO the entity to save.
     * @return the persisted entity.
     */
    public MetaDTO save(MetaDTO metaDTO) {
        log.debug("Request to save Meta : {}", metaDTO);
        Meta meta = metaMapper.toEntity(metaDTO);
        meta = metaRepository.save(meta);
        return metaMapper.toDto(meta);
    }

    /**
     * Update a meta.
     *
     * @param metaDTO the entity to save.
     * @return the persisted entity.
     */
    public MetaDTO update(MetaDTO metaDTO) {
        log.debug("Request to update Meta : {}", metaDTO);
        Meta meta = metaMapper.toEntity(metaDTO);
        meta = metaRepository.save(meta);
        return metaMapper.toDto(meta);
    }

    /**
     * Partially update a meta.
     *
     * @param metaDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<MetaDTO> partialUpdate(MetaDTO metaDTO) {
        log.debug("Request to partially update Meta : {}", metaDTO);

        return metaRepository
            .findById(metaDTO.getId())
            .map(existingMeta -> {
                metaMapper.partialUpdate(existingMeta, metaDTO);

                return existingMeta;
            })
            .map(metaRepository::save)
            .map(metaMapper::toDto);
    }

    /**
     * Get all the metas with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<MetaDTO> findAllWithEagerRelationships(Pageable pageable) {
        return metaRepository.findAllWithEagerRelationships(pageable).map(metaMapper::toDto);
    }

    /**
     * Get one meta by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MetaDTO> findOne(Long id) {
        log.debug("Request to get Meta : {}", id);
        return metaRepository.findOneWithEagerRelationships(id).map(metaMapper::toDto);
    }

    /**
     * Delete the meta by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Meta : {}", id);
        metaRepository.deleteById(id);
    }
}
