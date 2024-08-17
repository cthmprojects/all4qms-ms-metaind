package br.com.tellescom.domain;

import br.com.tellescom.domain.enumeration.EnumTendencia;
import br.com.tellescom.domain.enumeration.EnumUnidadeMedida;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Indicador.
 */
@Entity
@Table(name = Indicador.TABLE)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Indicador implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String TABLE = "indicador";
    public static final String SEQUENCE = TABLE + "_id_seq";

    @SequenceGenerator(name = SEQUENCE, sequenceName = SEQUENCE, allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(name = "codigo_indicador")
    private String codigoIndicador;

    @Column(name = "nome_indicador")
    private String nomeIndicador;

    @Column(name = "descricao_indicador")
    private String descricaoIndicador;

    @Enumerated(EnumType.STRING)
    @Column(name = "unidade")
    private EnumUnidadeMedida unidade;

    @Enumerated(EnumType.STRING)
    @Column(name = "tendencia")
    private EnumTendencia tendencia;

    @Column(name = "id_processo")
    private Integer idProcesso;

    @Column(name = "id_meta_indicador")
    private Integer idMetaIndicador;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "indicador")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "indicador" }, allowSetters = true)
    private Set<IndicadorMeta> indicadorMetas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Indicador id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigoIndicador() {
        return this.codigoIndicador;
    }

    public Indicador codigoIndicador(String codigoIndicador) {
        this.setCodigoIndicador(codigoIndicador);
        return this;
    }

    public void setCodigoIndicador(String codigoIndicador) {
        this.codigoIndicador = codigoIndicador;
    }

    public String getNomeIndicador() {
        return this.nomeIndicador;
    }

    public Indicador nomeIndicador(String nomeIndicador) {
        this.setNomeIndicador(nomeIndicador);
        return this;
    }

    public void setNomeIndicador(String nomeIndicador) {
        this.nomeIndicador = nomeIndicador;
    }

    public String getDescricaoIndicador() {
        return this.descricaoIndicador;
    }

    public Indicador descricaoIndicador(String descricaoIndicador) {
        this.setDescricaoIndicador(descricaoIndicador);
        return this;
    }

    public void setDescricaoIndicador(String descricaoIndicador) {
        this.descricaoIndicador = descricaoIndicador;
    }

    public EnumUnidadeMedida getUnidade() {
        return this.unidade;
    }

    public Indicador unidade(EnumUnidadeMedida unidade) {
        this.setUnidade(unidade);
        return this;
    }

    public void setUnidade(EnumUnidadeMedida unidade) {
        this.unidade = unidade;
    }

    public EnumTendencia getTendencia() {
        return this.tendencia;
    }

    public Indicador tendencia(EnumTendencia tendencia) {
        this.setTendencia(tendencia);
        return this;
    }

    public void setTendencia(EnumTendencia tendencia) {
        this.tendencia = tendencia;
    }

    public Integer getIdProcesso() {
        return this.idProcesso;
    }

    public Indicador idProcesso(Integer idProcesso) {
        this.setIdProcesso(idProcesso);
        return this;
    }

    public void setIdProcesso(Integer idProcesso) {
        this.idProcesso = idProcesso;
    }

    public Integer getIdMetaIndicador() {
        return this.idMetaIndicador;
    }

    public Indicador idMetaIndicador(Integer idMetaIndicador) {
        this.setIdMetaIndicador(idMetaIndicador);
        return this;
    }

    public void setIdMetaIndicador(Integer idMetaIndicador) {
        this.idMetaIndicador = idMetaIndicador;
    }

    public Set<IndicadorMeta> getIndicadorMetas() {
        return this.indicadorMetas;
    }

    public void setIndicadorMetas(Set<IndicadorMeta> indicadorMetas) {
        if (this.indicadorMetas != null) {
            this.indicadorMetas.forEach(i -> i.setIndicador(null));
        }
        if (indicadorMetas != null) {
            indicadorMetas.forEach(i -> i.setIndicador(this));
        }
        this.indicadorMetas = indicadorMetas;
    }

    public Indicador indicadorMetas(Set<IndicadorMeta> indicadorMetas) {
        this.setIndicadorMetas(indicadorMetas);
        return this;
    }

    public Indicador addIndicadorMeta(IndicadorMeta indicadorMeta) {
        this.indicadorMetas.add(indicadorMeta);
        indicadorMeta.setIndicador(this);
        return this;
    }

    public Indicador removeIndicadorMeta(IndicadorMeta indicadorMeta) {
        this.indicadorMetas.remove(indicadorMeta);
        indicadorMeta.setIndicador(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Indicador)) {
            return false;
        }
        return getId() != null && getId().equals(((Indicador) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Indicador{" +
            "id=" + getId() +
            ", codigoIndicador='" + getCodigoIndicador() + "'" +
            ", nomeIndicador='" + getNomeIndicador() + "'" +
            ", descricaoIndicador='" + getDescricaoIndicador() + "'" +
            ", unidade='" + getUnidade() + "'" +
            ", tendencia='" + getTendencia() + "'" +
            ", idProcesso=" + getIdProcesso() +
            ", idMetaIndicador=" + getIdMetaIndicador() +
            "}";
    }
}
