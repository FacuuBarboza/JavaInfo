package org.example;

import java.util.Arrays;
import java.util.Random;

public class CodeChadsAcademy {

    public static void main(String[] args) {
        //1era Parte
        int[] examenes = new int[5];

        Random random = new Random();
        int limite = 101;

        examenes[0] = random.nextInt(limite);
        examenes[1] = random.nextInt(limite);
        examenes[2] = random.nextInt(limite);
        if (examenes[1] < 60){
            examenes[3] = 100;
        } else {
            examenes [3] = examenes [1];
        }

        if ( examenes[0] + examenes[2] >= 150) {
            examenes[4] = 95;
        } else {
            examenes[4] = 70;
        }

        //2da Parte - 1
        boolean aprobado = false;
        boolean aproboAlguna = false;

        for (int i = 0; i < examenes.length; i++) {
            if (examenes[i] >= 60) {
                aprobado = true;
                aproboAlguna = true;
            } else {
                aprobado = false;
            }
        }

        if (aprobado) {
            System.out.println("Resultado: Aprobaste todas. ¡Backend Sensei!");
        } else if (aproboAlguna) {
            System.out.println("Resultado: Algunas aprobadas. Sos un refactor en progreso.");
        } else {
            System.out.println("Resultado: No aprobaste ninguna. ¡Sos un clon de frontend!");
        }

        //2da Parte - 2
        int mayorDif = 0;
        int examenUno = 0;
        int examenDos = 0;
        for (int i = 0; i < examenes.length-1; i++) {
            int diferencia = Math.abs(examenes[i] - examenes[i+1]);
            if ( mayorDif < diferencia) {
                mayorDif = diferencia;
                examenUno = i+1;
                examenDos = i+2;
            }
        }
        System.out.println("Mayor salto fue de " + mayorDif + " puntos entre la prueba " + examenUno + " y la prueba " + examenDos );

        //2da Parte - 3
        boolean mejora = false;
        for (int i = 0; i < examenes.length-1; i++) {
            if (examenes[i] < examenes[i+1]) {
                mejora = true;
            } else {
                mejora = false;
                break;
            }
        }

        if (mejora) {
            System.out.println("¡Nivel PROGRESIVO! Sos un Stone Chad en crecimiento");
        }

        //2da Parte - 4
        int [] notasOrdenadas = Arrays.copyOf(examenes, examenes.length);
        int temporal = 0;
        for (int i = 0; i < examenes.length; i++) {
            for (int j = 0; j < examenes.length - 1; j++) {
                if (notasOrdenadas[j] < notasOrdenadas[j + 1]) {
                    temporal = notasOrdenadas[j];
                    notasOrdenadas[j] = notasOrdenadas[j + 1];
                    notasOrdenadas[j + 1] = temporal;
                }
            }

        }
        System.out.println("Notas ordenadas de mayor a menor: " + Arrays.toString(notasOrdenadas) );

        System.out.println("###################################################################################################");
        //2da Parte - 5
        System.out.println("####################### Revisando el total de puntos en los examenes ####################### ");
        System.out.println("####################### Tu rango es ####################### ");
        int totalNotas = 0;
        for (int i = 0; i < examenes.length; i++) {
            totalNotas += examenes[i];
        }
        if (totalNotas <250) {
            System.out.println("Normie total");
        } else if (totalNotas <350) {
            System.out.println("Soft Chad");
        } else if (totalNotas < 450) {
            System.out.println("Chad");
        } else {
            System.out.println("Stone Chad definitivo");
        }


        //2da Parte - 6
        System.out.println("####################### Simulación de notas cargadasd de 4 alumnos ####################### ");
        int [][] alumnos = new int [4][5];
        for (int i = 0; i < alumnos.length; i++) {
            for (int j = 0; j < alumnos[i].length; j++) {
                alumnos[i][j] = random.nextInt(limite);
            }
        }
        int mejorAlumno = 0;
        double promedioMasAlto = 0.0;
        for (int i = 0; i < alumnos.length; i++) {
            double acumNotas = 0;
            for (int j = 0; j < alumnos[i].length; j++) {
              acumNotas += alumnos[i][j];
            }
            double promedioActual = acumNotas / alumnos[i].length;
            if (promedioActual > promedioMasAlto) {
                promedioMasAlto = promedioActual;
                mejorAlumno = i +1;
            }
        }

        System.out.println("El mejor promedio es el alumno " + mejorAlumno + " con un promedio de " + promedioMasAlto);

//        for (int i = 0; i < alumnos.length; i++) {
//            for (int j = 0; j < alumnos[i].length; j++) {
//                System.out.print(alumnos[i][j] + " ");
//            }
//            System.out.println();
//        }

        int dif = 1000;
        for (int i = 0; i < alumnos.length; i++) {
            int acumDif = 0;
            for (int j = 0; j < alumnos.length-1; j++) {
                int diferencia = Math.abs(alumnos[i][j] - alumnos[i][j + 1]);
                acumDif += diferencia;
            }
            if(acumDif < dif) {
                dif = acumDif;
                mejorAlumno = i + 1;
            }
        }

        System.out.println("El alumno con menor diferencia entre sus notas es el alumno " + mejorAlumno + " con una diferencia de " + dif + " puntos");

        int peorNota = 100;
        int peorAlumno = 0;
        for (int i = 0; i < alumnos.length - 1; i++) {

            if ( alumnos[i][2] < peorNota) {
                peorNota = alumnos[i][2];
                peorAlumno = i + 1;
            }


        }

        System.out.println("El peor desempeño en el tercer examen es del alumno " + peorAlumno + " con una nota de " + peorNota + " puntos");
    }
}
