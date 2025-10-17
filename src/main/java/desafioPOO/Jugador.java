package desafioPOO;

import java.util.List;

public class Jugador {
    private List<Mision> misiones;

    public Jugador(List<Mision> misiones) {
        this.misiones = misiones;
    }

    public List<Mision> getMisiones() {
        return misiones;
    }

    public int calcularTotal() {
        int total = 0;
        for (Mision m: misiones) {
            total += m.getPuntos();
        }
        return total;
    }

    public Mision maxPuntos() {
        if (misiones.isEmpty()) {
            return null;
        }

        Mision maxMision = misiones.getFirst();

        for (Mision m: misiones) {
            if (m.getPuntos() > maxMision.getPuntos()) {
                maxMision = m;
            }
        }
        return maxMision;
    }

    public Mision fallasteEnAlguna() {
        for (Mision m: misiones) {
            if (m.esFallida()) {
                return m;
            }
        }
        return null;
    }

    public Mision maxMision() {
        if (misiones.isEmpty()) {
            return null;
        }

        Mision maxMision = misiones.getFirst();

        for (Mision m: misiones) {
            if (m.getPuntos() > maxMision.getPuntos()) {
                maxMision = m;
            }
        }
        return maxMision;
    }

    public Mision minMision() {
        if (misiones.isEmpty()) {
            return null;
        }

        Mision minMision = misiones.getFirst();

        for (Mision m: misiones) {
            if (m.getPuntos() < minMision.getPuntos()) {
                minMision = m;
            }
        }
        return minMision;
    }

    public boolean sosConstante() {
        if (misiones.size() < 2) {
            return true;
        }

        Mision misionMaxima = maxMision();
        Mision misionMinima = minMision();

        if (misionMaxima == null || misionMinima == null) {
            return false; // O manejar el error como prefieras
        }

        return (misionMaxima.getPuntos() - misionMinima.getPuntos()) < 20;
    }

}
