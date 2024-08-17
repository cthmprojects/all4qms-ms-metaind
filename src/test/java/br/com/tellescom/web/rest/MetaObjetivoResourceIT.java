package br.com.tellescom.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.tellescom.IntegrationTest;
import br.com.tellescom.domain.MetaObjetivo;
import br.com.tellescom.repository.MetaObjetivoRepository;
import br.com.tellescom.service.dto.MetaObjetivoDTO;
import br.com.tellescom.service.mapper.MetaObjetivoMapper;
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
    private MetaObjetivoRepository metaObjetivoRepository;

    @Autowired
    private MetaObjetivoMapper metaObjetivoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMetaObjetivoMockMvc;

    private MetaObjetivo metaObjetivo;

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

    @Test
    @Transactional
    void createMetaObjetivo() throws Exception {
        int databaseSizeBeforeCreate = metaObjetivoRepository.findAll().size();
        // Create the MetaObjetivo
        MetaObjetivoDTO metaObjetivoDTO = metaObjetivoMapper.toDto(metaObjetivo);
        restMetaObjetivoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(metaObjetivoDTO))
            )
            .andExpect(status().isCreated());

        // Validate the MetaObjetivo in the database
        List<MetaObjetivo> metaObjetivoList = metaObjetivoRepository.findAll();
        assertThat(metaObjetivoList).hasSize(databaseSizeBeforeCreate + 1);
        MetaObjetivo testMetaObjetivo = metaObjetivoList.get(metaObjetivoList.size() - 1);
        assertThat(testMetaObjetivo.getPoliticaSGQ()).isEqualTo(DEFAULT_POLITICA_SGQ);
        assertThat(testMetaObjetivo.getDesdobramentoSGQ()).isEqualTo(DEFAULT_DESDOBRAMENTO_SGQ);
        assertThat(testMetaObjetivo.getObjetivoSGQ()).isEqualTo(DEFAULT_OBJETIVO_SGQ);
    }

    @Test
    @Transactional
    void createMetaObjetivoWithExistingId() throws Exception {
        // Create the MetaObjetivo with an existing ID
        metaObjetivo.setId(1L);
        MetaObjetivoDTO metaObjetivoDTO = metaObjetivoMapper.toDto(metaObjetivo);

        int databaseSizeBeforeCreate = metaObjetivoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMetaObjetivoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(metaObjetivoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MetaObjetivo in the database
        List<MetaObjetivo> metaObjetivoList = metaObjetivoRepository.findAll();
        assertThat(metaObjetivoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMetaObjetivos() throws Exception {
        // Initialize the database
        metaObjetivoRepository.saveAndFlush(metaObjetivo);

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
        metaObjetivoRepository.saveAndFlush(metaObjetivo);

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
        metaObjetivoRepository.saveAndFlush(metaObjetivo);

        int databaseSizeBeforeUpdate = metaObjetivoRepository.findAll().size();

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
                    .content(TestUtil.convertObjectToJsonBytes(metaObjetivoDTO))
            )
            .andExpect(status().isOk());

        // Validate the MetaObjetivo in the database
        List<MetaObjetivo> metaObjetivoList = metaObjetivoRepository.findAll();
        assertThat(metaObjetivoList).hasSize(databaseSizeBeforeUpdate);
        MetaObjetivo testMetaObjetivo = metaObjetivoList.get(metaObjetivoList.size() - 1);
        assertThat(testMetaObjetivo.getPoliticaSGQ()).isEqualTo(UPDATED_POLITICA_SGQ);
        assertThat(testMetaObjetivo.getDesdobramentoSGQ()).isEqualTo(UPDATED_DESDOBRAMENTO_SGQ);
        assertThat(testMetaObjetivo.getObjetivoSGQ()).isEqualTo(UPDATED_OBJETIVO_SGQ);
    }

    @Test
    @Transactional
    void putNonExistingMetaObjetivo() throws Exception {
        int databaseSizeBeforeUpdate = metaObjetivoRepository.findAll().size();
        metaObjetivo.setId(longCount.incrementAndGet());

        // Create the MetaObjetivo
        MetaObjetivoDTO metaObjetivoDTO = metaObjetivoMapper.toDto(metaObjetivo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMetaObjetivoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, metaObjetivoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(metaObjetivoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MetaObjetivo in the database
        List<MetaObjetivo> metaObjetivoList = metaObjetivoRepository.findAll();
        assertThat(metaObjetivoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMetaObjetivo() throws Exception {
        int databaseSizeBeforeUpdate = metaObjetivoRepository.findAll().size();
        metaObjetivo.setId(longCount.incrementAndGet());

        // Create the MetaObjetivo
        MetaObjetivoDTO metaObjetivoDTO = metaObjetivoMapper.toDto(metaObjetivo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMetaObjetivoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(metaObjetivoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MetaObjetivo in the database
        List<MetaObjetivo> metaObjetivoList = metaObjetivoRepository.findAll();
        assertThat(metaObjetivoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMetaObjetivo() throws Exception {
        int databaseSizeBeforeUpdate = metaObjetivoRepository.findAll().size();
        metaObjetivo.setId(longCount.incrementAndGet());

        // Create the MetaObjetivo
        MetaObjetivoDTO metaObjetivoDTO = metaObjetivoMapper.toDto(metaObjetivo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMetaObjetivoMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(metaObjetivoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MetaObjetivo in the database
        List<MetaObjetivo> metaObjetivoList = metaObjetivoRepository.findAll();
        assertThat(metaObjetivoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMetaObjetivoWithPatch() throws Exception {
        // Initialize the database
        metaObjetivoRepository.saveAndFlush(metaObjetivo);

        int databaseSizeBeforeUpdate = metaObjetivoRepository.findAll().size();

        // Update the metaObjetivo using partial update
        MetaObjetivo partialUpdatedMetaObjetivo = new MetaObjetivo();
        partialUpdatedMetaObjetivo.setId(metaObjetivo.getId());

        partialUpdatedMetaObjetivo.objetivoSGQ(UPDATED_OBJETIVO_SGQ);

        restMetaObjetivoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMetaObjetivo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMetaObjetivo))
            )
            .andExpect(status().isOk());

        // Validate the MetaObjetivo in the database
        List<MetaObjetivo> metaObjetivoList = metaObjetivoRepository.findAll();
        assertThat(metaObjetivoList).hasSize(databaseSizeBeforeUpdate);
        MetaObjetivo testMetaObjetivo = metaObjetivoList.get(metaObjetivoList.size() - 1);
        assertThat(testMetaObjetivo.getPoliticaSGQ()).isEqualTo(DEFAULT_POLITICA_SGQ);
        assertThat(testMetaObjetivo.getDesdobramentoSGQ()).isEqualTo(DEFAULT_DESDOBRAMENTO_SGQ);
        assertThat(testMetaObjetivo.getObjetivoSGQ()).isEqualTo(UPDATED_OBJETIVO_SGQ);
    }

    @Test
    @Transactional
    void fullUpdateMetaObjetivoWithPatch() throws Exception {
        // Initialize the database
        metaObjetivoRepository.saveAndFlush(metaObjetivo);

        int databaseSizeBeforeUpdate = metaObjetivoRepository.findAll().size();

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
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMetaObjetivo))
            )
            .andExpect(status().isOk());

        // Validate the MetaObjetivo in the database
        List<MetaObjetivo> metaObjetivoList = metaObjetivoRepository.findAll();
        assertThat(metaObjetivoList).hasSize(databaseSizeBeforeUpdate);
        MetaObjetivo testMetaObjetivo = metaObjetivoList.get(metaObjetivoList.size() - 1);
        assertThat(testMetaObjetivo.getPoliticaSGQ()).isEqualTo(UPDATED_POLITICA_SGQ);
        assertThat(testMetaObjetivo.getDesdobramentoSGQ()).isEqualTo(UPDATED_DESDOBRAMENTO_SGQ);
        assertThat(testMetaObjetivo.getObjetivoSGQ()).isEqualTo(UPDATED_OBJETIVO_SGQ);
    }

    @Test
    @Transactional
    void patchNonExistingMetaObjetivo() throws Exception {
        int databaseSizeBeforeUpdate = metaObjetivoRepository.findAll().size();
        metaObjetivo.setId(longCount.incrementAndGet());

        // Create the MetaObjetivo
        MetaObjetivoDTO metaObjetivoDTO = metaObjetivoMapper.toDto(metaObjetivo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMetaObjetivoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, metaObjetivoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(metaObjetivoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MetaObjetivo in the database
        List<MetaObjetivo> metaObjetivoList = metaObjetivoRepository.findAll();
        assertThat(metaObjetivoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMetaObjetivo() throws Exception {
        int databaseSizeBeforeUpdate = metaObjetivoRepository.findAll().size();
        metaObjetivo.setId(longCount.incrementAndGet());

        // Create the MetaObjetivo
        MetaObjetivoDTO metaObjetivoDTO = metaObjetivoMapper.toDto(metaObjetivo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMetaObjetivoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(metaObjetivoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MetaObjetivo in the database
        List<MetaObjetivo> metaObjetivoList = metaObjetivoRepository.findAll();
        assertThat(metaObjetivoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMetaObjetivo() throws Exception {
        int databaseSizeBeforeUpdate = metaObjetivoRepository.findAll().size();
        metaObjetivo.setId(longCount.incrementAndGet());

        // Create the MetaObjetivo
        MetaObjetivoDTO metaObjetivoDTO = metaObjetivoMapper.toDto(metaObjetivo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMetaObjetivoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(metaObjetivoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MetaObjetivo in the database
        List<MetaObjetivo> metaObjetivoList = metaObjetivoRepository.findAll();
        assertThat(metaObjetivoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMetaObjetivo() throws Exception {
        // Initialize the database
        metaObjetivoRepository.saveAndFlush(metaObjetivo);

        int databaseSizeBeforeDelete = metaObjetivoRepository.findAll().size();

        // Delete the metaObjetivo
        restMetaObjetivoMockMvc
            .perform(delete(ENTITY_API_URL_ID, metaObjetivo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MetaObjetivo> metaObjetivoList = metaObjetivoRepository.findAll();
        assertThat(metaObjetivoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
