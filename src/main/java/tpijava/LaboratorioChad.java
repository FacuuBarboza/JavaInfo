package tpijava;

import tpijava.domain.Experimento;
import tpijava.domain.ExperimentoFisico;
import tpijava.domain.ExperimentoQuimico;
import tpijava.domain.Investigador;
import tpijava.dto.ReporteDTO;
import tpijava.repository.ExperimentoRepository;
import tpijava.repository.ExperimentoRepositoryI;
import tpijava.repository.InvestigadorRepository;
import tpijava.repository.InvestigadorRepositoryI;
import tpijava.service.LaboratorioService;
import tpijava.service.ReporteService;


import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class LaboratorioChad {

    private final LaboratorioService laboratorioService;
    private final ReporteService reporteService;
    private final Scanner scanner = new Scanner(System.in);

    // Mantener referencia al repository de investigadores para validar existencia desde la UI
    private final InvestigadorRepositoryI investigadorRepo;

    public LaboratorioChad() {
        // crear los repos y guardarlos en campos para consultas desde la consola
        this.investigadorRepo = new InvestigadorRepository();
        ExperimentoRepositoryI experimentoRepo = new ExperimentoRepository();
        this.laboratorioService = new LaboratorioService(investigadorRepo, experimentoRepo);
        this.reporteService = new ReporteService(investigadorRepo);
    }

    public static void main(String[] args) {
        LaboratorioChad app = new LaboratorioChad();
        app.mostrarMenuPrincipal();
    }

    public void mostrarMenuPrincipal() {
        int opcion;
        do {
            System.out.println("\n=== LABORATORIO CHAD ===");
            System.out.println("1) Registrar investigador");
            System.out.println("2) Registrar experimento químico");
            System.out.println("3) Registrar experimento físico");
            System.out.println("4) Mostrar todos los experimentos");
            System.out.println("5) Mostrar estadísticas exitosos y fallidos");
            System.out.println("6) Mostrar experimento de mayor duración");
            System.out.println("7) Mostrar investigador con más experimentos");
            System.out.println("8) Exportar investigadores a CSV");
            System.out.println("0) Salir");
            System.out.print("Elija una opción: ");

            String line = scanner.nextLine().trim();
            if (line.isEmpty()) {
                opcion = -1;
            } else {
                try {
                    opcion = Integer.parseInt(line);
                } catch (NumberFormatException e) {
                    opcion = -1;
                }
            }

            switch (opcion) {
                case 1 -> gestionarRegistroInvestigador();
                case 2 -> {
                    if (investigadorRepo.getInvestigadores().isEmpty()) {
                        System.out.println("Antes de registrar un experimento debe registrar al menos un investigador.");
                    } else {
                        gestionarRegistroExperimentoQuimico();
                    }
                }
                case 3 -> {
                    if (investigadorRepo.getInvestigadores().isEmpty()) {
                        System.out.println("Antes de registrar un experimento debe registrar al menos un investigador.");
                    } else {
                        gestionarRegistroExperimentoFisico();
                    }
                }
                case 4 -> mostrarTodosLosExperimentos();
                case 5 -> mostrarEstadisticasGenerales();
                case 6 -> mostrarExperimentoMayorDuracion();
                case 7 -> mostrarInvestigadorConMasExperimentos();
                case 8 -> gestionarExportarCSV();
                case 0 -> System.out.println("Saliendo... ¡hasta luego!");
                default -> System.out.println("Opción inválida. Intente nuevamente.");
            }
        } while (opcion != 0);
    }

    private void gestionarRegistroInvestigador() {
        System.out.print("Nombre del investigador: ");
        String nombre = scanner.nextLine().trim();
        if (nombre.isEmpty()) {
            System.out.println("Nombre vacío. Operación cancelada.");
            return;
        }
        System.out.print("Edad: ");
        String edadStr = scanner.nextLine().trim();
        int edad;
        try {
            edad = Integer.parseInt(edadStr);
        } catch (NumberFormatException e) {
            System.out.println("Edad inválida. Operación cancelada.");
            return;
        }

        try {
            boolean ok = laboratorioService.registrarInvestigador(nombre, edad);
            if (ok) {
                System.out.println("Investigador registrado correctamente.");
            } else {
                System.out.println("No se registró el investigador (posible duplicado por nombre).");
            }
        } catch (IllegalArgumentException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    private void gestionarRegistroExperimentoQuimico() {
        System.out.print("Nombre del experimento químico: ");
        String nombre = scanner.nextLine().trim();
        if (nombre.isEmpty()) {
            System.out.println("Nombre vacío. Cancelado.");
            return;
        }
        Integer duracion = leerEntero("Duración en minutos: ");
        if (duracion == null) return;

        Boolean resultado = leerBoolean("Resultado exitoso? (s/n): ");
        if (resultado == null) return;

        System.out.print("Tipo de reactivo: ");
        String reactivo = scanner.nextLine().trim();
        if (reactivo.isEmpty()) {
            System.out.println("Tipo de reactivo vacío. Cancelado.");
            return;
        }

        System.out.print("Nombre del investigador responsable: ");
        String investigadorNombre = scanner.nextLine().trim();
        if (investigadorNombre.isEmpty()) {
            System.out.println("Nombre de investigador vacío. Cancelado.");
            return;
        }

        // Validación/creación interactiva del investigador responsable
        if (!verificarInvestigadorExistente(investigadorNombre)) {
            System.out.println("Operación cancelada: no se pudo asegurar la existencia del investigador.");
            return;
        }

        boolean ok = laboratorioService.registrarExpQuimico(nombre, duracion, resultado, reactivo, investigadorNombre);
        if (ok) {
            System.out.println("Experimento químico registrado correctamente.");
        } else {
            System.out.println("No se registró el experimento. Revise los datos e intente nuevamente.");
        }
    }

    private void gestionarRegistroExperimentoFisico() {
        System.out.print("Nombre del experimento físico: ");
        String nombre = scanner.nextLine().trim();
        if (nombre.isEmpty()) {
            System.out.println("Nombre vacío. Cancelado.");
            return;
        }
        Integer duracion = leerEntero("Duración en minutos: ");
        if (duracion == null) return;

        Boolean resultado = leerBoolean("Resultado exitoso? (s/n): ");
        if (resultado == null) return;

        System.out.print("Instrumento utilizado: ");
        String instrumento = scanner.nextLine().trim();
        if (instrumento.isEmpty()) {
            System.out.println("Instrumento vacío. Cancelado.");
            return;
        }

        System.out.print("Nombres de investigadores (separados por comas): ");
        String nombresLine = scanner.nextLine().trim();
        List<String> nombres = Arrays.stream(nombresLine.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());

        if (nombres.isEmpty()) {
            System.out.println("No se proporcionaron investigadores. Cancelado.");
            return;
        }

        // Creación de investigadores faltantes
        boolean okFaltantes = verificarInvestigadoresExistentes(nombres);
        if (!okFaltantes) {
            System.out.println("Operación cancelada: no se pudieron asegurar todos los investigadores.");
            return;
        }

        boolean ok = laboratorioService.registrarExpFisico(nombre, duracion, resultado, instrumento, nombres);
        if (ok) {
            System.out.println("Experimento físico registrado correctamente.");
        } else {
            System.out.println("No se registró el experimento. Revise los datos e intente nuevamente.");
        }
    }

    /**
     Se usa para que no haya investigadores faltantes al registrar
     */
    private boolean verificarInvestigadorExistente(String nombre) {
        if (investigadorRepo.buscarPorNombre(nombre).isPresent()) {
            return true; // ya existe
        }
        System.out.printf("No existe un investigador llamado '%s'. ¿Desea crearlo ahora? (s/n): ", nombre);
        String resp = scanner.nextLine().trim().toLowerCase();
        if (resp.isEmpty() || (!resp.equals("s") && !resp.equals("si") && !resp.equals("y") && !resp.equals("yes"))) {
            return false;
        }
        Integer edad = leerEntero("Ingrese la edad del nuevo investigador: ");
        if (edad == null) {
            System.out.println("Edad inválida. No se creó el investigador.");
            return false;
        }
        boolean creado = laboratorioService.registrarInvestigador(nombre, edad);
        if (creado) {
            System.out.println("Investigador creado correctamente.");
            return true;
        } else {
            System.out.println("No se pudo crear el investigador (posible duplicado).");
            return investigadorRepo.buscarPorNombre(nombre).isPresent();
        }
    }

    /**
     Acá busca que todos los investigadores que agregaste al agregar un experimento, existan la lista
     de investigadores registrados. Si falta alguno, te pregunta si queres crearlo.
     */
    private boolean verificarInvestigadoresExistentes(List<String> nombres) {
        List<String> faltantes = nombres.stream()
                .filter(n -> investigadorRepo.buscarPorNombre(n).isEmpty())
                .toList();

        if (faltantes.isEmpty()) return true;

        System.out.println("Faltan los siguientes investigadores: ");
        faltantes.forEach(n -> System.out.println(" - " + n));

        for (String falt : faltantes) {
            System.out.printf("Desea crear '%s' ahora? (s/n): ", falt);
            String resp = scanner.nextLine().trim().toLowerCase();
            if (resp.isEmpty() || (!resp.equals("s") && !resp.equals("si") && !resp.equals("y") && !resp.equals("yes"))) {
                System.out.println("No se creará '" + falt + "'.");
                return false;
            }
            Integer edad = leerEntero("Ingrese la edad de '" + falt + "': ");
            if (edad == null) {
                System.out.println("Edad inválida. No se creó '" + falt + "'.");
                return false;
            }
            boolean creado = laboratorioService.registrarInvestigador(falt, edad);
            if (!creado && investigadorRepo.buscarPorNombre(falt).isEmpty()) {
                System.out.println("No se pudo crear '" + falt + "'. Operación cancelada.");
                return false;
            }
            System.out.println("Investigador '" + falt + "' creado.");
        }

        return true;
    }

    // Aclaración: como no se usa un back o donde guardar los datos
    // en un archivo o base de datos, cada vez que se corre la app
    // se pierden los datos. Esto es solo una demo de consola.
    // Aquí muestra todos los experimentos registrados durante la ejecución actual.
    private void mostrarTodosLosExperimentos() {
        List<Experimento> experimentoList = laboratorioService.getTodosLosExperimentos();
        if (experimentoList.isEmpty()) {
            System.out.println("No hay experimentos registrados.");
            return;
        }
        System.out.println("\n--- Experimentos registrados ---");
        for (Experimento e : experimentoList) {
            String tipo = e instanceof ExperimentoQuimico ? "Químico" : e instanceof ExperimentoFisico ? "Físico" : "Desconocido";
            String resultado = e.isExitoso() ? "Éxito" : "Fallo";
            System.out.printf("- %s | tipo: %s | duración: %d min | resultado: %s%n",
                    e.getNombre(), tipo, e.getDuracionMinutos(), resultado);
        }
    }

    //Imprime los puntos 4 y 6 del enunciado
    // Exitos y fallos
    // Porcentaje de exitos
    // Promedio de duracion
    private void mostrarEstadisticasGenerales() {
        ReporteDTO dto = laboratorioService.getEstadisticasGenerales();
        System.out.println("\n--- Estadísticas generales ---");
        System.out.printf("Total experimentos: %d%n", dto.getTotalExperimentos());
        System.out.printf("Total exitos: %d%n", dto.getTotalExitos());
        System.out.printf("Total fallos: %d%n", dto.getTotalFallos());
        System.out.printf("Promedio duración: %.2f min%n", dto.getPromedioDuracion());
        System.out.printf("Porcentaje éxito: %.2f %% %n", dto.getPorcentajeExito());
    }

    private void mostrarExperimentoMayorDuracion() {
        Experimento mayor = laboratorioService.getExperimentoMayorDuracion();
        if (mayor == null) {
            System.out.println("No hay experimentos registrados.");
            return;
        }
        System.out.println("\n--- Experimento de mayor duración ---");
        System.out.printf("Nombre: %s | Duración: %d min | Resultado: %s%n",
                mayor.getNombre(), mayor.getDuracionMinutos(), mayor.isExitoso() ? "Éxito" : "Fallo");
    }

    private void mostrarInvestigadorConMasExperimentos() {
        Investigador top = laboratorioService.getInvestigadorConMasExperimentos();
        if (top == null) {
            System.out.println("No hay investigadores o no hay experimentos.");
            return;
        }
        System.out.println("\n--- Investigador con más experimentos ---");
        System.out.printf("Nombre: %s | Edad: %d | Cantidad de experimentos: %d%n",
                top.getNombre(), top.getEdad(), top.getCantidadExperimentos());
    }

    //En caso de no especificar ruta, usa la de por defecto y la exporta en el sistema de archivos local
    private void gestionarExportarCSV() {
        System.out.print("Ruta de archivo CSV (ej: investigadores.csv). Dejar vacío para la ruta por defecto: ");
        String ruta = scanner.nextLine().trim();
        try {
            if (ruta.isEmpty()) {
                reporteService.exportarInvestigadoresCSV();
            } else {
                reporteService.exportarInvestigadoresCSV(ruta);
            }
            System.out.println("Exportado correctamente.");
        } catch (IOException e) {
            System.out.println("Error al exportar CSV: " + e.getMessage());
        }
    }

    private Integer leerEntero(String prompt) {
        System.out.print(prompt);
        String s = scanner.nextLine().trim();
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            System.out.println("Número inválido. Cancelado.");
            return null;
        }
    }

    private Boolean leerBoolean(String prompt) {
        System.out.print(prompt);
        String s = scanner.nextLine().trim().toLowerCase();
        if (s.isEmpty()) {
            System.out.println("Entrada vacía. Cancelado.");
            return null;
        }
        if (s.equals("s") || s.equals("si") || s.equals("y") || s.equals("yes")) return true;
        if (s.equals("n") || s.equals("no")) return false;
        System.out.println("Respuesta no reconocida. Use 's' o 'n'. Cancelado.");
        return null;
    }
}