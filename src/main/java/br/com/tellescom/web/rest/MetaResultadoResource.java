package br.com.tellescom.web.rest;

import br.com.tellescom.domain.request.MetaResultadoRequest;
import br.com.tellescom.repository.MetaResultadoRepository;
import br.com.tellescom.service.MetaResultadoService;
import br.com.tellescom.service.dto.MetaResultadoDTO;
import br.com.tellescom.web.rest.errors.BadRequestAlertException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link br.com.tellescom.domain.MetaResultado}.
 */
@RestController
@RequestMapping("/api/metaobj/resultados")
public class MetaResultadoResource {

    private final Logger log = LoggerFactory.getLogger(MetaResultadoResource.class);

    private static final String ENTITY_NAME = "all4QmsMsMetaIndMetaResultado";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MetaResultadoService metaResultadoService;

    private final MetaResultadoRepository metaResultadoRepository;

    public MetaResultadoResource(MetaResultadoService metaResultadoService, MetaResultadoRepository metaResultadoRepository) {
        this.metaResultadoService = metaResultadoService;
        this.metaResultadoRepository = metaResultadoRepository;
    }

    /**
     * {@code POST  /meta-resultados} : Create a new metaResultado.
     *
     * @param metaResultadoDTO the metaResultadoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new metaResultadoDTO, or with status {@code 400 (Bad Request)} if the metaResultado has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @Operation(summary = "Upload de dados e arquivos", description = "Permite enviar um JSON como parte do formulário e arquivos.", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "JSON que representa o MetaResultadoDTO", required = true, content = @Content(schema = @Schema(implementation = MetaResultadoRequest.class))))
    @PostMapping(value = "", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<MetaResultadoDTO> createMetaResultado(
            @RequestPart("metaResultadoDTO") String metaResultadoDTOJson,
            @RequestPart("anexos") List<MultipartFile> files)
            throws URISyntaxException {
        MetaResultadoDTO metaResultadoDTO = convertJsonToMetaResultadoDTO(metaResultadoDTOJson);
        var request = new MetaResultadoRequest();
        request.setMetaResultadoDTO(metaResultadoDTO);
        request.setAnexos(files);
        log.debug("REST request to save MetaResultadoRequest : {}", request);
        if (request.getMetaResultadoDTO().getId() != null) {
            throw new BadRequestAlertException("A new metaResultado cannot already have an ID", ENTITY_NAME,
                    "idexists");
        }
        MetaResultadoDTO result = metaResultadoService.save(request);
        return ResponseEntity
                .created(new URI("/api/meta-resultados/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME,
                        result.getId().toString()))
                .body(result);
    }

    private MetaResultadoDTO convertJsonToMetaResultadoDTO(String json) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            return objectMapper.readValue(json, MetaResultadoDTO.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to convert JSON to MetaResultadoDTO", e);
        }
    }

    /**
     * {@code PUT  /meta-resultados/:id} : Updates an existing metaResultado.
     *
     * @param id the id of the metaResultadoDTO to save.
     * @param metaResultadoDTO the metaResultadoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated metaResultadoDTO,
     * or with status {@code 400 (Bad Request)} if the metaResultadoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the metaResultadoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<MetaResultadoDTO> updateMetaResultado(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody MetaResultadoDTO metaResultadoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update MetaResultado : {}, {}", id, metaResultadoDTO);
        if (metaResultadoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, metaResultadoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!metaResultadoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MetaResultadoDTO result = metaResultadoService.update(metaResultadoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, metaResultadoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /meta-resultados/:id} : Partial updates given fields of an existing metaResultado, field will ignore if it is null
     *
     * @param id the id of the metaResultadoDTO to save.
     * @param metaResultadoDTO the metaResultadoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated metaResultadoDTO,
     * or with status {@code 400 (Bad Request)} if the metaResultadoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the metaResultadoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the metaResultadoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MetaResultadoDTO> partialUpdateMetaResultado(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody MetaResultadoDTO metaResultadoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update MetaResultado partially : {}, {}", id, metaResultadoDTO);
        if (metaResultadoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, metaResultadoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!metaResultadoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MetaResultadoDTO> result = metaResultadoService.partialUpdate(metaResultadoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, metaResultadoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /meta-resultados} : get all the metaResultados.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of metaResultados in body.
     */
    @GetMapping("")
    public List<MetaResultadoDTO> getAllMetaResultados() {
        log.debug("REST request to get all MetaResultados");
        return metaResultadoService.findAll();
    }

    /**
     * {@code GET  /meta-resultados/:id} : get the "id" metaResultado.
     *
     * @param id the id of the metaResultadoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the metaResultadoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MetaResultadoDTO> getMetaResultado(@PathVariable("id") Long id) {
        log.debug("REST request to get MetaResultado : {}", id);
        Optional<MetaResultadoDTO> metaResultadoDTO = metaResultadoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(metaResultadoDTO);
    }

    /**
     * {@code DELETE  /meta-resultados/:id} : delete the "id" metaResultado.
     *
     * @param id the id of the metaResultadoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMetaResultado(@PathVariable("id") Long id) {
        log.debug("REST request to delete MetaResultado : {}", id);
        metaResultadoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code GET  /meta-resultados} : get all the metaResultados.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of metaResultados in body.
     */
    @GetMapping("/byidmeta/{id}")
    public List<MetaResultadoDTO> getAllMetaResultadosByIdMeta(@PathVariable("id") Long id) {
        log.debug("REST request to get all MetaResultados by idMeta {}", id);
        return metaResultadoService.findAllByIdMeta(id);
    }
}
