package br.com.tellescom.service;

import br.com.tellescom.domain.MetaResultado;
import br.com.tellescom.repository.MetaResultadoRepository;
import br.com.tellescom.service.dto.MetaResultadoDTO;
import br.com.tellescom.service.mapper.MetaResultadoMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link br.com.tellescom.domain.MetaResultado}.
 */
@Service
@Transactional
public class MetaResultadoService {

    private static final Logger log = LoggerFactory.getLogger(MetaResultadoService.class);

    private final MetaResultadoRepository metaResultadoRepository;

    private final MetaResultadoMapper metaResultadoMapper;

    public MetaResultadoService(MetaResultadoRepository metaResultadoRepository, MetaResultadoMapper metaResultadoMapper) {
        this.metaResultadoRepository = metaResultadoRepository;
        this.metaResultadoMapper = metaResultadoMapper;
    }

    /**
     * Save a metaResultado.
     *
     * @param metaResultadoDTO the entity to save.
     * @return the persisted entity.
     */
    public MetaResultadoDTO save(MetaResultadoDTO metaResultadoDTO) {
        log.debug("Request to save MetaResultado : {}", metaResultadoDTO);
        MetaResultado metaResultado = metaResultadoMapper.toEntity(metaResultadoDTO);
        metaResultado = metaResultadoRepository.save(metaResultado);
        return metaResultadoMapper.toDto(metaResultado);
    }

    /**
     * Update a metaResultado.
     *
     * @param metaResultadoDTO the entity to save.
     * @return the persisted entity.
     */
    public MetaResultadoDTO update(MetaResultadoDTO metaResultadoDTO) {
        log.debug("Request to update MetaResultado : {}", metaResultadoDTO);
        MetaResultado metaResultado = metaResultadoMapper.toEntity(metaResultadoDTO);
        metaResultado = metaResultadoRepository.save(metaResultado);
        return metaResultadoMapper.toDto(metaResultado);
    }

    /**
     * Partially update a metaResultado.
     *
     * @param metaResultadoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<MetaResultadoDTO> partialUpdate(MetaResultadoDTO metaResultadoDTO) {
        log.debug("Request to partially update MetaResultado : {}", metaResultadoDTO);

        return metaResultadoRepository
            .findById(metaResultadoDTO.getId())
            .map(existingMetaResultado -> {
                metaResultadoMapper.partialUpdate(existingMetaResultado, metaResultadoDTO);

                return existingMetaResultado;
            })
            .map(metaResultadoRepository::save)
            .map(metaResultadoMapper::toDto);
    }

    /**
     * Get all the metaResultados.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<MetaResultadoDTO> findAll() {
        log.debug("Request to get all MetaResultados");
        return metaResultadoRepository.findAll().stream().map(metaResultadoMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one metaResultado by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MetaResultadoDTO> findOne(Long id) {
        log.debug("Request to get MetaResultado : {}", id);
        return metaResultadoRepository.findById(id).map(metaResultadoMapper::toDto);
    }

    /**
     * Delete the metaResultado by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MetaResultado : {}", id);
        metaResultadoRepository.deleteById(id);
    }
}
