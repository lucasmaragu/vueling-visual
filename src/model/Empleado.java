package model;

public abstract class Empleado extends Usuario {

    private String idEmpleado;
    private int edad;

    public Empleado(String idUsuario, String nombreUsuario, String apellidoUsuario, String apellido2Usuario, String correoUsuario,
                    String idEmpleado, int edad, String contrasena) {
        super(idUsuario, nombreUsuario, apellidoUsuario, apellido2Usuario, correoUsuario, contrasena);
        this.idEmpleado = idEmpleado;
        this.edad = edad;
    }

    public String getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(String idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }
}
