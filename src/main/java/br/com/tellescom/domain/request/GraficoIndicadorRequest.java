package br.com.tellescom.domain.request;

public class GraficoIndicadorRequest {

    private int idIndicador;

    private int idProcesso;

    private int anoIndicador;

    public int getIdIndicador() {
        return idIndicador;
    }

    public void setIdIndicador(int idIndicador) {
        this.idIndicador = idIndicador;
    }

    public int getIdProcesso() {
        return idProcesso;
    }

    public void setIdProcesso(int idProcesso) {
        this.idProcesso = idProcesso;
    }

    public int getAnoIndicador() {
        return anoIndicador;
    }

    public void setAnoIndicador(int anoIndicador) {
        this.anoIndicador = anoIndicador;
    }
}
