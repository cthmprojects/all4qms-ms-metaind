package br.com.tellescom.web.rest;

import br.com.tellescom.domain.request.GraficoIndicadorRequest;
import br.com.tellescom.domain.response.graficos.BaseGraficoIndicadorResponse;
import br.com.tellescom.domain.response.graficos.GraficoIndicadorResponse;
import br.com.tellescom.domain.response.graficos.QualidadeProducaoIndicadorResponse;
import br.com.tellescom.repository.IndicadorRepository;
import br.com.tellescom.service.IndicadorService;
import br.com.tellescom.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;

/**
 * REST controller for managing {@link br.com.tellescom.domain.Indicador}.
 */
@RestController
@RequestMapping("/api/indicadores/graficos")
public class GraficosIndicadoresResource {

    private final Logger log = LoggerFactory.getLogger(GraficosIndicadoresResource.class);

    private static final String ENTITY_NAME = "all4QmsMsGraficoIndicador";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IndicadorService indicadorService;

    private final IndicadorRepository indicadorRepository;

    public GraficosIndicadoresResource(IndicadorService indicadorService, IndicadorRepository indicadorRepository) {
        this.indicadorService = indicadorService;
        this.indicadorRepository = indicadorRepository;
    }

    /**
     * {@code GET  /indicadores/graficos/producao} : Qualidade Geral da Producao.
     *
     * @param indicador id do {@link br.com.tellescom.domain.Indicador}
     * @param processo  id do Processo
     * @param ano       ano da meta do Indicador
     * @return the {@link ResponseEntity} with status {@code 200 (OK)}.
     */
    @GetMapping("/producao")
    public ResponseEntity<QualidadeProducaoIndicadorResponse> getQualidadeGeral(
        @RequestParam Integer indicador,
        @RequestParam Integer processo,
        @RequestParam(required = false) Integer ano
    ) {
        log.debug("REST request qualidade geral da produção");

        if (indicador == null || indicador <= 0) {
            throw new BadRequestAlertException("O id do Indicador é obrigatório", ENTITY_NAME, "semIndicador");
        }

        if (processo == null || processo <= 0) {
            throw new BadRequestAlertException("O id do Processo é obrigatório", ENTITY_NAME, "semProcesso");
        }

        GraficoIndicadorRequest request = new GraficoIndicadorRequest();
        request.setIdProcesso(processo);
        request.setIdIndicador(indicador);
        request.setAnoIndicador(ano != null && ano > 0 ? ano : Calendar.getInstance().get(Calendar.YEAR));

        QualidadeProducaoIndicadorResponse graficoIndicadorResponse = indicadorService.graficoQualidadeGeralProducao(request);

        return ResponseEntity.ok()
            .body(graficoIndicadorResponse);
    }

    /**
     * {@code GET  /indicadores/graficos/metas-processo} : Metas por processo.
     *
     * @param indicador id do {@link br.com.tellescom.domain.Indicador}
     * @param processo  id do Processo
     * @param ano       ano da meta do Indicador
     * @return the {@link ResponseEntity} with status {@code 200 (OK)}.
     */
    @GetMapping("/metas-processo")
    public ResponseEntity<GraficoIndicadorResponse> getMetasProcesso(
        @RequestParam Integer indicador,
        @RequestParam Integer processo,
        @RequestParam(required = false) Integer ano
    ) {
        log.debug("REST request metas por processo");

        if (indicador == null || indicador <= 0) {
            throw new BadRequestAlertException("O id do Indicador é obrigatório", ENTITY_NAME, "semIndicador");
        }

        if (processo == null || processo <= 0) {
            throw new BadRequestAlertException("O id do Processo é obrigatório", ENTITY_NAME, "semProcesso");
        }

        GraficoIndicadorRequest request = new GraficoIndicadorRequest();
        request.setIdProcesso(processo);
        request.setIdIndicador(indicador);
        request.setAnoIndicador(ano != null && ano > 0 ? ano : Calendar.getInstance().get(Calendar.YEAR));

        GraficoIndicadorResponse graficoIndicadorResponse = indicadorService.graficoMetasPorProcesso(request);

        return ResponseEntity.ok()
            .body(graficoIndicadorResponse);
    }

