package tpijava.repository;

import tpijava.domain.Investigador;

import java.util.List;
import java.util.Optional;

public interface InvestigadorRepositoryI {

    boolean guardarInvestigador(Investigador investigador);

    List<Investigador> getInvestigadores();

    Optional<Investigador> buscarPorNombre(String nombre);

    int size();

    void clear();

}
