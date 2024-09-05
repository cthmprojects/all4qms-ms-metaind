package br.com.tellescom.domain.response.graficos;

public class DadoMetaPorPeriodo implements DadosGraficoIndicador{

    private String unidadeTemporal;

    private float meta;

    private float realizado;

    public String getUnidadeTemporal() {
        return unidadeTemporal;
    }

    public void setUnidadeTemporal(String unidadeTemporal) {
        this.unidadeTemporal = unidadeTemporal;
    }

    public float getMeta() {
        return meta;
    }

    public void setMeta(float meta) {
        this.meta = meta;
    }

    public float getRealizado() {
        return realizado;
    }

    public void setRealizado(float realizado) {
        this.realizado = realizado;
    }
}
