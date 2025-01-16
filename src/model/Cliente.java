package model;

import java.util.List;

public class Cliente {
    private String idCliente;
    private List<Reservas> reservas;

    public Cliente(String idCliente, List<Reservas> reservas) {
        this.idCliente = idCliente;
        this.reservas = reservas;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public List<Reservas> getReservas(){
        return reservas;
    }

    public void setReservas(List<Reservas> reservas) {
        this.reservas = reservas;
    }
}
