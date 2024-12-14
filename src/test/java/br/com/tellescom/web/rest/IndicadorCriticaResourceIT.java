package br.com.tellescom.web.rest;

import br.com.tellescom.IntegrationTest;
import br.com.tellescom.domain.IndicadorCritica;
import br.com.tellescom.repository.IndicadorCriticaRepository;
import br.com.tellescom.service.dto.IndicadorCriticaDTO;
import br.com.tellescom.service.mapper.IndicadorCriticaMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

import static br.com.tellescom.domain.IndicadorCriticaAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link IndicadorCriticaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class IndicadorCriticaResourceIT {

    private static final Long DEFAULT_ID_INDICADOR_META = 1L;
    private static final Long UPDATED_ID_INDICADOR_META = 2L;

    private static final Long DEFAULT_ID_PLANO = 1L;
    private static final Long UPDATED_ID_PLANO = 2L;

    private static final String DEFAULT_ANALISE_CRITICA = "AAAAAAAAAA";
    private static final String UPDATED_ANALISE_CRITICA = "BBBBBBBBBB";

    private static final String DEFAULT_OBSERVACAO = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACAO = "BBBBBBBBBB";

    private static final Integer DEFAULT_MES = 1;
    private static final Integer UPDATED_MES = 2;

    private static final Integer DEFAULT_ANO = 1;
    private static final Integer UPDATED_ANO = 2;

    private static final Long DEFAULT_CRIADO_POR = 1L;
    private static final Long UPDATED_CRIADO_POR = 2L;

    private static final Instant DEFAULT_CRIADO_EM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CRIADO_EM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_ATUALIZADO_POR = 1L;
    private static final Long UPDATED_ATUALIZADO_POR = 2L;

    private static final Instant DEFAULT_ATUALIZADO_EM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ATUALIZADO_EM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/indicador-criticas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private IndicadorCriticaRepository indicadorCriticaRepository;

    @Autowired
    private IndicadorCriticaMapper indicadorCriticaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIndicadorCriticaMockMvc;

    private IndicadorCritica indicadorCritica;

    private IndicadorCritica insertedIndicadorCritica;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IndicadorCritica createEntity() {
        return new IndicadorCritica()
            .idIndicadorMeta(DEFAULT_ID_INDICADOR_META)
            .idPlano(DEFAULT_ID_PLANO)
            .analiseCritica(DEFAULT_ANALISE_CRITICA)
            .observacao(DEFAULT_OBSERVACAO)
            .mes(DEFAULT_MES)
            .ano(DEFAULT_ANO)
            .criadoPor(DEFAULT_CRIADO_POR)
            .criadoEm(DEFAULT_CRIADO_EM)
            .atualizadoPor(DEFAULT_ATUALIZADO_POR)
            .atualizadoEm(DEFAULT_ATUALIZADO_EM);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IndicadorCritica createUpdatedEntity() {
        return new IndicadorCritica()
            .idIndicadorMeta(UPDATED_ID_INDICADOR_META)
            .idPlano(UPDATED_ID_PLANO)
            .analiseCritica(UPDATED_ANALISE_CRITICA)
            .observacao(UPDATED_OBSERVACAO)
            .mes(UPDATED_MES)
            .ano(UPDATED_ANO)
            .criadoPor(UPDATED_CRIADO_POR)
            .criadoEm(UPDATED_CRIADO_EM)
            .atualizadoPor(UPDATED_ATUALIZADO_POR)
            .atualizadoEm(UPDATED_ATUALIZADO_EM);
    }

    @BeforeEach
    public void initTest() {
        indicadorCritica = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedIndicadorCritica != null) {
            indicadorCriticaRepository.delete(insertedIndicadorCritica);
            insertedIndicadorCritica = null;
        }
    }

    @Test
    @Transactional
    void createIndicadorCritica() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the IndicadorCritica
        IndicadorCriticaDTO indicadorCriticaDTO = indicadorCriticaMapper.toDto(indicadorCritica);
        var returnedIndicadorCriticaDTO = om.readValue(
            restIndicadorCriticaMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(indicadorCriticaDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            IndicadorCriticaDTO.class
        );

        // Validate the IndicadorCritica in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedIndicadorCritica = indicadorCriticaMapper.toEntity(returnedIndicadorCriticaDTO);
        assertIndicadorCriticaUpdatableFieldsEquals(returnedIndicadorCritica, getPersistedIndicadorCritica(returnedIndicadorCritica));

        insertedIndicadorCritica = returnedIndicadorCritica;
    }

    @Test
    @Transactional
    void createIndicadorCriticaWithExistingId() throws Exception {
        // Create the IndicadorCritica with an existing ID
        indicadorCritica.setId(1L);
        IndicadorCriticaDTO indicadorCriticaDTO = indicadorCriticaMapper.toDto(indicadorCritica);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restIndicadorCriticaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(indicadorCriticaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the IndicadorCritica in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllIndicadorCriticas() throws Exception {
        // Initialize the database
        insertedIndicadorCritica = indicadorCriticaRepository.saveAndFlush(indicadorCritica);

        // Get all the indicadorCriticaList
        restIndicadorCriticaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(indicadorCritica.getId().intValue())))
            .andExpect(jsonPath("$.[*].idIndicadorMeta").value(hasItem(DEFAULT_ID_INDICADOR_META.intValue())))
            .andExpect(jsonPath("$.[*].idPlano").value(hasItem(DEFAULT_ID_PLANO.intValue())))
            .andExpect(jsonPath("$.[*].analiseCritica").value(hasItem(DEFAULT_ANALISE_CRITICA)))
            .andExpect(jsonPath("$.[*].observacao").value(hasItem(DEFAULT_OBSERVACAO)))
            .andExpect(jsonPath("$.[*].mes").value(hasItem(DEFAULT_MES)))
            .andExpect(jsonPath("$.[*].ano").value(hasItem(DEFAULT_ANO)))
            .andExpect(jsonPath("$.[*].criadoPor").value(hasItem(DEFAULT_CRIADO_POR.intValue())))
            .andExpect(jsonPath("$.[*].criadoEm").value(hasItem(DEFAULT_CRIADO_EM.toString())))
            .andExpect(jsonPath("$.[*].atualizadoPor").value(hasItem(DEFAULT_ATUALIZADO_POR.intValue())))
            .andExpect(jsonPath("$.[*].atualizadoEm").value(hasItem(DEFAULT_ATUALIZADO_EM.toString())));
    }

    @Test
    @Transactional
    void getIndicadorCritica() throws Exception {
        // Initialize the database
        insertedIndicadorCritica = indicadorCriticaRepository.saveAndFlush(indicadorCritica);

        // Get the indicadorCritica
        restIndicadorCriticaMockMvc
            .perform(get(ENTITY_API_URL_ID, indicadorCritica.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(indicadorCritica.getId().intValue()))
            .andExpect(jsonPath("$.idIndicadorMeta").value(DEFAULT_ID_INDICADOR_META.intValue()))
            .andExpect(jsonPath("$.idPlano").value(DEFAULT_ID_PLANO.intValue()))
            .andExpect(jsonPath("$.analiseCritica").value(DEFAULT_ANALISE_CRITICA))
            .andExpect(jsonPath("$.observacao").value(DEFAULT_OBSERVACAO))
            .andExpect(jsonPath("$.mes").value(DEFAULT_MES))
            .andExpect(jsonPath("$.ano").value(DEFAULT_ANO))
            .andExpect(jsonPath("$.criadoPor").value(DEFAULT_CRIADO_POR.intValue()))
            .andExpect(jsonPath("$.criadoEm").value(DEFAULT_CRIADO_EM.toString()))
            .andExpect(jsonPath("$.atualizadoPor").value(DEFAULT_ATUALIZADO_POR.intValue()))
            .andExpect(jsonPath("$.atualizadoEm").value(DEFAULT_ATUALIZADO_EM.toString()));
    }

    @Test
    @Transactional
    void getNonExistingIndicadorCritica() throws Exception {
        // Get the indicadorCritica
        restIndicadorCriticaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingIndicadorCritica() throws Exception {
        // Initialize the database
        insertedIndicadorCritica = indicadorCriticaRepository.saveAndFlush(indicadorCritica);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the indicadorCritica
        IndicadorCritica updatedIndicadorCritica = indicadorCriticaRepository.findById(indicadorCritica.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedIndicadorCritica are not directly saved in db
        em.detach(updatedIndicadorCritica);
        updatedIndicadorCritica
            .idIndicadorMeta(UPDATED_ID_INDICADOR_META)
            .idPlano(UPDATED_ID_PLANO)
            .analiseCritica(UPDATED_ANALISE_CRITICA)
            .observacao(UPDATED_OBSERVACAO)
            .mes(UPDATED_MES)
            .ano(UPDATED_ANO)
            .criadoPor(UPDATED_CRIADO_POR)
            .criadoEm(UPDATED_CRIADO_EM)
            .atualizadoPor(UPDATED_ATUALIZADO_POR)
            .atualizadoEm(UPDATED_ATUALIZADO_EM);
        IndicadorCriticaDTO indicadorCriticaDTO = indicadorCriticaMapper.toDto(updatedIndicadorCritica);

        restIndicadorCriticaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, indicadorCriticaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(indicadorCriticaDTO))
            )
            .andExpect(status().isOk());

        // Validate the IndicadorCritica in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedIndicadorCriticaToMatchAllProperties(updatedIndicadorCritica);
    }

    @Test
    @Transactional
    void putNonExistingIndicadorCritica() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        indicadorCritica.setId(longCount.incrementAndGet());

        // Create the IndicadorCritica
        IndicadorCriticaDTO indicadorCriticaDTO = indicadorCriticaMapper.toDto(indicadorCritica);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIndicadorCriticaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, indicadorCriticaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(indicadorCriticaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IndicadorCritica in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchIndicadorCritica() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        indicadorCritica.setId(longCount.incrementAndGet());

        // Create the IndicadorCritica
        IndicadorCriticaDTO indicadorCriticaDTO = indicadorCriticaMapper.toDto(indicadorCritica);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndicadorCriticaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(indicadorCriticaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IndicadorCritica in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamIndicadorCritica() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        indicadorCritica.setId(longCount.incrementAndGet());

        // Create the IndicadorCritica
        IndicadorCriticaDTO indicadorCriticaDTO = indicadorCriticaMapper.toDto(indicadorCritica);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndicadorCriticaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(indicadorCriticaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the IndicadorCritica in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateIndicadorCriticaWithPatch() throws Exception {
        // Initialize the database
        insertedIndicadorCritica = indicadorCriticaRepository.saveAndFlush(indicadorCritica);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the indicadorCritica using partial update
        IndicadorCritica partialUpdatedIndicadorCritica = new IndicadorCritica();
        partialUpdatedIndicadorCritica.setId(indicadorCritica.getId());

        partialUpdatedIndicadorCritica
            .idIndicadorMeta(UPDATED_ID_INDICADOR_META)
            .idPlano(UPDATED_ID_PLANO)
            .analiseCritica(UPDATED_ANALISE_CRITICA);

        restIndicadorCriticaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIndicadorCritica.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedIndicadorCritica))
            )
            .andExpect(status().isOk());

        // Validate the IndicadorCritica in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void fullUpdateIndicadorCriticaWithPatch() throws Exception {
        // Initialize the database
        insertedIndicadorCritica = indicadorCriticaRepository.saveAndFlush(indicadorCritica);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the indicadorCritica using partial update
        IndicadorCritica partialUpdatedIndicadorCritica = new IndicadorCritica();
        partialUpdatedIndicadorCritica.setId(indicadorCritica.getId());

        partialUpdatedIndicadorCritica
            .idIndicadorMeta(UPDATED_ID_INDICADOR_META)
            .idPlano(UPDATED_ID_PLANO)
            .analiseCritica(UPDATED_ANALISE_CRITICA)
            .observacao(UPDATED_OBSERVACAO)
            .mes(UPDATED_MES)
            .ano(UPDATED_ANO)
            .criadoPor(UPDATED_CRIADO_POR)
            .criadoEm(UPDATED_CRIADO_EM)
            .atualizadoPor(UPDATED_ATUALIZADO_POR)
            .atualizadoEm(UPDATED_ATUALIZADO_EM);

        restIndicadorCriticaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIndicadorCritica.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedIndicadorCritica))
            )
            .andExpect(status().isOk());

        // Validate the IndicadorCritica in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertIndicadorCriticaUpdatableFieldsEquals(
            partialUpdatedIndicadorCritica,
            getPersistedIndicadorCritica(partialUpdatedIndicadorCritica)
        );
    }

    @Test
    @Transactional
    void patchNonExistingIndicadorCritica() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        indicadorCritica.setId(longCount.incrementAndGet());

        // Create the IndicadorCritica
        IndicadorCriticaDTO indicadorCriticaDTO = indicadorCriticaMapper.toDto(indicadorCritica);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIndicadorCriticaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, indicadorCriticaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(indicadorCriticaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IndicadorCritica in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchIndicadorCritica() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        indicadorCritica.setId(longCount.incrementAndGet());

        // Create the IndicadorCritica
        IndicadorCriticaDTO indicadorCriticaDTO = indicadorCriticaMapper.toDto(indicadorCritica);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndicadorCriticaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(indicadorCriticaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IndicadorCritica in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamIndicadorCritica() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        indicadorCritica.setId(longCount.incrementAndGet());

        // Create the IndicadorCritica
        IndicadorCriticaDTO indicadorCriticaDTO = indicadorCriticaMapper.toDto(indicadorCritica);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndicadorCriticaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(indicadorCriticaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the IndicadorCritica in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteIndicadorCritica() throws Exception {
        // Initialize the database
        insertedIndicadorCritica = indicadorCriticaRepository.saveAndFlush(indicadorCritica);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the indicadorCritica
        restIndicadorCriticaMockMvc
            .perform(delete(ENTITY_API_URL_ID, indicadorCritica.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return indicadorCriticaRepository.count();
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

    protected IndicadorCritica getPersistedIndicadorCritica(IndicadorCritica indicadorCritica) {
        return indicadorCriticaRepository.findById(indicadorCritica.getId()).orElseThrow();
    }

    protected void assertPersistedIndicadorCriticaToMatchAllProperties(IndicadorCritica expectedIndicadorCritica) {
        assertIndicadorCriticaAllPropertiesEquals(expectedIndicadorCritica, getPersistedIndicadorCritica(expectedIndicadorCritica));
    }

    protected void assertPersistedIndicadorCriticaToMatchUpdatableProperties(IndicadorCritica expectedIndicadorCritica) {
        assertIndicadorCriticaAllUpdatablePropertiesEquals(
            expectedIndicadorCritica,
            getPersistedIndicadorCritica(expectedIndicadorCritica)
        );
    }
}
