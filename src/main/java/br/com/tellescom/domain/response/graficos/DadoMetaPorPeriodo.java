package br.com.tellescom.domain.response.graficos;

public class DadoMetaPorPeriodo implements DadosGraficoIndicador {

    private String unidadeTemporal;

    private String unidadeMedida;

    private Double meta;

    private Double realizado;

    public String getUnidadeTemporal() {
        return unidadeTemporal;
    }

    public void setUnidadeTemporal(String unidadeTemporal) {
        this.unidadeTemporal = unidadeTemporal;
    }

    public Double getMeta() {
        return meta;
    }

    public void setMeta(Double meta) {
        this.meta = meta;
    }

    public Double getRealizado() {
        return realizado;
    }

    public void setRealizado(Double realizado) {
        this.realizado = realizado;
    }

    public String getUnidadeMedida() {
        return unidadeMedida;
    }

    public void setUnidadeMedida(String unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }
}
