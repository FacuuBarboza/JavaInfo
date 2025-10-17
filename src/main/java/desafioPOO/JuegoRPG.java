package desafioPOO;

public class JuegoRPG {
    Jugador jugador;

    public JuegoRPG(Jugador jugador) {
        this.jugador = jugador;
    }

    public void mostrarResultados() {
        int total = jugador.calcularTotal();
        Mision mejor = jugador.maxPuntos();
        Mision fallida = jugador.fallasteEnAlguna();


        String mensaje = (total > 300) ? "¡Felicidades! Sos una verdadero Chad del RPG" : "Te falta entrenamiento, joven aprendiz";
        System.out.println(mensaje);
        System.out.println("Puntos acumulados hasta ahora: " + total);
        System.out.println("Tu mejor desempeño fue en la misión " + mejor.getMision() + " con " + mejor.getPuntos() + " puntos");
        System.out.println((fallida != null) ? "Fallaste en la misión " + fallida.getMision() + ", que el backend te tenga piedad": "No fallaste, seguí por ese camino");

        if(jugador.sosConstante()) {
            System.out.println("¡Ejecución constante! Sos el dueño de la arquitectura del software");
        }


    }
}
