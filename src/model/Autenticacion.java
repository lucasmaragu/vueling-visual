package model;

import java.util.ArrayList;
import java.util.List;

public class Autenticacion {

    private List<Cliente> clientes;
    private List<PersonalAdministrativo> administrativos;


    public Autenticacion() {
        clientes = new ArrayList<>();
        administrativos = new ArrayList<>();
        // Agregar algunos usuarios iniciales (esto es solo de ejemplo)
        clientes.add(new Cliente("cliente1", "Juan", "Pérez", "González", "juan@correo.com", "1234"));
        clientes.add(new Cliente("cliente2", "María", "López", "Rodríguez", "maria@correo.com", "abcd"));
        administrativos.add(new PersonalAdministrativo("admin1", "Ana", "García", "Martínez", "ana@correo.com", "123", 35, "admin001", "Departamento A", "adminpass"));
        administrativos.add(new PersonalAdministrativo("admin2", "Carlos", "Jiménez", "Sánchez", "carlos@correo.com", "124", 42, "admin002", "Departamento B", "adminpass"));
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    // Método para registrar un nuevo cliente
    public boolean registrarCliente(String idCliente, String nombre, String apellido1, String apellido2, String email, String contrasena) {
        // Verificar si ya existe un cliente con el mismo ID
        for (Cliente cliente : clientes) {
            if (cliente.getIdCliente().equals(idCliente)) {
                return false; // Si ya existe, no se puede registrar
            }
        }

        // Crear un nuevo cliente y agregarlo a la lista
        Cliente nuevoCliente = new Cliente(idCliente, nombre, apellido1, apellido2, email, contrasena);
        clientes.add(nuevoCliente);
        return true;
    }

    // Método para iniciar sesión (ya lo tienes implementado)
    public Usuario iniciarSesion(String idUsuario, String contrasena) {
        for (Cliente cliente : clientes) {
            if (cliente.getIdCliente().equals(idUsuario) && cliente.getContrasena().equals(contrasena)) {
                System.out.println("Inicio de sesión exitoso como Cliente.");
                return cliente;
            }
        }

        for (PersonalAdministrativo admin : administrativos) {
            if (admin.getIdUsuario().equals(idUsuario) && admin.getContrasena().equals(contrasena)) {
                System.out.println("Inicio de sesión exitoso como Administrador.");
                return admin;
            }
        }

        System.out.println("Credenciales incorrectas.");
        return null;
    }
}
