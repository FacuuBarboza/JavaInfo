package tpijava.org.laboratorio.ui;

import tpijava.org.laboratorio.domain.Experimento;
import tpijava.org.laboratorio.domain.ExperimentoFisico;
import tpijava.org.laboratorio.domain.ExperimentoQuimico;
import tpijava.org.laboratorio.domain.Investigador;
import tpijava.org.laboratorio.repository.ExperimentoRepository;
import tpijava.org.laboratorio.repository.ExperimentoRepositoryI;
import tpijava.org.laboratorio.repository.InvestigadorRepository;
import tpijava.org.laboratorio.repository.InvestigadorRepositoryI;
import tpijava.org.laboratorio.dto.ReporteDTO;
import tpijava.org.laboratorio.service.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class MenuService {

    private final InvestigadorServiceI investigadorService;
    private final ExperimentoServiceI experimentoService;
    private final ReporteServiceI reporteService;

    private final Scanner scanner = new Scanner(System.in);
    private final InvestigadorRepositoryI investigadorRepo;

    public MenuService() {
        this.investigadorRepo = new InvestigadorRepository();
        ExperimentoRepositoryI experimentoRepo = new ExperimentoRepository();

        this.investigadorService = new InvestigadorService(investigadorRepo);
        this.experimentoService = new ExperimentoService(experimentoRepo, investigadorRepo);
        this.reporteService = new ReporteService(investigadorRepo);
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
            boolean ok = investigadorService.registrarInvestigador(nombre, edad);
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

        if (!verificarInvestigadorExistente(investigadorNombre)) {
            System.out.println("Operación cancelada: no se pudo asegurar la existencia del investigador.");
            return;
        }

        boolean ok = experimentoService.registrarExpQuimico(nombre, duracion, resultado, reactivo, investigadorNombre);
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

        boolean okFaltantes = verificarInvestigadoresExistentes(nombres);
        if (!okFaltantes) {
            System.out.println("Operación cancelada: no se pudieron asegurar todos los investigadores.");
            return;
        }

        boolean ok = experimentoService.registrarExpFisico(nombre, duracion, resultado, instrumento, nombres);
        if (ok) {
            System.out.println("Experimento físico registrado correctamente.");
        } else {
            System.out.println("No se registró el experimento. Revise los datos e intente nuevamente.");
        }
    }

    private boolean verificarInvestigadorExistente(String nombre) {
        if (investigadorRepo.buscarPorNombre(nombre).isPresent()) {
            return true;
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
        boolean creado = investigadorService.registrarInvestigador(nombre, edad);
        if (creado) {
            System.out.println("Investigador creado correctamente.");
            return true;
        } else {
            System.out.println("No se pudo crear el investigador (posible duplicado).");
            return investigadorRepo.buscarPorNombre(nombre).isPresent();
        }
    }

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
            boolean creado = investigadorService.registrarInvestigador(falt, edad);
            if (!creado && investigadorRepo.buscarPorNombre(falt).isEmpty()) {
                System.out.println("No se pudo crear '" + falt + "'. Operación cancelada.");
                return false;
            }
            System.out.println("Investigador '" + falt + "' creado.");
        }

        return true;
    }

    private void mostrarTodosLosExperimentos() {
        List<Experimento> experimentoList = experimentoService.getTodosLosExperimentos();
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

    private void mostrarEstadisticasGenerales() {
        ReporteDTO dto = experimentoService.getEstadisticasGenerales();
        System.out.println("\n--- Estadísticas generales ---");
        System.out.printf("Total experimentos: %d%n", dto.getTotalExperimentos());
        System.out.printf("Total exitos: %d%n", dto.getTotalExitos());
        System.out.printf("Total fallos: %d%n", dto.getTotalFallos());
        System.out.printf("Promedio duración: %.2f min%n", dto.getPromedioDuracion());
        System.out.printf("Porcentaje éxito: %.2f %% %n", dto.getPorcentajeExito());
    }

    private void mostrarExperimentoMayorDuracion() {
        Experimento mayor = experimentoService.getExperimentoMayorDuracion();
        if (mayor == null) {
            System.out.println("No hay experimentos registrados.");
            return;
        }
        System.out.println("\n--- Experimento de mayor duración ---");
        System.out.printf("Nombre: %s | Duración: %d min | Resultado: %s%n",
                mayor.getNombre(), mayor.getDuracionMinutos(), mayor.isExitoso() ? "Éxito" : "Fallo");
    }

    private void mostrarInvestigadorConMasExperimentos() {
        Investigador top = investigadorService.getInvestigadorConMasExperimentos();
        if (top == null) {
            System.out.println("No hay investigadores o no hay experimentos.");
            return;
        }
        System.out.println("\n--- Investigador con más experimentos ---");
        System.out.printf("Nombre: %s | Edad: %d | Cantidad de experimentos: %d%n",
                top.getNombre(), top.getEdad(), top.getCantidadExperimentos());
    }

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
