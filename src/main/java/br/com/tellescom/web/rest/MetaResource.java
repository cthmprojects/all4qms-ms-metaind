package br.com.tellescom.web.rest;

import br.com.tellescom.domain.request.MetaFilterRequest;
import br.com.tellescom.domain.response.MetaResponse;
import br.com.tellescom.repository.MetaRepository;
import br.com.tellescom.service.MetaQueryService;
import br.com.tellescom.service.MetaService;
import br.com.tellescom.service.criteria.MetaCriteria;
import br.com.tellescom.service.dto.MetaDTO;
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
 * REST controller for managing {@link br.com.tellescom.domain.Meta}.
 */
@RestController
@RequestMapping("/api/metaobj/metas")
public class MetaResource {

    private final Logger log = LoggerFactory.getLogger(MetaResource.class);

    private static final String ENTITY_NAME = "all4QmsMsMetaIndMeta";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MetaService metaService;

    private final MetaRepository metaRepository;

    private final MetaQueryService metaQueryService;

    public MetaResource(MetaService metaService, MetaRepository metaRepository, MetaQueryService metaQueryService) {
        this.metaService = metaService;
        this.metaRepository = metaRepository;
        this.metaQueryService = metaQueryService;
    }

    /**
     * {@code POST  } : Create a new meta.
     *
     * @param metaDTO the metaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new metaDTO, or with status {@code 400 (Bad Request)} if the meta has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<MetaDTO> createMeta(@RequestBody MetaDTO metaDTO) throws URISyntaxException {
        log.debug("REST request to save Meta : {}", metaDTO);
        if (metaDTO.getId() != null) {
            throw new BadRequestAlertException("A new meta cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MetaDTO result = metaService.save(metaDTO);
        return ResponseEntity
            .created(new URI("/api/metas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /:id} : Updates an existing meta.
     *
     * @param id      the id of the metaDTO to save.
     * @param metaDTO the metaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated metaDTO,
     * or with status {@code 400 (Bad Request)} if the metaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the metaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<MetaDTO> updateMeta(@PathVariable(value = "id", required = false) final Long id, @RequestBody MetaDTO metaDTO)
        throws URISyntaxException {
        log.debug("REST request to update Meta : {}, {}", id, metaDTO);
        if (metaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, metaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!metaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MetaDTO result = metaService.update(metaDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, metaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /:id} : Partial updates given fields of an existing meta, field will ignore if it is null
     *
     * @param id      the id of the metaDTO to save.
     * @param metaDTO the metaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated metaDTO,
     * or with status {@code 400 (Bad Request)} if the metaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the metaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the metaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = {"application/json", "application/merge-patch+json"})
    public ResponseEntity<MetaDTO> partialUpdateMeta(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MetaDTO metaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Meta partially : {}, {}", id, metaDTO);
        if (metaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, metaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!metaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MetaDTO> result = metaService.partialUpdate(metaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, metaDTO.getId().toString())
        );
    }


    /**
     * {@code GET  /:id} : get the "id" meta.
     *
     * @param id the id of the metaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the metaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MetaDTO> getMeta(@PathVariable("id") Long id) {
        log.debug("REST request to get Meta : {}", id);
        Optional<MetaDTO> metaDTO = metaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(metaDTO);
    }

    /**
     * {@code DELETE /:id} : delete the "id" meta.
     *
     * @param id the id of the metaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMeta(@PathVariable("id") Long id) {
        log.debug("REST request to delete Meta : {}", id);
        metaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code GET  /filtro} : get all the metas.
     *
     * @param pageable the pagination information.
     * @param filtro   the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of metas in body.
     */
    @PostMapping("/filtro")
    public ResponseEntity<Page<MetaResponse>> getAllMetasWithFilter(
        @RequestBody(required = false) MetaFilterRequest filtro,
        @ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Metas by filter: {}", filtro);
        Page<MetaResponse> page = metaService.findByFiltro(filtro, pageable);
        return ResponseEntity.ok().body(page);
    }

    /**
     * {@code GET  /:id} : get all the metas.
     *
     * @param id   id of MetaObjeto.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of metas in body.
     */
    @GetMapping("/byidmetaobjetivo/{id}")
    public ResponseEntity<List<MetaDTO>> getAllMetasByMetaObjetivo(@PathVariable("id") Long id) {
        log.debug("REST request to get Metas by idMetaObjetivo: {}", id);
        return ResponseEntity.ok(metaService.findAllByIdMetaObjetivo(id));
    }
}
