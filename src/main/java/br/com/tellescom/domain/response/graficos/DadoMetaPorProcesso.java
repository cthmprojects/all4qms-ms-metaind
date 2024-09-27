package br.com.tellescom.domain.response.graficos;

public class DadoMetaPorProcesso implements DadosGraficoIndicador{

    private String processo;

    private float valor;

    public String getProcesso() {
        return processo;
    }

    public void setProcesso(String processo) {
        this.processo = processo;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }
}
