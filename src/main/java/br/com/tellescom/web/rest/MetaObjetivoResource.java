package br.com.tellescom.web.rest;

import br.com.tellescom.repository.MetaObjetivoRepository;
import br.com.tellescom.service.MetaObjetivoService;
import br.com.tellescom.service.dto.MetaObjetivoDTO;
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
 * REST controller for managing {@link br.com.tellescom.domain.MetaObjetivo}.
 */
@RestController
@RequestMapping("/api/metaobj/objetivos")
public class MetaObjetivoResource {

    private final Logger log = LoggerFactory.getLogger(MetaObjetivoResource.class);

    private static final String ENTITY_NAME = "all4QmsMsMetaIndMetaObjetivo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MetaObjetivoService metaObjetivoService;

    private final MetaObjetivoRepository metaObjetivoRepository;

    public MetaObjetivoResource(MetaObjetivoService metaObjetivoService, MetaObjetivoRepository metaObjetivoRepository) {
        this.metaObjetivoService = metaObjetivoService;
        this.metaObjetivoRepository = metaObjetivoRepository;
    }

    /**
     * {@code POST  /meta-objetivos} : Create a new metaObjetivo.
     *
     * @param metaObjetivoDTO the metaObjetivoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new metaObjetivoDTO, or with status {@code 400 (Bad Request)} if the metaObjetivo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<MetaObjetivoDTO> createMetaObjetivo(@RequestBody MetaObjetivoDTO metaObjetivoDTO) throws URISyntaxException {
        log.debug("REST request to save MetaObjetivo : {}", metaObjetivoDTO);
        if (metaObjetivoDTO.getId() != null) {
            throw new BadRequestAlertException("A new metaObjetivo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MetaObjetivoDTO result = metaObjetivoService.save(metaObjetivoDTO);
        return ResponseEntity
            .created(new URI("/api/meta-objetivos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /meta-objetivos/:id} : Updates an existing metaObjetivo.
     *
     * @param id the id of the metaObjetivoDTO to save.
     * @param metaObjetivoDTO the metaObjetivoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated metaObjetivoDTO,
     * or with status {@code 400 (Bad Request)} if the metaObjetivoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the metaObjetivoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<MetaObjetivoDTO> updateMetaObjetivo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MetaObjetivoDTO metaObjetivoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update MetaObjetivo : {}, {}", id, metaObjetivoDTO);
        if (metaObjetivoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, metaObjetivoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!metaObjetivoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MetaObjetivoDTO result = metaObjetivoService.update(metaObjetivoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, metaObjetivoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /meta-objetivos/:id} : Partial updates given fields of an existing metaObjetivo, field will ignore if it is null
     *
     * @param id the id of the metaObjetivoDTO to save.
     * @param metaObjetivoDTO the metaObjetivoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated metaObjetivoDTO,
     * or with status {@code 400 (Bad Request)} if the metaObjetivoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the metaObjetivoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the metaObjetivoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MetaObjetivoDTO> partialUpdateMetaObjetivo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MetaObjetivoDTO metaObjetivoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update MetaObjetivo partially : {}, {}", id, metaObjetivoDTO);
        if (metaObjetivoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, metaObjetivoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!metaObjetivoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MetaObjetivoDTO> result = metaObjetivoService.partialUpdate(metaObjetivoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, metaObjetivoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /meta-objetivos} : get all the metaObjetivos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of metaObjetivos in body.
     */
    @GetMapping("")
    public List<MetaObjetivoDTO> getAllMetaObjetivos() {
        log.debug("REST request to get all MetaObjetivos");
        return metaObjetivoService.findAll();
    }

    /**
     * {@code GET  /meta-objetivos/:id} : get the "id" metaObjetivo.
     *
     * @param id the id of the metaObjetivoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the metaObjetivoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MetaObjetivoDTO> getMetaObjetivo(@PathVariable("id") Long id) {
        log.debug("REST request to get MetaObjetivo : {}", id);
        Optional<MetaObjetivoDTO> metaObjetivoDTO = metaObjetivoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(metaObjetivoDTO);
    }

    /**
     * {@code DELETE  /meta-objetivos/:id} : delete the "id" metaObjetivo.
     *
     * @param id the id of the metaObjetivoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMetaObjetivo(@PathVariable("id") Long id) {
        log.debug("REST request to delete MetaObjetivo : {}", id);
        metaObjetivoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
