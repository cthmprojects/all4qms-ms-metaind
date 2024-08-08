package br.com.tellescom.web.rest;

import static br.com.tellescom.domain.MetaAnexoAsserts.*;
import static br.com.tellescom.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.tellescom.IntegrationTest;
import br.com.tellescom.domain.MetaAnexo;
import br.com.tellescom.repository.MetaAnexoRepository;
import br.com.tellescom.service.dto.MetaAnexoDTO;
import br.com.tellescom.service.mapper.MetaAnexoMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
    private ObjectMapper om;

    @Autowired
    private MetaAnexoRepository metaAnexoRepository;

    @Autowired
    private MetaAnexoMapper metaAnexoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMetaAnexoMockMvc;

    private MetaAnexo metaAnexo;

    private MetaAnexo insertedMetaAnexo;

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

    @AfterEach
    public void cleanup() {
        if (insertedMetaAnexo != null) {
            metaAnexoRepository.delete(insertedMetaAnexo);
            insertedMetaAnexo = null;
        }
    }

    @Test
    @Transactional
    void createMetaAnexo() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the MetaAnexo
        MetaAnexoDTO metaAnexoDTO = metaAnexoMapper.toDto(metaAnexo);
        var returnedMetaAnexoDTO = om.readValue(
            restMetaAnexoMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(metaAnexoDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            MetaAnexoDTO.class
        );

        // Validate the MetaAnexo in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedMetaAnexo = metaAnexoMapper.toEntity(returnedMetaAnexoDTO);
        assertMetaAnexoUpdatableFieldsEquals(returnedMetaAnexo, getPersistedMetaAnexo(returnedMetaAnexo));

        insertedMetaAnexo = returnedMetaAnexo;
    }

    @Test
    @Transactional
    void createMetaAnexoWithExistingId() throws Exception {
        // Create the MetaAnexo with an existing ID
        metaAnexo.setId(1L);
        MetaAnexoDTO metaAnexoDTO = metaAnexoMapper.toDto(metaAnexo);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMetaAnexoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(metaAnexoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MetaAnexo in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMetaAnexos() throws Exception {
        // Initialize the database
        insertedMetaAnexo = metaAnexoRepository.saveAndFlush(metaAnexo);

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
        insertedMetaAnexo = metaAnexoRepository.saveAndFlush(metaAnexo);

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
        insertedMetaAnexo = metaAnexoRepository.saveAndFlush(metaAnexo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

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
                    .content(om.writeValueAsBytes(metaAnexoDTO))
            )
            .andExpect(status().isOk());

        // Validate the MetaAnexo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedMetaAnexoToMatchAllProperties(updatedMetaAnexo);
    }

    @Test
    @Transactional
    void putNonExistingMetaAnexo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        metaAnexo.setId(longCount.incrementAndGet());

        // Create the MetaAnexo
        MetaAnexoDTO metaAnexoDTO = metaAnexoMapper.toDto(metaAnexo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMetaAnexoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, metaAnexoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(metaAnexoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MetaAnexo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMetaAnexo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        metaAnexo.setId(longCount.incrementAndGet());

        // Create the MetaAnexo
        MetaAnexoDTO metaAnexoDTO = metaAnexoMapper.toDto(metaAnexo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMetaAnexoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(metaAnexoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MetaAnexo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMetaAnexo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        metaAnexo.setId(longCount.incrementAndGet());

        // Create the MetaAnexo
        MetaAnexoDTO metaAnexoDTO = metaAnexoMapper.toDto(metaAnexo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMetaAnexoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(metaAnexoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MetaAnexo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMetaAnexoWithPatch() throws Exception {
        // Initialize the database
        insertedMetaAnexo = metaAnexoRepository.saveAndFlush(metaAnexo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the metaAnexo using partial update
        MetaAnexo partialUpdatedMetaAnexo = new MetaAnexo();
        partialUpdatedMetaAnexo.setId(metaAnexo.getId());

        partialUpdatedMetaAnexo.nomeOriginal(UPDATED_NOME_ORIGINAL).extensao(UPDATED_EXTENSAO).dataCriacao(UPDATED_DATA_CRIACAO);

        restMetaAnexoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMetaAnexo.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMetaAnexo))
            )
            .andExpect(status().isOk());

        // Validate the MetaAnexo in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMetaAnexoUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedMetaAnexo, metaAnexo),
            getPersistedMetaAnexo(metaAnexo)
        );
    }

    @Test
    @Transactional
    void fullUpdateMetaAnexoWithPatch() throws Exception {
        // Initialize the database
        insertedMetaAnexo = metaAnexoRepository.saveAndFlush(metaAnexo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

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
                    .content(om.writeValueAsBytes(partialUpdatedMetaAnexo))
            )
            .andExpect(status().isOk());

        // Validate the MetaAnexo in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMetaAnexoUpdatableFieldsEquals(partialUpdatedMetaAnexo, getPersistedMetaAnexo(partialUpdatedMetaAnexo));
    }

    @Test
    @Transactional
    void patchNonExistingMetaAnexo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        metaAnexo.setId(longCount.incrementAndGet());

        // Create the MetaAnexo
        MetaAnexoDTO metaAnexoDTO = metaAnexoMapper.toDto(metaAnexo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMetaAnexoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, metaAnexoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(metaAnexoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MetaAnexo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMetaAnexo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        metaAnexo.setId(longCount.incrementAndGet());

        // Create the MetaAnexo
        MetaAnexoDTO metaAnexoDTO = metaAnexoMapper.toDto(metaAnexo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMetaAnexoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(metaAnexoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MetaAnexo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMetaAnexo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        metaAnexo.setId(longCount.incrementAndGet());

        // Create the MetaAnexo
        MetaAnexoDTO metaAnexoDTO = metaAnexoMapper.toDto(metaAnexo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMetaAnexoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(metaAnexoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MetaAnexo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMetaAnexo() throws Exception {
        // Initialize the database
        insertedMetaAnexo = metaAnexoRepository.saveAndFlush(metaAnexo);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the metaAnexo
        restMetaAnexoMockMvc
            .perform(delete(ENTITY_API_URL_ID, metaAnexo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return metaAnexoRepository.count();
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

    protected MetaAnexo getPersistedMetaAnexo(MetaAnexo metaAnexo) {
        return metaAnexoRepository.findById(metaAnexo.getId()).orElseThrow();
    }

    protected void assertPersistedMetaAnexoToMatchAllProperties(MetaAnexo expectedMetaAnexo) {
        assertMetaAnexoAllPropertiesEquals(expectedMetaAnexo, getPersistedMetaAnexo(expectedMetaAnexo));
    }

    protected void assertPersistedMetaAnexoToMatchUpdatableProperties(MetaAnexo expectedMetaAnexo) {
        assertMetaAnexoAllUpdatablePropertiesEquals(expectedMetaAnexo, getPersistedMetaAnexo(expectedMetaAnexo));
    }
}
