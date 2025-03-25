package br.com.tellescom.service;

import br.com.tellescom.domain.AcaoCritica;
import br.com.tellescom.domain.enumeration.EnumTipoCritica;
import br.com.tellescom.repository.AcaoCriticaRepository;
import br.com.tellescom.service.dto.AcaoCriticaDTO;
import br.com.tellescom.service.mapper.AcaoCriticaMapper;

import java.time.ZonedDateTime;
import java.util.*;
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

    private final NotificacaoUsuarioService notificacaoUsuarioService;

    public AcaoCriticaService(AcaoCriticaRepository acaoCriticaRepository, AcaoCriticaMapper acaoCriticaMapper, NotificacaoUsuarioService notificacaoUsuarioService) {
        this.acaoCriticaRepository = acaoCriticaRepository;
        this.acaoCriticaMapper = acaoCriticaMapper;
        this.notificacaoUsuarioService = notificacaoUsuarioService;
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
        acaoCritica.setIsNotificado(true);
        acaoCritica = acaoCriticaRepository.save(acaoCritica);
        AcaoCriticaDTO result = acaoCriticaMapper.toDto(acaoCritica);
        notificacaoUsuarioService.enviarEmailAcaoCritica(EnumTipoCritica.CRIAR, result);
        return result;
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
        AcaoCriticaDTO result = acaoCriticaMapper.toDto(acaoCritica);
        notificacaoUsuarioService.enviarEmailAcaoCritica(EnumTipoCritica.ATUALIZAR, result);
        return result;
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
    @Transactional
    public void delete(Long id) {
        LOG.debug("Request to delete AcaoCritica : {}", id);
        AcaoCritica acaoCritica = acaoCriticaRepository.findById(id).orElse(null);
        if (acaoCritica != null) {
            AcaoCriticaDTO acaoCriticaDTO = acaoCriticaMapper.toDto(acaoCritica);
            notificacaoUsuarioService.enviarEmailAcaoCritica(EnumTipoCritica.CANCELAR, acaoCriticaDTO);
            acaoCriticaRepository.deleteById(id);
        }
    }

    public Set<AcaoCriticaDTO> findAllByIndicadorCritica(Long idIndicadorCritica) {
        LOG.debug("Request to return a SET of AcaoCriticaDTO from Id of IndicadorMeta: {}", idIndicadorCritica);
        return acaoCriticaRepository.findAllByIndicadorCritica(idIndicadorCritica)
            .stream()
            .map(acaoCriticaMapper::toDto)
            .collect(Collectors.toSet());
    }

    public List<AcaoCriticaDTO> salvaAcaoCriticaEmLote(List<AcaoCriticaDTO> request) {
        LOG.debug("Request to save a lot of AcaoCriticaDTO: {}", request);
        return request.stream().map(this::save).toList();
    }


    public List<AcaoCriticaDTO> atualizaAcaoCriticaEmLote(List<AcaoCriticaDTO> request) {
        LOG.debug("Request to update a lot of AcaoCriticaDTO: {}", request);
        return request.stream()
            .map(item -> {
                try {
                    if (item.getId() == null) {
                        return this.save(item);
                    }
                    return this.update(item);
                } catch (Exception e) {
                    LOG.error("Error processing item: {}", item, e);
                    return null;
                }
            })
            .filter(Objects::nonNull)
            .toList();
    }
}