    /**
     * {@code GET  /indicadores/graficos/preenchimento-indicadores} : Preenchimento dos indicadores.
     *
     * @param indicador id do {@link br.com.tellescom.domain.Indicador}
     * @param processo  id do Processo
     * @param ano       ano da meta do Indicador
     * @return the {@link ResponseEntity} with status {@code 200 (OK)}.
     */
    @GetMapping("/preenchimento-indicadores")
    public ResponseEntity<GraficoIndicadorResponse> getPreenchimentoIndicadores(
        @RequestParam Integer indicador,
        @RequestParam Integer processo,
        @RequestParam(required = false) Integer ano
    ) {
        log.debug("REST request preenchimento dos indicadores");

        if (indicador == null || indicador <= 0) {
            throw new BadRequestAlertException("O id do Indicador é obrigatório", ENTITY_NAME, "semIndicador");
        }

        if (processo == null || processo <= 0) {
            throw new BadRequestAlertException("O id do Processo é obrigatório", ENTITY_NAME, "semProcesso");
        }

        GraficoIndicadorRequest request = new GraficoIndicadorRequest();
        request.setIdProcesso(processo);
        request.setIdIndicador(indicador);
        request.setAnoIndicador(ano != null && ano > 0 ? ano : Calendar.getInstance().get(Calendar.YEAR));

        GraficoIndicadorResponse graficoIndicadorResponse = indicadorService.graficoPreenchimentoIndicadores(request);

        return ResponseEntity.ok()
            .body(graficoIndicadorResponse);
    }

    /**
     * {@code GET  /indicadores/graficos/metas-periodo} : Metas por período.
     *
     * @param indicador id do {@link br.com.tellescom.domain.Indicador}
     * @param processo  id do Processo
     * @param ano       ano da meta do Indicador
     * @return the {@link ResponseEntity} with status {@code 200 (OK)}.
     */
    @GetMapping("/metas-periodo")
    public ResponseEntity<GraficoIndicadorResponse> getMetasPeriodo(
        @RequestParam Integer indicador,
        @RequestParam Integer processo,
        @RequestParam(required = false) Integer ano
    ) {
        log.debug("REST request metas por período");

        if (indicador == null || indicador <= 0) {
            throw new BadRequestAlertException("O id do Indicador é obrigatório", ENTITY_NAME, "semIndicador");
        }

        if (processo == null || processo <= 0) {
            throw new BadRequestAlertException("O id do Processo é obrigatório", ENTITY_NAME, "semProcesso");
        }

        GraficoIndicadorRequest request = new GraficoIndicadorRequest();
        request.setIdProcesso(processo);
        request.setIdIndicador(indicador);
        request.setAnoIndicador(ano != null && ano > 0 ? ano : Calendar.getInstance().get(Calendar.YEAR));

        GraficoIndicadorResponse graficoIndicadorResponse = indicadorService.graficoMetasPorPeriodo(request);

        return ResponseEntity.ok()
            .body(graficoIndicadorResponse);
    }

    /**
     * {@code GET  /indicadores/graficos/comparacao-periodos} : Comparacao por periodos.
     *
     * @param indicador id do {@link br.com.tellescom.domain.Indicador}
     * @param processo  id do Processo
     * @param ano       ano da meta do Indicador
     * @return the {@link ResponseEntity} with status {@code 200 (OK)}.
     */
    @GetMapping("/comparacao-periodos")
    public ResponseEntity<GraficoIndicadorResponse> getComparacaoPeriodos(
        @RequestParam Integer indicador,
        @RequestParam Integer processo,
        @RequestParam(required = false) Integer ano
    ) {
        log.debug("REST request comparacao por períodos");

        if (indicador == null || indicador <= 0) {
            throw new BadRequestAlertException("O id do Indicador é obrigatório", ENTITY_NAME, "semIndicador");
        }

        if (processo == null || processo <= 0) {
            throw new BadRequestAlertException("O id do Processo é obrigatório", ENTITY_NAME, "semProcesso");
        }

        GraficoIndicadorRequest request = new GraficoIndicadorRequest();
        request.setIdProcesso(processo);
        request.setIdIndicador(indicador);
        request.setAnoIndicador(ano != null && ano > 0 ? ano : Calendar.getInstance().get(Calendar.YEAR));

        GraficoIndicadorResponse graficoIndicadorResponse = indicadorService.graficoComparacaoPeriodos(request);

        return ResponseEntity.ok()
            .body(graficoIndicadorResponse);
    }

}
