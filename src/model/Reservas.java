package model;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class Reservas {
    private String idReserva;
    private EstadoReserva estado;
    private String numeroReserva;
    private LocalDateTime fechaReserva;
    private LocalDateTime fechaVuelo;
    private int idVuelo;
    private List<Asiento> asientoReservado;
    private String idCliente;
    private Double totalPago;

    // Constructor
    public Reservas(String idReserva, EstadoReserva estado, String numeroReserva, LocalDateTime fechaReserva, LocalDateTime fechaVuelo,
                    int idVuelo, List<Asiento> asientoReservado, String idCliente, Double totalPago) {
        this.idReserva = idReserva;
        this.estado = estado;
        this.numeroReserva = numeroReserva;
        this.fechaReserva = fechaReserva;
        this.fechaVuelo = fechaVuelo;
        this.idVuelo = idVuelo;
        this.asientoReservado = asientoReservado;
        this.idCliente = idCliente;
        this.totalPago = totalPago;
    }

    // Métodos getter y setter
    public String getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(String idReserva) {
        this.idReserva = idReserva;
    }

    public EstadoReserva getEstado() {
        return estado;
    }

    public void setEstado(EstadoReserva estado) {
        this.estado = estado;
    }

    public String getNumeroReserva() {
        return numeroReserva;
    }

    public void setNumeroReserva(String numeroReserva) {
        this.numeroReserva = numeroReserva;
    }

    public LocalDateTime getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(LocalDateTime fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public LocalDateTime getFechaVuelo() {
        return fechaVuelo;
    }

    public void setFechaVuelo(LocalDateTime fechaVuelo) {
        this.fechaVuelo = fechaVuelo;
    }

    public int getIdVuelo() {
        return idVuelo;
    }

    public void setIdVuelo(int idVuelo) {
        this.idVuelo = idVuelo;
    }

    public List<Asiento> getAsientoReservado() {
        return asientoReservado;
    }

    public void setAsientoReservado(List<Asiento> asientoReservado) {
        this.asientoReservado = asientoReservado;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public Double getTotalPago() {
        return totalPago;
    }

    public void setTotalPago(Double totalPago) {
        this.totalPago = totalPago;
    }

    @Override
    public String toString() {
        return "Reserva ID: " + idReserva + ", Cliente: " + idCliente + ", Vuelo: " + idVuelo + ", Fecha de Reserva: " + fechaReserva +
                ", Estado: " + estado + ", Asientos: " + asientoReservado.size() + ", Total Pago: " + totalPago;
    }
}
