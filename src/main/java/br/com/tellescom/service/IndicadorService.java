package br.com.tellescom.service;

import br.com.tellescom.domain.Indicador;
import br.com.tellescom.domain.IndicadorMeta;
import br.com.tellescom.domain.enumeration.EnumTemporal;
import br.com.tellescom.domain.request.GraficoIndicadorRequest;
import br.com.tellescom.domain.response.graficos.BaseGraficoIndicadorResponse;
import br.com.tellescom.domain.response.graficos.DadoMetaPorPeriodo;
import br.com.tellescom.domain.response.graficos.GraficoIndicadorResponse;
import br.com.tellescom.domain.response.graficos.QualidadeProducaoIndicadorResponse;
import br.com.tellescom.repository.IndicadorMetaRepository;
import br.com.tellescom.repository.IndicadorRepository;
import br.com.tellescom.service.dto.IndicadorDTO;
import br.com.tellescom.service.mapper.IndicadorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Service Implementation for managing {@link br.com.tellescom.domain.Indicador}.
 */
@Service
@Transactional
public class IndicadorService {

    private final Logger log = LoggerFactory.getLogger(IndicadorService.class);

    private final IndicadorRepository indicadorRepository;


    private final IndicadorMetaRepository indicadorMetaRepository;

    private final IndicadorMapper indicadorMapper;

    public IndicadorService(IndicadorRepository indicadorRepository, IndicadorMetaRepository indicadorMetaRepository, IndicadorMapper indicadorMapper) {
        this.indicadorRepository = indicadorRepository;
        this.indicadorMetaRepository = indicadorMetaRepository;
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
        metasPorProcesso.setDados(new ArrayList<>());

        Optional<IndicadorMeta> metaOpt = indicadorMetaRepository.findByIndicadorAndAnoIndicadorAndProcesso(
            request.getIdIndicador(),
            request.getIdProcesso(),
            String.valueOf(request.getAnoIndicador())
        );

        IndicadorMeta indicadorMeta = metaOpt.orElse(null);
        if (indicadorMeta != null) {

            int dividendo = getDividendo(indicadorMeta.getFrequencia());



        }

        return metasPorProcesso;
    }

    private int getDividendo(EnumTemporal frequencia) {

        int dividendo;

        switch (frequencia) {
            case MENSAL -> {
                dividendo = 12;
            }
            case BIMESTRAL -> {
                dividendo = 2;
            }
            case TRIMESTRAL -> {
                dividendo = 3;
            }
            case QUADRIMESTRAL -> {
                dividendo = 4;
            }
            case SEMESTRAL -> {
                dividendo = 6;
            }
            default -> {
                dividendo = 1;
            }
        }

        return dividendo;
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
        metasPeriodo.setDados(new ArrayList<>());

        Optional<IndicadorMeta> metaOpt = indicadorMetaRepository.findByIndicadorAndAnoIndicadorAndProcesso(
            request.getIdIndicador(),
            request.getIdProcesso(),
            String.valueOf(request.getAnoIndicador())
        );

        IndicadorMeta indicadorMeta = metaOpt.orElse(null);
        if (indicadorMeta != null) {

            int dividendo = getDividendo(indicadorMeta.getFrequencia());

            for (int i = 0; i < dividendo; i++) {

                DadoMetaPorPeriodo dadoMetaPorPeriodo = new DadoMetaPorPeriodo();

                dadoMetaPorPeriodo.setMeta(indicadorMeta.getMeta(i+1));
                dadoMetaPorPeriodo.setRealizado(indicadorMeta.getMedicao(i+1));
                dadoMetaPorPeriodo.setUnidadeTemporal(indicadorMeta.getFrequencia().getValue());
                dadoMetaPorPeriodo.setUnidadeMedida(indicadorMeta.getIndicador().getUnidade().getValue());

                metasPeriodo.getDados().add(dadoMetaPorPeriodo);
            }

        }

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
