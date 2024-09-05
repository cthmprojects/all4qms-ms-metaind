package br.com.tellescom.domain.response.graficos;

public class DadoPreenchimentoIndicadores implements DadosGraficoIndicador{

    private String estado;

    private float valor ;

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }
}
