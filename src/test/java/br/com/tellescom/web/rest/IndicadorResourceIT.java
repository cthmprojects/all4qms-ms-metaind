package br.com.tellescom.web.rest;

import static br.com.tellescom.domain.IndicadorAsserts.*;
import static br.com.tellescom.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.tellescom.IntegrationTest;
import br.com.tellescom.domain.Indicador;
import br.com.tellescom.domain.enumeration.EnumTendencia;
import br.com.tellescom.domain.enumeration.EnumUnidadeMedida;
import br.com.tellescom.repository.IndicadorRepository;
import br.com.tellescom.service.dto.IndicadorDTO;
import br.com.tellescom.service.mapper.IndicadorMapper;
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
 * Integration tests for the {@link IndicadorResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class IndicadorResourceIT {

    private static final String DEFAULT_CODIGO_INDICADOR = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO_INDICADOR = "BBBBBBBBBB";

    private static final String DEFAULT_NOME_INDICADOR = "AAAAAAAAAA";
    private static final String UPDATED_NOME_INDICADOR = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO_INDICADOR = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO_INDICADOR = "BBBBBBBBBB";

    private static final EnumUnidadeMedida DEFAULT_UNIDADE = EnumUnidadeMedida.PERCENTUAL;
    private static final EnumUnidadeMedida UPDATED_UNIDADE = EnumUnidadeMedida.MONETARIO;

    private static final EnumTendencia DEFAULT_TENDENCIA = EnumTendencia.MAIOR;
    private static final EnumTendencia UPDATED_TENDENCIA = EnumTendencia.MENOR;

    private static final Integer DEFAULT_ID_PROCESSO = 1;
    private static final Integer UPDATED_ID_PROCESSO = 2;

    private static final Integer DEFAULT_ID_META_INDICADOR = 1;
    private static final Integer UPDATED_ID_META_INDICADOR = 2;

    private static final String ENTITY_API_URL = "/api/indicadors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private IndicadorRepository indicadorRepository;

    @Autowired
    private IndicadorMapper indicadorMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIndicadorMockMvc;

    private Indicador indicador;

    private Indicador insertedIndicador;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Indicador createEntity(EntityManager em) {
        Indicador indicador = new Indicador()
            .codigoIndicador(DEFAULT_CODIGO_INDICADOR)
            .nomeIndicador(DEFAULT_NOME_INDICADOR)
            .descricaoIndicador(DEFAULT_DESCRICAO_INDICADOR)
            .unidade(DEFAULT_UNIDADE)
            .tendencia(DEFAULT_TENDENCIA)
            .idProcesso(DEFAULT_ID_PROCESSO)
            .idMetaIndicador(DEFAULT_ID_META_INDICADOR);
        return indicador;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Indicador createUpdatedEntity(EntityManager em) {
        Indicador indicador = new Indicador()
            .codigoIndicador(UPDATED_CODIGO_INDICADOR)
            .nomeIndicador(UPDATED_NOME_INDICADOR)
            .descricaoIndicador(UPDATED_DESCRICAO_INDICADOR)
            .unidade(UPDATED_UNIDADE)
            .tendencia(UPDATED_TENDENCIA)
            .idProcesso(UPDATED_ID_PROCESSO)
            .idMetaIndicador(UPDATED_ID_META_INDICADOR);
        return indicador;
    }

    @BeforeEach
    public void initTest() {
        indicador = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedIndicador != null) {
            indicadorRepository.delete(insertedIndicador);
            insertedIndicador = null;
        }
    }

    @Test
    @Transactional
    void createIndicador() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Indicador
        IndicadorDTO indicadorDTO = indicadorMapper.toDto(indicador);
        var returnedIndicadorDTO = om.readValue(
            restIndicadorMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(indicadorDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            IndicadorDTO.class
        );

        // Validate the Indicador in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedIndicador = indicadorMapper.toEntity(returnedIndicadorDTO);
        assertIndicadorUpdatableFieldsEquals(returnedIndicador, getPersistedIndicador(returnedIndicador));

        insertedIndicador = returnedIndicador;
    }

    @Test
    @Transactional
    void createIndicadorWithExistingId() throws Exception {
        // Create the Indicador with an existing ID
        indicador.setId(1L);
        IndicadorDTO indicadorDTO = indicadorMapper.toDto(indicador);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restIndicadorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(indicadorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Indicador in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllIndicadors() throws Exception {
        // Initialize the database
        insertedIndicador = indicadorRepository.saveAndFlush(indicador);

        // Get all the indicadorList
        restIndicadorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(indicador.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigoIndicador").value(hasItem(DEFAULT_CODIGO_INDICADOR)))
            .andExpect(jsonPath("$.[*].nomeIndicador").value(hasItem(DEFAULT_NOME_INDICADOR)))
            .andExpect(jsonPath("$.[*].descricaoIndicador").value(hasItem(DEFAULT_DESCRICAO_INDICADOR)))
            .andExpect(jsonPath("$.[*].unidade").value(hasItem(DEFAULT_UNIDADE.toString())))
            .andExpect(jsonPath("$.[*].tendencia").value(hasItem(DEFAULT_TENDENCIA.toString())))
            .andExpect(jsonPath("$.[*].idProcesso").value(hasItem(DEFAULT_ID_PROCESSO)))
            .andExpect(jsonPath("$.[*].idMetaIndicador").value(hasItem(DEFAULT_ID_META_INDICADOR)));
    }

    @Test
    @Transactional
    void getIndicador() throws Exception {
        // Initialize the database
        insertedIndicador = indicadorRepository.saveAndFlush(indicador);

        // Get the indicador
        restIndicadorMockMvc
            .perform(get(ENTITY_API_URL_ID, indicador.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(indicador.getId().intValue()))
            .andExpect(jsonPath("$.codigoIndicador").value(DEFAULT_CODIGO_INDICADOR))
            .andExpect(jsonPath("$.nomeIndicador").value(DEFAULT_NOME_INDICADOR))
            .andExpect(jsonPath("$.descricaoIndicador").value(DEFAULT_DESCRICAO_INDICADOR))
            .andExpect(jsonPath("$.unidade").value(DEFAULT_UNIDADE.toString()))
            .andExpect(jsonPath("$.tendencia").value(DEFAULT_TENDENCIA.toString()))
            .andExpect(jsonPath("$.idProcesso").value(DEFAULT_ID_PROCESSO))
            .andExpect(jsonPath("$.idMetaIndicador").value(DEFAULT_ID_META_INDICADOR));
    }

    @Test
    @Transactional
    void getNonExistingIndicador() throws Exception {
        // Get the indicador
        restIndicadorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingIndicador() throws Exception {
        // Initialize the database
        insertedIndicador = indicadorRepository.saveAndFlush(indicador);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the indicador
        Indicador updatedIndicador = indicadorRepository.findById(indicador.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedIndicador are not directly saved in db
        em.detach(updatedIndicador);
        updatedIndicador
            .codigoIndicador(UPDATED_CODIGO_INDICADOR)
            .nomeIndicador(UPDATED_NOME_INDICADOR)
            .descricaoIndicador(UPDATED_DESCRICAO_INDICADOR)
            .unidade(UPDATED_UNIDADE)
            .tendencia(UPDATED_TENDENCIA)
            .idProcesso(UPDATED_ID_PROCESSO)
            .idMetaIndicador(UPDATED_ID_META_INDICADOR);
        IndicadorDTO indicadorDTO = indicadorMapper.toDto(updatedIndicador);

        restIndicadorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, indicadorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(indicadorDTO))
            )
            .andExpect(status().isOk());

        // Validate the Indicador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedIndicadorToMatchAllProperties(updatedIndicador);
    }

    @Test
    @Transactional
    void putNonExistingIndicador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        indicador.setId(longCount.incrementAndGet());

        // Create the Indicador
        IndicadorDTO indicadorDTO = indicadorMapper.toDto(indicador);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIndicadorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, indicadorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(indicadorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Indicador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchIndicador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        indicador.setId(longCount.incrementAndGet());

        // Create the Indicador
        IndicadorDTO indicadorDTO = indicadorMapper.toDto(indicador);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndicadorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(indicadorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Indicador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamIndicador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        indicador.setId(longCount.incrementAndGet());

        // Create the Indicador
        IndicadorDTO indicadorDTO = indicadorMapper.toDto(indicador);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndicadorMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(indicadorDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Indicador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateIndicadorWithPatch() throws Exception {
        // Initialize the database
        insertedIndicador = indicadorRepository.saveAndFlush(indicador);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the indicador using partial update
        Indicador partialUpdatedIndicador = new Indicador();
        partialUpdatedIndicador.setId(indicador.getId());

        partialUpdatedIndicador
            .nomeIndicador(UPDATED_NOME_INDICADOR)
            .descricaoIndicador(UPDATED_DESCRICAO_INDICADOR)
            .unidade(UPDATED_UNIDADE)
            .tendencia(UPDATED_TENDENCIA);

        restIndicadorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIndicador.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedIndicador))
            )
            .andExpect(status().isOk());

        // Validate the Indicador in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertIndicadorUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedIndicador, indicador),
            getPersistedIndicador(indicador)
        );
    }

    @Test
    @Transactional
    void fullUpdateIndicadorWithPatch() throws Exception {
        // Initialize the database
        insertedIndicador = indicadorRepository.saveAndFlush(indicador);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the indicador using partial update
        Indicador partialUpdatedIndicador = new Indicador();
        partialUpdatedIndicador.setId(indicador.getId());

        partialUpdatedIndicador
            .codigoIndicador(UPDATED_CODIGO_INDICADOR)
            .nomeIndicador(UPDATED_NOME_INDICADOR)
            .descricaoIndicador(UPDATED_DESCRICAO_INDICADOR)
            .unidade(UPDATED_UNIDADE)
            .tendencia(UPDATED_TENDENCIA)
            .idProcesso(UPDATED_ID_PROCESSO)
            .idMetaIndicador(UPDATED_ID_META_INDICADOR);

        restIndicadorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIndicador.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedIndicador))
            )
            .andExpect(status().isOk());

        // Validate the Indicador in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertIndicadorUpdatableFieldsEquals(partialUpdatedIndicador, getPersistedIndicador(partialUpdatedIndicador));
    }

    @Test
    @Transactional
    void patchNonExistingIndicador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        indicador.setId(longCount.incrementAndGet());

        // Create the Indicador
        IndicadorDTO indicadorDTO = indicadorMapper.toDto(indicador);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIndicadorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, indicadorDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(indicadorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Indicador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchIndicador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        indicador.setId(longCount.incrementAndGet());

        // Create the Indicador
        IndicadorDTO indicadorDTO = indicadorMapper.toDto(indicador);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndicadorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(indicadorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Indicador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamIndicador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        indicador.setId(longCount.incrementAndGet());

        // Create the Indicador
        IndicadorDTO indicadorDTO = indicadorMapper.toDto(indicador);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndicadorMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(indicadorDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Indicador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteIndicador() throws Exception {
        // Initialize the database
        insertedIndicador = indicadorRepository.saveAndFlush(indicador);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the indicador
        restIndicadorMockMvc
            .perform(delete(ENTITY_API_URL_ID, indicador.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return indicadorRepository.count();
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

    protected Indicador getPersistedIndicador(Indicador indicador) {
        return indicadorRepository.findById(indicador.getId()).orElseThrow();
    }

    protected void assertPersistedIndicadorToMatchAllProperties(Indicador expectedIndicador) {
        assertIndicadorAllPropertiesEquals(expectedIndicador, getPersistedIndicador(expectedIndicador));
    }

    protected void assertPersistedIndicadorToMatchUpdatableProperties(Indicador expectedIndicador) {
        assertIndicadorAllUpdatablePropertiesEquals(expectedIndicador, getPersistedIndicador(expectedIndicador));
    }
}
