package br.com.tellescom.web.rest;

import br.com.tellescom.repository.AcaoCriticaRepository;
import br.com.tellescom.service.AcaoCriticaService;
import br.com.tellescom.service.dto.AcaoCriticaDTO;
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
 * REST controller for managing {@link br.com.tellescom.domain.AcaoCritica}.
 */
@RestController
@RequestMapping("/api/indicadores/acao-criticas")
public class AcaoCriticaResource {

    private static final Logger LOG = LoggerFactory.getLogger(AcaoCriticaResource.class);

    private static final String ENTITY_NAME = "all4QmsMsMetaIndAcaoCritica";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AcaoCriticaService acaoCriticaService;

    private final AcaoCriticaRepository acaoCriticaRepository;

    public AcaoCriticaResource(AcaoCriticaService acaoCriticaService, AcaoCriticaRepository acaoCriticaRepository) {
        this.acaoCriticaService = acaoCriticaService;
        this.acaoCriticaRepository = acaoCriticaRepository;
    }

    /**
     * {@code POST  /acao-criticas} : Create a new acaoCritica.
     *
     * @param acaoCriticaDTO the acaoCriticaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new acaoCriticaDTO, or with status {@code 400 (Bad Request)} if the acaoCritica has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AcaoCriticaDTO> createAcaoCritica(@Valid @RequestBody AcaoCriticaDTO acaoCriticaDTO) throws URISyntaxException {
        LOG.debug("REST request to save AcaoCritica : {}", acaoCriticaDTO);
        if (acaoCriticaDTO.getId() != null) {
            throw new BadRequestAlertException("A new acaoCritica cannot already have an ID", ENTITY_NAME, "idexists");
        }
        acaoCriticaDTO = acaoCriticaService.save(acaoCriticaDTO);
        return ResponseEntity.created(new URI("/api/acao-criticas/" + acaoCriticaDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, acaoCriticaDTO.getId().toString()))
            .body(acaoCriticaDTO);
    }

    /**
     * {@code PUT  /acao-criticas/:id} : Updates an existing acaoCritica.
     *
     * @param id the id of the acaoCriticaDTO to save.
     * @param acaoCriticaDTO the acaoCriticaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated acaoCriticaDTO,
     * or with status {@code 400 (Bad Request)} if the acaoCriticaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the acaoCriticaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AcaoCriticaDTO> updateAcaoCritica(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AcaoCriticaDTO acaoCriticaDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update AcaoCritica : {}, {}", id, acaoCriticaDTO);
        if (acaoCriticaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, acaoCriticaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!acaoCriticaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        acaoCriticaDTO = acaoCriticaService.update(acaoCriticaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, acaoCriticaDTO.getId().toString()))
            .body(acaoCriticaDTO);
    }

    /**
     * {@code PATCH  /acao-criticas/:id} : Partial updates given fields of an existing acaoCritica, field will ignore if it is null
     *
     * @param id the id of the acaoCriticaDTO to save.
     * @param acaoCriticaDTO the acaoCriticaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated acaoCriticaDTO,
     * or with status {@code 400 (Bad Request)} if the acaoCriticaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the acaoCriticaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the acaoCriticaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AcaoCriticaDTO> partialUpdateAcaoCritica(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AcaoCriticaDTO acaoCriticaDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update AcaoCritica partially : {}, {}", id, acaoCriticaDTO);
        if (acaoCriticaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, acaoCriticaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!acaoCriticaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AcaoCriticaDTO> result = acaoCriticaService.partialUpdate(acaoCriticaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, acaoCriticaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /acao-criticas} : get all the acaoCriticas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of acaoCriticas in body.
     */
    @GetMapping("")
    public List<AcaoCriticaDTO> getAllAcaoCriticas() {
        LOG.debug("REST request to get all AcaoCriticas");
        return acaoCriticaService.findAll();
    }

    /**
     * {@code GET  /acao-criticas/:id} : get the "id" acaoCritica.
     *
     * @param id the id of the acaoCriticaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the acaoCriticaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AcaoCriticaDTO> getAcaoCritica(@PathVariable("id") Long id) {
        LOG.debug("REST request to get AcaoCritica : {}", id);
        Optional<AcaoCriticaDTO> acaoCriticaDTO = acaoCriticaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(acaoCriticaDTO);
    }

    /**
     * {@code DELETE  /acao-criticas/:id} : delete the "id" acaoCritica.
     *
     * @param id the id of the acaoCriticaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAcaoCritica(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete AcaoCritica : {}", id);
        acaoCriticaService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    @PostMapping("/salva-lote")
    public ResponseEntity<List<AcaoCriticaDTO>> salvaAcaoCriticaEmLote(@RequestBody List<AcaoCriticaDTO> request){
        LOG.debug("REST request para salvar AcaoCritica em Lote, {}", request);
        return ResponseEntity.ok(acaoCriticaService.salvaAcaoCriticaEmLote(request));
    }

    @PostMapping("/atualiza-lote")
    public ResponseEntity<List<AcaoCriticaDTO>> atualizaAcaoCriticaEmLote(@RequestBody List<AcaoCriticaDTO> request){
        LOG.debug("REST request para salvar AcaoCritica em Lote, {}", request);
        return ResponseEntity.ok(acaoCriticaService.atualizaAcaoCriticaEmLote(request));
    }
}
