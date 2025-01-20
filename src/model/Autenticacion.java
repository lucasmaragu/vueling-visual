package model;

import java.util.ArrayList;
import java.util.List;

public class Autenticacion {

    private List<Cliente> clientes;
    private List<PersonalAdministrativo> administrativos;

    public Autenticacion() {
        clientes = new ArrayList<>();
        administrativos = new ArrayList<>();
        clientes.add(new Cliente("cliente1",  "Juan", "Pérez", "González", "juan@correo.com", "1234"));
        clientes.add(new Cliente("cliente2", "María", "López", "Rodríguez", "maria@correo.com", "abcd"));
        administrativos.add(new PersonalAdministrativo("admin1", "Ana", "García", "Martínez", "ana@correo.com", "123", 35, "admin001", "Departamento A", "adminpass"));
        administrativos.add(new PersonalAdministrativo("admin2", "Carlos", "Jiménez", "Sánchez", "carlos@correo.com", "124", 42, "admin002", "Departamento B", "adminpass"));
    }


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
