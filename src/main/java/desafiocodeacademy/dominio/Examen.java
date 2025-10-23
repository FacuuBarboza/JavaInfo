package desafiocodeacademy.dominio;

public class Examen {

    private int nota;
    private int numero;

    public Examen(int numero, int nota) {
        this.numero = numero;
        this.nota = nota;
    }

    public int getNota() {
        return nota;
    }

    public int getNumero() {
        return numero;
    }

    public boolean esAprobado() {
        return this.nota >= 60;
    }
//    public boolean esFallida() {
//        return this.getPuntos() == 0;
//    }
}
