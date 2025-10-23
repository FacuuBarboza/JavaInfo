package desafiocodeacademy.dominio;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Alumno {

    private List<Examen> examenes;
    private String nombre;

    //Constructor 1 para un alumno con 5 examenes
    public Alumno(String nombre, Random random) {
        this.nombre = nombre;
        this.examenes = new ArrayList<>();
        int limite = 101;

        int nota1 = random.nextInt(limite);
        int nota2 = random.nextInt(limite);
        int nota3 = random.nextInt(limite);
        this.examenes.add(new Examen(1, nota1));
        this.examenes.add(new Examen(2, nota2));
        this.examenes.add(new Examen(3, nota3));

        if (nota2 < 60) {
            this.examenes.add(new Examen(4, 100));
        } else {
            this.examenes.add(new Examen(4, nota2));
        }

        if (nota1 + nota3 >= 150) {
            this.examenes.add(new Examen(5, 95));
        } else {
            this.examenes.add(new Examen(5, 70));
        }


    }

    //Constructor 2 para la simulacion de 4 alumnos con 5 examenes
    public Alumno(String nombre, Random random, int limite) {
        this.nombre = nombre;
        this.examenes = new ArrayList<>();

        for (int i = 1; i <= 5; i++) {
            int notaAleatoria = random.nextInt(limite);
            this.examenes.add(new Examen(i, notaAleatoria));
        }
    }

    public String getNombre() {
        return nombre;
    }

    public List<Examen> getExamenes() {
        return examenes;
    }

    public boolean aproboTodas() {

        if (examenes.isEmpty()) {
            return false;
        }


        for (Examen e : examenes) {
            if (!e.esAprobado()) {
                return false;
            }
        }
        return true;
    }

    public boolean aproboAlguno() {

        for (Examen e : examenes) {
            if (e.esAprobado()) {
                return true;
            }
        }
        return false;
    }

    public String pruebaMasInconsistente() {


        int mayorDif = 0;
        int examenUno = 0;
        int examenDos = 0;


        for (int i = 0; i < examenes.size() - 1; i++) {

            //Nota del examen actual (i)
            int notaActual = examenes.get(i).getNota();

            //Nota del examen siguiente
            int notaSiguiente = examenes.get(i + 1).getNota();

            int diferencia = Math.abs(notaActual - notaSiguiente);

            if (mayorDif < diferencia) {
                mayorDif = diferencia;
                examenUno = i + 1;
                examenDos = i + 2;
            }
        }

        return "Mayor salto fue de " + mayorDif + " puntos entre la prueba " + examenUno + " y la prueba " + examenDos;
    }


    public String mejoraProgresiva() {
        for (int i = 0; i < examenes.size() - 1; i++) {
            int notaActual = examenes.get(i).getNota();
            int notaSiguiente = examenes.get(i + 1).getNota();

            if (notaSiguiente <= notaActual) {
                return "No tenes un nivel progresivo, pero podes mejorar";
            }
        }
        return "¡Nivel PROGRESIVO! Sos un Stone Chad en crecimiento";
    }

    //Ordena las notas sin necesidad de usar el sort.
    public List<Integer> notasOrdenadas() {
        List<Integer> notas = new ArrayList<>();
        for (Examen e : examenes) {
            notas.add(e.getNota());
        }

        int temporal = 0;
        for (int i = 0; i < notas.size(); i++) {
            for (int j = 0; j < notas.size() - 1; j++) {

                if (notas.get(j) < notas.get(j + 1)) {

                    temporal = notas.get(j);
                    notas.set(j, notas.get(j + 1));
                    notas.set(j + 1, temporal);
                }
            }
        }

        return notas;
    }

    public int calcularTotal() {
        int total = 0;
        for (Examen e : examenes) {
            total += e.getNota();
        }
        return total;
    }

    //Para obtener el rango del alumno según su puntaje total
    public String obtenerRango() {
        int total = calcularTotal();
        if (total < 250) {
            return "Normie total";
        } else if (total < 350) {
            return "Soft Chad";
        } else if (total < 450) {
            return "Chad";
        } else {
            return "Stone Chad definitivo";
        }
    }

    //Calcula el promedio de notas del alumno
    public double promedioNotas() {
        if (examenes.isEmpty()) {
            return 0.0;
        }
        int total = 0;
        for (Examen e : examenes) {
            total += e.getNota();
        }
        return (double) total / examenes.size();
    }

    //Acumula la diferencia entre notas consecutivas para luego comparar con otros alumnos
    public int menorDiferencia() {

        int acumDif = 0;
        for (int i = 0; i < examenes.size() - 1; i++) {
            int notaActual = examenes.get(i).getNota();
            int notaSiguiente = examenes.get(i + 1).getNota();

            acumDif += Math.abs(notaActual - notaSiguiente);
        }
        return acumDif;
    }

    public int notaTercerExamen() {
        if (examenes.size() < 3) {
            return -1; // Indica que no hay tercer examen
        }
        return examenes.get(2).getNota();
    }
}