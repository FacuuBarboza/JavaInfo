package desafiopoo;

public class Mision {

    private int puntos;
    private int numero;

    public Mision(int numero, int puntos) {
        this.numero = numero;
        this.puntos = puntos;
    }

    public int getPuntos() {
        return puntos;
    }

    public int getMision() {
        return numero;
    }

    public boolean esFallida() {
        return this.getPuntos() == 0;
    }

}
