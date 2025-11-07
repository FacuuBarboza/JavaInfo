package tpijava.service;

import tpijava.domain.*;
import tpijava.dto.ReporteDTO;
import tpijava.repository.ExperimentoRepositoryI;
import tpijava.repository.InvestigadorRepositoryI;

import java.util.*;
import java.util.stream.Collectors;

public class LaboratorioService implements LaboratorioServiceI {

    private final InvestigadorRepositoryI investigadorRepo;
    private final ExperimentoRepositoryI experimentoRepo;

    public LaboratorioService(InvestigadorRepositoryI investigadorRepo, ExperimentoRepositoryI experimentoRepo) {
        this.investigadorRepo = Objects.requireNonNull(investigadorRepo);
        this.experimentoRepo = Objects.requireNonNull(experimentoRepo);
    }

    @Override
    public boolean registrarInvestigador(String nombre, int edad) {
        if (nombre == null) throw new IllegalArgumentException("Nombre nulo");
        Investigador inv = new Investigador(nombre, edad);
        return investigadorRepo.guardarInvestigador(inv);
    }

    @Override
    public boolean registrarExpQuimico(String nombreExp, int duracionMinutos, boolean resultado, String tipoReactivo, String investigadorNombre) {
        if (nombreExp == null || tipoReactivo == null || investigadorNombre == null) throw new IllegalArgumentException("Argumentos nulos");
        Optional<Investigador> invOpt = investigadorRepo.buscarPorNombre(investigadorNombre);
        if (!invOpt.isPresent()) {
            return false; // o lanzar excepción, según tu política
        }
        Investigador inv = invOpt.get();
        ExperimentoQuimico exp = new ExperimentoQuimico(nombreExp, duracionMinutos, resultado, tipoReactivo, inv);
        boolean added = experimentoRepo.guardarExperimento(exp);
        if (added) inv.agregarExperimento(exp);
        return added;
    }

    @Override
    public boolean registrarExpFisico(String nombreExp, int duracionMinutos, boolean resultado, String instrumento, List<String> investigadoresNombres) {
        if (nombreExp == null || instrumento == null || investigadoresNombres == null) throw new IllegalArgumentException("Argumentos nulos");
        // localizar investigadores y abortar si alguno no existe
        List<Investigador> investigadores = new ArrayList<>();
        for (String nombre : investigadoresNombres) {
            Optional<Investigador> oi = investigadorRepo.buscarPorNombre(nombre);
            if (!oi.isPresent()) return false; // política simple: no registrar si faltan investigadores
            investigadores.add(oi.get());
        }
        ExperimentoFisico exp = new ExperimentoFisico(nombreExp, duracionMinutos, resultado, instrumento);
        boolean added = experimentoRepo.guardarExperimento(exp);
        if (added) {
            // asociar el experimento a los investigadores
            investigadores.forEach(i -> i.agregarExperimento(exp));
            // si ExperimentoFisico tiene lista de investigadores, agregar también ahí (si implementaste ese método)
            // exp.agregarInvestigadores(investigadores) // implementar si corresponde
        }
        return added;
    }

    @Override
    public List<Experimento> getTodosLosExperimentos() {
        return experimentoRepo.getExperimentos();
    }

    @Override
    public ReporteDTO getEstadisticasGenerales() {
        List<Experimento> list = experimentoRepo.getExperimentos();
        int total = list.size();
        if (total == 0) return new ReporteDTO(0.0, 0.0, 0, 0, 0);

        int exitos = (int) list.stream().filter(Experimento::isExitoso).count();
        int fallos = total - exitos;
        double promedio = list.stream().mapToInt(Experimento::getDuracionMinutos).average().orElse(0.0);
        double porcentajeExito = (100.0 * exitos) / total;
        return new ReporteDTO(promedio, porcentajeExito, exitos, fallos, total);
    }

    @Override
    public Experimento getExperimentoMayorDuracion() {
        return experimentoRepo.getExperimentos().stream()
                .max(Comparator.comparingInt(Experimento::getDuracionMinutos))
                .orElse(null);
    }

    @Override
    public Investigador getInvestigadorConMasExperimentos() {
        return investigadorRepo.getInvestigadores().stream()
                .max(Comparator.comparingInt(Investigador::getCantidadExperimentos))
                .orElse(null);
    }

    @Override
    public Map<String, Integer> getTotalesExitoFallo() {
        List<Experimento> list = experimentoRepo.getExperimentos();
        int exitos = (int) list.stream().filter(Experimento::isExitoso).count();
        int fallos = list.size() - exitos;
        Map<String, Integer> mapa = new HashMap<>();
        mapa.put("exitos", exitos);
        mapa.put("fallos", fallos);
        return mapa;
    }
}