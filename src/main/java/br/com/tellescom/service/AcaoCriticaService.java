package br.com.tellescom.service;

import br.com.tellescom.domain.AcaoCritica;
import br.com.tellescom.repository.AcaoCriticaRepository;
import br.com.tellescom.service.dto.AcaoCriticaDTO;
import br.com.tellescom.service.mapper.AcaoCriticaMapper;

import java.time.ZonedDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link br.com.tellescom.domain.AcaoCritica}.
 */
@Service
@Transactional
public class AcaoCriticaService {

    private static final Logger LOG = LoggerFactory.getLogger(AcaoCriticaService.class);

    private final AcaoCriticaRepository acaoCriticaRepository;

    private final AcaoCriticaMapper acaoCriticaMapper;

    public AcaoCriticaService(AcaoCriticaRepository acaoCriticaRepository, AcaoCriticaMapper acaoCriticaMapper) {
        this.acaoCriticaRepository = acaoCriticaRepository;
        this.acaoCriticaMapper = acaoCriticaMapper;
    }

    /**
     * Save a acaoCritica.
     *
     * @param acaoCriticaDTO the entity to save.
     * @return the persisted entity.
     */
    public AcaoCriticaDTO save(AcaoCriticaDTO acaoCriticaDTO) {
        LOG.debug("Request to save AcaoCritica : {}", acaoCriticaDTO);
        AcaoCritica acaoCritica = acaoCriticaMapper.toEntity(acaoCriticaDTO);
        acaoCritica.setCriadoEm(ZonedDateTime.now());
        acaoCritica = acaoCriticaRepository.save(acaoCritica);
        return acaoCriticaMapper.toDto(acaoCritica);
    }

    /**
     * Update a acaoCritica.
     *
     * @param acaoCriticaDTO the entity to save.
     * @return the persisted entity.
     */
    public AcaoCriticaDTO update(AcaoCriticaDTO acaoCriticaDTO) {
        LOG.debug("Request to update AcaoCritica : {}", acaoCriticaDTO);
        AcaoCritica acaoCritica = acaoCriticaMapper.toEntity(acaoCriticaDTO);
        acaoCritica.setAtualizadoEm(ZonedDateTime.now());
        acaoCritica = acaoCriticaRepository.save(acaoCritica);
        return acaoCriticaMapper.toDto(acaoCritica);
    }

    /**
     * Partially update a acaoCritica.
     *
     * @param acaoCriticaDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AcaoCriticaDTO> partialUpdate(AcaoCriticaDTO acaoCriticaDTO) {
        LOG.debug("Request to partially update AcaoCritica : {}", acaoCriticaDTO);

        return acaoCriticaRepository
            .findById(acaoCriticaDTO.getId())
            .map(existingAcaoCritica -> {
                acaoCriticaMapper.partialUpdate(existingAcaoCritica, acaoCriticaDTO);
                existingAcaoCritica.setAtualizadoEm(ZonedDateTime.now());
                return existingAcaoCritica;
            })
            .map(acaoCriticaRepository::save)
            .map(acaoCriticaMapper::toDto);
    }

    /**
     * Get all the acaoCriticas.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AcaoCriticaDTO> findAll() {
        LOG.debug("Request to get all AcaoCriticas");
        return acaoCriticaRepository.findAll().stream().map(acaoCriticaMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one acaoCritica by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AcaoCriticaDTO> findOne(Long id) {
        LOG.debug("Request to get AcaoCritica : {}", id);
        return acaoCriticaRepository.findById(id).map(acaoCriticaMapper::toDto);
    }

    /**
     * Delete the acaoCritica by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete AcaoCritica : {}", id);
        acaoCriticaRepository.deleteById(id);
    }

    public Set<AcaoCriticaDTO> findAllByIndicadorCritica(Long idIndicadorCritica){
        LOG.debug("Request to return a SET of AcaoCriticaDTO from Id of IndicadorMeta: {}", idIndicadorCritica);
        return acaoCriticaRepository.findAllByIndicadorCritica(idIndicadorCritica)
            .stream()
            .map(acaoCriticaMapper::toDto)
            .collect(Collectors.toSet());
    }
}
