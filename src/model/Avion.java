package model;

public class Avion {

    private String idAvion;
    private String modelo;
    private int asientosTotales;
    private int asiendosDisponibles;


    public Avion() {
    }

    public Avion(String idAvion, String modelo, int asientosTotales, int asiendosDisponibles) {
        this.idAvion = idAvion;
        this.modelo = modelo;
        this.asientosTotales = asientosTotales;
        this.asiendosDisponibles = asiendosDisponibles;
    }

    public String getIdAvion() {
        return idAvion;
    }

    public void setIdAvion(String idAvion) {
        this.idAvion = idAvion;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getAsientosTotales() {
        return asientosTotales;
    }

    public void setAsientosTotales(int asientosTotales) {
        this.asientosTotales = asientosTotales;
    }

    public int getAsiendosDisponibles() {
        return asiendosDisponibles;
    }

    public void setAsiendosDisponibles(int asiendosDisponibles) {
        this.asiendosDisponibles = asiendosDisponibles;
    }

    @Override
    public String toString() {
        return "Avion{" +
                "idAvion='" + idAvion + '\'' +
                ", modelo='" + modelo + '\'' +
                ", asientosTotales=" + asientosTotales +
                ", asiendosDisponibles=" + asiendosDisponibles +
                '}';
    }
}
