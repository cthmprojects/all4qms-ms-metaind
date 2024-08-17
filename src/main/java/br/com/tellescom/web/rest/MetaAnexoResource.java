package br.com.tellescom.web.rest;

import br.com.tellescom.domain.request.MetaAnexoRequest;
import br.com.tellescom.repository.MetaAnexoRepository;
import br.com.tellescom.service.MetaAnexoService;
import br.com.tellescom.service.dto.MetaAnexoDTO;
import br.com.tellescom.web.rest.errors.BadRequestAlertException;
import br.com.tellescom.web.rest.errors.NotFoundAlertException;
import br.com.tellescom.web.rest.errors.ServerErrorAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link br.com.tellescom.domain.MetaAnexo}.
 */
@RestController
@RequestMapping("/api/metaobj/anexos")
public class MetaAnexoResource {

    private final Logger log = LoggerFactory.getLogger(MetaAnexoResource.class);

    private static final String ENTITY_NAME = "all4QmsMsMetaIndMetaAnexo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MetaAnexoService metaAnexoService;

    private final MetaAnexoRepository metaAnexoRepository;

    public MetaAnexoResource(MetaAnexoService metaAnexoService, MetaAnexoRepository metaAnexoRepository) {
        this.metaAnexoService = metaAnexoService;
        this.metaAnexoRepository = metaAnexoRepository;
    }

    /**
     * {@code POST  /meta-anexos} : Create a new metaAnexo.
     *
     * @param metaAnexoDTO the metaAnexoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new metaAnexoDTO, or with status {@code 400 (Bad Request)} if the metaAnexo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping(value = "", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<MetaAnexoDTO> createMetaAnexo(@ModelAttribute MetaAnexoRequest metaAnexoDTO) throws URISyntaxException {
        log.debug("REST request to save MetaAnexo : {}", metaAnexoDTO);

        MetaAnexoDTO result = metaAnexoService.save(metaAnexoDTO);
        return ResponseEntity
            .created(new URI("/api/meta-anexos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /meta-anexos/:id} : Updates an existing metaAnexo.
     *
     * @param id           the id of the metaAnexoDTO to save.
     * @param metaAnexoDTO the metaAnexoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated metaAnexoDTO,
     * or with status {@code 400 (Bad Request)} if the metaAnexoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the metaAnexoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<MetaAnexoDTO> updateMetaAnexo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MetaAnexoDTO metaAnexoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update MetaAnexo : {}, {}", id, metaAnexoDTO);
        if (metaAnexoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, metaAnexoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!metaAnexoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MetaAnexoDTO result = metaAnexoService.update(metaAnexoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, metaAnexoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /meta-anexos/:id} : Partial updates given fields of an existing metaAnexo, field will ignore if it is null
     *
     * @param id           the id of the metaAnexoDTO to save.
     * @param metaAnexoDTO the metaAnexoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated metaAnexoDTO,
     * or with status {@code 400 (Bad Request)} if the metaAnexoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the metaAnexoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the metaAnexoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MetaAnexoDTO> partialUpdateMetaAnexo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MetaAnexoDTO metaAnexoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update MetaAnexo partially : {}, {}", id, metaAnexoDTO);
        if (metaAnexoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, metaAnexoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!metaAnexoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MetaAnexoDTO> result = metaAnexoService.partialUpdate(metaAnexoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, metaAnexoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /meta-anexos} : get all the metaAnexos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of metaAnexos in body.
     */
    @GetMapping("")
    public List<MetaAnexoDTO> getAllMetaAnexos() {
        log.debug("REST request to get all MetaAnexos");
        return metaAnexoService.findAll();
    }

    /**
     * {@code GET  /meta-anexos/:id} : get the "id" metaAnexo.
     *
     * @param id the id of the metaAnexoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the metaAnexoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MetaAnexoDTO> getMetaAnexo(@PathVariable("id") Long id) {
        log.debug("REST request to get MetaAnexo : {}", id);
        Optional<MetaAnexoDTO> metaAnexoDTO = metaAnexoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(metaAnexoDTO);
    }

    /**
     * {@code DELETE  /meta-anexos/:id} : delete the "id" metaAnexo.
     *
     * @param id the id of the metaAnexoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMetaAnexo(@PathVariable("id") Long id) {
        log.debug("REST request to delete MetaAnexo : {}", id);
        metaAnexoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    @GetMapping("/download/zip/{idmr}")
    public ResponseEntity<Resource> downloadArquivos(@PathVariable("idmr") Long id) {
        log.debug("REST request to get All MetaAnexo By MetaResultadoId : {}", id);

        ByteArrayResource resource = metaAnexoService.buscaZipAnexos(id);

        return ResponseEntity
            .ok()
            .contentLength(resource.contentLength())
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .header("Content-disposition", "attachment; filename=arquivos.zip")
            .body(resource);
    }
}
