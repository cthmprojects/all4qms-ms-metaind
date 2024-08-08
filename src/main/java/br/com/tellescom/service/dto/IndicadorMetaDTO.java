package br.com.tellescom.service.dto;

import br.com.tellescom.domain.enumeration.EnumTemporal;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link br.com.tellescom.domain.IndicadorMeta} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class IndicadorMetaDTO implements Serializable {

    private Long id;

    private EnumTemporal frequencia;

    private String anoIndicador;

    private Double meta01;

    private Double meta02;

    private Double meta03;

    private Double meta04;

    private Double meta05;

    private Double meta06;

    private Double meta07;

    private Double meta08;

    private Double meta09;

    private Double meta10;

    private Double meta11;

    private Double meta12;

    private Double medicao01;

    private Double medicao02;

    private Double medicao03;

    private Double medicao04;

    private Double medicao05;

    private Double medicao06;

    private Double medicao07;

    private Double medicao08;

    private Double medicao09;

    private Double medicao10;

    private Double medicao11;

    private Double medicao12;

    private IndicadorDTO indicador;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EnumTemporal getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(EnumTemporal frequencia) {
        this.frequencia = frequencia;
    }

    public String getAnoIndicador() {
        return anoIndicador;
    }

    public void setAnoIndicador(String anoIndicador) {
        this.anoIndicador = anoIndicador;
    }

    public Double getMeta01() {
        return meta01;
    }

    public void setMeta01(Double meta01) {
        this.meta01 = meta01;
    }

    public Double getMeta02() {
        return meta02;
    }

    public void setMeta02(Double meta02) {
        this.meta02 = meta02;
    }

    public Double getMeta03() {
        return meta03;
    }

    public void setMeta03(Double meta03) {
        this.meta03 = meta03;
    }

    public Double getMeta04() {
        return meta04;
    }

    public void setMeta04(Double meta04) {
        this.meta04 = meta04;
    }

    public Double getMeta05() {
        return meta05;
    }

    public void setMeta05(Double meta05) {
        this.meta05 = meta05;
    }

    public Double getMeta06() {
        return meta06;
    }

    public void setMeta06(Double meta06) {
        this.meta06 = meta06;
    }

    public Double getMeta07() {
        return meta07;
    }

    public void setMeta07(Double meta07) {
        this.meta07 = meta07;
    }

    public Double getMeta08() {
        return meta08;
    }

    public void setMeta08(Double meta08) {
        this.meta08 = meta08;
    }

    public Double getMeta09() {
        return meta09;
    }

    public void setMeta09(Double meta09) {
        this.meta09 = meta09;
    }

    public Double getMeta10() {
        return meta10;
    }

    public void setMeta10(Double meta10) {
        this.meta10 = meta10;
    }

    public Double getMeta11() {
        return meta11;
    }

    public void setMeta11(Double meta11) {
        this.meta11 = meta11;
    }

    public Double getMeta12() {
        return meta12;
    }

    public void setMeta12(Double meta12) {
        this.meta12 = meta12;
    }

    public Double getMedicao01() {
        return medicao01;
    }

    public void setMedicao01(Double medicao01) {
        this.medicao01 = medicao01;
    }

    public Double getMedicao02() {
        return medicao02;
    }

    public void setMedicao02(Double medicao02) {
        this.medicao02 = medicao02;
    }

    public Double getMedicao03() {
        return medicao03;
    }

    public void setMedicao03(Double medicao03) {
        this.medicao03 = medicao03;
    }

    public Double getMedicao04() {
        return medicao04;
    }

    public void setMedicao04(Double medicao04) {
        this.medicao04 = medicao04;
    }

    public Double getMedicao05() {
        return medicao05;
    }

    public void setMedicao05(Double medicao05) {
        this.medicao05 = medicao05;
    }

    public Double getMedicao06() {
        return medicao06;
    }

    public void setMedicao06(Double medicao06) {
        this.medicao06 = medicao06;
    }

    public Double getMedicao07() {
        return medicao07;
    }

    public void setMedicao07(Double medicao07) {
        this.medicao07 = medicao07;
    }

    public Double getMedicao08() {
        return medicao08;
    }

    public void setMedicao08(Double medicao08) {
        this.medicao08 = medicao08;
    }

    public Double getMedicao09() {
        return medicao09;
    }

    public void setMedicao09(Double medicao09) {
        this.medicao09 = medicao09;
    }

    public Double getMedicao10() {
        return medicao10;
    }

    public void setMedicao10(Double medicao10) {
        this.medicao10 = medicao10;
    }

    public Double getMedicao11() {
        return medicao11;
    }

    public void setMedicao11(Double medicao11) {
        this.medicao11 = medicao11;
    }

    public Double getMedicao12() {
        return medicao12;
    }

    public void setMedicao12(Double medicao12) {
        this.medicao12 = medicao12;
    }

    public IndicadorDTO getIndicador() {
        return indicador;
    }

    public void setIndicador(IndicadorDTO indicador) {
        this.indicador = indicador;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IndicadorMetaDTO)) {
            return false;
        }

        IndicadorMetaDTO indicadorMetaDTO = (IndicadorMetaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, indicadorMetaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IndicadorMetaDTO{" +
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
            ", indicador=" + getIndicador() +
            "}";
    }
}
