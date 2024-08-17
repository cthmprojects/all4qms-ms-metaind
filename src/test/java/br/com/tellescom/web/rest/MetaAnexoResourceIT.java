package br.com.tellescom.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.tellescom.IntegrationTest;
import br.com.tellescom.domain.MetaAnexo;
import br.com.tellescom.repository.MetaAnexoRepository;
import br.com.tellescom.service.dto.MetaAnexoDTO;
import br.com.tellescom.service.mapper.MetaAnexoMapper;
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
 * Integration tests for the {@link MetaAnexoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MetaAnexoResourceIT {

    private static final String DEFAULT_NOME_FISICO = "AAAAAAAAAA";
    private static final String UPDATED_NOME_FISICO = "BBBBBBBBBB";

    private static final String DEFAULT_NOME_ORIGINAL = "AAAAAAAAAA";
    private static final String UPDATED_NOME_ORIGINAL = "BBBBBBBBBB";

    private static final String DEFAULT_EXTENSAO = "AAAAAAAAAA";
    private static final String UPDATED_EXTENSAO = "BBBBBBBBBB";

    private static final String DEFAULT_CAMINHO = "AAAAAAAAAA";
    private static final String UPDATED_CAMINHO = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATA_CRIACAO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_CRIACAO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/meta-anexos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MetaAnexoRepository metaAnexoRepository;

    @Autowired
    private MetaAnexoMapper metaAnexoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMetaAnexoMockMvc;

    private MetaAnexo metaAnexo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MetaAnexo createEntity(EntityManager em) {
        MetaAnexo metaAnexo = new MetaAnexo()
            .nomeFisico(DEFAULT_NOME_FISICO)
            .nomeOriginal(DEFAULT_NOME_ORIGINAL)
            .extensao(DEFAULT_EXTENSAO)
            .caminho(DEFAULT_CAMINHO)
            .dataCriacao(DEFAULT_DATA_CRIACAO);
        return metaAnexo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MetaAnexo createUpdatedEntity(EntityManager em) {
        MetaAnexo metaAnexo = new MetaAnexo()
            .nomeFisico(UPDATED_NOME_FISICO)
            .nomeOriginal(UPDATED_NOME_ORIGINAL)
            .extensao(UPDATED_EXTENSAO)
            .caminho(UPDATED_CAMINHO)
            .dataCriacao(UPDATED_DATA_CRIACAO);
        return metaAnexo;
    }

    @BeforeEach
    public void initTest() {
        metaAnexo = createEntity(em);
    }

    @Test
    @Transactional
    void createMetaAnexo() throws Exception {
        int databaseSizeBeforeCreate = metaAnexoRepository.findAll().size();
        // Create the MetaAnexo
        MetaAnexoDTO metaAnexoDTO = metaAnexoMapper.toDto(metaAnexo);
        restMetaAnexoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(metaAnexoDTO)))
            .andExpect(status().isCreated());

        // Validate the MetaAnexo in the database
        List<MetaAnexo> metaAnexoList = metaAnexoRepository.findAll();
        assertThat(metaAnexoList).hasSize(databaseSizeBeforeCreate + 1);
        MetaAnexo testMetaAnexo = metaAnexoList.get(metaAnexoList.size() - 1);
        assertThat(testMetaAnexo.getNomeFisico()).isEqualTo(DEFAULT_NOME_FISICO);
        assertThat(testMetaAnexo.getNomeOriginal()).isEqualTo(DEFAULT_NOME_ORIGINAL);
        assertThat(testMetaAnexo.getExtensao()).isEqualTo(DEFAULT_EXTENSAO);
        assertThat(testMetaAnexo.getCaminho()).isEqualTo(DEFAULT_CAMINHO);
        assertThat(testMetaAnexo.getDataCriacao()).isEqualTo(DEFAULT_DATA_CRIACAO);
    }

    @Test
    @Transactional
    void createMetaAnexoWithExistingId() throws Exception {
        // Create the MetaAnexo with an existing ID
        metaAnexo.setId(1L);
        MetaAnexoDTO metaAnexoDTO = metaAnexoMapper.toDto(metaAnexo);

        int databaseSizeBeforeCreate = metaAnexoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMetaAnexoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(metaAnexoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MetaAnexo in the database
        List<MetaAnexo> metaAnexoList = metaAnexoRepository.findAll();
        assertThat(metaAnexoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMetaAnexos() throws Exception {
        // Initialize the database
        metaAnexoRepository.saveAndFlush(metaAnexo);

        // Get all the metaAnexoList
        restMetaAnexoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(metaAnexo.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomeFisico").value(hasItem(DEFAULT_NOME_FISICO)))
            .andExpect(jsonPath("$.[*].nomeOriginal").value(hasItem(DEFAULT_NOME_ORIGINAL)))
            .andExpect(jsonPath("$.[*].extensao").value(hasItem(DEFAULT_EXTENSAO)))
            .andExpect(jsonPath("$.[*].caminho").value(hasItem(DEFAULT_CAMINHO)))
            .andExpect(jsonPath("$.[*].dataCriacao").value(hasItem(DEFAULT_DATA_CRIACAO.toString())));
    }

    @Test
    @Transactional
    void getMetaAnexo() throws Exception {
        // Initialize the database
        metaAnexoRepository.saveAndFlush(metaAnexo);

        // Get the metaAnexo
        restMetaAnexoMockMvc
            .perform(get(ENTITY_API_URL_ID, metaAnexo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(metaAnexo.getId().intValue()))
            .andExpect(jsonPath("$.nomeFisico").value(DEFAULT_NOME_FISICO))
            .andExpect(jsonPath("$.nomeOriginal").value(DEFAULT_NOME_ORIGINAL))
            .andExpect(jsonPath("$.extensao").value(DEFAULT_EXTENSAO))
            .andExpect(jsonPath("$.caminho").value(DEFAULT_CAMINHO))
            .andExpect(jsonPath("$.dataCriacao").value(DEFAULT_DATA_CRIACAO.toString()));
    }

    @Test
    @Transactional
    void getNonExistingMetaAnexo() throws Exception {
        // Get the metaAnexo
        restMetaAnexoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMetaAnexo() throws Exception {
        // Initialize the database
        metaAnexoRepository.saveAndFlush(metaAnexo);

        int databaseSizeBeforeUpdate = metaAnexoRepository.findAll().size();

        // Update the metaAnexo
        MetaAnexo updatedMetaAnexo = metaAnexoRepository.findById(metaAnexo.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedMetaAnexo are not directly saved in db
        em.detach(updatedMetaAnexo);
        updatedMetaAnexo
            .nomeFisico(UPDATED_NOME_FISICO)
            .nomeOriginal(UPDATED_NOME_ORIGINAL)
            .extensao(UPDATED_EXTENSAO)
            .caminho(UPDATED_CAMINHO)
            .dataCriacao(UPDATED_DATA_CRIACAO);
        MetaAnexoDTO metaAnexoDTO = metaAnexoMapper.toDto(updatedMetaAnexo);

        restMetaAnexoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, metaAnexoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(metaAnexoDTO))
            )
            .andExpect(status().isOk());

        // Validate the MetaAnexo in the database
        List<MetaAnexo> metaAnexoList = metaAnexoRepository.findAll();
        assertThat(metaAnexoList).hasSize(databaseSizeBeforeUpdate);
        MetaAnexo testMetaAnexo = metaAnexoList.get(metaAnexoList.size() - 1);
        assertThat(testMetaAnexo.getNomeFisico()).isEqualTo(UPDATED_NOME_FISICO);
        assertThat(testMetaAnexo.getNomeOriginal()).isEqualTo(UPDATED_NOME_ORIGINAL);
        assertThat(testMetaAnexo.getExtensao()).isEqualTo(UPDATED_EXTENSAO);
        assertThat(testMetaAnexo.getCaminho()).isEqualTo(UPDATED_CAMINHO);
        assertThat(testMetaAnexo.getDataCriacao()).isEqualTo(UPDATED_DATA_CRIACAO);
    }

    @Test
    @Transactional
    void putNonExistingMetaAnexo() throws Exception {
        int databaseSizeBeforeUpdate = metaAnexoRepository.findAll().size();
        metaAnexo.setId(longCount.incrementAndGet());

        // Create the MetaAnexo
        MetaAnexoDTO metaAnexoDTO = metaAnexoMapper.toDto(metaAnexo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMetaAnexoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, metaAnexoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(metaAnexoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MetaAnexo in the database
        List<MetaAnexo> metaAnexoList = metaAnexoRepository.findAll();
        assertThat(metaAnexoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMetaAnexo() throws Exception {
        int databaseSizeBeforeUpdate = metaAnexoRepository.findAll().size();
        metaAnexo.setId(longCount.incrementAndGet());

        // Create the MetaAnexo
        MetaAnexoDTO metaAnexoDTO = metaAnexoMapper.toDto(metaAnexo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMetaAnexoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(metaAnexoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MetaAnexo in the database
        List<MetaAnexo> metaAnexoList = metaAnexoRepository.findAll();
        assertThat(metaAnexoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMetaAnexo() throws Exception {
        int databaseSizeBeforeUpdate = metaAnexoRepository.findAll().size();
        metaAnexo.setId(longCount.incrementAndGet());

        // Create the MetaAnexo
        MetaAnexoDTO metaAnexoDTO = metaAnexoMapper.toDto(metaAnexo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMetaAnexoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(metaAnexoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MetaAnexo in the database
        List<MetaAnexo> metaAnexoList = metaAnexoRepository.findAll();
        assertThat(metaAnexoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMetaAnexoWithPatch() throws Exception {
        // Initialize the database
        metaAnexoRepository.saveAndFlush(metaAnexo);

        int databaseSizeBeforeUpdate = metaAnexoRepository.findAll().size();

        // Update the metaAnexo using partial update
        MetaAnexo partialUpdatedMetaAnexo = new MetaAnexo();
        partialUpdatedMetaAnexo.setId(metaAnexo.getId());

        partialUpdatedMetaAnexo.caminho(UPDATED_CAMINHO);

        restMetaAnexoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMetaAnexo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMetaAnexo))
            )
            .andExpect(status().isOk());

        // Validate the MetaAnexo in the database
        List<MetaAnexo> metaAnexoList = metaAnexoRepository.findAll();
        assertThat(metaAnexoList).hasSize(databaseSizeBeforeUpdate);
        MetaAnexo testMetaAnexo = metaAnexoList.get(metaAnexoList.size() - 1);
        assertThat(testMetaAnexo.getNomeFisico()).isEqualTo(DEFAULT_NOME_FISICO);
        assertThat(testMetaAnexo.getNomeOriginal()).isEqualTo(DEFAULT_NOME_ORIGINAL);
        assertThat(testMetaAnexo.getExtensao()).isEqualTo(DEFAULT_EXTENSAO);
        assertThat(testMetaAnexo.getCaminho()).isEqualTo(UPDATED_CAMINHO);
        assertThat(testMetaAnexo.getDataCriacao()).isEqualTo(DEFAULT_DATA_CRIACAO);
    }

    @Test
    @Transactional
    void fullUpdateMetaAnexoWithPatch() throws Exception {
        // Initialize the database
        metaAnexoRepository.saveAndFlush(metaAnexo);

        int databaseSizeBeforeUpdate = metaAnexoRepository.findAll().size();

        // Update the metaAnexo using partial update
        MetaAnexo partialUpdatedMetaAnexo = new MetaAnexo();
        partialUpdatedMetaAnexo.setId(metaAnexo.getId());

        partialUpdatedMetaAnexo
            .nomeFisico(UPDATED_NOME_FISICO)
            .nomeOriginal(UPDATED_NOME_ORIGINAL)
            .extensao(UPDATED_EXTENSAO)
            .caminho(UPDATED_CAMINHO)
            .dataCriacao(UPDATED_DATA_CRIACAO);

        restMetaAnexoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMetaAnexo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMetaAnexo))
            )
            .andExpect(status().isOk());

        // Validate the MetaAnexo in the database
        List<MetaAnexo> metaAnexoList = metaAnexoRepository.findAll();
        assertThat(metaAnexoList).hasSize(databaseSizeBeforeUpdate);
        MetaAnexo testMetaAnexo = metaAnexoList.get(metaAnexoList.size() - 1);
        assertThat(testMetaAnexo.getNomeFisico()).isEqualTo(UPDATED_NOME_FISICO);
        assertThat(testMetaAnexo.getNomeOriginal()).isEqualTo(UPDATED_NOME_ORIGINAL);
        assertThat(testMetaAnexo.getExtensao()).isEqualTo(UPDATED_EXTENSAO);
        assertThat(testMetaAnexo.getCaminho()).isEqualTo(UPDATED_CAMINHO);
        assertThat(testMetaAnexo.getDataCriacao()).isEqualTo(UPDATED_DATA_CRIACAO);
    }

    @Test
    @Transactional
    void patchNonExistingMetaAnexo() throws Exception {
        int databaseSizeBeforeUpdate = metaAnexoRepository.findAll().size();
        metaAnexo.setId(longCount.incrementAndGet());

        // Create the MetaAnexo
        MetaAnexoDTO metaAnexoDTO = metaAnexoMapper.toDto(metaAnexo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMetaAnexoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, metaAnexoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(metaAnexoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MetaAnexo in the database
        List<MetaAnexo> metaAnexoList = metaAnexoRepository.findAll();
        assertThat(metaAnexoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMetaAnexo() throws Exception {
        int databaseSizeBeforeUpdate = metaAnexoRepository.findAll().size();
        metaAnexo.setId(longCount.incrementAndGet());

        // Create the MetaAnexo
        MetaAnexoDTO metaAnexoDTO = metaAnexoMapper.toDto(metaAnexo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMetaAnexoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(metaAnexoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MetaAnexo in the database
        List<MetaAnexo> metaAnexoList = metaAnexoRepository.findAll();
        assertThat(metaAnexoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMetaAnexo() throws Exception {
        int databaseSizeBeforeUpdate = metaAnexoRepository.findAll().size();
        metaAnexo.setId(longCount.incrementAndGet());

        // Create the MetaAnexo
        MetaAnexoDTO metaAnexoDTO = metaAnexoMapper.toDto(metaAnexo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMetaAnexoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(metaAnexoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MetaAnexo in the database
        List<MetaAnexo> metaAnexoList = metaAnexoRepository.findAll();
        assertThat(metaAnexoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMetaAnexo() throws Exception {
        // Initialize the database
        metaAnexoRepository.saveAndFlush(metaAnexo);

        int databaseSizeBeforeDelete = metaAnexoRepository.findAll().size();

        // Delete the metaAnexo
        restMetaAnexoMockMvc
            .perform(delete(ENTITY_API_URL_ID, metaAnexo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MetaAnexo> metaAnexoList = metaAnexoRepository.findAll();
        assertThat(metaAnexoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
