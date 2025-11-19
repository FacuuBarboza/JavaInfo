package tpijava.org.laboratorio.repository;

import tpijava.org.laboratorio.domain.Investigador;

import java.util.*;

public class InvestigadorRepository implements InvestigadorRepositoryI {

    private final List<Investigador> dInvestigadores;

    public InvestigadorRepository() {
        this.dInvestigadores = new ArrayList<>();
    }

    //Constructor con lista inicial
    public InvestigadorRepository(List<Investigador> inicial) {
        this.dInvestigadores = new ArrayList<>(Objects.requireNonNull(inicial, "La lista inicial no puede ser nula"));
    }

    //Guarda investigador si no existe otro con mismo nombre, true si guardo y false si ya existe duplicado
    @Override
    public synchronized boolean guardarInvestigador(Investigador investigador) {
        Objects.requireNonNull(investigador, "El investigador no puede ser nulo");
        String nombre = investigador.getNombre();
        if (nombre == null) {
            throw new IllegalArgumentException("El nombre no puede ser nulo");
        }
        if (existsByNombre(nombre)) {
            return false;
        }

        return dInvestigadores.add(investigador);
    }

    //Para comprobar nombres duplicados
    public boolean existsByNombre(String nombre) {
        if (nombre == null) return false;
        for (Investigador inv : dInvestigadores) {
            if (nombre.equals(inv.getNombre())) {
                return true;
            }
        }
        return false;
    }

    //Devuelve lista inmodificable de investigadores
    @Override
    public synchronized List<Investigador> getInvestigadores() {
        return Collections.unmodifiableList(dInvestigadores);
    }

    // Devuelve nombre investigador si lo encuentra, sino null
    @Override
    public synchronized Optional<Investigador> buscarPorNombre(String nombre) {
        if (nombre == null) return Optional.empty();
        return dInvestigadores.stream()
                .filter(inv -> nombre.equals(inv.getNombre()))
                .findFirst();
    }

    public int size() {
        return dInvestigadores.size();
    }

    public void clear() {
        dInvestigadores.clear();
    }
}
