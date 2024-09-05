package br.com.tellescom.domain.response.graficos;

public class DadoComparacaoPeriodos implements DadosGraficoIndicador{

    private String periodoAtual;

    private float valorAtual;

    private String periodoAnterior;

    private float valorAnterior;

    public String getPeriodoAtual() {
        return periodoAtual;
    }

    public void setPeriodoAtual(String periodoAtual) {
        this.periodoAtual = periodoAtual;
    }

    public float getValorAtual() {
        return valorAtual;
    }

    public void setValorAtual(float valorAtual) {
        this.valorAtual = valorAtual;
    }

    public String getPeriodoAnterior() {
        return periodoAnterior;
    }

    public void setPeriodoAnterior(String periodoAnterior) {
        this.periodoAnterior = periodoAnterior;
    }

    public float getValorAnterior() {
        return valorAnterior;
    }

    public void setValorAnterior(float valorAnterior) {
        this.valorAnterior = valorAnterior;
    }
}
