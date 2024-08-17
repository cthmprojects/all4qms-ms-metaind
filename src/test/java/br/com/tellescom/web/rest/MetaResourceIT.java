package br.com.tellescom.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.tellescom.IntegrationTest;
import br.com.tellescom.domain.Meta;
import br.com.tellescom.domain.MetaObjetivo;
import br.com.tellescom.domain.MetaRecurso;
import br.com.tellescom.domain.MetaResultado;
import br.com.tellescom.domain.enumeration.EnumTemporal;
import br.com.tellescom.domain.enumeration.EnumTemporal;
import br.com.tellescom.repository.MetaRepository;
import br.com.tellescom.service.MetaService;
import br.com.tellescom.service.dto.MetaDTO;
import br.com.tellescom.service.mapper.MetaMapper;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link MetaResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class MetaResourceIT {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String DEFAULT_INDICADOR = "AAAAAAAAAA";
    private static final String UPDATED_INDICADOR = "BBBBBBBBBB";

    private static final String DEFAULT_MEDICAO = "AAAAAAAAAA";
    private static final String UPDATED_MEDICAO = "BBBBBBBBBB";

    private static final String DEFAULT_ACAO = "AAAAAAAAAA";
    private static final String UPDATED_ACAO = "BBBBBBBBBB";

    private static final String DEFAULT_AVALIACAO_RESULTADO = "AAAAAAAAAA";
    private static final String UPDATED_AVALIACAO_RESULTADO = "BBBBBBBBBB";

    private static final Integer DEFAULT_ID_PROCESSO = 1;
    private static final Integer UPDATED_ID_PROCESSO = 2;
    private static final Integer SMALLER_ID_PROCESSO = 1 - 1;

    private static final EnumTemporal DEFAULT_MONITORAMENTO = EnumTemporal.MENSAL;
    private static final EnumTemporal UPDATED_MONITORAMENTO = EnumTemporal.BIMESTRAL;

    private static final EnumTemporal DEFAULT_PERIODO = EnumTemporal.MENSAL;
    private static final EnumTemporal UPDATED_PERIODO = EnumTemporal.BIMESTRAL;

    private static final String ENTITY_API_URL = "/api/metas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MetaRepository metaRepository;

    @Mock
    private MetaRepository metaRepositoryMock;

    @Autowired
    private MetaMapper metaMapper;

    @Mock
    private MetaService metaServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMetaMockMvc;

    private Meta meta;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Meta createEntity(EntityManager em) {
        Meta meta = new Meta()
            .descricao(DEFAULT_DESCRICAO)
            .indicador(DEFAULT_INDICADOR)
            .medicao(DEFAULT_MEDICAO)
            .acao(DEFAULT_ACAO)
            .avaliacaoResultado(DEFAULT_AVALIACAO_RESULTADO)
            .idProcesso(DEFAULT_ID_PROCESSO)
            .monitoramento(DEFAULT_MONITORAMENTO)
            .periodo(DEFAULT_PERIODO);
        return meta;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Meta createUpdatedEntity(EntityManager em) {
        Meta meta = new Meta()
            .descricao(UPDATED_DESCRICAO)
            .indicador(UPDATED_INDICADOR)
            .medicao(UPDATED_MEDICAO)
            .acao(UPDATED_ACAO)
            .avaliacaoResultado(UPDATED_AVALIACAO_RESULTADO)
            .idProcesso(UPDATED_ID_PROCESSO)
            .monitoramento(UPDATED_MONITORAMENTO)
            .periodo(UPDATED_PERIODO);
        return meta;
    }

    @BeforeEach
    public void initTest() {
        meta = createEntity(em);
    }

    @Test
    @Transactional
    void createMeta() throws Exception {
        int databaseSizeBeforeCreate = metaRepository.findAll().size();
        // Create the Meta
        MetaDTO metaDTO = metaMapper.toDto(meta);
        restMetaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(metaDTO)))
            .andExpect(status().isCreated());

        // Validate the Meta in the database
        List<Meta> metaList = metaRepository.findAll();
        assertThat(metaList).hasSize(databaseSizeBeforeCreate + 1);
        Meta testMeta = metaList.get(metaList.size() - 1);
        assertThat(testMeta.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testMeta.getIndicador()).isEqualTo(DEFAULT_INDICADOR);
        assertThat(testMeta.getMedicao()).isEqualTo(DEFAULT_MEDICAO);
        assertThat(testMeta.getAcao()).isEqualTo(DEFAULT_ACAO);
        assertThat(testMeta.getAvaliacaoResultado()).isEqualTo(DEFAULT_AVALIACAO_RESULTADO);
        assertThat(testMeta.getIdProcesso()).isEqualTo(DEFAULT_ID_PROCESSO);
        assertThat(testMeta.getMonitoramento()).isEqualTo(DEFAULT_MONITORAMENTO);
        assertThat(testMeta.getPeriodo()).isEqualTo(DEFAULT_PERIODO);
    }

    @Test
    @Transactional
    void createMetaWithExistingId() throws Exception {
        // Create the Meta with an existing ID
        meta.setId(1L);
        MetaDTO metaDTO = metaMapper.toDto(meta);

        int databaseSizeBeforeCreate = metaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMetaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(metaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Meta in the database
        List<Meta> metaList = metaRepository.findAll();
        assertThat(metaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMetas() throws Exception {
        // Initialize the database
        metaRepository.saveAndFlush(meta);

        // Get all the metaList
        restMetaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(meta.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].indicador").value(hasItem(DEFAULT_INDICADOR)))
            .andExpect(jsonPath("$.[*].medicao").value(hasItem(DEFAULT_MEDICAO)))
            .andExpect(jsonPath("$.[*].acao").value(hasItem(DEFAULT_ACAO)))
            .andExpect(jsonPath("$.[*].avaliacaoResultado").value(hasItem(DEFAULT_AVALIACAO_RESULTADO)))
            .andExpect(jsonPath("$.[*].idProcesso").value(hasItem(DEFAULT_ID_PROCESSO)))
            .andExpect(jsonPath("$.[*].monitoramento").value(hasItem(DEFAULT_MONITORAMENTO.toString())))
            .andExpect(jsonPath("$.[*].periodo").value(hasItem(DEFAULT_PERIODO.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllMetasWithEagerRelationshipsIsEnabled() throws Exception {
        when(metaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restMetaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(metaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllMetasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(metaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restMetaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(metaRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getMeta() throws Exception {
        // Initialize the database
        metaRepository.saveAndFlush(meta);

        // Get the meta
        restMetaMockMvc
            .perform(get(ENTITY_API_URL_ID, meta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(meta.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.indicador").value(DEFAULT_INDICADOR))
            .andExpect(jsonPath("$.medicao").value(DEFAULT_MEDICAO))
            .andExpect(jsonPath("$.acao").value(DEFAULT_ACAO))
            .andExpect(jsonPath("$.avaliacaoResultado").value(DEFAULT_AVALIACAO_RESULTADO))
            .andExpect(jsonPath("$.idProcesso").value(DEFAULT_ID_PROCESSO))
            .andExpect(jsonPath("$.monitoramento").value(DEFAULT_MONITORAMENTO.toString()))
            .andExpect(jsonPath("$.periodo").value(DEFAULT_PERIODO.toString()));
    }

    @Test
    @Transactional
    void getMetasByIdFiltering() throws Exception {
        // Initialize the database
        metaRepository.saveAndFlush(meta);

        Long id = meta.getId();

        defaultMetaShouldBeFound("id.equals=" + id);
        defaultMetaShouldNotBeFound("id.notEquals=" + id);

        defaultMetaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMetaShouldNotBeFound("id.greaterThan=" + id);

        defaultMetaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMetaShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllMetasByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        metaRepository.saveAndFlush(meta);

        // Get all the metaList where descricao equals to DEFAULT_DESCRICAO
        defaultMetaShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the metaList where descricao equals to UPDATED_DESCRICAO
        defaultMetaShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllMetasByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        metaRepository.saveAndFlush(meta);

        // Get all the metaList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultMetaShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the metaList where descricao equals to UPDATED_DESCRICAO
        defaultMetaShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllMetasByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        metaRepository.saveAndFlush(meta);

        // Get all the metaList where descricao is not null
        defaultMetaShouldBeFound("descricao.specified=true");

        // Get all the metaList where descricao is null
        defaultMetaShouldNotBeFound("descricao.specified=false");
    }

    @Test
    @Transactional
    void getAllMetasByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        metaRepository.saveAndFlush(meta);

        // Get all the metaList where descricao contains DEFAULT_DESCRICAO
        defaultMetaShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the metaList where descricao contains UPDATED_DESCRICAO
        defaultMetaShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllMetasByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        metaRepository.saveAndFlush(meta);

        // Get all the metaList where descricao does not contain DEFAULT_DESCRICAO
        defaultMetaShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the metaList where descricao does not contain UPDATED_DESCRICAO
        defaultMetaShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllMetasByIndicadorIsEqualToSomething() throws Exception {
        // Initialize the database
        metaRepository.saveAndFlush(meta);

        // Get all the metaList where indicador equals to DEFAULT_INDICADOR
        defaultMetaShouldBeFound("indicador.equals=" + DEFAULT_INDICADOR);

        // Get all the metaList where indicador equals to UPDATED_INDICADOR
        defaultMetaShouldNotBeFound("indicador.equals=" + UPDATED_INDICADOR);
    }

    @Test
    @Transactional
    void getAllMetasByIndicadorIsInShouldWork() throws Exception {
        // Initialize the database
        metaRepository.saveAndFlush(meta);

        // Get all the metaList where indicador in DEFAULT_INDICADOR or UPDATED_INDICADOR
        defaultMetaShouldBeFound("indicador.in=" + DEFAULT_INDICADOR + "," + UPDATED_INDICADOR);

        // Get all the metaList where indicador equals to UPDATED_INDICADOR
        defaultMetaShouldNotBeFound("indicador.in=" + UPDATED_INDICADOR);
    }

    @Test
    @Transactional
    void getAllMetasByIndicadorIsNullOrNotNull() throws Exception {
        // Initialize the database
        metaRepository.saveAndFlush(meta);

        // Get all the metaList where indicador is not null
        defaultMetaShouldBeFound("indicador.specified=true");

        // Get all the metaList where indicador is null
        defaultMetaShouldNotBeFound("indicador.specified=false");
    }

    @Test
    @Transactional
    void getAllMetasByIndicadorContainsSomething() throws Exception {
        // Initialize the database
        metaRepository.saveAndFlush(meta);

        // Get all the metaList where indicador contains DEFAULT_INDICADOR
        defaultMetaShouldBeFound("indicador.contains=" + DEFAULT_INDICADOR);

        // Get all the metaList where indicador contains UPDATED_INDICADOR
        defaultMetaShouldNotBeFound("indicador.contains=" + UPDATED_INDICADOR);
    }

    @Test
    @Transactional
    void getAllMetasByIndicadorNotContainsSomething() throws Exception {
        // Initialize the database
        metaRepository.saveAndFlush(meta);

        // Get all the metaList where indicador does not contain DEFAULT_INDICADOR
        defaultMetaShouldNotBeFound("indicador.doesNotContain=" + DEFAULT_INDICADOR);

        // Get all the metaList where indicador does not contain UPDATED_INDICADOR
        defaultMetaShouldBeFound("indicador.doesNotContain=" + UPDATED_INDICADOR);
    }

    @Test
    @Transactional
    void getAllMetasByMedicaoIsEqualToSomething() throws Exception {
        // Initialize the database
        metaRepository.saveAndFlush(meta);

        // Get all the metaList where medicao equals to DEFAULT_MEDICAO
        defaultMetaShouldBeFound("medicao.equals=" + DEFAULT_MEDICAO);

        // Get all the metaList where medicao equals to UPDATED_MEDICAO
        defaultMetaShouldNotBeFound("medicao.equals=" + UPDATED_MEDICAO);
    }

    @Test
    @Transactional
    void getAllMetasByMedicaoIsInShouldWork() throws Exception {
        // Initialize the database
        metaRepository.saveAndFlush(meta);

        // Get all the metaList where medicao in DEFAULT_MEDICAO or UPDATED_MEDICAO
        defaultMetaShouldBeFound("medicao.in=" + DEFAULT_MEDICAO + "," + UPDATED_MEDICAO);

        // Get all the metaList where medicao equals to UPDATED_MEDICAO
        defaultMetaShouldNotBeFound("medicao.in=" + UPDATED_MEDICAO);
    }

    @Test
    @Transactional
    void getAllMetasByMedicaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        metaRepository.saveAndFlush(meta);

        // Get all the metaList where medicao is not null
        defaultMetaShouldBeFound("medicao.specified=true");

        // Get all the metaList where medicao is null
        defaultMetaShouldNotBeFound("medicao.specified=false");
    }

    @Test
    @Transactional
    void getAllMetasByMedicaoContainsSomething() throws Exception {
        // Initialize the database
        metaRepository.saveAndFlush(meta);

        // Get all the metaList where medicao contains DEFAULT_MEDICAO
        defaultMetaShouldBeFound("medicao.contains=" + DEFAULT_MEDICAO);

        // Get all the metaList where medicao contains UPDATED_MEDICAO
        defaultMetaShouldNotBeFound("medicao.contains=" + UPDATED_MEDICAO);
    }

    @Test
    @Transactional
    void getAllMetasByMedicaoNotContainsSomething() throws Exception {
        // Initialize the database
        metaRepository.saveAndFlush(meta);

        // Get all the metaList where medicao does not contain DEFAULT_MEDICAO
        defaultMetaShouldNotBeFound("medicao.doesNotContain=" + DEFAULT_MEDICAO);

        // Get all the metaList where medicao does not contain UPDATED_MEDICAO
        defaultMetaShouldBeFound("medicao.doesNotContain=" + UPDATED_MEDICAO);
    }

    @Test
    @Transactional
    void getAllMetasByAcaoIsEqualToSomething() throws Exception {
        // Initialize the database
        metaRepository.saveAndFlush(meta);

        // Get all the metaList where acao equals to DEFAULT_ACAO
        defaultMetaShouldBeFound("acao.equals=" + DEFAULT_ACAO);

        // Get all the metaList where acao equals to UPDATED_ACAO
        defaultMetaShouldNotBeFound("acao.equals=" + UPDATED_ACAO);
    }

    @Test
    @Transactional
    void getAllMetasByAcaoIsInShouldWork() throws Exception {
        // Initialize the database
        metaRepository.saveAndFlush(meta);

        // Get all the metaList where acao in DEFAULT_ACAO or UPDATED_ACAO
        defaultMetaShouldBeFound("acao.in=" + DEFAULT_ACAO + "," + UPDATED_ACAO);

        // Get all the metaList where acao equals to UPDATED_ACAO
        defaultMetaShouldNotBeFound("acao.in=" + UPDATED_ACAO);
    }

    @Test
    @Transactional
    void getAllMetasByAcaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        metaRepository.saveAndFlush(meta);

        // Get all the metaList where acao is not null
        defaultMetaShouldBeFound("acao.specified=true");

        // Get all the metaList where acao is null
        defaultMetaShouldNotBeFound("acao.specified=false");
    }

    @Test
    @Transactional
    void getAllMetasByAcaoContainsSomething() throws Exception {
        // Initialize the database
        metaRepository.saveAndFlush(meta);

        // Get all the metaList where acao contains DEFAULT_ACAO
        defaultMetaShouldBeFound("acao.contains=" + DEFAULT_ACAO);

        // Get all the metaList where acao contains UPDATED_ACAO
        defaultMetaShouldNotBeFound("acao.contains=" + UPDATED_ACAO);
    }

    @Test
    @Transactional
    void getAllMetasByAcaoNotContainsSomething() throws Exception {
        // Initialize the database
        metaRepository.saveAndFlush(meta);

        // Get all the metaList where acao does not contain DEFAULT_ACAO
        defaultMetaShouldNotBeFound("acao.doesNotContain=" + DEFAULT_ACAO);

        // Get all the metaList where acao does not contain UPDATED_ACAO
        defaultMetaShouldBeFound("acao.doesNotContain=" + UPDATED_ACAO);
    }

    @Test
    @Transactional
    void getAllMetasByAvaliacaoResultadoIsEqualToSomething() throws Exception {
        // Initialize the database
        metaRepository.saveAndFlush(meta);

        // Get all the metaList where avaliacaoResultado equals to DEFAULT_AVALIACAO_RESULTADO
        defaultMetaShouldBeFound("avaliacaoResultado.equals=" + DEFAULT_AVALIACAO_RESULTADO);

        // Get all the metaList where avaliacaoResultado equals to UPDATED_AVALIACAO_RESULTADO
        defaultMetaShouldNotBeFound("avaliacaoResultado.equals=" + UPDATED_AVALIACAO_RESULTADO);
    }

    @Test
    @Transactional
    void getAllMetasByAvaliacaoResultadoIsInShouldWork() throws Exception {
        // Initialize the database
        metaRepository.saveAndFlush(meta);

        // Get all the metaList where avaliacaoResultado in DEFAULT_AVALIACAO_RESULTADO or UPDATED_AVALIACAO_RESULTADO
        defaultMetaShouldBeFound("avaliacaoResultado.in=" + DEFAULT_AVALIACAO_RESULTADO + "," + UPDATED_AVALIACAO_RESULTADO);

        // Get all the metaList where avaliacaoResultado equals to UPDATED_AVALIACAO_RESULTADO
        defaultMetaShouldNotBeFound("avaliacaoResultado.in=" + UPDATED_AVALIACAO_RESULTADO);
    }

    @Test
    @Transactional
    void getAllMetasByAvaliacaoResultadoIsNullOrNotNull() throws Exception {
        // Initialize the database
        metaRepository.saveAndFlush(meta);

        // Get all the metaList where avaliacaoResultado is not null
        defaultMetaShouldBeFound("avaliacaoResultado.specified=true");

        // Get all the metaList where avaliacaoResultado is null
        defaultMetaShouldNotBeFound("avaliacaoResultado.specified=false");
    }

    @Test
    @Transactional
    void getAllMetasByAvaliacaoResultadoContainsSomething() throws Exception {
        // Initialize the database
        metaRepository.saveAndFlush(meta);

        // Get all the metaList where avaliacaoResultado contains DEFAULT_AVALIACAO_RESULTADO
        defaultMetaShouldBeFound("avaliacaoResultado.contains=" + DEFAULT_AVALIACAO_RESULTADO);

        // Get all the metaList where avaliacaoResultado contains UPDATED_AVALIACAO_RESULTADO
        defaultMetaShouldNotBeFound("avaliacaoResultado.contains=" + UPDATED_AVALIACAO_RESULTADO);
    }

    @Test
    @Transactional
    void getAllMetasByAvaliacaoResultadoNotContainsSomething() throws Exception {
        // Initialize the database
        metaRepository.saveAndFlush(meta);

        // Get all the metaList where avaliacaoResultado does not contain DEFAULT_AVALIACAO_RESULTADO
        defaultMetaShouldNotBeFound("avaliacaoResultado.doesNotContain=" + DEFAULT_AVALIACAO_RESULTADO);

        // Get all the metaList where avaliacaoResultado does not contain UPDATED_AVALIACAO_RESULTADO
        defaultMetaShouldBeFound("avaliacaoResultado.doesNotContain=" + UPDATED_AVALIACAO_RESULTADO);
    }

    @Test
    @Transactional
    void getAllMetasByIdProcessoIsEqualToSomething() throws Exception {
        // Initialize the database
        metaRepository.saveAndFlush(meta);

        // Get all the metaList where idProcesso equals to DEFAULT_ID_PROCESSO
        defaultMetaShouldBeFound("idProcesso.equals=" + DEFAULT_ID_PROCESSO);

        // Get all the metaList where idProcesso equals to UPDATED_ID_PROCESSO
        defaultMetaShouldNotBeFound("idProcesso.equals=" + UPDATED_ID_PROCESSO);
    }

    @Test
    @Transactional
    void getAllMetasByIdProcessoIsInShouldWork() throws Exception {
        // Initialize the database
        metaRepository.saveAndFlush(meta);

        // Get all the metaList where idProcesso in DEFAULT_ID_PROCESSO or UPDATED_ID_PROCESSO
        defaultMetaShouldBeFound("idProcesso.in=" + DEFAULT_ID_PROCESSO + "," + UPDATED_ID_PROCESSO);

        // Get all the metaList where idProcesso equals to UPDATED_ID_PROCESSO
        defaultMetaShouldNotBeFound("idProcesso.in=" + UPDATED_ID_PROCESSO);
    }

    @Test
    @Transactional
    void getAllMetasByIdProcessoIsNullOrNotNull() throws Exception {
        // Initialize the database
        metaRepository.saveAndFlush(meta);

        // Get all the metaList where idProcesso is not null
        defaultMetaShouldBeFound("idProcesso.specified=true");

        // Get all the metaList where idProcesso is null
        defaultMetaShouldNotBeFound("idProcesso.specified=false");
    }

    @Test
    @Transactional
    void getAllMetasByIdProcessoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        metaRepository.saveAndFlush(meta);

        // Get all the metaList where idProcesso is greater than or equal to DEFAULT_ID_PROCESSO
        defaultMetaShouldBeFound("idProcesso.greaterThanOrEqual=" + DEFAULT_ID_PROCESSO);

        // Get all the metaList where idProcesso is greater than or equal to UPDATED_ID_PROCESSO
        defaultMetaShouldNotBeFound("idProcesso.greaterThanOrEqual=" + UPDATED_ID_PROCESSO);
    }

    @Test
    @Transactional
    void getAllMetasByIdProcessoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        metaRepository.saveAndFlush(meta);

        // Get all the metaList where idProcesso is less than or equal to DEFAULT_ID_PROCESSO
        defaultMetaShouldBeFound("idProcesso.lessThanOrEqual=" + DEFAULT_ID_PROCESSO);

        // Get all the metaList where idProcesso is less than or equal to SMALLER_ID_PROCESSO
        defaultMetaShouldNotBeFound("idProcesso.lessThanOrEqual=" + SMALLER_ID_PROCESSO);
    }

    @Test
    @Transactional
    void getAllMetasByIdProcessoIsLessThanSomething() throws Exception {
        // Initialize the database
        metaRepository.saveAndFlush(meta);

        // Get all the metaList where idProcesso is less than DEFAULT_ID_PROCESSO
        defaultMetaShouldNotBeFound("idProcesso.lessThan=" + DEFAULT_ID_PROCESSO);

        // Get all the metaList where idProcesso is less than UPDATED_ID_PROCESSO
        defaultMetaShouldBeFound("idProcesso.lessThan=" + UPDATED_ID_PROCESSO);
    }

    @Test
    @Transactional
    void getAllMetasByIdProcessoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        metaRepository.saveAndFlush(meta);

        // Get all the metaList where idProcesso is greater than DEFAULT_ID_PROCESSO
        defaultMetaShouldNotBeFound("idProcesso.greaterThan=" + DEFAULT_ID_PROCESSO);

        // Get all the metaList where idProcesso is greater than SMALLER_ID_PROCESSO
        defaultMetaShouldBeFound("idProcesso.greaterThan=" + SMALLER_ID_PROCESSO);
    }

    @Test
    @Transactional
    void getAllMetasByMonitoramentoIsEqualToSomething() throws Exception {
        // Initialize the database
        metaRepository.saveAndFlush(meta);

        // Get all the metaList where monitoramento equals to DEFAULT_MONITORAMENTO
        defaultMetaShouldBeFound("monitoramento.equals=" + DEFAULT_MONITORAMENTO);

        // Get all the metaList where monitoramento equals to UPDATED_MONITORAMENTO
        defaultMetaShouldNotBeFound("monitoramento.equals=" + UPDATED_MONITORAMENTO);
    }

    @Test
    @Transactional
    void getAllMetasByMonitoramentoIsInShouldWork() throws Exception {
        // Initialize the database
        metaRepository.saveAndFlush(meta);

        // Get all the metaList where monitoramento in DEFAULT_MONITORAMENTO or UPDATED_MONITORAMENTO
        defaultMetaShouldBeFound("monitoramento.in=" + DEFAULT_MONITORAMENTO + "," + UPDATED_MONITORAMENTO);

        // Get all the metaList where monitoramento equals to UPDATED_MONITORAMENTO
        defaultMetaShouldNotBeFound("monitoramento.in=" + UPDATED_MONITORAMENTO);
    }

    @Test
    @Transactional
    void getAllMetasByMonitoramentoIsNullOrNotNull() throws Exception {
        // Initialize the database
        metaRepository.saveAndFlush(meta);

        // Get all the metaList where monitoramento is not null
        defaultMetaShouldBeFound("monitoramento.specified=true");

        // Get all the metaList where monitoramento is null
        defaultMetaShouldNotBeFound("monitoramento.specified=false");
    }

    @Test
    @Transactional
    void getAllMetasByPeriodoIsEqualToSomething() throws Exception {
        // Initialize the database
        metaRepository.saveAndFlush(meta);

        // Get all the metaList where periodo equals to DEFAULT_PERIODO
        defaultMetaShouldBeFound("periodo.equals=" + DEFAULT_PERIODO);

        // Get all the metaList where periodo equals to UPDATED_PERIODO
        defaultMetaShouldNotBeFound("periodo.equals=" + UPDATED_PERIODO);
    }

    @Test
    @Transactional
    void getAllMetasByPeriodoIsInShouldWork() throws Exception {
        // Initialize the database
        metaRepository.saveAndFlush(meta);

        // Get all the metaList where periodo in DEFAULT_PERIODO or UPDATED_PERIODO
        defaultMetaShouldBeFound("periodo.in=" + DEFAULT_PERIODO + "," + UPDATED_PERIODO);

        // Get all the metaList where periodo equals to UPDATED_PERIODO
        defaultMetaShouldNotBeFound("periodo.in=" + UPDATED_PERIODO);
    }

    @Test
    @Transactional
    void getAllMetasByPeriodoIsNullOrNotNull() throws Exception {
        // Initialize the database
        metaRepository.saveAndFlush(meta);

        // Get all the metaList where periodo is not null
        defaultMetaShouldBeFound("periodo.specified=true");

        // Get all the metaList where periodo is null
        defaultMetaShouldNotBeFound("periodo.specified=false");
    }

    @Test
    @Transactional
    void getAllMetasByResultadosIsEqualToSomething() throws Exception {
        MetaResultado resultados;
        if (TestUtil.findAll(em, MetaResultado.class).isEmpty()) {
            metaRepository.saveAndFlush(meta);
            resultados = MetaResultadoResourceIT.createEntity(em);
        } else {
            resultados = TestUtil.findAll(em, MetaResultado.class).get(0);
        }
        em.persist(resultados);
        em.flush();
        meta.addResultados(resultados);
        metaRepository.saveAndFlush(meta);
        Long resultadosId = resultados.getId();
        // Get all the metaList where resultados equals to resultadosId
        defaultMetaShouldBeFound("resultadosId.equals=" + resultadosId);

        // Get all the metaList where resultados equals to (resultadosId + 1)
        defaultMetaShouldNotBeFound("resultadosId.equals=" + (resultadosId + 1));
    }

    @Test
    @Transactional
    void getAllMetasByRecursosIsEqualToSomething() throws Exception {
        MetaRecurso recursos;
        if (TestUtil.findAll(em, MetaRecurso.class).isEmpty()) {
            metaRepository.saveAndFlush(meta);
            recursos = MetaRecursoResourceIT.createEntity(em);
        } else {
            recursos = TestUtil.findAll(em, MetaRecurso.class).get(0);
        }
        em.persist(recursos);
        em.flush();
        meta.addRecursos(recursos);
        metaRepository.saveAndFlush(meta);
        Long recursosId = recursos.getId();
        // Get all the metaList where recursos equals to recursosId
        defaultMetaShouldBeFound("recursosId.equals=" + recursosId);

        // Get all the metaList where recursos equals to (recursosId + 1)
        defaultMetaShouldNotBeFound("recursosId.equals=" + (recursosId + 1));
    }

    @Test
    @Transactional
    void getAllMetasByMetaObjetivoIsEqualToSomething() throws Exception {
        MetaObjetivo metaObjetivo;
        if (TestUtil.findAll(em, MetaObjetivo.class).isEmpty()) {
            metaRepository.saveAndFlush(meta);
            metaObjetivo = MetaObjetivoResourceIT.createEntity(em);
        } else {
            metaObjetivo = TestUtil.findAll(em, MetaObjetivo.class).get(0);
        }
        em.persist(metaObjetivo);
        em.flush();
        meta.setMetaObjetivo(metaObjetivo);
        metaRepository.saveAndFlush(meta);
        Long metaObjetivoId = metaObjetivo.getId();
        // Get all the metaList where metaObjetivo equals to metaObjetivoId
        defaultMetaShouldBeFound("metaObjetivoId.equals=" + metaObjetivoId);

        // Get all the metaList where metaObjetivo equals to (metaObjetivoId + 1)
        defaultMetaShouldNotBeFound("metaObjetivoId.equals=" + (metaObjetivoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMetaShouldBeFound(String filter) throws Exception {
        restMetaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(meta.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].indicador").value(hasItem(DEFAULT_INDICADOR)))
            .andExpect(jsonPath("$.[*].medicao").value(hasItem(DEFAULT_MEDICAO)))
            .andExpect(jsonPath("$.[*].acao").value(hasItem(DEFAULT_ACAO)))
            .andExpect(jsonPath("$.[*].avaliacaoResultado").value(hasItem(DEFAULT_AVALIACAO_RESULTADO)))
            .andExpect(jsonPath("$.[*].idProcesso").value(hasItem(DEFAULT_ID_PROCESSO)))
            .andExpect(jsonPath("$.[*].monitoramento").value(hasItem(DEFAULT_MONITORAMENTO.toString())))
            .andExpect(jsonPath("$.[*].periodo").value(hasItem(DEFAULT_PERIODO.toString())));

        // Check, that the count call also returns 1
        restMetaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMetaShouldNotBeFound(String filter) throws Exception {
        restMetaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMetaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingMeta() throws Exception {
        // Get the meta
        restMetaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMeta() throws Exception {
        // Initialize the database
        metaRepository.saveAndFlush(meta);

        int databaseSizeBeforeUpdate = metaRepository.findAll().size();

        // Update the meta
        Meta updatedMeta = metaRepository.findById(meta.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedMeta are not directly saved in db
        em.detach(updatedMeta);
        updatedMeta
            .descricao(UPDATED_DESCRICAO)
            .indicador(UPDATED_INDICADOR)
            .medicao(UPDATED_MEDICAO)
            .acao(UPDATED_ACAO)
            .avaliacaoResultado(UPDATED_AVALIACAO_RESULTADO)
            .idProcesso(UPDATED_ID_PROCESSO)
            .monitoramento(UPDATED_MONITORAMENTO)
            .periodo(UPDATED_PERIODO);
        MetaDTO metaDTO = metaMapper.toDto(updatedMeta);

        restMetaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, metaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(metaDTO))
            )
            .andExpect(status().isOk());

        // Validate the Meta in the database
        List<Meta> metaList = metaRepository.findAll();
        assertThat(metaList).hasSize(databaseSizeBeforeUpdate);
        Meta testMeta = metaList.get(metaList.size() - 1);
        assertThat(testMeta.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testMeta.getIndicador()).isEqualTo(UPDATED_INDICADOR);
        assertThat(testMeta.getMedicao()).isEqualTo(UPDATED_MEDICAO);
        assertThat(testMeta.getAcao()).isEqualTo(UPDATED_ACAO);
        assertThat(testMeta.getAvaliacaoResultado()).isEqualTo(UPDATED_AVALIACAO_RESULTADO);
        assertThat(testMeta.getIdProcesso()).isEqualTo(UPDATED_ID_PROCESSO);
        assertThat(testMeta.getMonitoramento()).isEqualTo(UPDATED_MONITORAMENTO);
        assertThat(testMeta.getPeriodo()).isEqualTo(UPDATED_PERIODO);
    }

    @Test
    @Transactional
    void putNonExistingMeta() throws Exception {
        int databaseSizeBeforeUpdate = metaRepository.findAll().size();
        meta.setId(longCount.incrementAndGet());

        // Create the Meta
        MetaDTO metaDTO = metaMapper.toDto(meta);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMetaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, metaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(metaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Meta in the database
        List<Meta> metaList = metaRepository.findAll();
        assertThat(metaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMeta() throws Exception {
        int databaseSizeBeforeUpdate = metaRepository.findAll().size();
        meta.setId(longCount.incrementAndGet());

        // Create the Meta
        MetaDTO metaDTO = metaMapper.toDto(meta);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMetaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(metaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Meta in the database
        List<Meta> metaList = metaRepository.findAll();
        assertThat(metaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMeta() throws Exception {
        int databaseSizeBeforeUpdate = metaRepository.findAll().size();
        meta.setId(longCount.incrementAndGet());

        // Create the Meta
        MetaDTO metaDTO = metaMapper.toDto(meta);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMetaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(metaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Meta in the database
        List<Meta> metaList = metaRepository.findAll();
        assertThat(metaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMetaWithPatch() throws Exception {
        // Initialize the database
        metaRepository.saveAndFlush(meta);

        int databaseSizeBeforeUpdate = metaRepository.findAll().size();

        // Update the meta using partial update
        Meta partialUpdatedMeta = new Meta();
        partialUpdatedMeta.setId(meta.getId());

        partialUpdatedMeta.indicador(UPDATED_INDICADOR).medicao(UPDATED_MEDICAO).idProcesso(UPDATED_ID_PROCESSO);

        restMetaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMeta.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMeta))
            )
            .andExpect(status().isOk());

        // Validate the Meta in the database
        List<Meta> metaList = metaRepository.findAll();
        assertThat(metaList).hasSize(databaseSizeBeforeUpdate);
        Meta testMeta = metaList.get(metaList.size() - 1);
        assertThat(testMeta.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testMeta.getIndicador()).isEqualTo(UPDATED_INDICADOR);
        assertThat(testMeta.getMedicao()).isEqualTo(UPDATED_MEDICAO);
        assertThat(testMeta.getAcao()).isEqualTo(DEFAULT_ACAO);
        assertThat(testMeta.getAvaliacaoResultado()).isEqualTo(DEFAULT_AVALIACAO_RESULTADO);
        assertThat(testMeta.getIdProcesso()).isEqualTo(UPDATED_ID_PROCESSO);
        assertThat(testMeta.getMonitoramento()).isEqualTo(DEFAULT_MONITORAMENTO);
        assertThat(testMeta.getPeriodo()).isEqualTo(DEFAULT_PERIODO);
    }

    @Test
    @Transactional
    void fullUpdateMetaWithPatch() throws Exception {
        // Initialize the database
        metaRepository.saveAndFlush(meta);

        int databaseSizeBeforeUpdate = metaRepository.findAll().size();

        // Update the meta using partial update
        Meta partialUpdatedMeta = new Meta();
        partialUpdatedMeta.setId(meta.getId());

        partialUpdatedMeta
            .descricao(UPDATED_DESCRICAO)
            .indicador(UPDATED_INDICADOR)
            .medicao(UPDATED_MEDICAO)
            .acao(UPDATED_ACAO)
            .avaliacaoResultado(UPDATED_AVALIACAO_RESULTADO)
            .idProcesso(UPDATED_ID_PROCESSO)
            .monitoramento(UPDATED_MONITORAMENTO)
            .periodo(UPDATED_PERIODO);

        restMetaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMeta.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMeta))
            )
            .andExpect(status().isOk());

        // Validate the Meta in the database
        List<Meta> metaList = metaRepository.findAll();
        assertThat(metaList).hasSize(databaseSizeBeforeUpdate);
        Meta testMeta = metaList.get(metaList.size() - 1);
        assertThat(testMeta.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testMeta.getIndicador()).isEqualTo(UPDATED_INDICADOR);
        assertThat(testMeta.getMedicao()).isEqualTo(UPDATED_MEDICAO);
        assertThat(testMeta.getAcao()).isEqualTo(UPDATED_ACAO);
        assertThat(testMeta.getAvaliacaoResultado()).isEqualTo(UPDATED_AVALIACAO_RESULTADO);
        assertThat(testMeta.getIdProcesso()).isEqualTo(UPDATED_ID_PROCESSO);
        assertThat(testMeta.getMonitoramento()).isEqualTo(UPDATED_MONITORAMENTO);
        assertThat(testMeta.getPeriodo()).isEqualTo(UPDATED_PERIODO);
    }

    @Test
    @Transactional
    void patchNonExistingMeta() throws Exception {
        int databaseSizeBeforeUpdate = metaRepository.findAll().size();
        meta.setId(longCount.incrementAndGet());

        // Create the Meta
        MetaDTO metaDTO = metaMapper.toDto(meta);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMetaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, metaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(metaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Meta in the database
        List<Meta> metaList = metaRepository.findAll();
        assertThat(metaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMeta() throws Exception {
        int databaseSizeBeforeUpdate = metaRepository.findAll().size();
        meta.setId(longCount.incrementAndGet());

        // Create the Meta
        MetaDTO metaDTO = metaMapper.toDto(meta);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMetaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(metaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Meta in the database
        List<Meta> metaList = metaRepository.findAll();
        assertThat(metaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMeta() throws Exception {
        int databaseSizeBeforeUpdate = metaRepository.findAll().size();
        meta.setId(longCount.incrementAndGet());

        // Create the Meta
        MetaDTO metaDTO = metaMapper.toDto(meta);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMetaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(metaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Meta in the database
        List<Meta> metaList = metaRepository.findAll();
        assertThat(metaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMeta() throws Exception {
        // Initialize the database
        metaRepository.saveAndFlush(meta);

        int databaseSizeBeforeDelete = metaRepository.findAll().size();

        // Delete the meta
        restMetaMockMvc
            .perform(delete(ENTITY_API_URL_ID, meta.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Meta> metaList = metaRepository.findAll();
        assertThat(metaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
