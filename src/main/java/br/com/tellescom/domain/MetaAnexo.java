package br.com.tellescom.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A MetaAnexo.
 */
@Entity
@Table(name = MetaAnexo.TABLE)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MetaAnexo implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String TABLE = "meta_anexo";
    public static final String SEQUENCE = TABLE + "_id_seq";

    @Id
    @SequenceGenerator(name = SEQUENCE, sequenceName = SEQUENCE, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(name = "nome_fisico")
    private String nomeFisico;

    @Column(name = "nome_original")
    private String nomeOriginal;

    @Column(name = "extensao")
    private String extensao;

    @Column(name = "caminho")
    private String caminho;

    @Column(name = "data_criacao")
    private Instant dataCriacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "anexos", "meta" }, allowSetters = true)
    private MetaResultado metaResultado;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public MetaAnexo id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeFisico() {
        return this.nomeFisico;
    }

    public MetaAnexo nomeFisico(String nomeFisico) {
        this.setNomeFisico(nomeFisico);
        return this;
    }

    public void setNomeFisico(String nomeFisico) {
        this.nomeFisico = nomeFisico;
    }

    public String getNomeOriginal() {
        return this.nomeOriginal;
    }

    public MetaAnexo nomeOriginal(String nomeOriginal) {
        this.setNomeOriginal(nomeOriginal);
        return this;
    }

    public void setNomeOriginal(String nomeOriginal) {
        this.nomeOriginal = nomeOriginal;
    }

    public String getExtensao() {
        return this.extensao;
    }

    public MetaAnexo extensao(String extensao) {
        this.setExtensao(extensao);
        return this;
    }

    public void setExtensao(String extensao) {
        this.extensao = extensao;
    }

    public String getCaminho() {
        return this.caminho;
    }

    public MetaAnexo caminho(String caminho) {
        this.setCaminho(caminho);
        return this;
    }

    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }

    public Instant getDataCriacao() {
        return this.dataCriacao;
    }

    public MetaAnexo dataCriacao(Instant dataCriacao) {
        this.setDataCriacao(dataCriacao);
        return this;
    }

    public void setDataCriacao(Instant dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public MetaResultado getMetaResultado() {
        return this.metaResultado;
    }

    public void setMetaResultado(MetaResultado metaResultado) {
        this.metaResultado = metaResultado;
    }

    public MetaAnexo metaResultado(MetaResultado metaResultado) {
        this.setMetaResultado(metaResultado);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MetaAnexo)) {
            return false;
        }
        return getId() != null && getId().equals(((MetaAnexo) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MetaAnexo{" +
            "id=" + getId() +
            ", nomeFisico='" + getNomeFisico() + "'" +
            ", nomeOriginal='" + getNomeOriginal() + "'" +
            ", extensao='" + getExtensao() + "'" +
            ", caminho='" + getCaminho() + "'" +
            ", dataCriacao='" + getDataCriacao() + "'" +
            "}";
    }
}
