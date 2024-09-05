package br.com.tellescom.domain.response.graficos;

public class QualidadeProducaoIndicadorResponse extends BaseGraficoIndicadorResponse {

    private float qualidadeProducao;

    private float variacao;

    public float getQualidadeProducao() {
        return qualidadeProducao;
    }

    public void setQualidadeProducao(float qualidadeProducao) {
        this.qualidadeProducao = qualidadeProducao;
    }

    public float getVariacao() {
        return variacao;
    }

    public void setVariacao(float variacao) {
        this.variacao = variacao;
    }
}
