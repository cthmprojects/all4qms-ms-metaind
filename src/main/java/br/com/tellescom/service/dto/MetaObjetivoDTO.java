package br.com.tellescom.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link br.com.tellescom.domain.MetaObjetivo} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MetaObjetivoDTO implements Serializable {

    private Long id;

    private String politicaSGQ;

    private String desdobramentoSGQ;

    private String objetivoSGQ;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPoliticaSGQ() {
        return politicaSGQ;
    }

    public void setPoliticaSGQ(String politicaSGQ) {
        this.politicaSGQ = politicaSGQ;
    }

    public String getDesdobramentoSGQ() {
        return desdobramentoSGQ;
    }

    public void setDesdobramentoSGQ(String desdobramentoSGQ) {
        this.desdobramentoSGQ = desdobramentoSGQ;
    }

    public String getObjetivoSGQ() {
        return objetivoSGQ;
    }

    public void setObjetivoSGQ(String objetivoSGQ) {
        this.objetivoSGQ = objetivoSGQ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MetaObjetivoDTO)) {
            return false;
        }

        MetaObjetivoDTO metaObjetivoDTO = (MetaObjetivoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, metaObjetivoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MetaObjetivoDTO{" +
            "id=" + getId() +
            ", politicaSGQ='" + getPoliticaSGQ() + "'" +
            ", desdobramentoSGQ='" + getDesdobramentoSGQ() + "'" +
            ", objetivoSGQ='" + getObjetivoSGQ() + "'" +
            "}";
    }
}
