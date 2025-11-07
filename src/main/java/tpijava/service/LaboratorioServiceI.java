package tpijava.service;

import tpijava.domain.Experimento;
import tpijava.domain.Investigador;
import tpijava.dto.ReporteDTO;

import java.util.List;
import java.util.Map;

public interface LaboratorioServiceI {
    boolean registrarInvestigador(String nombre, int edad);
    boolean registrarExpQuimico(String nombreExp, int duracionMinutos, boolean resultado, String tipoReactivo, String investigadorNombre);
    boolean registrarExpFisico(String nombreExp, int duracionMinutos, boolean resultado, String instrumento, List<String> investigadoresNombres);

    List<Experimento> getTodosLosExperimentos();
    ReporteDTO getEstadisticasGenerales();
    Experimento getExperimentoMayorDuracion();
    Investigador getInvestigadorConMasExperimentos();
    Map<String, Integer> getTotalesExitoFallo();
}