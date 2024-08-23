package br.com.tellescom.service;

import br.com.tellescom.domain.Meta;
import br.com.tellescom.domain.request.MetaFilterRequest;
import br.com.tellescom.domain.response.MetaResponse;
import br.com.tellescom.repository.MetaRepository;
import br.com.tellescom.repository.MetaResponseRepository;
import br.com.tellescom.service.dto.MetaDTO;
import br.com.tellescom.service.mapper.MetaMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    private final Logger log = LoggerFactory.getLogger(MetaService.class);

    private final MetaRepository metaRepository;

    private final MetaMapper metaMapper;

    private final MetaResponseRepository metaResponseRepository;

    public MetaService(MetaRepository metaRepository, MetaMapper metaMapper, MetaResponseRepository metaResponseRepository) {
        this.metaRepository = metaRepository;
        this.metaMapper = metaMapper;
        this.metaResponseRepository = metaResponseRepository;
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
     * Get all the metas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MetaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Metas");
        return metaRepository.findAll(pageable).map(metaMapper::toDto);
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

    /**
     * Return a {@link Page} of {@link MetaResponse} which matches the criteria from the database.
     *
     * @param filtro   The object which holds all the filters, which the entities should match.
     * @param pageable The page, which should be returned.
     * @return the matching entities.
     */
    public Page<MetaResponse> findByFiltro(MetaFilterRequest filtro, Pageable pageable) {
        Boolean metaParcial = null;
        Boolean metaAtingida = null;

        if (filtro == null) {
            filtro = new MetaFilterRequest();
        }
        if (filtro.getSituacao() != null) {
            metaAtingida = (filtro.getSituacao().equals("Finalizado")) ? true : null;
            metaParcial = (filtro.getSituacao().equals("Parcial")) ? true : null;
        }
        return metaResponseRepository.getAllMetaByFilter(
            filtro.getAno(),
            filtro.getMes(),
            filtro.getIdProcesso(),
            filtro.getPesquisa(),
            metaAtingida,
            metaParcial,
            pageable
        );
    }

    public List<MetaDTO> findAllByIdMetaObjetivo(Long id) {
        log.debug("Request to find all Meta by MetaObjetivo : {}", id);
        return metaRepository.findAllByMetaObjetivoId(id)
            .stream()
            .map(metaMapper::toDto)
            .collect(Collectors.toList());
    }
}
