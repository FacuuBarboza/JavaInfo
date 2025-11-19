package tpijava.org.laboratorio.repository;

import tpijava.org.laboratorio.domain.Experimento;

import java.util.*;
import java.util.stream.Collectors;

public class ExperimentoRepository implements ExperimentoRepositoryI {

    private final List<Experimento> dExperimentos;

    public ExperimentoRepository() {
        this.dExperimentos = new ArrayList<>();
    }

    public ExperimentoRepository(List<Experimento> inicial) {
        this.dExperimentos = new ArrayList<>(Objects.requireNonNull(inicial, "La lista no puede ser nula"));
    }

    @Override
    public synchronized boolean guardarExperimento(Experimento experimento) {
        Objects.requireNonNull(experimento, "El experimento no puede ser nulo");
        return dExperimentos.add(experimento);
    }

    @Override
    public synchronized List<Experimento> getExperimentos() {
        return Collections.unmodifiableList(dExperimentos);
    }

    public List<Experimento> obtenerExitosos() {
        return dExperimentos.stream()
                .filter(Experimento::isExitoso)
                .collect(Collectors.toList());
    }

    @Override
    public synchronized Optional<Experimento> encontrarPorNombre(String nombre) {
        if (nombre == null) return Optional.empty();
        return dExperimentos.stream()
                .filter(exp -> nombre.equals(exp.getNombre()))
                .findFirst();
    }

    /**
     * Encuentra experimentos con duración mayor o igual a minDuracion (en minutos).
    public List<Experimento> encontrarConDuracionMinima(int minDuracion) {
        return dExperimentos.stream()
                .filter(e -> e.getDuracionMinutos() >= minDuracion)
                .collect(Collectors.toList());
    }
     */

    @Override
    public synchronized int size() {
        return dExperimentos.size();
    }

    @Override
    public synchronized void clear() {
        dExperimentos.clear();
    }
}
