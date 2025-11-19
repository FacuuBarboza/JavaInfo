package tpijava.org.laboratorio.service;

import tpijava.org.laboratorio.domain.Investigador;

public interface InvestigadorServiceI {

    boolean registrarInvestigador(String nombre, int edad);
    Investigador getInvestigadorConMasExperimentos();

}
