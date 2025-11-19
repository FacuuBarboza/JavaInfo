package tpijava.org.laboratorio.dto;

public class ReporteDTO {
    private double promedioDuracion;
    private double porcentajeExito;
    private int totalExitos;
    private int totalFallos;
    private int totalExperimentos;

    public ReporteDTO(double promedioDuracion, double porcentajeExito, int totalExitos, int totalFallos, int totalExperimentos) {
        this.promedioDuracion = promedioDuracion;
        this.porcentajeExito = porcentajeExito;
        this.totalExitos = totalExitos;
        this.totalFallos = totalFallos;
        this.totalExperimentos = totalExperimentos;
    }

    public double getPromedioDuracion() { return promedioDuracion; }
    public double getPorcentajeExito() { return porcentajeExito; }
    public int getTotalExitos() { return totalExitos; }
    public int getTotalFallos() { return totalFallos; }
    public int getTotalExperimentos() { return totalExperimentos; }
}