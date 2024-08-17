package br.com.tellescom.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.tellescom.IntegrationTest;
import br.com.tellescom.domain.IndicadorMeta;
import br.com.tellescom.domain.enumeration.EnumTemporal;
import br.com.tellescom.repository.IndicadorMetaRepository;
import br.com.tellescom.service.dto.IndicadorMetaDTO;
import br.com.tellescom.service.mapper.IndicadorMetaMapper;
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
 * Integration tests for the {@link IndicadorMetaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class IndicadorMetaResourceIT {

    private static final EnumTemporal DEFAULT_FREQUENCIA = EnumTemporal.MENSAL;
    private static final EnumTemporal UPDATED_FREQUENCIA = EnumTemporal.BIMESTRAL;

    private static final String DEFAULT_ANO_INDICADOR = "AAAAAAAAAA";
    private static final String UPDATED_ANO_INDICADOR = "BBBBBBBBBB";

    private static final Double DEFAULT_META_01 = 1D;
    private static final Double UPDATED_META_01 = 2D;

    private static final Double DEFAULT_META_02 = 1D;
    private static final Double UPDATED_META_02 = 2D;

    private static final Double DEFAULT_META_03 = 1D;
    private static final Double UPDATED_META_03 = 2D;

    private static final Double DEFAULT_META_04 = 1D;
    private static final Double UPDATED_META_04 = 2D;

    private static final Double DEFAULT_META_05 = 1D;
    private static final Double UPDATED_META_05 = 2D;

    private static final Double DEFAULT_META_06 = 1D;
    private static final Double UPDATED_META_06 = 2D;

    private static final Double DEFAULT_META_07 = 1D;
    private static final Double UPDATED_META_07 = 2D;

    private static final Double DEFAULT_META_08 = 1D;
    private static final Double UPDATED_META_08 = 2D;

    private static final Double DEFAULT_META_09 = 1D;
    private static final Double UPDATED_META_09 = 2D;

    private static final Double DEFAULT_META_10 = 1D;
    private static final Double UPDATED_META_10 = 2D;

    private static final Double DEFAULT_META_11 = 1D;
    private static final Double UPDATED_META_11 = 2D;

    private static final Double DEFAULT_META_12 = 1D;
    private static final Double UPDATED_META_12 = 2D;

    private static final Double DEFAULT_MEDICAO_01 = 1D;
    private static final Double UPDATED_MEDICAO_01 = 2D;

    private static final Double DEFAULT_MEDICAO_02 = 1D;
    private static final Double UPDATED_MEDICAO_02 = 2D;

    private static final Double DEFAULT_MEDICAO_03 = 1D;
    private static final Double UPDATED_MEDICAO_03 = 2D;

    private static final Double DEFAULT_MEDICAO_04 = 1D;
    private static final Double UPDATED_MEDICAO_04 = 2D;

    private static final Double DEFAULT_MEDICAO_05 = 1D;
    private static final Double UPDATED_MEDICAO_05 = 2D;

    private static final Double DEFAULT_MEDICAO_06 = 1D;
    private static final Double UPDATED_MEDICAO_06 = 2D;

    private static final Double DEFAULT_MEDICAO_07 = 1D;
    private static final Double UPDATED_MEDICAO_07 = 2D;

    private static final Double DEFAULT_MEDICAO_08 = 1D;
    private static final Double UPDATED_MEDICAO_08 = 2D;

    private static final Double DEFAULT_MEDICAO_09 = 1D;
    private static final Double UPDATED_MEDICAO_09 = 2D;

    private static final Double DEFAULT_MEDICAO_10 = 1D;
    private static final Double UPDATED_MEDICAO_10 = 2D;

    private static final Double DEFAULT_MEDICAO_11 = 1D;
    private static final Double UPDATED_MEDICAO_11 = 2D;

    private static final Double DEFAULT_MEDICAO_12 = 1D;
    private static final Double UPDATED_MEDICAO_12 = 2D;

    private static final String ENTITY_API_URL = "/api/indicador-metas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private IndicadorMetaRepository indicadorMetaRepository;

    @Autowired
    private IndicadorMetaMapper indicadorMetaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIndicadorMetaMockMvc;

    private IndicadorMeta indicadorMeta;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IndicadorMeta createEntity(EntityManager em) {
        IndicadorMeta indicadorMeta = new IndicadorMeta()
            .frequencia(DEFAULT_FREQUENCIA)
            .anoIndicador(DEFAULT_ANO_INDICADOR)
            .meta01(DEFAULT_META_01)
            .meta02(DEFAULT_META_02)
            .meta03(DEFAULT_META_03)
            .meta04(DEFAULT_META_04)
            .meta05(DEFAULT_META_05)
            .meta06(DEFAULT_META_06)
            .meta07(DEFAULT_META_07)
            .meta08(DEFAULT_META_08)
            .meta09(DEFAULT_META_09)
            .meta10(DEFAULT_META_10)
            .meta11(DEFAULT_META_11)
            .meta12(DEFAULT_META_12)
            .medicao01(DEFAULT_MEDICAO_01)
            .medicao02(DEFAULT_MEDICAO_02)
            .medicao03(DEFAULT_MEDICAO_03)
            .medicao04(DEFAULT_MEDICAO_04)
            .medicao05(DEFAULT_MEDICAO_05)
            .medicao06(DEFAULT_MEDICAO_06)
            .medicao07(DEFAULT_MEDICAO_07)
            .medicao08(DEFAULT_MEDICAO_08)
            .medicao09(DEFAULT_MEDICAO_09)
            .medicao10(DEFAULT_MEDICAO_10)
            .medicao11(DEFAULT_MEDICAO_11)
            .medicao12(DEFAULT_MEDICAO_12);
        return indicadorMeta;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IndicadorMeta createUpdatedEntity(EntityManager em) {
        IndicadorMeta indicadorMeta = new IndicadorMeta()
            .frequencia(UPDATED_FREQUENCIA)
            .anoIndicador(UPDATED_ANO_INDICADOR)
            .meta01(UPDATED_META_01)
            .meta02(UPDATED_META_02)
            .meta03(UPDATED_META_03)
            .meta04(UPDATED_META_04)
            .meta05(UPDATED_META_05)
            .meta06(UPDATED_META_06)
            .meta07(UPDATED_META_07)
            .meta08(UPDATED_META_08)
            .meta09(UPDATED_META_09)
            .meta10(UPDATED_META_10)
            .meta11(UPDATED_META_11)
            .meta12(UPDATED_META_12)
            .medicao01(UPDATED_MEDICAO_01)
            .medicao02(UPDATED_MEDICAO_02)
            .medicao03(UPDATED_MEDICAO_03)
            .medicao04(UPDATED_MEDICAO_04)
            .medicao05(UPDATED_MEDICAO_05)
            .medicao06(UPDATED_MEDICAO_06)
            .medicao07(UPDATED_MEDICAO_07)
            .medicao08(UPDATED_MEDICAO_08)
            .medicao09(UPDATED_MEDICAO_09)
            .medicao10(UPDATED_MEDICAO_10)
            .medicao11(UPDATED_MEDICAO_11)
            .medicao12(UPDATED_MEDICAO_12);
        return indicadorMeta;
    }

    @BeforeEach
    public void initTest() {
        indicadorMeta = createEntity(em);
    }

    @Test
    @Transactional
    void createIndicadorMeta() throws Exception {
        int databaseSizeBeforeCreate = indicadorMetaRepository.findAll().size();
        // Create the IndicadorMeta
        IndicadorMetaDTO indicadorMetaDTO = indicadorMetaMapper.toDto(indicadorMeta);
        restIndicadorMetaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indicadorMetaDTO))
            )
            .andExpect(status().isCreated());

        // Validate the IndicadorMeta in the database
        List<IndicadorMeta> indicadorMetaList = indicadorMetaRepository.findAll();
        assertThat(indicadorMetaList).hasSize(databaseSizeBeforeCreate + 1);
        IndicadorMeta testIndicadorMeta = indicadorMetaList.get(indicadorMetaList.size() - 1);
        assertThat(testIndicadorMeta.getFrequencia()).isEqualTo(DEFAULT_FREQUENCIA);
        assertThat(testIndicadorMeta.getAnoIndicador()).isEqualTo(DEFAULT_ANO_INDICADOR);
        assertThat(testIndicadorMeta.getMeta01()).isEqualTo(DEFAULT_META_01);
        assertThat(testIndicadorMeta.getMeta02()).isEqualTo(DEFAULT_META_02);
        assertThat(testIndicadorMeta.getMeta03()).isEqualTo(DEFAULT_META_03);
        assertThat(testIndicadorMeta.getMeta04()).isEqualTo(DEFAULT_META_04);
        assertThat(testIndicadorMeta.getMeta05()).isEqualTo(DEFAULT_META_05);
        assertThat(testIndicadorMeta.getMeta06()).isEqualTo(DEFAULT_META_06);
        assertThat(testIndicadorMeta.getMeta07()).isEqualTo(DEFAULT_META_07);
        assertThat(testIndicadorMeta.getMeta08()).isEqualTo(DEFAULT_META_08);
        assertThat(testIndicadorMeta.getMeta09()).isEqualTo(DEFAULT_META_09);
        assertThat(testIndicadorMeta.getMeta10()).isEqualTo(DEFAULT_META_10);
        assertThat(testIndicadorMeta.getMeta11()).isEqualTo(DEFAULT_META_11);
        assertThat(testIndicadorMeta.getMeta12()).isEqualTo(DEFAULT_META_12);
        assertThat(testIndicadorMeta.getMedicao01()).isEqualTo(DEFAULT_MEDICAO_01);
        assertThat(testIndicadorMeta.getMedicao02()).isEqualTo(DEFAULT_MEDICAO_02);
        assertThat(testIndicadorMeta.getMedicao03()).isEqualTo(DEFAULT_MEDICAO_03);
        assertThat(testIndicadorMeta.getMedicao04()).isEqualTo(DEFAULT_MEDICAO_04);
        assertThat(testIndicadorMeta.getMedicao05()).isEqualTo(DEFAULT_MEDICAO_05);
        assertThat(testIndicadorMeta.getMedicao06()).isEqualTo(DEFAULT_MEDICAO_06);
        assertThat(testIndicadorMeta.getMedicao07()).isEqualTo(DEFAULT_MEDICAO_07);
        assertThat(testIndicadorMeta.getMedicao08()).isEqualTo(DEFAULT_MEDICAO_08);
        assertThat(testIndicadorMeta.getMedicao09()).isEqualTo(DEFAULT_MEDICAO_09);
        assertThat(testIndicadorMeta.getMedicao10()).isEqualTo(DEFAULT_MEDICAO_10);
        assertThat(testIndicadorMeta.getMedicao11()).isEqualTo(DEFAULT_MEDICAO_11);
        assertThat(testIndicadorMeta.getMedicao12()).isEqualTo(DEFAULT_MEDICAO_12);
    }

    @Test
    @Transactional
    void createIndicadorMetaWithExistingId() throws Exception {
        // Create the IndicadorMeta with an existing ID
        indicadorMeta.setId(1L);
        IndicadorMetaDTO indicadorMetaDTO = indicadorMetaMapper.toDto(indicadorMeta);

        int databaseSizeBeforeCreate = indicadorMetaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restIndicadorMetaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indicadorMetaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IndicadorMeta in the database
        List<IndicadorMeta> indicadorMetaList = indicadorMetaRepository.findAll();
        assertThat(indicadorMetaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllIndicadorMetas() throws Exception {
        // Initialize the database
        indicadorMetaRepository.saveAndFlush(indicadorMeta);

        // Get all the indicadorMetaList
        restIndicadorMetaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(indicadorMeta.getId().intValue())))
            .andExpect(jsonPath("$.[*].frequencia").value(hasItem(DEFAULT_FREQUENCIA.toString())))
            .andExpect(jsonPath("$.[*].anoIndicador").value(hasItem(DEFAULT_ANO_INDICADOR)))
            .andExpect(jsonPath("$.[*].meta01").value(hasItem(DEFAULT_META_01.doubleValue())))
            .andExpect(jsonPath("$.[*].meta02").value(hasItem(DEFAULT_META_02.doubleValue())))
            .andExpect(jsonPath("$.[*].meta03").value(hasItem(DEFAULT_META_03.doubleValue())))
            .andExpect(jsonPath("$.[*].meta04").value(hasItem(DEFAULT_META_04.doubleValue())))
            .andExpect(jsonPath("$.[*].meta05").value(hasItem(DEFAULT_META_05.doubleValue())))
            .andExpect(jsonPath("$.[*].meta06").value(hasItem(DEFAULT_META_06.doubleValue())))
            .andExpect(jsonPath("$.[*].meta07").value(hasItem(DEFAULT_META_07.doubleValue())))
            .andExpect(jsonPath("$.[*].meta08").value(hasItem(DEFAULT_META_08.doubleValue())))
            .andExpect(jsonPath("$.[*].meta09").value(hasItem(DEFAULT_META_09.doubleValue())))
            .andExpect(jsonPath("$.[*].meta10").value(hasItem(DEFAULT_META_10.doubleValue())))
            .andExpect(jsonPath("$.[*].meta11").value(hasItem(DEFAULT_META_11.doubleValue())))
            .andExpect(jsonPath("$.[*].meta12").value(hasItem(DEFAULT_META_12.doubleValue())))
            .andExpect(jsonPath("$.[*].medicao01").value(hasItem(DEFAULT_MEDICAO_01.doubleValue())))
            .andExpect(jsonPath("$.[*].medicao02").value(hasItem(DEFAULT_MEDICAO_02.doubleValue())))
            .andExpect(jsonPath("$.[*].medicao03").value(hasItem(DEFAULT_MEDICAO_03.doubleValue())))
            .andExpect(jsonPath("$.[*].medicao04").value(hasItem(DEFAULT_MEDICAO_04.doubleValue())))
            .andExpect(jsonPath("$.[*].medicao05").value(hasItem(DEFAULT_MEDICAO_05.doubleValue())))
            .andExpect(jsonPath("$.[*].medicao06").value(hasItem(DEFAULT_MEDICAO_06.doubleValue())))
            .andExpect(jsonPath("$.[*].medicao07").value(hasItem(DEFAULT_MEDICAO_07.doubleValue())))
            .andExpect(jsonPath("$.[*].medicao08").value(hasItem(DEFAULT_MEDICAO_08.doubleValue())))
            .andExpect(jsonPath("$.[*].medicao09").value(hasItem(DEFAULT_MEDICAO_09.doubleValue())))
            .andExpect(jsonPath("$.[*].medicao10").value(hasItem(DEFAULT_MEDICAO_10.doubleValue())))
            .andExpect(jsonPath("$.[*].medicao11").value(hasItem(DEFAULT_MEDICAO_11.doubleValue())))
            .andExpect(jsonPath("$.[*].medicao12").value(hasItem(DEFAULT_MEDICAO_12.doubleValue())));
    }

    @Test
    @Transactional
    void getIndicadorMeta() throws Exception {
        // Initialize the database
        indicadorMetaRepository.saveAndFlush(indicadorMeta);

        // Get the indicadorMeta
        restIndicadorMetaMockMvc
            .perform(get(ENTITY_API_URL_ID, indicadorMeta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(indicadorMeta.getId().intValue()))
            .andExpect(jsonPath("$.frequencia").value(DEFAULT_FREQUENCIA.toString()))
            .andExpect(jsonPath("$.anoIndicador").value(DEFAULT_ANO_INDICADOR))
            .andExpect(jsonPath("$.meta01").value(DEFAULT_META_01.doubleValue()))
            .andExpect(jsonPath("$.meta02").value(DEFAULT_META_02.doubleValue()))
            .andExpect(jsonPath("$.meta03").value(DEFAULT_META_03.doubleValue()))
            .andExpect(jsonPath("$.meta04").value(DEFAULT_META_04.doubleValue()))
            .andExpect(jsonPath("$.meta05").value(DEFAULT_META_05.doubleValue()))
            .andExpect(jsonPath("$.meta06").value(DEFAULT_META_06.doubleValue()))
            .andExpect(jsonPath("$.meta07").value(DEFAULT_META_07.doubleValue()))
            .andExpect(jsonPath("$.meta08").value(DEFAULT_META_08.doubleValue()))
            .andExpect(jsonPath("$.meta09").value(DEFAULT_META_09.doubleValue()))
            .andExpect(jsonPath("$.meta10").value(DEFAULT_META_10.doubleValue()))
            .andExpect(jsonPath("$.meta11").value(DEFAULT_META_11.doubleValue()))
            .andExpect(jsonPath("$.meta12").value(DEFAULT_META_12.doubleValue()))
            .andExpect(jsonPath("$.medicao01").value(DEFAULT_MEDICAO_01.doubleValue()))
            .andExpect(jsonPath("$.medicao02").value(DEFAULT_MEDICAO_02.doubleValue()))
            .andExpect(jsonPath("$.medicao03").value(DEFAULT_MEDICAO_03.doubleValue()))
            .andExpect(jsonPath("$.medicao04").value(DEFAULT_MEDICAO_04.doubleValue()))
            .andExpect(jsonPath("$.medicao05").value(DEFAULT_MEDICAO_05.doubleValue()))
            .andExpect(jsonPath("$.medicao06").value(DEFAULT_MEDICAO_06.doubleValue()))
            .andExpect(jsonPath("$.medicao07").value(DEFAULT_MEDICAO_07.doubleValue()))
            .andExpect(jsonPath("$.medicao08").value(DEFAULT_MEDICAO_08.doubleValue()))
            .andExpect(jsonPath("$.medicao09").value(DEFAULT_MEDICAO_09.doubleValue()))
            .andExpect(jsonPath("$.medicao10").value(DEFAULT_MEDICAO_10.doubleValue()))
            .andExpect(jsonPath("$.medicao11").value(DEFAULT_MEDICAO_11.doubleValue()))
            .andExpect(jsonPath("$.medicao12").value(DEFAULT_MEDICAO_12.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingIndicadorMeta() throws Exception {
        // Get the indicadorMeta
        restIndicadorMetaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingIndicadorMeta() throws Exception {
        // Initialize the database
        indicadorMetaRepository.saveAndFlush(indicadorMeta);

        int databaseSizeBeforeUpdate = indicadorMetaRepository.findAll().size();

        // Update the indicadorMeta
        IndicadorMeta updatedIndicadorMeta = indicadorMetaRepository.findById(indicadorMeta.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedIndicadorMeta are not directly saved in db
        em.detach(updatedIndicadorMeta);
        updatedIndicadorMeta
            .frequencia(UPDATED_FREQUENCIA)
            .anoIndicador(UPDATED_ANO_INDICADOR)
            .meta01(UPDATED_META_01)
            .meta02(UPDATED_META_02)
            .meta03(UPDATED_META_03)
            .meta04(UPDATED_META_04)
            .meta05(UPDATED_META_05)
            .meta06(UPDATED_META_06)
            .meta07(UPDATED_META_07)
            .meta08(UPDATED_META_08)
            .meta09(UPDATED_META_09)
            .meta10(UPDATED_META_10)
            .meta11(UPDATED_META_11)
            .meta12(UPDATED_META_12)
            .medicao01(UPDATED_MEDICAO_01)
            .medicao02(UPDATED_MEDICAO_02)
            .medicao03(UPDATED_MEDICAO_03)
            .medicao04(UPDATED_MEDICAO_04)
            .medicao05(UPDATED_MEDICAO_05)
            .medicao06(UPDATED_MEDICAO_06)
            .medicao07(UPDATED_MEDICAO_07)
            .medicao08(UPDATED_MEDICAO_08)
            .medicao09(UPDATED_MEDICAO_09)
            .medicao10(UPDATED_MEDICAO_10)
            .medicao11(UPDATED_MEDICAO_11)
            .medicao12(UPDATED_MEDICAO_12);
        IndicadorMetaDTO indicadorMetaDTO = indicadorMetaMapper.toDto(updatedIndicadorMeta);

        restIndicadorMetaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, indicadorMetaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(indicadorMetaDTO))
            )
            .andExpect(status().isOk());

        // Validate the IndicadorMeta in the database
        List<IndicadorMeta> indicadorMetaList = indicadorMetaRepository.findAll();
        assertThat(indicadorMetaList).hasSize(databaseSizeBeforeUpdate);
        IndicadorMeta testIndicadorMeta = indicadorMetaList.get(indicadorMetaList.size() - 1);
        assertThat(testIndicadorMeta.getFrequencia()).isEqualTo(UPDATED_FREQUENCIA);
        assertThat(testIndicadorMeta.getAnoIndicador()).isEqualTo(UPDATED_ANO_INDICADOR);
        assertThat(testIndicadorMeta.getMeta01()).isEqualTo(UPDATED_META_01);
        assertThat(testIndicadorMeta.getMeta02()).isEqualTo(UPDATED_META_02);
        assertThat(testIndicadorMeta.getMeta03()).isEqualTo(UPDATED_META_03);
        assertThat(testIndicadorMeta.getMeta04()).isEqualTo(UPDATED_META_04);
        assertThat(testIndicadorMeta.getMeta05()).isEqualTo(UPDATED_META_05);
        assertThat(testIndicadorMeta.getMeta06()).isEqualTo(UPDATED_META_06);
        assertThat(testIndicadorMeta.getMeta07()).isEqualTo(UPDATED_META_07);
        assertThat(testIndicadorMeta.getMeta08()).isEqualTo(UPDATED_META_08);
        assertThat(testIndicadorMeta.getMeta09()).isEqualTo(UPDATED_META_09);
        assertThat(testIndicadorMeta.getMeta10()).isEqualTo(UPDATED_META_10);
        assertThat(testIndicadorMeta.getMeta11()).isEqualTo(UPDATED_META_11);
        assertThat(testIndicadorMeta.getMeta12()).isEqualTo(UPDATED_META_12);
        assertThat(testIndicadorMeta.getMedicao01()).isEqualTo(UPDATED_MEDICAO_01);
        assertThat(testIndicadorMeta.getMedicao02()).isEqualTo(UPDATED_MEDICAO_02);
        assertThat(testIndicadorMeta.getMedicao03()).isEqualTo(UPDATED_MEDICAO_03);
        assertThat(testIndicadorMeta.getMedicao04()).isEqualTo(UPDATED_MEDICAO_04);
        assertThat(testIndicadorMeta.getMedicao05()).isEqualTo(UPDATED_MEDICAO_05);
        assertThat(testIndicadorMeta.getMedicao06()).isEqualTo(UPDATED_MEDICAO_06);
        assertThat(testIndicadorMeta.getMedicao07()).isEqualTo(UPDATED_MEDICAO_07);
        assertThat(testIndicadorMeta.getMedicao08()).isEqualTo(UPDATED_MEDICAO_08);
        assertThat(testIndicadorMeta.getMedicao09()).isEqualTo(UPDATED_MEDICAO_09);
        assertThat(testIndicadorMeta.getMedicao10()).isEqualTo(UPDATED_MEDICAO_10);
        assertThat(testIndicadorMeta.getMedicao11()).isEqualTo(UPDATED_MEDICAO_11);
        assertThat(testIndicadorMeta.getMedicao12()).isEqualTo(UPDATED_MEDICAO_12);
    }

    @Test
    @Transactional
    void putNonExistingIndicadorMeta() throws Exception {
        int databaseSizeBeforeUpdate = indicadorMetaRepository.findAll().size();
        indicadorMeta.setId(longCount.incrementAndGet());

        // Create the IndicadorMeta
        IndicadorMetaDTO indicadorMetaDTO = indicadorMetaMapper.toDto(indicadorMeta);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIndicadorMetaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, indicadorMetaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(indicadorMetaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IndicadorMeta in the database
        List<IndicadorMeta> indicadorMetaList = indicadorMetaRepository.findAll();
        assertThat(indicadorMetaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchIndicadorMeta() throws Exception {
        int databaseSizeBeforeUpdate = indicadorMetaRepository.findAll().size();
        indicadorMeta.setId(longCount.incrementAndGet());

        // Create the IndicadorMeta
        IndicadorMetaDTO indicadorMetaDTO = indicadorMetaMapper.toDto(indicadorMeta);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndicadorMetaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(indicadorMetaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IndicadorMeta in the database
        List<IndicadorMeta> indicadorMetaList = indicadorMetaRepository.findAll();
        assertThat(indicadorMetaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamIndicadorMeta() throws Exception {
        int databaseSizeBeforeUpdate = indicadorMetaRepository.findAll().size();
        indicadorMeta.setId(longCount.incrementAndGet());

        // Create the IndicadorMeta
        IndicadorMetaDTO indicadorMetaDTO = indicadorMetaMapper.toDto(indicadorMeta);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndicadorMetaMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indicadorMetaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the IndicadorMeta in the database
        List<IndicadorMeta> indicadorMetaList = indicadorMetaRepository.findAll();
        assertThat(indicadorMetaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateIndicadorMetaWithPatch() throws Exception {
        // Initialize the database
        indicadorMetaRepository.saveAndFlush(indicadorMeta);

        int databaseSizeBeforeUpdate = indicadorMetaRepository.findAll().size();

        // Update the indicadorMeta using partial update
        IndicadorMeta partialUpdatedIndicadorMeta = new IndicadorMeta();
        partialUpdatedIndicadorMeta.setId(indicadorMeta.getId());

        partialUpdatedIndicadorMeta
            .frequencia(UPDATED_FREQUENCIA)
            .meta01(UPDATED_META_01)
            .meta02(UPDATED_META_02)
            .meta03(UPDATED_META_03)
            .meta08(UPDATED_META_08)
            .meta09(UPDATED_META_09)
            .meta12(UPDATED_META_12)
            .medicao01(UPDATED_MEDICAO_01)
            .medicao02(UPDATED_MEDICAO_02)
            .medicao03(UPDATED_MEDICAO_03)
            .medicao04(UPDATED_MEDICAO_04)
            .medicao09(UPDATED_MEDICAO_09);

        restIndicadorMetaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIndicadorMeta.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIndicadorMeta))
            )
            .andExpect(status().isOk());

        // Validate the IndicadorMeta in the database
        List<IndicadorMeta> indicadorMetaList = indicadorMetaRepository.findAll();
        assertThat(indicadorMetaList).hasSize(databaseSizeBeforeUpdate);
        IndicadorMeta testIndicadorMeta = indicadorMetaList.get(indicadorMetaList.size() - 1);
        assertThat(testIndicadorMeta.getFrequencia()).isEqualTo(UPDATED_FREQUENCIA);
        assertThat(testIndicadorMeta.getAnoIndicador()).isEqualTo(DEFAULT_ANO_INDICADOR);
        assertThat(testIndicadorMeta.getMeta01()).isEqualTo(UPDATED_META_01);
        assertThat(testIndicadorMeta.getMeta02()).isEqualTo(UPDATED_META_02);
        assertThat(testIndicadorMeta.getMeta03()).isEqualTo(UPDATED_META_03);
        assertThat(testIndicadorMeta.getMeta04()).isEqualTo(DEFAULT_META_04);
        assertThat(testIndicadorMeta.getMeta05()).isEqualTo(DEFAULT_META_05);
        assertThat(testIndicadorMeta.getMeta06()).isEqualTo(DEFAULT_META_06);
        assertThat(testIndicadorMeta.getMeta07()).isEqualTo(DEFAULT_META_07);
        assertThat(testIndicadorMeta.getMeta08()).isEqualTo(UPDATED_META_08);
        assertThat(testIndicadorMeta.getMeta09()).isEqualTo(UPDATED_META_09);
        assertThat(testIndicadorMeta.getMeta10()).isEqualTo(DEFAULT_META_10);
        assertThat(testIndicadorMeta.getMeta11()).isEqualTo(DEFAULT_META_11);
        assertThat(testIndicadorMeta.getMeta12()).isEqualTo(UPDATED_META_12);
        assertThat(testIndicadorMeta.getMedicao01()).isEqualTo(UPDATED_MEDICAO_01);
        assertThat(testIndicadorMeta.getMedicao02()).isEqualTo(UPDATED_MEDICAO_02);
        assertThat(testIndicadorMeta.getMedicao03()).isEqualTo(UPDATED_MEDICAO_03);
        assertThat(testIndicadorMeta.getMedicao04()).isEqualTo(UPDATED_MEDICAO_04);
        assertThat(testIndicadorMeta.getMedicao05()).isEqualTo(DEFAULT_MEDICAO_05);
        assertThat(testIndicadorMeta.getMedicao06()).isEqualTo(DEFAULT_MEDICAO_06);
        assertThat(testIndicadorMeta.getMedicao07()).isEqualTo(DEFAULT_MEDICAO_07);
        assertThat(testIndicadorMeta.getMedicao08()).isEqualTo(DEFAULT_MEDICAO_08);
        assertThat(testIndicadorMeta.getMedicao09()).isEqualTo(UPDATED_MEDICAO_09);
        assertThat(testIndicadorMeta.getMedicao10()).isEqualTo(DEFAULT_MEDICAO_10);
        assertThat(testIndicadorMeta.getMedicao11()).isEqualTo(DEFAULT_MEDICAO_11);
        assertThat(testIndicadorMeta.getMedicao12()).isEqualTo(DEFAULT_MEDICAO_12);
    }

    @Test
    @Transactional
    void fullUpdateIndicadorMetaWithPatch() throws Exception {
        // Initialize the database
        indicadorMetaRepository.saveAndFlush(indicadorMeta);

        int databaseSizeBeforeUpdate = indicadorMetaRepository.findAll().size();

        // Update the indicadorMeta using partial update
        IndicadorMeta partialUpdatedIndicadorMeta = new IndicadorMeta();
        partialUpdatedIndicadorMeta.setId(indicadorMeta.getId());

        partialUpdatedIndicadorMeta
            .frequencia(UPDATED_FREQUENCIA)
            .anoIndicador(UPDATED_ANO_INDICADOR)
            .meta01(UPDATED_META_01)
            .meta02(UPDATED_META_02)
            .meta03(UPDATED_META_03)
            .meta04(UPDATED_META_04)
            .meta05(UPDATED_META_05)
            .meta06(UPDATED_META_06)
            .meta07(UPDATED_META_07)
            .meta08(UPDATED_META_08)
            .meta09(UPDATED_META_09)
            .meta10(UPDATED_META_10)
            .meta11(UPDATED_META_11)
            .meta12(UPDATED_META_12)
            .medicao01(UPDATED_MEDICAO_01)
            .medicao02(UPDATED_MEDICAO_02)
            .medicao03(UPDATED_MEDICAO_03)
            .medicao04(UPDATED_MEDICAO_04)
            .medicao05(UPDATED_MEDICAO_05)
            .medicao06(UPDATED_MEDICAO_06)
            .medicao07(UPDATED_MEDICAO_07)
            .medicao08(UPDATED_MEDICAO_08)
            .medicao09(UPDATED_MEDICAO_09)
            .medicao10(UPDATED_MEDICAO_10)
            .medicao11(UPDATED_MEDICAO_11)
            .medicao12(UPDATED_MEDICAO_12);

        restIndicadorMetaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIndicadorMeta.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIndicadorMeta))
            )
            .andExpect(status().isOk());

        // Validate the IndicadorMeta in the database
        List<IndicadorMeta> indicadorMetaList = indicadorMetaRepository.findAll();
        assertThat(indicadorMetaList).hasSize(databaseSizeBeforeUpdate);
        IndicadorMeta testIndicadorMeta = indicadorMetaList.get(indicadorMetaList.size() - 1);
        assertThat(testIndicadorMeta.getFrequencia()).isEqualTo(UPDATED_FREQUENCIA);
        assertThat(testIndicadorMeta.getAnoIndicador()).isEqualTo(UPDATED_ANO_INDICADOR);
        assertThat(testIndicadorMeta.getMeta01()).isEqualTo(UPDATED_META_01);
        assertThat(testIndicadorMeta.getMeta02()).isEqualTo(UPDATED_META_02);
        assertThat(testIndicadorMeta.getMeta03()).isEqualTo(UPDATED_META_03);
        assertThat(testIndicadorMeta.getMeta04()).isEqualTo(UPDATED_META_04);
        assertThat(testIndicadorMeta.getMeta05()).isEqualTo(UPDATED_META_05);
        assertThat(testIndicadorMeta.getMeta06()).isEqualTo(UPDATED_META_06);
        assertThat(testIndicadorMeta.getMeta07()).isEqualTo(UPDATED_META_07);
        assertThat(testIndicadorMeta.getMeta08()).isEqualTo(UPDATED_META_08);
        assertThat(testIndicadorMeta.getMeta09()).isEqualTo(UPDATED_META_09);
        assertThat(testIndicadorMeta.getMeta10()).isEqualTo(UPDATED_META_10);
        assertThat(testIndicadorMeta.getMeta11()).isEqualTo(UPDATED_META_11);
        assertThat(testIndicadorMeta.getMeta12()).isEqualTo(UPDATED_META_12);
        assertThat(testIndicadorMeta.getMedicao01()).isEqualTo(UPDATED_MEDICAO_01);
        assertThat(testIndicadorMeta.getMedicao02()).isEqualTo(UPDATED_MEDICAO_02);
        assertThat(testIndicadorMeta.getMedicao03()).isEqualTo(UPDATED_MEDICAO_03);
        assertThat(testIndicadorMeta.getMedicao04()).isEqualTo(UPDATED_MEDICAO_04);
        assertThat(testIndicadorMeta.getMedicao05()).isEqualTo(UPDATED_MEDICAO_05);
        assertThat(testIndicadorMeta.getMedicao06()).isEqualTo(UPDATED_MEDICAO_06);
        assertThat(testIndicadorMeta.getMedicao07()).isEqualTo(UPDATED_MEDICAO_07);
        assertThat(testIndicadorMeta.getMedicao08()).isEqualTo(UPDATED_MEDICAO_08);
        assertThat(testIndicadorMeta.getMedicao09()).isEqualTo(UPDATED_MEDICAO_09);
        assertThat(testIndicadorMeta.getMedicao10()).isEqualTo(UPDATED_MEDICAO_10);
        assertThat(testIndicadorMeta.getMedicao11()).isEqualTo(UPDATED_MEDICAO_11);
        assertThat(testIndicadorMeta.getMedicao12()).isEqualTo(UPDATED_MEDICAO_12);
    }

    @Test
    @Transactional
    void patchNonExistingIndicadorMeta() throws Exception {
        int databaseSizeBeforeUpdate = indicadorMetaRepository.findAll().size();
        indicadorMeta.setId(longCount.incrementAndGet());

        // Create the IndicadorMeta
        IndicadorMetaDTO indicadorMetaDTO = indicadorMetaMapper.toDto(indicadorMeta);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIndicadorMetaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, indicadorMetaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(indicadorMetaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IndicadorMeta in the database
        List<IndicadorMeta> indicadorMetaList = indicadorMetaRepository.findAll();
        assertThat(indicadorMetaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchIndicadorMeta() throws Exception {
        int databaseSizeBeforeUpdate = indicadorMetaRepository.findAll().size();
        indicadorMeta.setId(longCount.incrementAndGet());

        // Create the IndicadorMeta
        IndicadorMetaDTO indicadorMetaDTO = indicadorMetaMapper.toDto(indicadorMeta);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndicadorMetaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(indicadorMetaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IndicadorMeta in the database
        List<IndicadorMeta> indicadorMetaList = indicadorMetaRepository.findAll();
        assertThat(indicadorMetaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamIndicadorMeta() throws Exception {
        int databaseSizeBeforeUpdate = indicadorMetaRepository.findAll().size();
        indicadorMeta.setId(longCount.incrementAndGet());

        // Create the IndicadorMeta
        IndicadorMetaDTO indicadorMetaDTO = indicadorMetaMapper.toDto(indicadorMeta);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndicadorMetaMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(indicadorMetaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the IndicadorMeta in the database
        List<IndicadorMeta> indicadorMetaList = indicadorMetaRepository.findAll();
        assertThat(indicadorMetaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteIndicadorMeta() throws Exception {
        // Initialize the database
        indicadorMetaRepository.saveAndFlush(indicadorMeta);

        int databaseSizeBeforeDelete = indicadorMetaRepository.findAll().size();

        // Delete the indicadorMeta
        restIndicadorMetaMockMvc
            .perform(delete(ENTITY_API_URL_ID, indicadorMeta.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<IndicadorMeta> indicadorMetaList = indicadorMetaRepository.findAll();
        assertThat(indicadorMetaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
