package tpijava.org.laboratorio.service;

import tpijava.org.laboratorio.domain.Investigador;
import tpijava.org.laboratorio.repository.InvestigadorRepositoryI;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ReporteService implements ReporteServiceI {

    private final InvestigadorRepositoryI investigadorRepo;

    public ReporteService(InvestigadorRepositoryI investigadorRepo) {
        this.investigadorRepo = investigadorRepo;
    }

    @Override
    public void exportarInvestigadoresCSV(String rutaArchivo) throws IOException {
        List<Investigador> invs = investigadorRepo.getInvestigadores();
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(rutaArchivo))) {
            // cabecera
            writer.write("nombre,edad,cantidad_experimentos");
            writer.newLine();
            for (Investigador inv : invs) {
                writer.write(String.format("%s,%d,%d", escapeCsv(inv.getNombre()), inv.getEdad(), inv.getCantidadExperimentos()));
                writer.newLine();
            }
        }
    }

    @Override
    public void exportarInvestigadoresCSV() throws IOException {
        String defaultFileName = "investigadores.csv";
        String userHome = System.getProperty("user.home");
        Path defaultPath = Paths.get(userHome, defaultFileName);
        exportarInvestigadoresCSV(defaultPath.toString());
    }

    private String escapeCsv(String s) {
        if (s == null) return "";
        // minimal escaping: si contiene comas o comillas, encerrar entre comillas y escapar comillas
        if (s.contains(",") || s.contains("\"") || s.contains("\n")) {
            return "\"" + s.replace("\"", "\"\"") + "\"";
        }
        return s;
    }
}