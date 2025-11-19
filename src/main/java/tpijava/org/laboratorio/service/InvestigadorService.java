package tpijava.org.laboratorio.service;

import tpijava.org.laboratorio.domain.Investigador;
import tpijava.org.laboratorio.repository.InvestigadorRepositoryI;

import java.util.Comparator;

public class InvestigadorService implements InvestigadorServiceI {

    private final InvestigadorRepositoryI investigadorRepo;

    public InvestigadorService(InvestigadorRepositoryI investigadorRepo) {
        this.investigadorRepo = investigadorRepo;
    }

    @Override
    public boolean registrarInvestigador(String nombre, int edad) {
        if (nombre == null) throw new IllegalArgumentException("Nombre nulo");
        Investigador inv = new Investigador(nombre, edad);
        return investigadorRepo.guardarInvestigador(inv);
    }

    @Override
    public Investigador getInvestigadorConMasExperimentos() {
        return investigadorRepo.getInvestigadores().stream()
                .max(Comparator.comparingInt(Investigador::getCantidadExperimentos))
                .orElse(null);
    }
}
