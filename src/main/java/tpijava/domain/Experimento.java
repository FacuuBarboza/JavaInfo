package tpijava.domain;

public abstract class Experimento {
    private String nombre;
    private int duracionMinutos;
    private boolean resultado;

    public Experimento(String nombre, int duracionMinutos, boolean resultado) {
        this.nombre = nombre;
        this.duracionMinutos = duracionMinutos;
        this.resultado = resultado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDuracionMinutos(int duracionMinutos) {
        this.duracionMinutos = duracionMinutos;
    }




    public boolean isExitoso() {
        return resultado;
    }

    public int getDuracionMinutos(){
        return duracionMinutos;
    }
}
