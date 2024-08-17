package br.com.tellescom.web.rest;

import br.com.tellescom.repository.IndicadorMetaRepository;
import br.com.tellescom.service.IndicadorMetaService;
import br.com.tellescom.service.dto.IndicadorMetaDTO;
import br.com.tellescom.web.rest.errors.BadRequestAlertException;
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
 * REST controller for managing {@link br.com.tellescom.domain.IndicadorMeta}.
 */
@RestController
@RequestMapping("/api/indicadores/metas")
public class IndicadorMetaResource {

    private final Logger log = LoggerFactory.getLogger(IndicadorMetaResource.class);

    private static final String ENTITY_NAME = "all4QmsMsMetaIndIndicadorMeta";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IndicadorMetaService indicadorMetaService;

    private final IndicadorMetaRepository indicadorMetaRepository;

    public IndicadorMetaResource(IndicadorMetaService indicadorMetaService, IndicadorMetaRepository indicadorMetaRepository) {
        this.indicadorMetaService = indicadorMetaService;
        this.indicadorMetaRepository = indicadorMetaRepository;
    }

    /**
     * {@code POST  /indicador-metas} : Create a new indicadorMeta.
     *
     * @param indicadorMetaDTO the indicadorMetaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new indicadorMetaDTO, or with status {@code 400 (Bad Request)} if the indicadorMeta has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<IndicadorMetaDTO> createIndicadorMeta(@RequestBody IndicadorMetaDTO indicadorMetaDTO) throws URISyntaxException {
        log.debug("REST request to save IndicadorMeta : {}", indicadorMetaDTO);
        if (indicadorMetaDTO.getId() != null) {
            throw new BadRequestAlertException("A new indicadorMeta cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IndicadorMetaDTO result = indicadorMetaService.save(indicadorMetaDTO);
        return ResponseEntity
            .created(new URI("/api/indicador-metas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /indicador-metas/:id} : Updates an existing indicadorMeta.
     *
     * @param id the id of the indicadorMetaDTO to save.
     * @param indicadorMetaDTO the indicadorMetaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated indicadorMetaDTO,
     * or with status {@code 400 (Bad Request)} if the indicadorMetaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the indicadorMetaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<IndicadorMetaDTO> updateIndicadorMeta(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody IndicadorMetaDTO indicadorMetaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update IndicadorMeta : {}, {}", id, indicadorMetaDTO);
        if (indicadorMetaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, indicadorMetaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!indicadorMetaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        IndicadorMetaDTO result = indicadorMetaService.update(indicadorMetaDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, indicadorMetaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /indicador-metas/:id} : Partial updates given fields of an existing indicadorMeta, field will ignore if it is null
     *
     * @param id the id of the indicadorMetaDTO to save.
     * @param indicadorMetaDTO the indicadorMetaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated indicadorMetaDTO,
     * or with status {@code 400 (Bad Request)} if the indicadorMetaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the indicadorMetaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the indicadorMetaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<IndicadorMetaDTO> partialUpdateIndicadorMeta(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody IndicadorMetaDTO indicadorMetaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update IndicadorMeta partially : {}, {}", id, indicadorMetaDTO);
        if (indicadorMetaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, indicadorMetaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!indicadorMetaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<IndicadorMetaDTO> result = indicadorMetaService.partialUpdate(indicadorMetaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, indicadorMetaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /indicador-metas} : get all the indicadorMetas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of indicadorMetas in body.
     */
    @GetMapping("")
    public List<IndicadorMetaDTO> getAllIndicadorMetas() {
        log.debug("REST request to get all IndicadorMetas");
        return indicadorMetaService.findAll();
    }

    /**
     * {@code GET  /indicador-metas/:id} : get the "id" indicadorMeta.
     *
     * @param id the id of the indicadorMetaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the indicadorMetaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<IndicadorMetaDTO> getIndicadorMeta(@PathVariable("id") Long id) {
        log.debug("REST request to get IndicadorMeta : {}", id);
        Optional<IndicadorMetaDTO> indicadorMetaDTO = indicadorMetaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(indicadorMetaDTO);
    }

    /**
     * {@code DELETE  /indicador-metas/:id} : delete the "id" indicadorMeta.
     *
     * @param id the id of the indicadorMetaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIndicadorMeta(@PathVariable("id") Long id) {
        log.debug("REST request to delete IndicadorMeta : {}", id);
        indicadorMetaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
