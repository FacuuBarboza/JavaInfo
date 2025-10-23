package desafiocodeacademy;

import desafiocodeacademy.dominio.Alumno;
import desafiocodeacademy.dominio.CodeAcademy;

import java.util.Random;
import java.util.Scanner;

public class CodeChads {

    public static void main(String[] args) {
        System.out.println("¡Bienvenido a CodeChads Academy!");
        // 1. Inicializamos las herramientas
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        //Creamos al Alumno Principal
        Alumno alumnoPrincipal = new Alumno("Stone Chad (Principal)", random);
        //Creamos la academia
        CodeAcademy academia = new CodeAcademy(alumnoPrincipal, random);

        //Menu
        boolean salir = false;
        while (!salir) {
            System.out.println("\n### CodeAcademy ###");
            System.out.println("1. Mostrar resultados del Alumno Principal");
            System.out.println("2. Ejecutar simulación de 4 alumnos");
            System.out.println("3. Salir");
            System.out.print("Selecciona una opción: ");

            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    academia.mostrarResultados();
                    break;
                case "2":
                    academia.simulacionAlumnos();
                    break;
                case "3":
                    salir = true;
                    break;
                default:
                    System.out.println("Opción no válida. Inténtalo de nuevo.");
            }
        }

        // 5. Salir del programa
        System.out.println("¡Gracias por usar CodeChads Academy! Adiós.");
        scanner.close();
    }
}



