package br.com.tellescom.domain.response.graficos;

import java.util.List;

public class GraficoIndicadorResponse extends  BaseGraficoIndicadorResponse {

    private List<DadosGraficoIndicador> dados;

    public List<DadosGraficoIndicador> getDados() {
        return dados;
    }

    public void setDados(List<DadosGraficoIndicador> dados) {
        this.dados = dados;
    }
}
