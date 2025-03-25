package br.com.tellescom.web.rest;

import static br.com.tellescom.domain.AcaoCriticaAsserts.*;
import static br.com.tellescom.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.tellescom.IntegrationTest;
import br.com.tellescom.domain.AcaoCritica;
import br.com.tellescom.repository.AcaoCriticaRepository;
import br.com.tellescom.service.dto.AcaoCriticaDTO;
import br.com.tellescom.service.mapper.AcaoCriticaMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
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
 * Integration tests for the {@link AcaoCriticaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AcaoCriticaResourceIT {

    private static final String DEFAULT_ACAO_CRITICA = "AAAAAAAAAA";
    private static final String UPDATED_ACAO_CRITICA = "BBBBBBBBBB";

    private static final String DEFAULT_NOME_RESPONSAVEL = "AAAAAAAAAA";
    private static final String UPDATED_NOME_RESPONSAVEL = "BBBBBBBBBB";

    private static final Long DEFAULT_ID_RESPONSAVEL = 1L;
    private static final Long UPDATED_ID_RESPONSAVEL = 2L;

    private static final LocalDate DEFAULT_DATA_ACAO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_ACAO = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_INSTRUCAO_ACAO = "AAAAAAAAAA";
    private static final String UPDATED_INSTRUCAO_ACAO = "BBBBBBBBBB";

    private static final Long DEFAULT_ID_INDICADOR_CRITICA = 1L;
    private static final Long UPDATED_ID_INDICADOR_CRITICA = 2L;

    private static final ZonedDateTime DEFAULT_CRIADO_EM = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CRIADO_EM = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_ATUALIZADO_EM = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ATUALIZADO_EM = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Boolean DEFAULT_IS_NOTIFICADO = false;
    private static final Boolean UPDATED_IS_NOTIFICADO = true;

    private static final String ENTITY_API_URL = "/api/acao-criticas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AcaoCriticaRepository acaoCriticaRepository;

    @Autowired
    private AcaoCriticaMapper acaoCriticaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAcaoCriticaMockMvc;

    private AcaoCritica acaoCritica;

    private AcaoCritica insertedAcaoCritica;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AcaoCritica createEntity() {
        return new AcaoCritica()
            .acaoCritica(DEFAULT_ACAO_CRITICA)
            .nomeResponsavel(DEFAULT_NOME_RESPONSAVEL)
            .idResponsavel(DEFAULT_ID_RESPONSAVEL)
            .dataAcao(DEFAULT_DATA_ACAO)
            .instrucaoAcao(DEFAULT_INSTRUCAO_ACAO)
            .idIndicadorCritica(DEFAULT_ID_INDICADOR_CRITICA)
            .criadoEm(DEFAULT_CRIADO_EM)
            .atualizadoEm(DEFAULT_ATUALIZADO_EM)
            .isNotificado(DEFAULT_IS_NOTIFICADO);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AcaoCritica createUpdatedEntity() {
        return new AcaoCritica()
            .acaoCritica(UPDATED_ACAO_CRITICA)
            .nomeResponsavel(UPDATED_NOME_RESPONSAVEL)
            .idResponsavel(UPDATED_ID_RESPONSAVEL)
            .dataAcao(UPDATED_DATA_ACAO)
            .instrucaoAcao(UPDATED_INSTRUCAO_ACAO)
            .idIndicadorCritica(UPDATED_ID_INDICADOR_CRITICA)
            .criadoEm(UPDATED_CRIADO_EM)
            .atualizadoEm(UPDATED_ATUALIZADO_EM)
            .isNotificado(UPDATED_IS_NOTIFICADO);
    }

    @BeforeEach
    public void initTest() {
        acaoCritica = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedAcaoCritica != null) {
            acaoCriticaRepository.delete(insertedAcaoCritica);
            insertedAcaoCritica = null;
        }
    }

    @Test
    @Transactional
    void createAcaoCritica() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the AcaoCritica
        AcaoCriticaDTO acaoCriticaDTO = acaoCriticaMapper.toDto(acaoCritica);
        var returnedAcaoCriticaDTO = om.readValue(
            restAcaoCriticaMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(acaoCriticaDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            AcaoCriticaDTO.class
        );

        // Validate the AcaoCritica in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedAcaoCritica = acaoCriticaMapper.toEntity(returnedAcaoCriticaDTO);
        assertAcaoCriticaUpdatableFieldsEquals(returnedAcaoCritica, getPersistedAcaoCritica(returnedAcaoCritica));

        insertedAcaoCritica = returnedAcaoCritica;
    }

    @Test
    @Transactional
    void createAcaoCriticaWithExistingId() throws Exception {
        // Create the AcaoCritica with an existing ID
        acaoCritica.setId(1L);
        AcaoCriticaDTO acaoCriticaDTO = acaoCriticaMapper.toDto(acaoCritica);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAcaoCriticaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(acaoCriticaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AcaoCritica in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkIdIndicadorCriticaIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        acaoCritica.setIdIndicadorCritica(null);

        // Create the AcaoCritica, which fails.
        AcaoCriticaDTO acaoCriticaDTO = acaoCriticaMapper.toDto(acaoCritica);

        restAcaoCriticaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(acaoCriticaDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAcaoCriticas() throws Exception {
        // Initialize the database
        insertedAcaoCritica = acaoCriticaRepository.saveAndFlush(acaoCritica);

        // Get all the acaoCriticaList
        restAcaoCriticaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(acaoCritica.getId().intValue())))
            .andExpect(jsonPath("$.[*].acaoCritica").value(hasItem(DEFAULT_ACAO_CRITICA)))
            .andExpect(jsonPath("$.[*].nomeResponsavel").value(hasItem(DEFAULT_NOME_RESPONSAVEL)))
            .andExpect(jsonPath("$.[*].idResponsavel").value(hasItem(DEFAULT_ID_RESPONSAVEL.intValue())))
            .andExpect(jsonPath("$.[*].dataAcao").value(hasItem(DEFAULT_DATA_ACAO.toString())))
            .andExpect(jsonPath("$.[*].instrucaoAcao").value(hasItem(DEFAULT_INSTRUCAO_ACAO)))
            .andExpect(jsonPath("$.[*].idIndicadorCritica").value(hasItem(DEFAULT_ID_INDICADOR_CRITICA.intValue())))
            .andExpect(jsonPath("$.[*].criadoEm").value(hasItem(sameInstant(DEFAULT_CRIADO_EM))))
            .andExpect(jsonPath("$.[*].atualizadoEm").value(hasItem(sameInstant(DEFAULT_ATUALIZADO_EM))))
            .andExpect(jsonPath("$.[*].isNotificado").value(hasItem(DEFAULT_IS_NOTIFICADO)));
    }

    @Test
    @Transactional
    void getAcaoCritica() throws Exception {
        // Initialize the database
        insertedAcaoCritica = acaoCriticaRepository.saveAndFlush(acaoCritica);

        // Get the acaoCritica
        restAcaoCriticaMockMvc
            .perform(get(ENTITY_API_URL_ID, acaoCritica.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(acaoCritica.getId().intValue()))
            .andExpect(jsonPath("$.acaoCritica").value(DEFAULT_ACAO_CRITICA))
            .andExpect(jsonPath("$.nomeResponsavel").value(DEFAULT_NOME_RESPONSAVEL))
            .andExpect(jsonPath("$.idResponsavel").value(DEFAULT_ID_RESPONSAVEL.intValue()))
            .andExpect(jsonPath("$.dataAcao").value(DEFAULT_DATA_ACAO.toString()))
            .andExpect(jsonPath("$.instrucaoAcao").value(DEFAULT_INSTRUCAO_ACAO))
            .andExpect(jsonPath("$.idIndicadorCritica").value(DEFAULT_ID_INDICADOR_CRITICA.intValue()))
            .andExpect(jsonPath("$.criadoEm").value(sameInstant(DEFAULT_CRIADO_EM)))
            .andExpect(jsonPath("$.atualizadoEm").value(sameInstant(DEFAULT_ATUALIZADO_EM)))
            .andExpect(jsonPath("$.isNotificado").value(DEFAULT_IS_NOTIFICADO));
    }

    @Test
    @Transactional
    void getNonExistingAcaoCritica() throws Exception {
        // Get the acaoCritica
        restAcaoCriticaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAcaoCritica() throws Exception {
        // Initialize the database
        insertedAcaoCritica = acaoCriticaRepository.saveAndFlush(acaoCritica);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the acaoCritica
        AcaoCritica updatedAcaoCritica = acaoCriticaRepository.findById(acaoCritica.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAcaoCritica are not directly saved in db
        em.detach(updatedAcaoCritica);
        updatedAcaoCritica
            .acaoCritica(UPDATED_ACAO_CRITICA)
            .nomeResponsavel(UPDATED_NOME_RESPONSAVEL)
            .idResponsavel(UPDATED_ID_RESPONSAVEL)
            .dataAcao(UPDATED_DATA_ACAO)
            .instrucaoAcao(UPDATED_INSTRUCAO_ACAO)
            .idIndicadorCritica(UPDATED_ID_INDICADOR_CRITICA)
            .criadoEm(UPDATED_CRIADO_EM)
            .atualizadoEm(UPDATED_ATUALIZADO_EM)
            .isNotificado(UPDATED_IS_NOTIFICADO);
        AcaoCriticaDTO acaoCriticaDTO = acaoCriticaMapper.toDto(updatedAcaoCritica);

        restAcaoCriticaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, acaoCriticaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(acaoCriticaDTO))
            )
            .andExpect(status().isOk());

        // Validate the AcaoCritica in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAcaoCriticaToMatchAllProperties(updatedAcaoCritica);
    }

    @Test
    @Transactional
    void putNonExistingAcaoCritica() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        acaoCritica.setId(longCount.incrementAndGet());

        // Create the AcaoCritica
        AcaoCriticaDTO acaoCriticaDTO = acaoCriticaMapper.toDto(acaoCritica);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAcaoCriticaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, acaoCriticaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(acaoCriticaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AcaoCritica in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAcaoCritica() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        acaoCritica.setId(longCount.incrementAndGet());

        // Create the AcaoCritica
        AcaoCriticaDTO acaoCriticaDTO = acaoCriticaMapper.toDto(acaoCritica);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAcaoCriticaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(acaoCriticaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AcaoCritica in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAcaoCritica() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        acaoCritica.setId(longCount.incrementAndGet());

        // Create the AcaoCritica
        AcaoCriticaDTO acaoCriticaDTO = acaoCriticaMapper.toDto(acaoCritica);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAcaoCriticaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(acaoCriticaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AcaoCritica in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAcaoCriticaWithPatch() throws Exception {
        // Initialize the database
        insertedAcaoCritica = acaoCriticaRepository.saveAndFlush(acaoCritica);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the acaoCritica using partial update
        AcaoCritica partialUpdatedAcaoCritica = new AcaoCritica();
        partialUpdatedAcaoCritica.setId(acaoCritica.getId());

        partialUpdatedAcaoCritica.idResponsavel(UPDATED_ID_RESPONSAVEL).dataAcao(UPDATED_DATA_ACAO);

        restAcaoCriticaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAcaoCritica.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAcaoCritica))
            )
            .andExpect(status().isOk());

        // Validate the AcaoCritica in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void fullUpdateAcaoCriticaWithPatch() throws Exception {
        // Initialize the database
        insertedAcaoCritica = acaoCriticaRepository.saveAndFlush(acaoCritica);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the acaoCritica using partial update
        AcaoCritica partialUpdatedAcaoCritica = new AcaoCritica();
        partialUpdatedAcaoCritica.setId(acaoCritica.getId());

        partialUpdatedAcaoCritica
            .acaoCritica(UPDATED_ACAO_CRITICA)
            .nomeResponsavel(UPDATED_NOME_RESPONSAVEL)
            .idResponsavel(UPDATED_ID_RESPONSAVEL)
            .dataAcao(UPDATED_DATA_ACAO)
            .instrucaoAcao(UPDATED_INSTRUCAO_ACAO)
            .idIndicadorCritica(UPDATED_ID_INDICADOR_CRITICA)
            .criadoEm(UPDATED_CRIADO_EM)
            .atualizadoEm(UPDATED_ATUALIZADO_EM)
            .isNotificado(UPDATED_IS_NOTIFICADO);

        restAcaoCriticaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAcaoCritica.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAcaoCritica))
            )
            .andExpect(status().isOk());

        // Validate the AcaoCritica in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAcaoCriticaUpdatableFieldsEquals(partialUpdatedAcaoCritica, getPersistedAcaoCritica(partialUpdatedAcaoCritica));
    }

    @Test
    @Transactional
    void patchNonExistingAcaoCritica() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        acaoCritica.setId(longCount.incrementAndGet());

        // Create the AcaoCritica
        AcaoCriticaDTO acaoCriticaDTO = acaoCriticaMapper.toDto(acaoCritica);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAcaoCriticaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, acaoCriticaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(acaoCriticaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AcaoCritica in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAcaoCritica() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        acaoCritica.setId(longCount.incrementAndGet());

        // Create the AcaoCritica
        AcaoCriticaDTO acaoCriticaDTO = acaoCriticaMapper.toDto(acaoCritica);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAcaoCriticaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(acaoCriticaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AcaoCritica in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAcaoCritica() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        acaoCritica.setId(longCount.incrementAndGet());

        // Create the AcaoCritica
        AcaoCriticaDTO acaoCriticaDTO = acaoCriticaMapper.toDto(acaoCritica);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAcaoCriticaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(acaoCriticaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AcaoCritica in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAcaoCritica() throws Exception {
        // Initialize the database
        insertedAcaoCritica = acaoCriticaRepository.saveAndFlush(acaoCritica);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the acaoCritica
        restAcaoCriticaMockMvc
            .perform(delete(ENTITY_API_URL_ID, acaoCritica.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return acaoCriticaRepository.count();
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

    protected AcaoCritica getPersistedAcaoCritica(AcaoCritica acaoCritica) {
        return acaoCriticaRepository.findById(acaoCritica.getId()).orElseThrow();
    }

    protected void assertPersistedAcaoCriticaToMatchAllProperties(AcaoCritica expectedAcaoCritica) {
        assertAcaoCriticaAllPropertiesEquals(expectedAcaoCritica, getPersistedAcaoCritica(expectedAcaoCritica));
    }

    protected void assertPersistedAcaoCriticaToMatchUpdatableProperties(AcaoCritica expectedAcaoCritica) {
        assertAcaoCriticaAllUpdatablePropertiesEquals(expectedAcaoCritica, getPersistedAcaoCritica(expectedAcaoCritica));
    }
}
