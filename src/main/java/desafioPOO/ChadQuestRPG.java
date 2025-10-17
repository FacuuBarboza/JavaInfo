package desafioPOO;

import java.util.ArrayList;
import java.util.List;

public class ChadQuestRPG {
    public static void main(String[] args) {
        System.out.println("Bienvenido a Chad Quest RPG, el juego de rol más épico jamás creado.");
        System.out.println("Carga de misiones...");
        List<Mision> misiones = new ArrayList<>();

        misiones.add(new Mision(1,75));
        misiones.add(new Mision(2,80));
        misiones.add(new Mision(3,75));
        misiones.add(new Mision(4,90));
        misiones.add(new Mision(5,73));

        Jugador jugador = new Jugador(misiones);
        JuegoRPG juego = new JuegoRPG(jugador);
        juego.mostrarResultados();




//        int[] puntosPorMision = new int[5];
//        int total = 0;
//        puntosPorMision[0] = 50;
//        puntosPorMision[1] = 80;
//        puntosPorMision[2] = 60;
//        puntosPorMision[3] = 90;
//        puntosPorMision[4] = 70;
//
//        //Parte 2.1
//        for (int i = 0; i < puntosPorMision.length; i++) {
//
//            total += puntosPorMision[i];
//
//        }
//        System.out.println("Puntos acumulados hasta ahora: " + total);
//
//        //Parte 2.2
//
//        if (total > 300) {
//            System.out.println("¡Felicidades! Sos una verdadero Chad del RPG");
//        } else {
//            System.out.println("Te falta entrenamiento, joven aprendiz");
//        }
//
//        //Parte 2.3
//        int misionMaxPuntos = 1;
//        int maxPuntos = 50;
//        for (int i = 0; i < puntosPorMision.length; i++) {
//            if (puntosPorMision[i] > maxPuntos) {
//                maxPuntos = puntosPorMision[i];
//                misionMaxPuntos = i + 1;
//            }
//        }
//        System.out.println("Tu mejor desempeño fue en la misión " + misionMaxPuntos + " con " + maxPuntos + " puntos");
//
//        //Parte 2.4
//        for (int i = 0; i < puntosPorMision.length; i++) {
//            if (puntosPorMision[i] == 0) {
//                System.out.println("Fallaste en la misión " + i + ", que el backend te tenga piedad");
//            }
//        }
//
//        //Parte 2.5
//        int minPuntos = 50;
//        for(int i = 0; i < puntosPorMision.length; i++){
//            if(puntosPorMision[i] < minPuntos){
//                minPuntos = puntosPorMision[i];
//            } else if(puntosPorMision[i] > maxPuntos) {
//                maxPuntos = puntosPorMision[i];
//            }
//        }
//        if (maxPuntos - minPuntos < 20) {
//            System.out.println("¡Ejecución constante! Sos el dueño de la arquitectura del software");
//        }
//
//        //Parte 2.6
//        int misionesMayores = 1;
//        for (int i = 0; i < puntosPorMision.length; i++) {
//            if (puntosPorMision[i] > 75) {
//                misionesMayores++;
//            }
//        }
//
//        if (misionesMayores >= 3) {
//            System.out.println("Nivel desbloqueado: Mini Stone Chad");
//        }
//
//        //Parte 2.7
//        int acumuladorPuntos = 0;
//        for (int i = 0; i < puntosPorMision.length; i++) {
//            System.out.println("Misión " + (i + 1) + ": " + puntosPorMision[i] + " puntos");
//            System.out.println("Puntos acumulados: " + (acumuladorPuntos += puntosPorMision[i]));
//        }
//
//        System.out.println("Total de puntos: " + total);
    }
}
