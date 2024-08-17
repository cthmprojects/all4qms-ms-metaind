package br.com.tellescom.web.rest;

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
    private IndicadorRepository indicadorRepository;

    @Autowired
    private IndicadorMapper indicadorMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIndicadorMockMvc;

    private Indicador indicador;

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

    @Test
    @Transactional
    void createIndicador() throws Exception {
        int databaseSizeBeforeCreate = indicadorRepository.findAll().size();
        // Create the Indicador
        IndicadorDTO indicadorDTO = indicadorMapper.toDto(indicador);
        restIndicadorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indicadorDTO)))
            .andExpect(status().isCreated());

        // Validate the Indicador in the database
        List<Indicador> indicadorList = indicadorRepository.findAll();
        assertThat(indicadorList).hasSize(databaseSizeBeforeCreate + 1);
        Indicador testIndicador = indicadorList.get(indicadorList.size() - 1);
        assertThat(testIndicador.getCodigoIndicador()).isEqualTo(DEFAULT_CODIGO_INDICADOR);
        assertThat(testIndicador.getNomeIndicador()).isEqualTo(DEFAULT_NOME_INDICADOR);
        assertThat(testIndicador.getDescricaoIndicador()).isEqualTo(DEFAULT_DESCRICAO_INDICADOR);
        assertThat(testIndicador.getUnidade()).isEqualTo(DEFAULT_UNIDADE);
        assertThat(testIndicador.getTendencia()).isEqualTo(DEFAULT_TENDENCIA);
        assertThat(testIndicador.getIdProcesso()).isEqualTo(DEFAULT_ID_PROCESSO);
        assertThat(testIndicador.getIdMetaIndicador()).isEqualTo(DEFAULT_ID_META_INDICADOR);
    }

    @Test
    @Transactional
    void createIndicadorWithExistingId() throws Exception {
        // Create the Indicador with an existing ID
        indicador.setId(1L);
        IndicadorDTO indicadorDTO = indicadorMapper.toDto(indicador);

        int databaseSizeBeforeCreate = indicadorRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restIndicadorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indicadorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Indicador in the database
        List<Indicador> indicadorList = indicadorRepository.findAll();
        assertThat(indicadorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllIndicadors() throws Exception {
        // Initialize the database
        indicadorRepository.saveAndFlush(indicador);

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
        indicadorRepository.saveAndFlush(indicador);

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
        indicadorRepository.saveAndFlush(indicador);

        int databaseSizeBeforeUpdate = indicadorRepository.findAll().size();

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
                    .content(TestUtil.convertObjectToJsonBytes(indicadorDTO))
            )
            .andExpect(status().isOk());

        // Validate the Indicador in the database
        List<Indicador> indicadorList = indicadorRepository.findAll();
        assertThat(indicadorList).hasSize(databaseSizeBeforeUpdate);
        Indicador testIndicador = indicadorList.get(indicadorList.size() - 1);
        assertThat(testIndicador.getCodigoIndicador()).isEqualTo(UPDATED_CODIGO_INDICADOR);
        assertThat(testIndicador.getNomeIndicador()).isEqualTo(UPDATED_NOME_INDICADOR);
        assertThat(testIndicador.getDescricaoIndicador()).isEqualTo(UPDATED_DESCRICAO_INDICADOR);
        assertThat(testIndicador.getUnidade()).isEqualTo(UPDATED_UNIDADE);
        assertThat(testIndicador.getTendencia()).isEqualTo(UPDATED_TENDENCIA);
        assertThat(testIndicador.getIdProcesso()).isEqualTo(UPDATED_ID_PROCESSO);
        assertThat(testIndicador.getIdMetaIndicador()).isEqualTo(UPDATED_ID_META_INDICADOR);
    }

    @Test
    @Transactional
    void putNonExistingIndicador() throws Exception {
        int databaseSizeBeforeUpdate = indicadorRepository.findAll().size();
        indicador.setId(longCount.incrementAndGet());

        // Create the Indicador
        IndicadorDTO indicadorDTO = indicadorMapper.toDto(indicador);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIndicadorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, indicadorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(indicadorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Indicador in the database
        List<Indicador> indicadorList = indicadorRepository.findAll();
        assertThat(indicadorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchIndicador() throws Exception {
        int databaseSizeBeforeUpdate = indicadorRepository.findAll().size();
        indicador.setId(longCount.incrementAndGet());

        // Create the Indicador
        IndicadorDTO indicadorDTO = indicadorMapper.toDto(indicador);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndicadorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(indicadorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Indicador in the database
        List<Indicador> indicadorList = indicadorRepository.findAll();
        assertThat(indicadorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamIndicador() throws Exception {
        int databaseSizeBeforeUpdate = indicadorRepository.findAll().size();
        indicador.setId(longCount.incrementAndGet());

        // Create the Indicador
        IndicadorDTO indicadorDTO = indicadorMapper.toDto(indicador);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndicadorMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indicadorDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Indicador in the database
        List<Indicador> indicadorList = indicadorRepository.findAll();
        assertThat(indicadorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateIndicadorWithPatch() throws Exception {
        // Initialize the database
        indicadorRepository.saveAndFlush(indicador);

        int databaseSizeBeforeUpdate = indicadorRepository.findAll().size();

        // Update the indicador using partial update
        Indicador partialUpdatedIndicador = new Indicador();
        partialUpdatedIndicador.setId(indicador.getId());

        partialUpdatedIndicador
            .codigoIndicador(UPDATED_CODIGO_INDICADOR)
            .unidade(UPDATED_UNIDADE)
            .idProcesso(UPDATED_ID_PROCESSO)
            .idMetaIndicador(UPDATED_ID_META_INDICADOR);

        restIndicadorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIndicador.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIndicador))
            )
            .andExpect(status().isOk());

        // Validate the Indicador in the database
        List<Indicador> indicadorList = indicadorRepository.findAll();
        assertThat(indicadorList).hasSize(databaseSizeBeforeUpdate);
        Indicador testIndicador = indicadorList.get(indicadorList.size() - 1);
        assertThat(testIndicador.getCodigoIndicador()).isEqualTo(UPDATED_CODIGO_INDICADOR);
        assertThat(testIndicador.getNomeIndicador()).isEqualTo(DEFAULT_NOME_INDICADOR);
        assertThat(testIndicador.getDescricaoIndicador()).isEqualTo(DEFAULT_DESCRICAO_INDICADOR);
        assertThat(testIndicador.getUnidade()).isEqualTo(UPDATED_UNIDADE);
        assertThat(testIndicador.getTendencia()).isEqualTo(DEFAULT_TENDENCIA);
        assertThat(testIndicador.getIdProcesso()).isEqualTo(UPDATED_ID_PROCESSO);
        assertThat(testIndicador.getIdMetaIndicador()).isEqualTo(UPDATED_ID_META_INDICADOR);
    }

    @Test
    @Transactional
    void fullUpdateIndicadorWithPatch() throws Exception {
        // Initialize the database
        indicadorRepository.saveAndFlush(indicador);

        int databaseSizeBeforeUpdate = indicadorRepository.findAll().size();

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
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIndicador))
            )
            .andExpect(status().isOk());

        // Validate the Indicador in the database
        List<Indicador> indicadorList = indicadorRepository.findAll();
        assertThat(indicadorList).hasSize(databaseSizeBeforeUpdate);
        Indicador testIndicador = indicadorList.get(indicadorList.size() - 1);
        assertThat(testIndicador.getCodigoIndicador()).isEqualTo(UPDATED_CODIGO_INDICADOR);
        assertThat(testIndicador.getNomeIndicador()).isEqualTo(UPDATED_NOME_INDICADOR);
        assertThat(testIndicador.getDescricaoIndicador()).isEqualTo(UPDATED_DESCRICAO_INDICADOR);
        assertThat(testIndicador.getUnidade()).isEqualTo(UPDATED_UNIDADE);
        assertThat(testIndicador.getTendencia()).isEqualTo(UPDATED_TENDENCIA);
        assertThat(testIndicador.getIdProcesso()).isEqualTo(UPDATED_ID_PROCESSO);
        assertThat(testIndicador.getIdMetaIndicador()).isEqualTo(UPDATED_ID_META_INDICADOR);
    }

    @Test
    @Transactional
    void patchNonExistingIndicador() throws Exception {
        int databaseSizeBeforeUpdate = indicadorRepository.findAll().size();
        indicador.setId(longCount.incrementAndGet());

        // Create the Indicador
        IndicadorDTO indicadorDTO = indicadorMapper.toDto(indicador);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIndicadorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, indicadorDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(indicadorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Indicador in the database
        List<Indicador> indicadorList = indicadorRepository.findAll();
        assertThat(indicadorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchIndicador() throws Exception {
        int databaseSizeBeforeUpdate = indicadorRepository.findAll().size();
        indicador.setId(longCount.incrementAndGet());

        // Create the Indicador
        IndicadorDTO indicadorDTO = indicadorMapper.toDto(indicador);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndicadorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(indicadorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Indicador in the database
        List<Indicador> indicadorList = indicadorRepository.findAll();
        assertThat(indicadorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamIndicador() throws Exception {
        int databaseSizeBeforeUpdate = indicadorRepository.findAll().size();
        indicador.setId(longCount.incrementAndGet());

        // Create the Indicador
        IndicadorDTO indicadorDTO = indicadorMapper.toDto(indicador);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndicadorMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(indicadorDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Indicador in the database
        List<Indicador> indicadorList = indicadorRepository.findAll();
        assertThat(indicadorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteIndicador() throws Exception {
        // Initialize the database
        indicadorRepository.saveAndFlush(indicador);

        int databaseSizeBeforeDelete = indicadorRepository.findAll().size();

        // Delete the indicador
        restIndicadorMockMvc
            .perform(delete(ENTITY_API_URL_ID, indicador.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Indicador> indicadorList = indicadorRepository.findAll();
        assertThat(indicadorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
