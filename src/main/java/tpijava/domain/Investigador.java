package tpijava.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Investigador {
    private String nombre;
    private int edad;
    private List<Experimento> experimentosRealizados = new ArrayList<>();

    public Investigador(String nombre, int edad) {
        this.nombre = nombre;
        this.edad = edad;
    }

    public String getNombre() {
        return nombre;
    }

    public int getEdad() {
        return edad;
    }


    public int getCantidadExperimentos(){
        return experimentosRealizados.size();
    }

    public boolean agregarExperimento(Experimento experimento){
        Objects.requireNonNull(experimento, "El experimento no puede ser nulo");
        return experimentosRealizados.add(experimento);
    }

    public List<Experimento> getExperimentosRealizados() {
        return Collections.unmodifiableList(experimentosRealizados);
    }


}
