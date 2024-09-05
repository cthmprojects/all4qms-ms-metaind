package br.com.tellescom.service;

import br.com.tellescom.domain.Indicador;
import br.com.tellescom.domain.request.GraficoIndicadorRequest;
import br.com.tellescom.domain.response.graficos.BaseGraficoIndicadorResponse;
import br.com.tellescom.domain.response.graficos.GraficoIndicadorResponse;
import br.com.tellescom.domain.response.graficos.QualidadeProducaoIndicadorResponse;
import br.com.tellescom.repository.IndicadorRepository;
import br.com.tellescom.service.dto.IndicadorDTO;
import br.com.tellescom.service.mapper.IndicadorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link br.com.tellescom.domain.Indicador}.
 */
@Service
@Transactional
public class IndicadorService {

    private final Logger log = LoggerFactory.getLogger(IndicadorService.class);

    private final IndicadorRepository indicadorRepository;

    private final IndicadorMapper indicadorMapper;

    public IndicadorService(IndicadorRepository indicadorRepository, IndicadorMapper indicadorMapper) {
        this.indicadorRepository = indicadorRepository;
        this.indicadorMapper = indicadorMapper;
    }

    /**
     * Save a indicador.
     *
     * @param indicadorDTO the entity to save.
     * @return the persisted entity.
     */
    public IndicadorDTO save(IndicadorDTO indicadorDTO) {
        log.debug("Request to save Indicador : {}", indicadorDTO);
        Indicador indicador = indicadorMapper.toEntity(indicadorDTO);
        indicador = indicadorRepository.save(indicador);
        return indicadorMapper.toDto(indicador);
    }

    /**
     * Update a indicador.
     *
     * @param indicadorDTO the entity to save.
     * @return the persisted entity.
     */
    public IndicadorDTO update(IndicadorDTO indicadorDTO) {
        log.debug("Request to update Indicador : {}", indicadorDTO);
        Indicador indicador = indicadorMapper.toEntity(indicadorDTO);
        indicador = indicadorRepository.save(indicador);
        return indicadorMapper.toDto(indicador);
    }

    /**
     * Partially update a indicador.
     *
     * @param indicadorDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<IndicadorDTO> partialUpdate(IndicadorDTO indicadorDTO) {
        log.debug("Request to partially update Indicador : {}", indicadorDTO);

        return indicadorRepository
            .findById(indicadorDTO.getId())
            .map(existingIndicador -> {
                indicadorMapper.partialUpdate(existingIndicador, indicadorDTO);

                return existingIndicador;
            })
            .map(indicadorRepository::save)
            .map(indicadorMapper::toDto);
    }

    /**
     * Get all the indicadors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<IndicadorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Indicadors");
        return indicadorRepository.findAll(pageable).map(indicadorMapper::toDto);
    }

    /**
     * Get one indicador by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<IndicadorDTO> findOne(Long id) {
        log.debug("Request to get Indicador : {}", id);
        return indicadorRepository.findById(id).map(indicadorMapper::toDto);
    }

    /**
     * Delete the indicador by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Indicador : {}", id);
        indicadorRepository.deleteById(id);
    }

    public GraficoIndicadorResponse graficoMetasPorProcesso(GraficoIndicadorRequest request) {

        GraficoIndicadorResponse metasPorProcesso = new GraficoIndicadorResponse();
        metasPorProcesso.setIdProcesso(request.getIdProcesso());
        metasPorProcesso.setIdIndicador(request.getIdIndicador());
        metasPorProcesso.setAnoIndicador(request.getAnoIndicador());


        return metasPorProcesso;
    }

    public QualidadeProducaoIndicadorResponse graficoQualidadeGeralProducao(GraficoIndicadorRequest request) {

        QualidadeProducaoIndicadorResponse qualidadeProducao = new QualidadeProducaoIndicadorResponse();
        qualidadeProducao.setIdProcesso(request.getIdProcesso());
        qualidadeProducao.setIdIndicador(request.getIdIndicador());
        qualidadeProducao.setAnoIndicador(request.getAnoIndicador());


        return qualidadeProducao;
    }

    public GraficoIndicadorResponse graficoPreenchimentoIndicadores(GraficoIndicadorRequest request) {

        GraficoIndicadorResponse preenchimentoIndicadores = new GraficoIndicadorResponse();
        preenchimentoIndicadores.setIdProcesso(request.getIdProcesso());
        preenchimentoIndicadores.setIdIndicador(request.getIdIndicador());
        preenchimentoIndicadores.setAnoIndicador(request.getAnoIndicador());


        return preenchimentoIndicadores;
    }

    public GraficoIndicadorResponse graficoMetasPorPeriodo(GraficoIndicadorRequest request) {

        GraficoIndicadorResponse metasPeriodo = new GraficoIndicadorResponse();
        metasPeriodo.setIdProcesso(request.getIdProcesso());
        metasPeriodo.setIdIndicador(request.getIdIndicador());
        metasPeriodo.setAnoIndicador(request.getAnoIndicador());


        return metasPeriodo;
    }

    public GraficoIndicadorResponse graficoComparacaoPeriodos(GraficoIndicadorRequest request) {

        GraficoIndicadorResponse comparacaoPeriodos = new GraficoIndicadorResponse();
        comparacaoPeriodos.setIdProcesso(request.getIdProcesso());
        comparacaoPeriodos.setIdIndicador(request.getIdIndicador());
        comparacaoPeriodos.setAnoIndicador(request.getAnoIndicador());


        return comparacaoPeriodos;
    }
}
