package br.com.tellescom.domain;

import br.com.tellescom.domain.enumeration.EnumTemporal;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.io.Serializable;

/**
 * A IndicadorMeta.
 */
@Entity
@Table(name = IndicadorMeta.TABLE)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class IndicadorMeta implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String TABLE = "indicador_meta";
    public static final String SEQUENCE = TABLE + "_id_seq";

    @Id
    @SequenceGenerator(name = SEQUENCE, sequenceName = SEQUENCE, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "frequencia")
    private EnumTemporal frequencia;

    @Column(name = "ano_indicador")
    private String anoIndicador;

    @Column(name = "meta_01")
    private Double meta01;

    @Column(name = "meta_02")
    private Double meta02;

    @Column(name = "meta_03")
    private Double meta03;

    @Column(name = "meta_04")
    private Double meta04;

    @Column(name = "meta_05")
    private Double meta05;

    @Column(name = "meta_06")
    private Double meta06;

    @Column(name = "meta_07")
    private Double meta07;

    @Column(name = "meta_08")
    private Double meta08;

    @Column(name = "meta_09")
    private Double meta09;

    @Column(name = "meta_10")
    private Double meta10;

    @Column(name = "meta_11")
    private Double meta11;

    @Column(name = "meta_12")
    private Double meta12;

    @Column(name = "medicao_01")
    private Double medicao01;

    @Column(name = "medicao_02")
    private Double medicao02;

    @Column(name = "medicao_03")
    private Double medicao03;

    @Column(name = "medicao_04")
    private Double medicao04;

    @Column(name = "medicao_05")
    private Double medicao05;

    @Column(name = "medicao_06")
    private Double medicao06;

    @Column(name = "medicao_07")
    private Double medicao07;

    @Column(name = "medicao_08")
    private Double medicao08;

    @Column(name = "medicao_09")
    private Double medicao09;

    @Column(name = "medicao_10")
    private Double medicao10;

    @Column(name = "medicao_11")
    private Double medicao11;

    @Column(name = "medicao_12")
    private Double medicao12;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = {"indicadorMetas"}, allowSetters = true)
    private Indicador indicador;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public IndicadorMeta id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EnumTemporal getFrequencia() {
        return this.frequencia;
    }

    public IndicadorMeta frequencia(EnumTemporal frequencia) {
        this.setFrequencia(frequencia);
        return this;
    }

    public void setFrequencia(EnumTemporal frequencia) {
        this.frequencia = frequencia;
    }

    public String getAnoIndicador() {
        return this.anoIndicador;
    }

    public IndicadorMeta anoIndicador(String anoIndicador) {
        this.setAnoIndicador(anoIndicador);
        return this;
    }

    public void setAnoIndicador(String anoIndicador) {
        this.anoIndicador = anoIndicador;
    }

    public Double getMeta01() {
        return this.meta01;
    }

    public IndicadorMeta meta01(Double meta01) {
        this.setMeta01(meta01);
        return this;
    }

    public void setMeta01(Double meta01) {
        this.meta01 = meta01;
    }

    public Double getMeta02() {
        return this.meta02;
    }

    public IndicadorMeta meta02(Double meta02) {
        this.setMeta02(meta02);
        return this;
    }

    public void setMeta02(Double meta02) {
        this.meta02 = meta02;
    }

    public Double getMeta03() {
        return this.meta03;
    }

    public IndicadorMeta meta03(Double meta03) {
        this.setMeta03(meta03);
        return this;
    }

    public void setMeta03(Double meta03) {
        this.meta03 = meta03;
    }

    public Double getMeta04() {
        return this.meta04;
    }

    public IndicadorMeta meta04(Double meta04) {
        this.setMeta04(meta04);
        return this;
    }

    public void setMeta04(Double meta04) {
        this.meta04 = meta04;
    }

    public Double getMeta05() {
        return this.meta05;
    }

    public IndicadorMeta meta05(Double meta05) {
        this.setMeta05(meta05);
        return this;
    }

    public void setMeta05(Double meta05) {
        this.meta05 = meta05;
    }

    public Double getMeta06() {
        return this.meta06;
    }

    public IndicadorMeta meta06(Double meta06) {
        this.setMeta06(meta06);
        return this;
    }

    public void setMeta06(Double meta06) {
        this.meta06 = meta06;
    }

    public Double getMeta07() {
        return this.meta07;
    }

    public IndicadorMeta meta07(Double meta07) {
        this.setMeta07(meta07);
        return this;
    }

    public void setMeta07(Double meta07) {
        this.meta07 = meta07;
    }

    public Double getMeta08() {
        return this.meta08;
    }

    public IndicadorMeta meta08(Double meta08) {
        this.setMeta08(meta08);
        return this;
    }

    public void setMeta08(Double meta08) {
        this.meta08 = meta08;
    }

    public Double getMeta09() {
        return this.meta09;
    }

    public IndicadorMeta meta09(Double meta09) {
        this.setMeta09(meta09);
        return this;
    }

    public void setMeta09(Double meta09) {
        this.meta09 = meta09;
    }

    public Double getMeta10() {
        return this.meta10;
    }

    public IndicadorMeta meta10(Double meta10) {
        this.setMeta10(meta10);
        return this;
    }

    public void setMeta10(Double meta10) {
        this.meta10 = meta10;
    }

    public Double getMeta11() {
        return this.meta11;
    }

    public IndicadorMeta meta11(Double meta11) {
        this.setMeta11(meta11);
        return this;
    }

    public void setMeta11(Double meta11) {
        this.meta11 = meta11;
    }

    public Double getMeta12() {
        return this.meta12;
    }

    public IndicadorMeta meta12(Double meta12) {
        this.setMeta12(meta12);
        return this;
    }

    public void setMeta12(Double meta12) {
        this.meta12 = meta12;
    }

    public Double getMedicao01() {
        return this.medicao01;
    }

    public IndicadorMeta medicao01(Double medicao01) {
        this.setMedicao01(medicao01);
        return this;
    }

    public void setMedicao01(Double medicao01) {
        this.medicao01 = medicao01;
    }

    public Double getMedicao02() {
        return this.medicao02;
    }

    public IndicadorMeta medicao02(Double medicao02) {
        this.setMedicao02(medicao02);
        return this;
    }

    public void setMedicao02(Double medicao02) {
        this.medicao02 = medicao02;
    }

    public Double getMedicao03() {
        return this.medicao03;
    }

    public IndicadorMeta medicao03(Double medicao03) {
        this.setMedicao03(medicao03);
        return this;
    }

    public void setMedicao03(Double medicao03) {
        this.medicao03 = medicao03;
    }

    public Double getMedicao04() {
        return this.medicao04;
    }

    public IndicadorMeta medicao04(Double medicao04) {
        this.setMedicao04(medicao04);
        return this;
    }

    public void setMedicao04(Double medicao04) {
        this.medicao04 = medicao04;
    }

    public Double getMedicao05() {
        return this.medicao05;
    }

    public IndicadorMeta medicao05(Double medicao05) {
        this.setMedicao05(medicao05);
        return this;
    }

    public void setMedicao05(Double medicao05) {
        this.medicao05 = medicao05;
    }

    public Double getMedicao06() {
        return this.medicao06;
    }

    public IndicadorMeta medicao06(Double medicao06) {
        this.setMedicao06(medicao06);
        return this;
    }

    public void setMedicao06(Double medicao06) {
        this.medicao06 = medicao06;
    }

    public Double getMedicao07() {
        return this.medicao07;
    }

    public IndicadorMeta medicao07(Double medicao07) {
        this.setMedicao07(medicao07);
        return this;
    }

    public void setMedicao07(Double medicao07) {
        this.medicao07 = medicao07;
    }

    public Double getMedicao08() {
        return this.medicao08;
    }

    public IndicadorMeta medicao08(Double medicao08) {
        this.setMedicao08(medicao08);
        return this;
    }

    public void setMedicao08(Double medicao08) {
        this.medicao08 = medicao08;
    }

    public Double getMedicao09() {
        return this.medicao09;
    }

    public IndicadorMeta medicao09(Double medicao09) {
        this.setMedicao09(medicao09);
        return this;
    }

    public void setMedicao09(Double medicao09) {
        this.medicao09 = medicao09;
    }

    public Double getMedicao10() {
        return this.medicao10;
    }

    public IndicadorMeta medicao10(Double medicao10) {
        this.setMedicao10(medicao10);
        return this;
    }

    public void setMedicao10(Double medicao10) {
        this.medicao10 = medicao10;
    }

    public Double getMedicao11() {
        return this.medicao11;
    }

    public IndicadorMeta medicao11(Double medicao11) {
        this.setMedicao11(medicao11);
        return this;
    }

    public void setMedicao11(Double medicao11) {
        this.medicao11 = medicao11;
    }

    public Double getMedicao12() {
        return this.medicao12;
    }

    public IndicadorMeta medicao12(Double medicao12) {
        this.setMedicao12(medicao12);
        return this;
    }

    public void setMedicao12(Double medicao12) {
        this.medicao12 = medicao12;
    }

    public Indicador getIndicador() {
        return this.indicador;
    }

    public void setIndicador(Indicador indicador) {
        this.indicador = indicador;
    }

    public IndicadorMeta indicador(Indicador indicador) {
        this.setIndicador(indicador);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IndicadorMeta)) {
            return false;
        }
        return getId() != null && getId().equals(((IndicadorMeta) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IndicadorMeta{" +
            "id=" + getId() +
            ", frequencia='" + getFrequencia() + "'" +
            ", anoIndicador='" + getAnoIndicador() + "'" +
            ", meta01=" + getMeta01() +
            ", meta02=" + getMeta02() +
            ", meta03=" + getMeta03() +
            ", meta04=" + getMeta04() +
            ", meta05=" + getMeta05() +
            ", meta06=" + getMeta06() +
            ", meta07=" + getMeta07() +
            ", meta08=" + getMeta08() +
            ", meta09=" + getMeta09() +
            ", meta10=" + getMeta10() +
            ", meta11=" + getMeta11() +
            ", meta12=" + getMeta12() +
            ", medicao01=" + getMedicao01() +
            ", medicao02=" + getMedicao02() +
            ", medicao03=" + getMedicao03() +
            ", medicao04=" + getMedicao04() +
            ", medicao05=" + getMedicao05() +
            ", medicao06=" + getMedicao06() +
            ", medicao07=" + getMedicao07() +
            ", medicao08=" + getMedicao08() +
            ", medicao09=" + getMedicao09() +
            ", medicao10=" + getMedicao10() +
            ", medicao11=" + getMedicao11() +
            ", medicao12=" + getMedicao12() +
            "}";
    }

    public Double getMeta(int indice) {

        Double meta;

        switch (indice) {
            case 2 -> {
                meta = getMeta02();
            }
            case 3 -> {
                meta = getMeta03();
            }
            case 4 -> {
                meta = getMeta04();
            }
            case 5 -> {
                meta = getMeta05();
            }
            case 6 -> {
                meta = getMeta06();
            }
            case 7 -> {
                meta = getMeta07();
            }
            case 8 -> {
                meta = getMeta08();
            }
            case 9 -> {
                meta = getMeta09();
            }
            case 10 -> {
                meta = getMeta10();
            }
            case 11 -> {
                meta = getMeta11();
            }
            default -> {
                meta = getMeta01();
            }
        }

        return meta;
    }

    public Double getMedicao(int indice) {
        Double medicao;

        switch (indice) {
            case 2 -> {
                medicao = getMedicao02();
            }
            case 3 -> {
                medicao = getMedicao03();
            }
            case 4 -> {
                medicao = getMedicao04();
            }
            case 5 -> {
                medicao = getMedicao05();
            }
            case 6 -> {
                medicao = getMedicao06();
            }
            case 7 -> {
                medicao = getMedicao07();
            }
            case 8 -> {
                medicao = getMedicao08();
            }
            case 9 -> {
                medicao = getMedicao09();
            }
            case 10 -> {
                medicao = getMedicao10();
            }
            case 11 -> {
                medicao = getMedicao11();
            }
            default -> {
                medicao = getMedicao01();
            }
        }

        return medicao;
    }
}
