package br.com.tellescom.web.rest;

import br.com.tellescom.repository.IndicadorCriticaRepository;
import br.com.tellescom.service.IndicadorCriticaService;
import br.com.tellescom.service.dto.IndicadorCriticaDTO;
import br.com.tellescom.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link br.com.tellescom.domain.IndicadorCritica}.
 */
@RestController
@RequestMapping("/api/indicadores/criticas")
public class IndicadorCriticaResource {

    private static final Logger LOG = LoggerFactory.getLogger(IndicadorCriticaResource.class);

    private static final String ENTITY_NAME = "all4QmsMsMetaIndIndicadorCritica";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IndicadorCriticaService indicadorCriticaService;

    private final IndicadorCriticaRepository indicadorCriticaRepository;

    public IndicadorCriticaResource(
        IndicadorCriticaService indicadorCriticaService,
        IndicadorCriticaRepository indicadorCriticaRepository
    ) {
        this.indicadorCriticaService = indicadorCriticaService;
        this.indicadorCriticaRepository = indicadorCriticaRepository;
    }

    /**
     * {@code POST  /indicador-criticas} : Create a new indicadorCritica.
     *
     * @param indicadorCriticaDTO the indicadorCriticaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new indicadorCriticaDTO, or with status {@code 400 (Bad Request)} if the indicadorCritica has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<IndicadorCriticaDTO> createIndicadorCritica(@Valid @RequestBody IndicadorCriticaDTO indicadorCriticaDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save IndicadorCritica : {}", indicadorCriticaDTO);
        if (indicadorCriticaDTO.getId() != null) {
            throw new BadRequestAlertException("A new indicadorCritica cannot already have an ID", ENTITY_NAME, "idexists");
        }
        indicadorCriticaDTO = indicadorCriticaService.save(indicadorCriticaDTO);
        return ResponseEntity.created(new URI("/api/indicador-criticas/" + indicadorCriticaDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, indicadorCriticaDTO.getId().toString()))
            .body(indicadorCriticaDTO);
    }

    /**
     * {@code PUT  /indicador-criticas/:id} : Updates an existing indicadorCritica.
     *
     * @param id the id of the indicadorCriticaDTO to save.
     * @param indicadorCriticaDTO the indicadorCriticaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated indicadorCriticaDTO,
     * or with status {@code 400 (Bad Request)} if the indicadorCriticaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the indicadorCriticaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<IndicadorCriticaDTO> updateIndicadorCritica(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody IndicadorCriticaDTO indicadorCriticaDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update IndicadorCritica : {}, {}", id, indicadorCriticaDTO);
        if (indicadorCriticaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, indicadorCriticaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!indicadorCriticaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        indicadorCriticaDTO = indicadorCriticaService.update(indicadorCriticaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, indicadorCriticaDTO.getId().toString()))
            .body(indicadorCriticaDTO);
    }

    /**
     * {@code PATCH  /indicador-criticas/:id} : Partial updates given fields of an existing indicadorCritica, field will ignore if it is null
     *
     * @param id the id of the indicadorCriticaDTO to save.
     * @param indicadorCriticaDTO the indicadorCriticaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated indicadorCriticaDTO,
     * or with status {@code 400 (Bad Request)} if the indicadorCriticaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the indicadorCriticaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the indicadorCriticaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<IndicadorCriticaDTO> partialUpdateIndicadorCritica(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody IndicadorCriticaDTO indicadorCriticaDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update IndicadorCritica partially : {}, {}", id, indicadorCriticaDTO);
        if (indicadorCriticaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, indicadorCriticaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!indicadorCriticaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<IndicadorCriticaDTO> result = indicadorCriticaService.partialUpdate(indicadorCriticaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, indicadorCriticaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /indicador-criticas} : get all the indicadorCriticas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of indicadorCriticas in body.
     */
    @GetMapping("")
    public List<IndicadorCriticaDTO> getAllIndicadorCriticas() {
        LOG.debug("REST request to get all IndicadorCriticas");
        return indicadorCriticaService.findAll();
    }

    /**
     * {@code GET  /indicador-criticas/:id} : get the "id" indicadorCritica.
     *
     * @param id the id of the indicadorCriticaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the indicadorCriticaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<IndicadorCriticaDTO> getIndicadorCritica(@PathVariable("id") Long id) {
        LOG.debug("REST request to get IndicadorCritica : {}", id);
        Optional<IndicadorCriticaDTO> indicadorCriticaDTO = indicadorCriticaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(indicadorCriticaDTO);
    }

    /**
     * {@code DELETE  /indicador-criticas/:id} : delete the "id" indicadorCritica.
     *
     * @param id the id of the indicadorCriticaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIndicadorCritica(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete IndicadorCritica : {}", id);
        indicadorCriticaService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    @GetMapping("/byindmeta/{id}")
    public ResponseEntity<List<IndicadorCriticaDTO>> getAllByIdIndicadorMeta(@PathVariable("id") Long id){
        LOG.debug("REST request to get All IndicadorCritica by IndicadorMeta: {}", id);
        return ResponseEntity.ok().body(indicadorCriticaService.getAllByIdIndicadorMeta(id));
    }

    @GetMapping("/byindmeta/{id}/mes/{mes}/ano/{ano}")
    public ResponseEntity<IndicadorCriticaDTO> getByIdIndicadorMetaMesAno(@PathVariable("id") Long id,
                                                                          @PathVariable("mes") Integer mes,
                                                                          @PathVariable("ano") Integer ano) {
        LOG.debug("REST request to get IndicadorCritica by id IndicadorMeta: {}, month: {}, year: {}", id, mes, ano);
        return ResponseEntity.ok().body(indicadorCriticaService.getByIdIndicadorMetaMesAno(id, mes, ano));
    }
}
