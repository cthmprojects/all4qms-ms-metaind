package br.com.tellescom.service;

import br.com.tellescom.domain.IndicadorCritica;
import br.com.tellescom.repository.IndicadorCriticaRepository;
import br.com.tellescom.service.dto.IndicadorCriticaDTO;
import br.com.tellescom.service.mapper.IndicadorCriticaMapper;

import java.time.Instant;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link br.com.tellescom.domain.IndicadorCritica}.
 */
@Service
@Transactional
public class IndicadorCriticaService {

    private static final Logger LOG = LoggerFactory.getLogger(IndicadorCriticaService.class);

    private final IndicadorCriticaRepository indicadorCriticaRepository;

    private final IndicadorCriticaMapper indicadorCriticaMapper;

    private final AcaoCriticaService acaoCriticaService;

    public IndicadorCriticaService(IndicadorCriticaRepository indicadorCriticaRepository, IndicadorCriticaMapper indicadorCriticaMapper, AcaoCriticaService acaoCriticaService) {
        this.indicadorCriticaRepository = indicadorCriticaRepository;
        this.indicadorCriticaMapper = indicadorCriticaMapper;
        this.acaoCriticaService = acaoCriticaService;
    }

    /**
     * Save a indicadorCritica.
     *
     * @param indicadorCriticaDTO the entity to save.
     * @return the persisted entity.
     */
    public IndicadorCriticaDTO save(IndicadorCriticaDTO indicadorCriticaDTO) {
        LOG.debug("Request to save IndicadorCritica : {}", indicadorCriticaDTO);
        IndicadorCritica indicadorCritica = indicadorCriticaMapper.toEntity(indicadorCriticaDTO);
        indicadorCritica.setCriadoEm(Instant.now());
        indicadorCritica = indicadorCriticaRepository.save(indicadorCritica);
        return indicadorCriticaMapper.toDto(indicadorCritica);
    }

    /**
     * Update a indicadorCritica.
     *
     * @param indicadorCriticaDTO the entity to save.
     * @return the persisted entity.
     */
    public IndicadorCriticaDTO update(IndicadorCriticaDTO indicadorCriticaDTO) {
        LOG.debug("Request to update IndicadorCritica : {}", indicadorCriticaDTO);
        IndicadorCritica indicadorCritica = indicadorCriticaMapper.toEntity(indicadorCriticaDTO);
        indicadorCritica.atualizadoEm(Instant.now());
        indicadorCritica = indicadorCriticaRepository.save(indicadorCritica);
        return indicadorCriticaMapper.toDto(indicadorCritica);
    }

    /**
     * Partially update a indicadorCritica.
     *
     * @param indicadorCriticaDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<IndicadorCriticaDTO> partialUpdate(IndicadorCriticaDTO indicadorCriticaDTO) {
        LOG.debug("Request to partially update IndicadorCritica : {}", indicadorCriticaDTO);

        return indicadorCriticaRepository
            .findById(indicadorCriticaDTO.getId())
            .map(existingIndicadorCritica -> {

                indicadorCriticaMapper.partialUpdate(existingIndicadorCritica, indicadorCriticaDTO);
                existingIndicadorCritica.setAtualizadoEm(Instant.now());
                return existingIndicadorCritica;
            })
            .map(indicadorCriticaRepository::save)
            .map(indicadorCriticaMapper::toDto);
    }

    /**
     * Get all the indicadorCriticas.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<IndicadorCriticaDTO> findAll() {
        LOG.debug("Request to get all IndicadorCriticas");
        return indicadorCriticaRepository
            .findAll()
            .stream()
            .map(indicadorCritica -> indicadorCriticaMapper.toDto(indicadorCritica, acaoCriticaService))
            .toList();
    }

    /**
     * Get one indicadorCritica by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<IndicadorCriticaDTO> findOne(Long id) {
        LOG.debug("Request to get IndicadorCritica : {}", id);
        return indicadorCriticaRepository.findById(id).map(indicadorCriticaMapper::toDto);
    }

    /**
     * Delete the indicadorCritica by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete IndicadorCritica : {}", id);
        indicadorCriticaRepository.deleteById(id);
    }

    public List<IndicadorCriticaDTO> getAllByIdIndicadorMeta(Long id) {
        LOG.debug("Request to get all IndicadorCriticas by id IndicadorMeta  : {}", id);
        return indicadorCriticaRepository.findAllByIdIndicadorMeta(id).stream().map(indicadorCriticaMapper::toDto).collect(Collectors.toList());
    }

    public IndicadorCriticaDTO getByIdIndicadorMetaMesAno(Long id, Integer mes, Integer ano) {
        LOG.debug("Request to get IndicadorCritica by id IndicadorMeta: {}, month: {}, year: {}", id, mes, ano);
        return indicadorCriticaMapper.toDto(indicadorCriticaRepository.findByIdIndicadorMetaAndMesAndAno(id, mes, ano));
    }
}
