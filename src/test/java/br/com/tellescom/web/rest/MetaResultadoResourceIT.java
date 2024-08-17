package br.com.tellescom.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.tellescom.IntegrationTest;
import br.com.tellescom.domain.MetaResultado;
import br.com.tellescom.repository.MetaResultadoRepository;
import br.com.tellescom.service.dto.MetaResultadoDTO;
import br.com.tellescom.service.mapper.MetaResultadoMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link MetaResultadoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MetaResultadoResourceIT {

    private static final Instant DEFAULT_LANCADO_EM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LANCADO_EM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_PARCIAL = false;
    private static final Boolean UPDATED_PARCIAL = true;

    private static final Boolean DEFAULT_META_ATINGIDA = false;
    private static final Boolean UPDATED_META_ATINGIDA = true;

    private static final Instant DEFAULT_PERIODO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PERIODO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_AVALIACAO = "AAAAAAAAAA";
    private static final String UPDATED_AVALIACAO = "BBBBBBBBBB";

    private static final String DEFAULT_ANALISE = "AAAAAAAAAA";
    private static final String UPDATED_ANALISE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/meta-resultados";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MetaResultadoRepository metaResultadoRepository;

    @Autowired
    private MetaResultadoMapper metaResultadoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMetaResultadoMockMvc;

    private MetaResultado metaResultado;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MetaResultado createEntity(EntityManager em) {
        MetaResultado metaResultado = new MetaResultado()
            .lancadoEm(DEFAULT_LANCADO_EM)
            .parcial(DEFAULT_PARCIAL)
            .metaAtingida(DEFAULT_META_ATINGIDA)
            .periodo(DEFAULT_PERIODO)
            .avaliacao(DEFAULT_AVALIACAO)
            .analise(DEFAULT_ANALISE);
        return metaResultado;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MetaResultado createUpdatedEntity(EntityManager em) {
        MetaResultado metaResultado = new MetaResultado()
            .lancadoEm(UPDATED_LANCADO_EM)
            .parcial(UPDATED_PARCIAL)
            .metaAtingida(UPDATED_META_ATINGIDA)
            .periodo(UPDATED_PERIODO)
            .avaliacao(UPDATED_AVALIACAO)
            .analise(UPDATED_ANALISE);
        return metaResultado;
    }

    @BeforeEach
    public void initTest() {
        metaResultado = createEntity(em);
    }

    @Test
    @Transactional
    void createMetaResultado() throws Exception {
        int databaseSizeBeforeCreate = metaResultadoRepository.findAll().size();
        // Create the MetaResultado
        MetaResultadoDTO metaResultadoDTO = metaResultadoMapper.toDto(metaResultado);
        restMetaResultadoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(metaResultadoDTO))
            )
            .andExpect(status().isCreated());

        // Validate the MetaResultado in the database
        List<MetaResultado> metaResultadoList = metaResultadoRepository.findAll();
        assertThat(metaResultadoList).hasSize(databaseSizeBeforeCreate + 1);
        MetaResultado testMetaResultado = metaResultadoList.get(metaResultadoList.size() - 1);
        assertThat(testMetaResultado.getLancadoEm()).isEqualTo(DEFAULT_LANCADO_EM);
        assertThat(testMetaResultado.getParcial()).isEqualTo(DEFAULT_PARCIAL);
        assertThat(testMetaResultado.getMetaAtingida()).isEqualTo(DEFAULT_META_ATINGIDA);
        assertThat(testMetaResultado.getPeriodo()).isEqualTo(DEFAULT_PERIODO);
        assertThat(testMetaResultado.getAvaliacao()).isEqualTo(DEFAULT_AVALIACAO);
        assertThat(testMetaResultado.getAnalise()).isEqualTo(DEFAULT_ANALISE);
    }

    @Test
    @Transactional
    void createMetaResultadoWithExistingId() throws Exception {
        // Create the MetaResultado with an existing ID
        metaResultado.setId(1L);
        MetaResultadoDTO metaResultadoDTO = metaResultadoMapper.toDto(metaResultado);

        int databaseSizeBeforeCreate = metaResultadoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMetaResultadoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(metaResultadoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MetaResultado in the database
        List<MetaResultado> metaResultadoList = metaResultadoRepository.findAll();
        assertThat(metaResultadoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMetaResultados() throws Exception {
        // Initialize the database
        metaResultadoRepository.saveAndFlush(metaResultado);

        // Get all the metaResultadoList
        restMetaResultadoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(metaResultado.getId().intValue())))
            .andExpect(jsonPath("$.[*].lancadoEm").value(hasItem(DEFAULT_LANCADO_EM.toString())))
            .andExpect(jsonPath("$.[*].parcial").value(hasItem(DEFAULT_PARCIAL.booleanValue())))
            .andExpect(jsonPath("$.[*].metaAtingida").value(hasItem(DEFAULT_META_ATINGIDA.booleanValue())))
            .andExpect(jsonPath("$.[*].periodo").value(hasItem(DEFAULT_PERIODO.toString())))
            .andExpect(jsonPath("$.[*].avaliacao").value(hasItem(DEFAULT_AVALIACAO)))
            .andExpect(jsonPath("$.[*].analise").value(hasItem(DEFAULT_ANALISE)));
    }

    @Test
    @Transactional
    void getMetaResultado() throws Exception {
        // Initialize the database
        metaResultadoRepository.saveAndFlush(metaResultado);

        // Get the metaResultado
        restMetaResultadoMockMvc
            .perform(get(ENTITY_API_URL_ID, metaResultado.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(metaResultado.getId().intValue()))
            .andExpect(jsonPath("$.lancadoEm").value(DEFAULT_LANCADO_EM.toString()))
            .andExpect(jsonPath("$.parcial").value(DEFAULT_PARCIAL.booleanValue()))
            .andExpect(jsonPath("$.metaAtingida").value(DEFAULT_META_ATINGIDA.booleanValue()))
            .andExpect(jsonPath("$.periodo").value(DEFAULT_PERIODO.toString()))
            .andExpect(jsonPath("$.avaliacao").value(DEFAULT_AVALIACAO))
            .andExpect(jsonPath("$.analise").value(DEFAULT_ANALISE));
    }

    @Test
    @Transactional
    void getNonExistingMetaResultado() throws Exception {
        // Get the metaResultado
        restMetaResultadoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMetaResultado() throws Exception {
        // Initialize the database
        metaResultadoRepository.saveAndFlush(metaResultado);

        int databaseSizeBeforeUpdate = metaResultadoRepository.findAll().size();

        // Update the metaResultado
        MetaResultado updatedMetaResultado = metaResultadoRepository.findById(metaResultado.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedMetaResultado are not directly saved in db
        em.detach(updatedMetaResultado);
        updatedMetaResultado
            .lancadoEm(UPDATED_LANCADO_EM)
            .parcial(UPDATED_PARCIAL)
            .metaAtingida(UPDATED_META_ATINGIDA)
            .periodo(UPDATED_PERIODO)
            .avaliacao(UPDATED_AVALIACAO)
            .analise(UPDATED_ANALISE);
        MetaResultadoDTO metaResultadoDTO = metaResultadoMapper.toDto(updatedMetaResultado);

        restMetaResultadoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, metaResultadoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(metaResultadoDTO))
            )
            .andExpect(status().isOk());

        // Validate the MetaResultado in the database
        List<MetaResultado> metaResultadoList = metaResultadoRepository.findAll();
        assertThat(metaResultadoList).hasSize(databaseSizeBeforeUpdate);
        MetaResultado testMetaResultado = metaResultadoList.get(metaResultadoList.size() - 1);
        assertThat(testMetaResultado.getLancadoEm()).isEqualTo(UPDATED_LANCADO_EM);
        assertThat(testMetaResultado.getParcial()).isEqualTo(UPDATED_PARCIAL);
        assertThat(testMetaResultado.getMetaAtingida()).isEqualTo(UPDATED_META_ATINGIDA);
        assertThat(testMetaResultado.getPeriodo()).isEqualTo(UPDATED_PERIODO);
        assertThat(testMetaResultado.getAvaliacao()).isEqualTo(UPDATED_AVALIACAO);
        assertThat(testMetaResultado.getAnalise()).isEqualTo(UPDATED_ANALISE);
    }

    @Test
    @Transactional
    void putNonExistingMetaResultado() throws Exception {
        int databaseSizeBeforeUpdate = metaResultadoRepository.findAll().size();
        metaResultado.setId(longCount.incrementAndGet());

        // Create the MetaResultado
        MetaResultadoDTO metaResultadoDTO = metaResultadoMapper.toDto(metaResultado);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMetaResultadoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, metaResultadoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(metaResultadoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MetaResultado in the database
        List<MetaResultado> metaResultadoList = metaResultadoRepository.findAll();
        assertThat(metaResultadoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMetaResultado() throws Exception {
        int databaseSizeBeforeUpdate = metaResultadoRepository.findAll().size();
        metaResultado.setId(longCount.incrementAndGet());

        // Create the MetaResultado
        MetaResultadoDTO metaResultadoDTO = metaResultadoMapper.toDto(metaResultado);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMetaResultadoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(metaResultadoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MetaResultado in the database
        List<MetaResultado> metaResultadoList = metaResultadoRepository.findAll();
        assertThat(metaResultadoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMetaResultado() throws Exception {
        int databaseSizeBeforeUpdate = metaResultadoRepository.findAll().size();
        metaResultado.setId(longCount.incrementAndGet());

        // Create the MetaResultado
        MetaResultadoDTO metaResultadoDTO = metaResultadoMapper.toDto(metaResultado);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMetaResultadoMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(metaResultadoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MetaResultado in the database
        List<MetaResultado> metaResultadoList = metaResultadoRepository.findAll();
        assertThat(metaResultadoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMetaResultadoWithPatch() throws Exception {
        // Initialize the database
        metaResultadoRepository.saveAndFlush(metaResultado);

        int databaseSizeBeforeUpdate = metaResultadoRepository.findAll().size();

        // Update the metaResultado using partial update
        MetaResultado partialUpdatedMetaResultado = new MetaResultado();
        partialUpdatedMetaResultado.setId(metaResultado.getId());

        partialUpdatedMetaResultado.parcial(UPDATED_PARCIAL).avaliacao(UPDATED_AVALIACAO);

        restMetaResultadoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMetaResultado.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMetaResultado))
            )
            .andExpect(status().isOk());

        // Validate the MetaResultado in the database
        List<MetaResultado> metaResultadoList = metaResultadoRepository.findAll();
        assertThat(metaResultadoList).hasSize(databaseSizeBeforeUpdate);
        MetaResultado testMetaResultado = metaResultadoList.get(metaResultadoList.size() - 1);
        assertThat(testMetaResultado.getLancadoEm()).isEqualTo(DEFAULT_LANCADO_EM);
        assertThat(testMetaResultado.getParcial()).isEqualTo(UPDATED_PARCIAL);
        assertThat(testMetaResultado.getMetaAtingida()).isEqualTo(DEFAULT_META_ATINGIDA);
        assertThat(testMetaResultado.getPeriodo()).isEqualTo(DEFAULT_PERIODO);
        assertThat(testMetaResultado.getAvaliacao()).isEqualTo(UPDATED_AVALIACAO);
        assertThat(testMetaResultado.getAnalise()).isEqualTo(DEFAULT_ANALISE);
    }

    @Test
    @Transactional
    void fullUpdateMetaResultadoWithPatch() throws Exception {
        // Initialize the database
        metaResultadoRepository.saveAndFlush(metaResultado);

        int databaseSizeBeforeUpdate = metaResultadoRepository.findAll().size();

        // Update the metaResultado using partial update
        MetaResultado partialUpdatedMetaResultado = new MetaResultado();
        partialUpdatedMetaResultado.setId(metaResultado.getId());

        partialUpdatedMetaResultado
            .lancadoEm(UPDATED_LANCADO_EM)
            .parcial(UPDATED_PARCIAL)
            .metaAtingida(UPDATED_META_ATINGIDA)
            .periodo(UPDATED_PERIODO)
            .avaliacao(UPDATED_AVALIACAO)
            .analise(UPDATED_ANALISE);

        restMetaResultadoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMetaResultado.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMetaResultado))
            )
            .andExpect(status().isOk());

        // Validate the MetaResultado in the database
        List<MetaResultado> metaResultadoList = metaResultadoRepository.findAll();
        assertThat(metaResultadoList).hasSize(databaseSizeBeforeUpdate);
        MetaResultado testMetaResultado = metaResultadoList.get(metaResultadoList.size() - 1);
        assertThat(testMetaResultado.getLancadoEm()).isEqualTo(UPDATED_LANCADO_EM);
        assertThat(testMetaResultado.getParcial()).isEqualTo(UPDATED_PARCIAL);
        assertThat(testMetaResultado.getMetaAtingida()).isEqualTo(UPDATED_META_ATINGIDA);
        assertThat(testMetaResultado.getPeriodo()).isEqualTo(UPDATED_PERIODO);
        assertThat(testMetaResultado.getAvaliacao()).isEqualTo(UPDATED_AVALIACAO);
        assertThat(testMetaResultado.getAnalise()).isEqualTo(UPDATED_ANALISE);
    }

    @Test
    @Transactional
    void patchNonExistingMetaResultado() throws Exception {
        int databaseSizeBeforeUpdate = metaResultadoRepository.findAll().size();
        metaResultado.setId(longCount.incrementAndGet());

        // Create the MetaResultado
        MetaResultadoDTO metaResultadoDTO = metaResultadoMapper.toDto(metaResultado);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMetaResultadoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, metaResultadoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(metaResultadoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MetaResultado in the database
        List<MetaResultado> metaResultadoList = metaResultadoRepository.findAll();
        assertThat(metaResultadoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMetaResultado() throws Exception {
        int databaseSizeBeforeUpdate = metaResultadoRepository.findAll().size();
        metaResultado.setId(longCount.incrementAndGet());

        // Create the MetaResultado
        MetaResultadoDTO metaResultadoDTO = metaResultadoMapper.toDto(metaResultado);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMetaResultadoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(metaResultadoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MetaResultado in the database
        List<MetaResultado> metaResultadoList = metaResultadoRepository.findAll();
        assertThat(metaResultadoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMetaResultado() throws Exception {
        int databaseSizeBeforeUpdate = metaResultadoRepository.findAll().size();
        metaResultado.setId(longCount.incrementAndGet());

        // Create the MetaResultado
        MetaResultadoDTO metaResultadoDTO = metaResultadoMapper.toDto(metaResultado);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMetaResultadoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(metaResultadoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MetaResultado in the database
        List<MetaResultado> metaResultadoList = metaResultadoRepository.findAll();
        assertThat(metaResultadoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMetaResultado() throws Exception {
        // Initialize the database
        metaResultadoRepository.saveAndFlush(metaResultado);

        int databaseSizeBeforeDelete = metaResultadoRepository.findAll().size();

        // Delete the metaResultado
        restMetaResultadoMockMvc
            .perform(delete(ENTITY_API_URL_ID, metaResultado.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MetaResultado> metaResultadoList = metaResultadoRepository.findAll();
        assertThat(metaResultadoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
