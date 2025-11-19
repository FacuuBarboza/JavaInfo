package tpijava.org.laboratorio.repository;

import tpijava.org.laboratorio.domain.Experimento;

import java.util.List;
import java.util.Optional;

public interface ExperimentoRepositoryI {

    boolean guardarExperimento(Experimento experimento);

    List<Experimento> getExperimentos();

    int size();

    void clear();

    List<Experimento> obtenerExitosos();

    Optional<Experimento> encontrarPorNombre(String nombre);

}
