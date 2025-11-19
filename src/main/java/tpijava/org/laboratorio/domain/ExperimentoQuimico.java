package tpijava.org.laboratorio.domain;

public class ExperimentoQuimico extends Experimento {
    private String tipoReactivo;
    private Investigador investigador;

    public ExperimentoQuimico(String nombre, int duracionMinutos, boolean resultado, String tipoReactivo, Investigador investigador) {
        super(nombre, duracionMinutos, resultado);
        this.tipoReactivo = tipoReactivo;
        this.investigador = investigador;
    }

    public String getTipoReactivo() {
        return tipoReactivo;
    }

    public Investigador getInvestigador() {
        return investigador;
    }

    public void setTipoReactivo(String tipoReactivo) {
        this.tipoReactivo = tipoReactivo;
    }

    public void setInvestigador(Investigador investigador) {
        this.investigador = investigador;
    }

}
