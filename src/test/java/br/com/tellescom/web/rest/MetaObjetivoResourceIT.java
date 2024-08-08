package br.com.tellescom.web.rest;

import static br.com.tellescom.domain.MetaObjetivoAsserts.*;
import static br.com.tellescom.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.tellescom.IntegrationTest;
import br.com.tellescom.domain.MetaObjetivo;
import br.com.tellescom.repository.MetaObjetivoRepository;
import br.com.tellescom.service.dto.MetaObjetivoDTO;
import br.com.tellescom.service.mapper.MetaObjetivoMapper;
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
 * Integration tests for the {@link MetaObjetivoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MetaObjetivoResourceIT {

    private static final String DEFAULT_POLITICA_SGQ = "AAAAAAAAAA";
    private static final String UPDATED_POLITICA_SGQ = "BBBBBBBBBB";

    private static final String DEFAULT_DESDOBRAMENTO_SGQ = "AAAAAAAAAA";
    private static final String UPDATED_DESDOBRAMENTO_SGQ = "BBBBBBBBBB";

    private static final String DEFAULT_OBJETIVO_SGQ = "AAAAAAAAAA";
    private static final String UPDATED_OBJETIVO_SGQ = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/meta-objetivos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MetaObjetivoRepository metaObjetivoRepository;

    @Autowired
    private MetaObjetivoMapper metaObjetivoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMetaObjetivoMockMvc;

    private MetaObjetivo metaObjetivo;

    private MetaObjetivo insertedMetaObjetivo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MetaObjetivo createEntity(EntityManager em) {
        MetaObjetivo metaObjetivo = new MetaObjetivo()
            .politicaSGQ(DEFAULT_POLITICA_SGQ)
            .desdobramentoSGQ(DEFAULT_DESDOBRAMENTO_SGQ)
            .objetivoSGQ(DEFAULT_OBJETIVO_SGQ);
        return metaObjetivo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MetaObjetivo createUpdatedEntity(EntityManager em) {
        MetaObjetivo metaObjetivo = new MetaObjetivo()
            .politicaSGQ(UPDATED_POLITICA_SGQ)
            .desdobramentoSGQ(UPDATED_DESDOBRAMENTO_SGQ)
            .objetivoSGQ(UPDATED_OBJETIVO_SGQ);
        return metaObjetivo;
    }

    @BeforeEach
    public void initTest() {
        metaObjetivo = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedMetaObjetivo != null) {
            metaObjetivoRepository.delete(insertedMetaObjetivo);
            insertedMetaObjetivo = null;
        }
    }

    @Test
    @Transactional
    void createMetaObjetivo() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the MetaObjetivo
        MetaObjetivoDTO metaObjetivoDTO = metaObjetivoMapper.toDto(metaObjetivo);
        var returnedMetaObjetivoDTO = om.readValue(
            restMetaObjetivoMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(metaObjetivoDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            MetaObjetivoDTO.class
        );

        // Validate the MetaObjetivo in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedMetaObjetivo = metaObjetivoMapper.toEntity(returnedMetaObjetivoDTO);
        assertMetaObjetivoUpdatableFieldsEquals(returnedMetaObjetivo, getPersistedMetaObjetivo(returnedMetaObjetivo));

        insertedMetaObjetivo = returnedMetaObjetivo;
    }

    @Test
    @Transactional
    void createMetaObjetivoWithExistingId() throws Exception {
        // Create the MetaObjetivo with an existing ID
        metaObjetivo.setId(1L);
        MetaObjetivoDTO metaObjetivoDTO = metaObjetivoMapper.toDto(metaObjetivo);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMetaObjetivoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(metaObjetivoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MetaObjetivo in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMetaObjetivos() throws Exception {
        // Initialize the database
        insertedMetaObjetivo = metaObjetivoRepository.saveAndFlush(metaObjetivo);

        // Get all the metaObjetivoList
        restMetaObjetivoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(metaObjetivo.getId().intValue())))
            .andExpect(jsonPath("$.[*].politicaSGQ").value(hasItem(DEFAULT_POLITICA_SGQ)))
            .andExpect(jsonPath("$.[*].desdobramentoSGQ").value(hasItem(DEFAULT_DESDOBRAMENTO_SGQ)))
            .andExpect(jsonPath("$.[*].objetivoSGQ").value(hasItem(DEFAULT_OBJETIVO_SGQ)));
    }

    @Test
    @Transactional
    void getMetaObjetivo() throws Exception {
        // Initialize the database
        insertedMetaObjetivo = metaObjetivoRepository.saveAndFlush(metaObjetivo);

        // Get the metaObjetivo
        restMetaObjetivoMockMvc
            .perform(get(ENTITY_API_URL_ID, metaObjetivo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(metaObjetivo.getId().intValue()))
            .andExpect(jsonPath("$.politicaSGQ").value(DEFAULT_POLITICA_SGQ))
            .andExpect(jsonPath("$.desdobramentoSGQ").value(DEFAULT_DESDOBRAMENTO_SGQ))
            .andExpect(jsonPath("$.objetivoSGQ").value(DEFAULT_OBJETIVO_SGQ));
    }

    @Test
    @Transactional
    void getNonExistingMetaObjetivo() throws Exception {
        // Get the metaObjetivo
        restMetaObjetivoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMetaObjetivo() throws Exception {
        // Initialize the database
        insertedMetaObjetivo = metaObjetivoRepository.saveAndFlush(metaObjetivo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the metaObjetivo
        MetaObjetivo updatedMetaObjetivo = metaObjetivoRepository.findById(metaObjetivo.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedMetaObjetivo are not directly saved in db
        em.detach(updatedMetaObjetivo);
        updatedMetaObjetivo.politicaSGQ(UPDATED_POLITICA_SGQ).desdobramentoSGQ(UPDATED_DESDOBRAMENTO_SGQ).objetivoSGQ(UPDATED_OBJETIVO_SGQ);
        MetaObjetivoDTO metaObjetivoDTO = metaObjetivoMapper.toDto(updatedMetaObjetivo);

        restMetaObjetivoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, metaObjetivoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(metaObjetivoDTO))
            )
            .andExpect(status().isOk());

        // Validate the MetaObjetivo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedMetaObjetivoToMatchAllProperties(updatedMetaObjetivo);
    }

    @Test
    @Transactional
    void putNonExistingMetaObjetivo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        metaObjetivo.setId(longCount.incrementAndGet());

        // Create the MetaObjetivo
        MetaObjetivoDTO metaObjetivoDTO = metaObjetivoMapper.toDto(metaObjetivo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMetaObjetivoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, metaObjetivoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(metaObjetivoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MetaObjetivo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMetaObjetivo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        metaObjetivo.setId(longCount.incrementAndGet());

        // Create the MetaObjetivo
        MetaObjetivoDTO metaObjetivoDTO = metaObjetivoMapper.toDto(metaObjetivo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMetaObjetivoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(metaObjetivoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MetaObjetivo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMetaObjetivo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        metaObjetivo.setId(longCount.incrementAndGet());

        // Create the MetaObjetivo
        MetaObjetivoDTO metaObjetivoDTO = metaObjetivoMapper.toDto(metaObjetivo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMetaObjetivoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(metaObjetivoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MetaObjetivo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMetaObjetivoWithPatch() throws Exception {
        // Initialize the database
        insertedMetaObjetivo = metaObjetivoRepository.saveAndFlush(metaObjetivo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the metaObjetivo using partial update
        MetaObjetivo partialUpdatedMetaObjetivo = new MetaObjetivo();
        partialUpdatedMetaObjetivo.setId(metaObjetivo.getId());

        restMetaObjetivoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMetaObjetivo.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMetaObjetivo))
            )
            .andExpect(status().isOk());

        // Validate the MetaObjetivo in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMetaObjetivoUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedMetaObjetivo, metaObjetivo),
            getPersistedMetaObjetivo(metaObjetivo)
        );
    }

    @Test
    @Transactional
    void fullUpdateMetaObjetivoWithPatch() throws Exception {
        // Initialize the database
        insertedMetaObjetivo = metaObjetivoRepository.saveAndFlush(metaObjetivo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the metaObjetivo using partial update
        MetaObjetivo partialUpdatedMetaObjetivo = new MetaObjetivo();
        partialUpdatedMetaObjetivo.setId(metaObjetivo.getId());

        partialUpdatedMetaObjetivo
            .politicaSGQ(UPDATED_POLITICA_SGQ)
            .desdobramentoSGQ(UPDATED_DESDOBRAMENTO_SGQ)
            .objetivoSGQ(UPDATED_OBJETIVO_SGQ);

        restMetaObjetivoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMetaObjetivo.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMetaObjetivo))
            )
            .andExpect(status().isOk());

        // Validate the MetaObjetivo in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMetaObjetivoUpdatableFieldsEquals(partialUpdatedMetaObjetivo, getPersistedMetaObjetivo(partialUpdatedMetaObjetivo));
    }

    @Test
    @Transactional
    void patchNonExistingMetaObjetivo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        metaObjetivo.setId(longCount.incrementAndGet());

        // Create the MetaObjetivo
        MetaObjetivoDTO metaObjetivoDTO = metaObjetivoMapper.toDto(metaObjetivo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMetaObjetivoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, metaObjetivoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(metaObjetivoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MetaObjetivo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMetaObjetivo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        metaObjetivo.setId(longCount.incrementAndGet());

        // Create the MetaObjetivo
        MetaObjetivoDTO metaObjetivoDTO = metaObjetivoMapper.toDto(metaObjetivo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMetaObjetivoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(metaObjetivoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MetaObjetivo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMetaObjetivo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        metaObjetivo.setId(longCount.incrementAndGet());

        // Create the MetaObjetivo
        MetaObjetivoDTO metaObjetivoDTO = metaObjetivoMapper.toDto(metaObjetivo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMetaObjetivoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(metaObjetivoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MetaObjetivo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMetaObjetivo() throws Exception {
        // Initialize the database
        insertedMetaObjetivo = metaObjetivoRepository.saveAndFlush(metaObjetivo);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the metaObjetivo
        restMetaObjetivoMockMvc
            .perform(delete(ENTITY_API_URL_ID, metaObjetivo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return metaObjetivoRepository.count();
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

    protected MetaObjetivo getPersistedMetaObjetivo(MetaObjetivo metaObjetivo) {
        return metaObjetivoRepository.findById(metaObjetivo.getId()).orElseThrow();
    }

    protected void assertPersistedMetaObjetivoToMatchAllProperties(MetaObjetivo expectedMetaObjetivo) {
        assertMetaObjetivoAllPropertiesEquals(expectedMetaObjetivo, getPersistedMetaObjetivo(expectedMetaObjetivo));
    }

    protected void assertPersistedMetaObjetivoToMatchUpdatableProperties(MetaObjetivo expectedMetaObjetivo) {
        assertMetaObjetivoAllUpdatablePropertiesEquals(expectedMetaObjetivo, getPersistedMetaObjetivo(expectedMetaObjetivo));
    }
}
