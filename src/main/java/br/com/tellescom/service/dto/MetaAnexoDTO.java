package br.com.tellescom.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link br.com.tellescom.domain.MetaAnexo} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MetaAnexoDTO implements Serializable {

    private Long id;

    private String nomeFisico;

    private String nomeOriginal;

    private String extensao;

    private String caminho;

    private Instant dataCriacao;

    private MetaResultadoDTO metaResultado;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeFisico() {
        return nomeFisico;
    }

    public void setNomeFisico(String nomeFisico) {
        this.nomeFisico = nomeFisico;
    }

    public String getNomeOriginal() {
        return nomeOriginal;
    }

    public void setNomeOriginal(String nomeOriginal) {
        this.nomeOriginal = nomeOriginal;
    }

    public String getExtensao() {
        return extensao;
    }

    public void setExtensao(String extensao) {
        this.extensao = extensao;
    }

    public String getCaminho() {
        return caminho;
    }

    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }

    public Instant getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Instant dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public MetaResultadoDTO getMetaResultado() {
        return metaResultado;
    }

    public void setMetaResultado(MetaResultadoDTO metaResultado) {
        this.metaResultado = metaResultado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MetaAnexoDTO)) {
            return false;
        }

        MetaAnexoDTO metaAnexoDTO = (MetaAnexoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, metaAnexoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MetaAnexoDTO{" +
            "id=" + getId() +
            ", nomeFisico='" + getNomeFisico() + "'" +
            ", nomeOriginal='" + getNomeOriginal() + "'" +
            ", extensao='" + getExtensao() + "'" +
            ", caminho='" + getCaminho() + "'" +
            ", dataCriacao='" + getDataCriacao() + "'" +
            ", metaResultado=" + getMetaResultado() +
            "}";
    }
}
