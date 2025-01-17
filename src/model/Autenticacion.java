package model;

import java.util.ArrayList;
import java.util.List;

public class Autenticacion {

    // Lista de clientes simulados (esto sería reemplazado por una base de datos)
    private List<Cliente> clientes;

    public Autenticacion() {
        // Crear algunos clientes de ejemplo (esto debe estar en una base de datos)
        clientes = new ArrayList<>();
        clientes.add(new Cliente("cliente1",  "Juan", "Pérez", "González", "juan@correo.com", "1234"));
        clientes.add(new Cliente("cliente2", "María", "López", "Rodríguez", "maria@correo.com", "abcd"));
    }


    public Cliente iniciarSesion(String idCliente, String contrasena) {
        for (Cliente cliente : clientes) {
            if (cliente.getIdCliente().equals(idCliente) && cliente.getContrasena().equals(contrasena)) {
                System.out.println("Inicio de sesión exitoso.");
                return cliente;  // Devuelve el cliente autenticado
            }
        }
        System.out.println("Credenciales incorrectas.");
        return null;
    }
}
