package model;

public class PersonalAdministrativo extends Empleado {
    private String idPersonalAdministrativo;
    private String departamento;

    public PersonalAdministrativo(String idUsuario, String nombre, String apellido, String apellido2, String correo,
                                  String idEmpleado, int edad, String idPersonalAdministrativo, String departamento, String contrasena) {
        super(idUsuario, nombre, apellido, apellido2, correo, idEmpleado, edad, contrasena);
        this.idPersonalAdministrativo = idPersonalAdministrativo;
        this.departamento = departamento;
    }

    public String getIdPersonalAdministrativo() {
        return idPersonalAdministrativo;
    }

    public void setIdPersonalAdministrativo(String idPersonalAdministrativo) {
        this.idPersonalAdministrativo = idPersonalAdministrativo;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public void gestionarReservas() {
        System.out.println("Gestionando reservas...");
    }

    public void gestionarCambiosDeVuelo() {
        System.out.println("Gestionando cambios de vuelo...");
    }
}
