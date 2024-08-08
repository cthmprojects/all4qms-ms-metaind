package br.com.tellescom.service;

import br.com.tellescom.domain.MetaAnexo;
import br.com.tellescom.repository.MetaAnexoRepository;
import br.com.tellescom.service.dto.MetaAnexoDTO;
import br.com.tellescom.service.mapper.MetaAnexoMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link br.com.tellescom.domain.MetaAnexo}.
 */
@Service
@Transactional
public class MetaAnexoService {

    private static final Logger log = LoggerFactory.getLogger(MetaAnexoService.class);

    private final MetaAnexoRepository metaAnexoRepository;

    private final MetaAnexoMapper metaAnexoMapper;

    public MetaAnexoService(MetaAnexoRepository metaAnexoRepository, MetaAnexoMapper metaAnexoMapper) {
        this.metaAnexoRepository = metaAnexoRepository;
        this.metaAnexoMapper = metaAnexoMapper;
    }

    /**
     * Save a metaAnexo.
     *
     * @param metaAnexoDTO the entity to save.
     * @return the persisted entity.
     */
    public MetaAnexoDTO save(MetaAnexoDTO metaAnexoDTO) {
        log.debug("Request to save MetaAnexo : {}", metaAnexoDTO);
        MetaAnexo metaAnexo = metaAnexoMapper.toEntity(metaAnexoDTO);
        metaAnexo = metaAnexoRepository.save(metaAnexo);
        return metaAnexoMapper.toDto(metaAnexo);
    }

    /**
     * Update a metaAnexo.
     *
     * @param metaAnexoDTO the entity to save.
     * @return the persisted entity.
     */
    public MetaAnexoDTO update(MetaAnexoDTO metaAnexoDTO) {
        log.debug("Request to update MetaAnexo : {}", metaAnexoDTO);
        MetaAnexo metaAnexo = metaAnexoMapper.toEntity(metaAnexoDTO);
        metaAnexo = metaAnexoRepository.save(metaAnexo);
        return metaAnexoMapper.toDto(metaAnexo);
    }

    /**
     * Partially update a metaAnexo.
     *
     * @param metaAnexoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<MetaAnexoDTO> partialUpdate(MetaAnexoDTO metaAnexoDTO) {
        log.debug("Request to partially update MetaAnexo : {}", metaAnexoDTO);

        return metaAnexoRepository
            .findById(metaAnexoDTO.getId())
            .map(existingMetaAnexo -> {
                metaAnexoMapper.partialUpdate(existingMetaAnexo, metaAnexoDTO);

                return existingMetaAnexo;
            })
            .map(metaAnexoRepository::save)
            .map(metaAnexoMapper::toDto);
    }

    /**
     * Get all the metaAnexos.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<MetaAnexoDTO> findAll() {
        log.debug("Request to get all MetaAnexos");
        return metaAnexoRepository.findAll().stream().map(metaAnexoMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one metaAnexo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MetaAnexoDTO> findOne(Long id) {
        log.debug("Request to get MetaAnexo : {}", id);
        return metaAnexoRepository.findById(id).map(metaAnexoMapper::toDto);
    }

    /**
     * Delete the metaAnexo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MetaAnexo : {}", id);
        metaAnexoRepository.deleteById(id);
    }
}
