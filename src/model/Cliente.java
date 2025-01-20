package model;

import java.util.ArrayList;
import java.util.List;

public class Cliente extends Usuario {
    private String idCliente;
    private List<Reservas> reservas;

    public Cliente(String idCliente, String nombre, String apellido, String apellido2, String correo, String contrasena) {
        super(idCliente, nombre, apellido, apellido2, correo, contrasena);
        this.idCliente = idCliente;
        this.reservas = new ArrayList<>();
    }

    public String getIdCliente() {
        return idCliente;
    }

    public List<Reservas> getReservas() {
        return reservas;
    }

    public void setReservas(List<Reservas> reservas) {
        this.reservas = reservas;
    }
}
