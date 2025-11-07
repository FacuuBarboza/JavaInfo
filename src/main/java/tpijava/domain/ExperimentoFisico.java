package tpijava.domain;

import java.util.Collections;
import java.util.List;

public class ExperimentoFisico extends Experimento {
    private String instrumentoUtilizado;
    private List<Investigador> investigadores;

    public ExperimentoFisico(String nombre, int duracionMinutos, boolean resultado, String instrumentoUtilizado) {
        super(nombre, duracionMinutos, resultado);
        this.instrumentoUtilizado = instrumentoUtilizado;
    }

    public String getInstrumentoUtilizado() {
        return instrumentoUtilizado;
    }

    public void setInstrumentoUtilizado(String instrumentoUtilizado) {
        this.instrumentoUtilizado = instrumentoUtilizado;
    }

    public List<Investigador> getInvestigadores() {
        return Collections.unmodifiableList(investigadores);
    }

    public boolean agregarInvestigador(Investigador investigador) {
        if (investigador == null) return false;
        return investigadores.add(investigador);
    }





}
