package tpijava.service;

import java.io.IOException;

public interface ReporteServiceI {
    void exportarInvestigadoresCSV(String rutaArchivo) throws IOException;
    void exportarInvestigadoresCSV() throws IOException; //Para usar ruta por defecto
}
