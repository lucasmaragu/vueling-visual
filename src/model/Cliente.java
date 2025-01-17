package model;

import java.util.ArrayList;
import java.util.List;

public class Cliente extends Usuario {
    private String idCliente;
    private List<Reservas> reservas;

    // Cambiar para recibir idCliente y otros parámetros correctamente
    public Cliente(String idCliente, String nombre, String apellido, String apellido2, String correo, String contrasena) {
        super(idCliente, nombre, apellido, apellido2, correo, contrasena);  // Se pasa idCliente desde el constructor de Usuario
        this.idCliente = idCliente;  // Asignamos el valor del parámetro idCliente
        this.reservas = new ArrayList<>();  // Inicializamos la lista de reservas
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
