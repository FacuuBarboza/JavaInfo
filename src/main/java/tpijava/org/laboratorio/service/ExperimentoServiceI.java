package tpijava.org.laboratorio.service;

import tpijava.org.laboratorio.domain.Experimento;
import tpijava.org.laboratorio.dto.ReporteDTO;

import java.util.List;
import java.util.Map;

public interface ExperimentoServiceI {

    boolean registrarExpQuimico(String nombreExp, int duracionMinutos, boolean resultado, String tipoReactivo, String investigadorNombre);
    boolean registrarExpFisico(String nombreExp, int duracionMinutos, boolean resultado, String instrumento, List<String> investigadoresNombres);
    List<Experimento> getTodosLosExperimentos();
    ReporteDTO getEstadisticasGenerales();
    Experimento getExperimentoMayorDuracion();
    Map<String, Integer> getTotalesExitoFallo();
}
