package model;

import java.time.LocalDateTime;

public class Ticket {
    private String nombrePasajero;
    private String companiaAerea;
    private String origen;
    private String destino;
    private LocalDateTime fechaSalida;
    private LocalDateTime fechaLlegada;
    private double precioTotal;

    // Constructor
    public Ticket(String nombrePasajero, String companiaAerea, String origen, String destino,
                  LocalDateTime fechaSalida, LocalDateTime fechaLlegada, double precioTotal) {
        this.nombrePasajero = nombrePasajero;
        this.companiaAerea = companiaAerea;
        this.origen = origen;
        this.destino = destino;
        this.fechaSalida = fechaSalida;
        this.fechaLlegada = fechaLlegada;
        this.precioTotal = precioTotal;
    }

    // Métodos getters
    public String getNombrePasajero() {
        return nombrePasajero;
    }

    public String getCompaniaAerea() {
        return companiaAerea;
    }

    public String getOrigen() {
        return origen;
    }

    public String getDestino() {
        return destino;
    }

    public LocalDateTime getFechaSalida() {
        return fechaSalida;
    }

    public LocalDateTime getFechaLlegada() {
        return fechaLlegada;
    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    // Métodos setters (si es necesario)
    public void setNombrePasajero(String nombrePasajero) {
        this.nombrePasajero = nombrePasajero;
    }

    public void setCompaniaAerea(String companiaAerea) {
        this.companiaAerea = companiaAerea;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public void setFechaSalida(LocalDateTime fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public void setFechaLlegada(LocalDateTime fechaLlegada) {
        this.fechaLlegada = fechaLlegada;
    }

    public void setPrecioTotal(double precioTotal) {
        this.precioTotal = precioTotal;
    }

    // Método toString() para mostrar los detalles del ticket
    @Override
    public String toString() {
        return "Ticket [nombrePasajero=" + nombrePasajero + ", companiaAerea=" + companiaAerea +
                ", origen=" + origen + ", destino=" + destino +
                ", fechaSalida=" + fechaSalida + ", fechaLlegada=" + fechaLlegada +
                ", precioTotal=" + precioTotal + "]";
    }
}
