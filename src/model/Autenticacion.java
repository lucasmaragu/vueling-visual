package model;

import java.util.ArrayList;
import java.util.List;

public class Autenticacion {

    // Lista de clientes simulados (esto sería reemplazado por una base de datos)
    private List<Cliente> clientes;
    private List<PersonalAdministrativo> administrativos;

    public Autenticacion() {
        // Crear algunos clientes de ejemplo (esto debe estar en una base de datos)
        clientes = new ArrayList<>();
        administrativos = new ArrayList<>();
        clientes.add(new Cliente("cliente1",  "Juan", "Pérez", "González", "juan@correo.com", "1234"));
        clientes.add(new Cliente("cliente2", "María", "López", "Rodríguez", "maria@correo.com", "abcd"));
        administrativos.add(new PersonalAdministrativo("admin1", "Ana", "García", "Martínez", "ana@correo.com", "123", 35, "admin001", "Departamento A", "adminpass"));
        administrativos.add(new PersonalAdministrativo("admin2", "Carlos", "Jiménez", "Sánchez", "carlos@correo.com", "124", 42, "admin002", "Departamento B", "adminpass"));
    }


    public Usuario iniciarSesion(String idUsuario, String contrasena) {
        // Verificar si es un cliente
        for (Cliente cliente : clientes) {
            if (cliente.getIdCliente().equals(idUsuario) && cliente.getContrasena().equals(contrasena)) {
                System.out.println("Inicio de sesión exitoso como Cliente.");
                return cliente;  // Devuelve el cliente autenticado
            }
        }

        // Verificar si es un administrador
        for (PersonalAdministrativo admin : administrativos) {
            if (admin.getIdUsuario().equals(idUsuario) && admin.getContrasena().equals(contrasena)) {
                System.out.println("Inicio de sesión exitoso como Administrador.");
                return admin;  // Devuelve el administrador autenticado
            }
        }

        System.out.println("Credenciales incorrectas.");
        return null;
    }
}
