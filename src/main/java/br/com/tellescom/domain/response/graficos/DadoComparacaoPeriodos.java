package br.com.tellescom.domain.response.graficos;

public class DadoComparacaoPeriodos implements DadosGraficoIndicador{

    private String periodoAtual;

    private Double valorAtual;

    private String periodoAnterior;

    private Double valorAnterior;

    public String getPeriodoAtual() {
        return periodoAtual;
    }

    public void setPeriodoAtual(String periodoAtual) {
        this.periodoAtual = periodoAtual;
    }

    public Double getValorAtual() {
        return valorAtual;
    }

    public void setValorAtual(Double valorAtual) {
        this.valorAtual = valorAtual;
    }

    public String getPeriodoAnterior() {
        return periodoAnterior;
    }

    public void setPeriodoAnterior(String periodoAnterior) {
        this.periodoAnterior = periodoAnterior;
    }

    public Double getValorAnterior() {
        return valorAnterior;
    }

    public void setValorAnterior(Double valorAnterior) {
        this.valorAnterior = valorAnterior;
    }
}
