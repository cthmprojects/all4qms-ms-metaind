package br.com.tellescom.web.rest;

import br.com.tellescom.repository.MetaRecursoRepository;
import br.com.tellescom.service.MetaRecursoQueryService;
import br.com.tellescom.service.MetaRecursoService;
import br.com.tellescom.service.criteria.MetaRecursoCriteria;
import br.com.tellescom.service.dto.MetaRecursoDTO;
import br.com.tellescom.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link br.com.tellescom.domain.MetaRecurso}.
 */
@RestController
@RequestMapping("/api/metaobj/recursos")
public class MetaRecursoResource {

    private final Logger log = LoggerFactory.getLogger(MetaRecursoResource.class);

    private static final String ENTITY_NAME = "all4QmsMsMetaIndMetaRecurso";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MetaRecursoService metaRecursoService;

    private final MetaRecursoRepository metaRecursoRepository;

    private final MetaRecursoQueryService metaRecursoQueryService;

    public MetaRecursoResource(
        MetaRecursoService metaRecursoService,
        MetaRecursoRepository metaRecursoRepository,
        MetaRecursoQueryService metaRecursoQueryService
    ) {
        this.metaRecursoService = metaRecursoService;
        this.metaRecursoRepository = metaRecursoRepository;
        this.metaRecursoQueryService = metaRecursoQueryService;
    }

    /**
     * {@code POST  /meta-recursos} : Create a new metaRecurso.
     *
     * @param metaRecursoDTO the metaRecursoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new metaRecursoDTO, or with status {@code 400 (Bad Request)} if the metaRecurso has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<MetaRecursoDTO> createMetaRecurso(@RequestBody MetaRecursoDTO metaRecursoDTO) throws URISyntaxException {
        log.debug("REST request to save MetaRecurso : {}", metaRecursoDTO);
        if (metaRecursoDTO.getId() != null) {
            throw new BadRequestAlertException("A new metaRecurso cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MetaRecursoDTO result = metaRecursoService.save(metaRecursoDTO);
        return ResponseEntity
            .created(new URI("/api/meta-recursos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /meta-recursos/:id} : Updates an existing metaRecurso.
     *
     * @param id             the id of the metaRecursoDTO to save.
     * @param metaRecursoDTO the metaRecursoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated metaRecursoDTO,
     * or with status {@code 400 (Bad Request)} if the metaRecursoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the metaRecursoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<MetaRecursoDTO> updateMetaRecurso(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MetaRecursoDTO metaRecursoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update MetaRecurso : {}, {}", id, metaRecursoDTO);
        if (metaRecursoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, metaRecursoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!metaRecursoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MetaRecursoDTO result = metaRecursoService.update(metaRecursoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, metaRecursoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /meta-recursos/:id} : Partial updates given fields of an existing metaRecurso, field will ignore if it is null
     *
     * @param id             the id of the metaRecursoDTO to save.
     * @param metaRecursoDTO the metaRecursoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated metaRecursoDTO,
     * or with status {@code 400 (Bad Request)} if the metaRecursoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the metaRecursoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the metaRecursoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MetaRecursoDTO> partialUpdateMetaRecurso(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MetaRecursoDTO metaRecursoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update MetaRecurso partially : {}, {}", id, metaRecursoDTO);
        if (metaRecursoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, metaRecursoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!metaRecursoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MetaRecursoDTO> result = metaRecursoService.partialUpdate(metaRecursoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, metaRecursoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /meta-recursos} : get all the metaRecursos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of metaRecursos in body.
     */
    @GetMapping("")
    public ResponseEntity<Page<MetaRecursoDTO>> getAllMetaRecursos(MetaRecursoCriteria criteria, @ParameterObject Pageable pageable) {
        log.debug("REST request to get MetaRecursos by criteria: {}", criteria);

        Page<MetaRecursoDTO> page = metaRecursoQueryService.findByCriteria(criteria, pageable);
        return ResponseEntity.ok().body(page);
    }

    /**
     * {@code GET  /meta-recursos/count} : count all the metaRecursos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countMetaRecursos(MetaRecursoCriteria criteria) {
        log.debug("REST request to count MetaRecursos by criteria: {}", criteria);
        return ResponseEntity.ok().body(metaRecursoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /meta-recursos/:id} : get the "id" metaRecurso.
     *
     * @param id the id of the metaRecursoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the metaRecursoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MetaRecursoDTO> getMetaRecurso(@PathVariable("id") Long id) {
        log.debug("REST request to get MetaRecurso : {}", id);
        Optional<MetaRecursoDTO> metaRecursoDTO = metaRecursoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(metaRecursoDTO);
    }

    /**
     * {@code DELETE  /meta-recursos/:id} : delete the "id" metaRecurso.
     *
     * @param id the id of the metaRecursoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMetaRecurso(@PathVariable("id") Long id) {
        log.debug("REST request to delete MetaRecurso : {}", id);
        metaRecursoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code GET  /meta-recursos} : get all the metaRecursos.
     *
     * @param pageable the pagination information.
     * @param filtro   the filtro which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of metaRecursos in body.
     */
    @GetMapping("/filtro")
    public ResponseEntity<Page<MetaRecursoDTO>> getAllMetaRecursosFiltro(
        @RequestParam(value = "filtro", required = false) String filtro,
        @ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get MetaRecursos with filtro: {}", filtro);

        Page<MetaRecursoDTO> page = metaRecursoService.findWithFiltro(filtro, pageable);
        return ResponseEntity.ok().body(page);
    }
}
