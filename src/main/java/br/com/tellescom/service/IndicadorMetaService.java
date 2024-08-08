package br.com.tellescom.service;

import br.com.tellescom.domain.IndicadorMeta;
import br.com.tellescom.repository.IndicadorMetaRepository;
import br.com.tellescom.service.dto.IndicadorMetaDTO;
import br.com.tellescom.service.mapper.IndicadorMetaMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link br.com.tellescom.domain.IndicadorMeta}.
 */
@Service
@Transactional
public class IndicadorMetaService {

    private static final Logger log = LoggerFactory.getLogger(IndicadorMetaService.class);

    private final IndicadorMetaRepository indicadorMetaRepository;

    private final IndicadorMetaMapper indicadorMetaMapper;

    public IndicadorMetaService(IndicadorMetaRepository indicadorMetaRepository, IndicadorMetaMapper indicadorMetaMapper) {
        this.indicadorMetaRepository = indicadorMetaRepository;
        this.indicadorMetaMapper = indicadorMetaMapper;
    }

    /**
     * Save a indicadorMeta.
     *
     * @param indicadorMetaDTO the entity to save.
     * @return the persisted entity.
     */
    public IndicadorMetaDTO save(IndicadorMetaDTO indicadorMetaDTO) {
        log.debug("Request to save IndicadorMeta : {}", indicadorMetaDTO);
        IndicadorMeta indicadorMeta = indicadorMetaMapper.toEntity(indicadorMetaDTO);
        indicadorMeta = indicadorMetaRepository.save(indicadorMeta);
        return indicadorMetaMapper.toDto(indicadorMeta);
    }

    /**
     * Update a indicadorMeta.
     *
     * @param indicadorMetaDTO the entity to save.
     * @return the persisted entity.
     */
    public IndicadorMetaDTO update(IndicadorMetaDTO indicadorMetaDTO) {
        log.debug("Request to update IndicadorMeta : {}", indicadorMetaDTO);
        IndicadorMeta indicadorMeta = indicadorMetaMapper.toEntity(indicadorMetaDTO);
        indicadorMeta = indicadorMetaRepository.save(indicadorMeta);
        return indicadorMetaMapper.toDto(indicadorMeta);
    }

    /**
     * Partially update a indicadorMeta.
     *
     * @param indicadorMetaDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<IndicadorMetaDTO> partialUpdate(IndicadorMetaDTO indicadorMetaDTO) {
        log.debug("Request to partially update IndicadorMeta : {}", indicadorMetaDTO);

        return indicadorMetaRepository
            .findById(indicadorMetaDTO.getId())
            .map(existingIndicadorMeta -> {
                indicadorMetaMapper.partialUpdate(existingIndicadorMeta, indicadorMetaDTO);

                return existingIndicadorMeta;
            })
            .map(indicadorMetaRepository::save)
            .map(indicadorMetaMapper::toDto);
    }

    /**
     * Get all the indicadorMetas.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<IndicadorMetaDTO> findAll() {
        log.debug("Request to get all IndicadorMetas");
        return indicadorMetaRepository.findAll().stream().map(indicadorMetaMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one indicadorMeta by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<IndicadorMetaDTO> findOne(Long id) {
        log.debug("Request to get IndicadorMeta : {}", id);
        return indicadorMetaRepository.findById(id).map(indicadorMetaMapper::toDto);
    }

    /**
     * Delete the indicadorMeta by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete IndicadorMeta : {}", id);
        indicadorMetaRepository.deleteById(id);
    }
}
