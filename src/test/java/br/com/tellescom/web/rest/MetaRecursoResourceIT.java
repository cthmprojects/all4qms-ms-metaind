package br.com.tellescom.web.rest;

import static br.com.tellescom.domain.MetaRecursoAsserts.*;
import static br.com.tellescom.web.rest.TestUtil.createUpdateProxyForBean;
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
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
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
    private ObjectMapper om;

    @Autowired
    private MetaRecursoRepository metaRecursoRepository;

    @Autowired
    private MetaRecursoMapper metaRecursoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMetaRecursoMockMvc;

    private MetaRecurso metaRecurso;

    private MetaRecurso insertedMetaRecurso;

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

    @AfterEach
    public void cleanup() {
        if (insertedMetaRecurso != null) {
            metaRecursoRepository.delete(insertedMetaRecurso);
            insertedMetaRecurso = null;
        }
    }

    @Test
    @Transactional
    void createMetaRecurso() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the MetaRecurso
        MetaRecursoDTO metaRecursoDTO = metaRecursoMapper.toDto(metaRecurso);
        var returnedMetaRecursoDTO = om.readValue(
            restMetaRecursoMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(metaRecursoDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            MetaRecursoDTO.class
        );

        // Validate the MetaRecurso in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedMetaRecurso = metaRecursoMapper.toEntity(returnedMetaRecursoDTO);
        assertMetaRecursoUpdatableFieldsEquals(returnedMetaRecurso, getPersistedMetaRecurso(returnedMetaRecurso));

        insertedMetaRecurso = returnedMetaRecurso;
    }

    @Test
    @Transactional
    void createMetaRecursoWithExistingId() throws Exception {
        // Create the MetaRecurso with an existing ID
        metaRecurso.setId(1L);
        MetaRecursoDTO metaRecursoDTO = metaRecursoMapper.toDto(metaRecurso);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMetaRecursoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(metaRecursoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MetaRecurso in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMetaRecursos() throws Exception {
        // Initialize the database
        insertedMetaRecurso = metaRecursoRepository.saveAndFlush(metaRecurso);

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
        insertedMetaRecurso = metaRecursoRepository.saveAndFlush(metaRecurso);

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
        insertedMetaRecurso = metaRecursoRepository.saveAndFlush(metaRecurso);

        Long id = metaRecurso.getId();

        defaultMetaRecursoFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultMetaRecursoFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultMetaRecursoFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllMetaRecursosByRecursoNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedMetaRecurso = metaRecursoRepository.saveAndFlush(metaRecurso);

        // Get all the metaRecursoList where recursoNome equals to
        defaultMetaRecursoFiltering("recursoNome.equals=" + DEFAULT_RECURSO_NOME, "recursoNome.equals=" + UPDATED_RECURSO_NOME);
    }

    @Test
    @Transactional
    void getAllMetaRecursosByRecursoNomeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedMetaRecurso = metaRecursoRepository.saveAndFlush(metaRecurso);

        // Get all the metaRecursoList where recursoNome in
        defaultMetaRecursoFiltering(
            "recursoNome.in=" + DEFAULT_RECURSO_NOME + "," + UPDATED_RECURSO_NOME,
            "recursoNome.in=" + UPDATED_RECURSO_NOME
        );
    }

    @Test
    @Transactional
    void getAllMetaRecursosByRecursoNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedMetaRecurso = metaRecursoRepository.saveAndFlush(metaRecurso);

        // Get all the metaRecursoList where recursoNome is not null
        defaultMetaRecursoFiltering("recursoNome.specified=true", "recursoNome.specified=false");
    }

    @Test
    @Transactional
    void getAllMetaRecursosByRecursoNomeContainsSomething() throws Exception {
        // Initialize the database
        insertedMetaRecurso = metaRecursoRepository.saveAndFlush(metaRecurso);

        // Get all the metaRecursoList where recursoNome contains
        defaultMetaRecursoFiltering("recursoNome.contains=" + DEFAULT_RECURSO_NOME, "recursoNome.contains=" + UPDATED_RECURSO_NOME);
    }

    @Test
    @Transactional
    void getAllMetaRecursosByRecursoNomeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedMetaRecurso = metaRecursoRepository.saveAndFlush(metaRecurso);

        // Get all the metaRecursoList where recursoNome does not contain
        defaultMetaRecursoFiltering(
            "recursoNome.doesNotContain=" + UPDATED_RECURSO_NOME,
            "recursoNome.doesNotContain=" + DEFAULT_RECURSO_NOME
        );
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

    private void defaultMetaRecursoFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultMetaRecursoShouldBeFound(shouldBeFound);
        defaultMetaRecursoShouldNotBeFound(shouldNotBeFound);
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
        insertedMetaRecurso = metaRecursoRepository.saveAndFlush(metaRecurso);

        long databaseSizeBeforeUpdate = getRepositoryCount();

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
                    .content(om.writeValueAsBytes(metaRecursoDTO))
            )
            .andExpect(status().isOk());

        // Validate the MetaRecurso in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedMetaRecursoToMatchAllProperties(updatedMetaRecurso);
    }

    @Test
    @Transactional
    void putNonExistingMetaRecurso() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        metaRecurso.setId(longCount.incrementAndGet());

        // Create the MetaRecurso
        MetaRecursoDTO metaRecursoDTO = metaRecursoMapper.toDto(metaRecurso);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMetaRecursoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, metaRecursoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(metaRecursoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MetaRecurso in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMetaRecurso() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        metaRecurso.setId(longCount.incrementAndGet());

        // Create the MetaRecurso
        MetaRecursoDTO metaRecursoDTO = metaRecursoMapper.toDto(metaRecurso);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMetaRecursoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(metaRecursoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MetaRecurso in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMetaRecurso() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        metaRecurso.setId(longCount.incrementAndGet());

        // Create the MetaRecurso
        MetaRecursoDTO metaRecursoDTO = metaRecursoMapper.toDto(metaRecurso);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMetaRecursoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(metaRecursoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MetaRecurso in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMetaRecursoWithPatch() throws Exception {
        // Initialize the database
        insertedMetaRecurso = metaRecursoRepository.saveAndFlush(metaRecurso);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the metaRecurso using partial update
        MetaRecurso partialUpdatedMetaRecurso = new MetaRecurso();
        partialUpdatedMetaRecurso.setId(metaRecurso.getId());

        partialUpdatedMetaRecurso.recursoNome(UPDATED_RECURSO_NOME);

        restMetaRecursoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMetaRecurso.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMetaRecurso))
            )
            .andExpect(status().isOk());

        // Validate the MetaRecurso in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMetaRecursoUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedMetaRecurso, metaRecurso),
            getPersistedMetaRecurso(metaRecurso)
        );
    }

    @Test
    @Transactional
    void fullUpdateMetaRecursoWithPatch() throws Exception {
        // Initialize the database
        insertedMetaRecurso = metaRecursoRepository.saveAndFlush(metaRecurso);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the metaRecurso using partial update
        MetaRecurso partialUpdatedMetaRecurso = new MetaRecurso();
        partialUpdatedMetaRecurso.setId(metaRecurso.getId());

        partialUpdatedMetaRecurso.recursoNome(UPDATED_RECURSO_NOME);

        restMetaRecursoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMetaRecurso.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMetaRecurso))
            )
            .andExpect(status().isOk());

        // Validate the MetaRecurso in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMetaRecursoUpdatableFieldsEquals(partialUpdatedMetaRecurso, getPersistedMetaRecurso(partialUpdatedMetaRecurso));
    }

    @Test
    @Transactional
    void patchNonExistingMetaRecurso() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        metaRecurso.setId(longCount.incrementAndGet());

        // Create the MetaRecurso
        MetaRecursoDTO metaRecursoDTO = metaRecursoMapper.toDto(metaRecurso);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMetaRecursoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, metaRecursoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(metaRecursoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MetaRecurso in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMetaRecurso() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        metaRecurso.setId(longCount.incrementAndGet());

        // Create the MetaRecurso
        MetaRecursoDTO metaRecursoDTO = metaRecursoMapper.toDto(metaRecurso);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMetaRecursoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(metaRecursoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MetaRecurso in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMetaRecurso() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        metaRecurso.setId(longCount.incrementAndGet());

        // Create the MetaRecurso
        MetaRecursoDTO metaRecursoDTO = metaRecursoMapper.toDto(metaRecurso);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMetaRecursoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(metaRecursoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MetaRecurso in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMetaRecurso() throws Exception {
        // Initialize the database
        insertedMetaRecurso = metaRecursoRepository.saveAndFlush(metaRecurso);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the metaRecurso
        restMetaRecursoMockMvc
            .perform(delete(ENTITY_API_URL_ID, metaRecurso.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return metaRecursoRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected MetaRecurso getPersistedMetaRecurso(MetaRecurso metaRecurso) {
        return metaRecursoRepository.findById(metaRecurso.getId()).orElseThrow();
    }

    protected void assertPersistedMetaRecursoToMatchAllProperties(MetaRecurso expectedMetaRecurso) {
        assertMetaRecursoAllPropertiesEquals(expectedMetaRecurso, getPersistedMetaRecurso(expectedMetaRecurso));
    }

    protected void assertPersistedMetaRecursoToMatchUpdatableProperties(MetaRecurso expectedMetaRecurso) {
        assertMetaRecursoAllUpdatablePropertiesEquals(expectedMetaRecurso, getPersistedMetaRecurso(expectedMetaRecurso));
    }
}
