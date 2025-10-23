package desafiocodeacademy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CodeAcademy {
    Alumno alumno;
    Random random;

    public CodeAcademy(Alumno alumno, Random random) {
        this.alumno = alumno;
        this.random = random;
    }

    public void mostrarResultados() {

        boolean aprobada = alumno.aproboTodas();
        boolean aproboAlguna = alumno.aproboAlguno();
        String inconsistente = alumno.pruebaMasInconsistente();
        String progresivo = alumno.mejoraProgresiva();
        int Total = alumno.calcularTotal();
        String rango = alumno.obtenerRango();


        if (aprobada) {
            System.out.println("Resultado: Aprobaste todas. ¡Backend Sensei!");
        } else if (aproboAlguna) {
            System.out.println("Resultado: Algunas aprobadas. Sos un refactor en progreso.");
        } else {
            System.out.println("Resultado: No aprobaste ninguna. ¡Sos un clon de frontend!");
        }

        System.out.println(inconsistente);
        System.out.println(progresivo);
        System.out.println("Notas ordenadas de mayor a menor: " + alumno.notasOrdenadas());
        System.out.println("Puntaje total acumulado: " + Total);
        System.out.println("Tu rango según tu puntaje total de notas es: " + rango);


    }

    public void simulacionAlumnos(){
        //Lista local para los 4 alumnos de simulación
        List<Alumno> alumnosSimulados = new ArrayList<>();
        int limite = 101;


        alumnosSimulados.add(new Alumno("Juan", this.random, limite));
        alumnosSimulados.add(new Alumno("Hernan", this.random, limite));
        alumnosSimulados.add(new Alumno("Joaco", this.random, limite));
        alumnosSimulados.add(new Alumno("Aristobulo", this.random, limite));


        //Encontrar el mejor promedio
        Alumno mejorAlumno = alumnosSimulados.get(0);

        for (Alumno alu : alumnosSimulados) {
            //Usamos el metodo de Alumno
            if (alu.promedioNotas() > mejorAlumno.promedioNotas()) {
                mejorAlumno = alu;
            }
        }
        System.out.println("El mejor promedio es el " + mejorAlumno.getNombre() + " con un promedio de " + mejorAlumno.promedioNotas());


        //Encontrar el alumno más consistente
        Alumno alumnoMasConsistente = alumnosSimulados.get(0);
        int difMinima = alumnoMasConsistente.menorDiferencia();

        for (Alumno alu : alumnosSimulados) {
            int difActual = alu.menorDiferencia();
            if (difActual < difMinima) {
                difMinima = difActual;
                alumnoMasConsistente = alu;
            }
        }
        System.out.println("El alumno con menor diferencia entre sus notas es " + alumnoMasConsistente.getNombre() + " con una diferencia de " + difMinima + " puntos");


        //Encontrar la peor nota en el 3er examen
        Alumno peorAlumno = alumnosSimulados.get(0);
        int peorNota = peorAlumno.notaTercerExamen();

        for (Alumno alu : alumnosSimulados) {
            int notaActual = alu.notaTercerExamen();
            if (notaActual < peorNota) {
                peorNota = notaActual;
                peorAlumno = alu;
            }
        }
        System.out.println("El peor desempeño en el 3er examen es del " + peorAlumno.getNombre() + " con una nota de " + peorNota);
    }




}
