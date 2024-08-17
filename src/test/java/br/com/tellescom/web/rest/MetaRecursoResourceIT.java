package br.com.tellescom.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.tellescom.IntegrationTest;
import br.com.tellescom.domain.Meta;
import br.com.tellescom.domain.MetaRecurso;
import br.com.tellescom.repository.MetaRecursoRepository;
import br.com.tellescom.service.dto.MetaRecursoDTO;
import br.com.tellescom.service.mapper.MetaRecursoMapper;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link MetaRecursoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MetaRecursoResourceIT {

    private static final String DEFAULT_RECURSO_NOME = "AAAAAAAAAA";
    private static final String UPDATED_RECURSO_NOME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/meta-recursos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MetaRecursoRepository metaRecursoRepository;

    @Autowired
    private MetaRecursoMapper metaRecursoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMetaRecursoMockMvc;

    private MetaRecurso metaRecurso;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MetaRecurso createEntity(EntityManager em) {
        MetaRecurso metaRecurso = new MetaRecurso().recursoNome(DEFAULT_RECURSO_NOME);
        return metaRecurso;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MetaRecurso createUpdatedEntity(EntityManager em) {
        MetaRecurso metaRecurso = new MetaRecurso().recursoNome(UPDATED_RECURSO_NOME);
        return metaRecurso;
    }

    @BeforeEach
    public void initTest() {
        metaRecurso = createEntity(em);
    }

    @Test
    @Transactional
    void createMetaRecurso() throws Exception {
        int databaseSizeBeforeCreate = metaRecursoRepository.findAll().size();
        // Create the MetaRecurso
        MetaRecursoDTO metaRecursoDTO = metaRecursoMapper.toDto(metaRecurso);
        restMetaRecursoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(metaRecursoDTO))
            )
            .andExpect(status().isCreated());

        // Validate the MetaRecurso in the database
        List<MetaRecurso> metaRecursoList = metaRecursoRepository.findAll();
        assertThat(metaRecursoList).hasSize(databaseSizeBeforeCreate + 1);
        MetaRecurso testMetaRecurso = metaRecursoList.get(metaRecursoList.size() - 1);
        assertThat(testMetaRecurso.getRecursoNome()).isEqualTo(DEFAULT_RECURSO_NOME);
    }

    @Test
    @Transactional
    void createMetaRecursoWithExistingId() throws Exception {
        // Create the MetaRecurso with an existing ID
        metaRecurso.setId(1L);
        MetaRecursoDTO metaRecursoDTO = metaRecursoMapper.toDto(metaRecurso);

        int databaseSizeBeforeCreate = metaRecursoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMetaRecursoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(metaRecursoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MetaRecurso in the database
        List<MetaRecurso> metaRecursoList = metaRecursoRepository.findAll();
        assertThat(metaRecursoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMetaRecursos() throws Exception {
        // Initialize the database
        metaRecursoRepository.saveAndFlush(metaRecurso);

        // Get all the metaRecursoList
        restMetaRecursoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(metaRecurso.getId().intValue())))
            .andExpect(jsonPath("$.[*].recursoNome").value(hasItem(DEFAULT_RECURSO_NOME)));
    }

    @Test
    @Transactional
    void getMetaRecurso() throws Exception {
        // Initialize the database
        metaRecursoRepository.saveAndFlush(metaRecurso);

        // Get the metaRecurso
        restMetaRecursoMockMvc
            .perform(get(ENTITY_API_URL_ID, metaRecurso.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(metaRecurso.getId().intValue()))
            .andExpect(jsonPath("$.recursoNome").value(DEFAULT_RECURSO_NOME));
    }

    @Test
    @Transactional
    void getMetaRecursosByIdFiltering() throws Exception {
        // Initialize the database
        metaRecursoRepository.saveAndFlush(metaRecurso);

        Long id = metaRecurso.getId();

        defaultMetaRecursoShouldBeFound("id.equals=" + id);
        defaultMetaRecursoShouldNotBeFound("id.notEquals=" + id);

        defaultMetaRecursoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMetaRecursoShouldNotBeFound("id.greaterThan=" + id);

        defaultMetaRecursoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMetaRecursoShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllMetaRecursosByRecursoNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        metaRecursoRepository.saveAndFlush(metaRecurso);

        // Get all the metaRecursoList where recursoNome equals to DEFAULT_RECURSO_NOME
        defaultMetaRecursoShouldBeFound("recursoNome.equals=" + DEFAULT_RECURSO_NOME);

        // Get all the metaRecursoList where recursoNome equals to UPDATED_RECURSO_NOME
        defaultMetaRecursoShouldNotBeFound("recursoNome.equals=" + UPDATED_RECURSO_NOME);
    }

    @Test
    @Transactional
    void getAllMetaRecursosByRecursoNomeIsInShouldWork() throws Exception {
        // Initialize the database
        metaRecursoRepository.saveAndFlush(metaRecurso);

        // Get all the metaRecursoList where recursoNome in DEFAULT_RECURSO_NOME or UPDATED_RECURSO_NOME
        defaultMetaRecursoShouldBeFound("recursoNome.in=" + DEFAULT_RECURSO_NOME + "," + UPDATED_RECURSO_NOME);

        // Get all the metaRecursoList where recursoNome equals to UPDATED_RECURSO_NOME
        defaultMetaRecursoShouldNotBeFound("recursoNome.in=" + UPDATED_RECURSO_NOME);
    }

    @Test
    @Transactional
    void getAllMetaRecursosByRecursoNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        metaRecursoRepository.saveAndFlush(metaRecurso);

        // Get all the metaRecursoList where recursoNome is not null
        defaultMetaRecursoShouldBeFound("recursoNome.specified=true");

        // Get all the metaRecursoList where recursoNome is null
        defaultMetaRecursoShouldNotBeFound("recursoNome.specified=false");
    }

    @Test
    @Transactional
    void getAllMetaRecursosByRecursoNomeContainsSomething() throws Exception {
        // Initialize the database
        metaRecursoRepository.saveAndFlush(metaRecurso);

        // Get all the metaRecursoList where recursoNome contains DEFAULT_RECURSO_NOME
        defaultMetaRecursoShouldBeFound("recursoNome.contains=" + DEFAULT_RECURSO_NOME);

        // Get all the metaRecursoList where recursoNome contains UPDATED_RECURSO_NOME
        defaultMetaRecursoShouldNotBeFound("recursoNome.contains=" + UPDATED_RECURSO_NOME);
    }

    @Test
    @Transactional
    void getAllMetaRecursosByRecursoNomeNotContainsSomething() throws Exception {
        // Initialize the database
        metaRecursoRepository.saveAndFlush(metaRecurso);

        // Get all the metaRecursoList where recursoNome does not contain DEFAULT_RECURSO_NOME
        defaultMetaRecursoShouldNotBeFound("recursoNome.doesNotContain=" + DEFAULT_RECURSO_NOME);

        // Get all the metaRecursoList where recursoNome does not contain UPDATED_RECURSO_NOME
        defaultMetaRecursoShouldBeFound("recursoNome.doesNotContain=" + UPDATED_RECURSO_NOME);
    }

    @Test
    @Transactional
    void getAllMetaRecursosByMetasIsEqualToSomething() throws Exception {
        Meta metas;
        if (TestUtil.findAll(em, Meta.class).isEmpty()) {
            metaRecursoRepository.saveAndFlush(metaRecurso);
            metas = MetaResourceIT.createEntity(em);
        } else {
            metas = TestUtil.findAll(em, Meta.class).get(0);
        }
        em.persist(metas);
        em.flush();
        metaRecurso.addMetas(metas);
        metaRecursoRepository.saveAndFlush(metaRecurso);
        Long metasId = metas.getId();
        // Get all the metaRecursoList where metas equals to metasId
        defaultMetaRecursoShouldBeFound("metasId.equals=" + metasId);

        // Get all the metaRecursoList where metas equals to (metasId + 1)
        defaultMetaRecursoShouldNotBeFound("metasId.equals=" + (metasId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMetaRecursoShouldBeFound(String filter) throws Exception {
        restMetaRecursoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(metaRecurso.getId().intValue())))
            .andExpect(jsonPath("$.[*].recursoNome").value(hasItem(DEFAULT_RECURSO_NOME)));

        // Check, that the count call also returns 1
        restMetaRecursoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMetaRecursoShouldNotBeFound(String filter) throws Exception {
        restMetaRecursoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMetaRecursoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingMetaRecurso() throws Exception {
        // Get the metaRecurso
        restMetaRecursoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMetaRecurso() throws Exception {
        // Initialize the database
        metaRecursoRepository.saveAndFlush(metaRecurso);

        int databaseSizeBeforeUpdate = metaRecursoRepository.findAll().size();

        // Update the metaRecurso
        MetaRecurso updatedMetaRecurso = metaRecursoRepository.findById(metaRecurso.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedMetaRecurso are not directly saved in db
        em.detach(updatedMetaRecurso);
        updatedMetaRecurso.recursoNome(UPDATED_RECURSO_NOME);
        MetaRecursoDTO metaRecursoDTO = metaRecursoMapper.toDto(updatedMetaRecurso);

        restMetaRecursoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, metaRecursoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(metaRecursoDTO))
            )
            .andExpect(status().isOk());

        // Validate the MetaRecurso in the database
        List<MetaRecurso> metaRecursoList = metaRecursoRepository.findAll();
        assertThat(metaRecursoList).hasSize(databaseSizeBeforeUpdate);
        MetaRecurso testMetaRecurso = metaRecursoList.get(metaRecursoList.size() - 1);
        assertThat(testMetaRecurso.getRecursoNome()).isEqualTo(UPDATED_RECURSO_NOME);
    }

    @Test
    @Transactional
    void putNonExistingMetaRecurso() throws Exception {
        int databaseSizeBeforeUpdate = metaRecursoRepository.findAll().size();
        metaRecurso.setId(longCount.incrementAndGet());

        // Create the MetaRecurso
        MetaRecursoDTO metaRecursoDTO = metaRecursoMapper.toDto(metaRecurso);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMetaRecursoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, metaRecursoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(metaRecursoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MetaRecurso in the database
        List<MetaRecurso> metaRecursoList = metaRecursoRepository.findAll();
        assertThat(metaRecursoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMetaRecurso() throws Exception {
        int databaseSizeBeforeUpdate = metaRecursoRepository.findAll().size();
        metaRecurso.setId(longCount.incrementAndGet());

        // Create the MetaRecurso
        MetaRecursoDTO metaRecursoDTO = metaRecursoMapper.toDto(metaRecurso);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMetaRecursoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(metaRecursoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MetaRecurso in the database
        List<MetaRecurso> metaRecursoList = metaRecursoRepository.findAll();
        assertThat(metaRecursoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMetaRecurso() throws Exception {
        int databaseSizeBeforeUpdate = metaRecursoRepository.findAll().size();
        metaRecurso.setId(longCount.incrementAndGet());

        // Create the MetaRecurso
        MetaRecursoDTO metaRecursoDTO = metaRecursoMapper.toDto(metaRecurso);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMetaRecursoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(metaRecursoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MetaRecurso in the database
        List<MetaRecurso> metaRecursoList = metaRecursoRepository.findAll();
        assertThat(metaRecursoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMetaRecursoWithPatch() throws Exception {
        // Initialize the database
        metaRecursoRepository.saveAndFlush(metaRecurso);

        int databaseSizeBeforeUpdate = metaRecursoRepository.findAll().size();

        // Update the metaRecurso using partial update
        MetaRecurso partialUpdatedMetaRecurso = new MetaRecurso();
        partialUpdatedMetaRecurso.setId(metaRecurso.getId());

        restMetaRecursoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMetaRecurso.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMetaRecurso))
            )
            .andExpect(status().isOk());

        // Validate the MetaRecurso in the database
        List<MetaRecurso> metaRecursoList = metaRecursoRepository.findAll();
        assertThat(metaRecursoList).hasSize(databaseSizeBeforeUpdate);
        MetaRecurso testMetaRecurso = metaRecursoList.get(metaRecursoList.size() - 1);
        assertThat(testMetaRecurso.getRecursoNome()).isEqualTo(DEFAULT_RECURSO_NOME);
    }

    @Test
    @Transactional
    void fullUpdateMetaRecursoWithPatch() throws Exception {
        // Initialize the database
        metaRecursoRepository.saveAndFlush(metaRecurso);

        int databaseSizeBeforeUpdate = metaRecursoRepository.findAll().size();

        // Update the metaRecurso using partial update
        MetaRecurso partialUpdatedMetaRecurso = new MetaRecurso();
        partialUpdatedMetaRecurso.setId(metaRecurso.getId());

        partialUpdatedMetaRecurso.recursoNome(UPDATED_RECURSO_NOME);

        restMetaRecursoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMetaRecurso.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMetaRecurso))
            )
            .andExpect(status().isOk());

        // Validate the MetaRecurso in the database
        List<MetaRecurso> metaRecursoList = metaRecursoRepository.findAll();
        assertThat(metaRecursoList).hasSize(databaseSizeBeforeUpdate);
        MetaRecurso testMetaRecurso = metaRecursoList.get(metaRecursoList.size() - 1);
        assertThat(testMetaRecurso.getRecursoNome()).isEqualTo(UPDATED_RECURSO_NOME);
    }

    @Test
    @Transactional
    void patchNonExistingMetaRecurso() throws Exception {
        int databaseSizeBeforeUpdate = metaRecursoRepository.findAll().size();
        metaRecurso.setId(longCount.incrementAndGet());

        // Create the MetaRecurso
        MetaRecursoDTO metaRecursoDTO = metaRecursoMapper.toDto(metaRecurso);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMetaRecursoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, metaRecursoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(metaRecursoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MetaRecurso in the database
        List<MetaRecurso> metaRecursoList = metaRecursoRepository.findAll();
        assertThat(metaRecursoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMetaRecurso() throws Exception {
        int databaseSizeBeforeUpdate = metaRecursoRepository.findAll().size();
        metaRecurso.setId(longCount.incrementAndGet());

        // Create the MetaRecurso
        MetaRecursoDTO metaRecursoDTO = metaRecursoMapper.toDto(metaRecurso);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMetaRecursoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(metaRecursoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MetaRecurso in the database
        List<MetaRecurso> metaRecursoList = metaRecursoRepository.findAll();
        assertThat(metaRecursoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMetaRecurso() throws Exception {
        int databaseSizeBeforeUpdate = metaRecursoRepository.findAll().size();
        metaRecurso.setId(longCount.incrementAndGet());

        // Create the MetaRecurso
        MetaRecursoDTO metaRecursoDTO = metaRecursoMapper.toDto(metaRecurso);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMetaRecursoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(metaRecursoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MetaRecurso in the database
        List<MetaRecurso> metaRecursoList = metaRecursoRepository.findAll();
        assertThat(metaRecursoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMetaRecurso() throws Exception {
        // Initialize the database
        metaRecursoRepository.saveAndFlush(metaRecurso);

        int databaseSizeBeforeDelete = metaRecursoRepository.findAll().size();

        // Delete the metaRecurso
        restMetaRecursoMockMvc
            .perform(delete(ENTITY_API_URL_ID, metaRecurso.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MetaRecurso> metaRecursoList = metaRecursoRepository.findAll();
        assertThat(metaRecursoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
