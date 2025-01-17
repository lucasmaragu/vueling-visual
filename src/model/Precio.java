package model;

public class Precio {
    private Double precioBase;
    private Double precioEquipaje;

    public Precio() {
        this.precioBase = 0.0;
        this.precioEquipaje = 0.0;
    }

    public Precio(Double precioBase, Double precioEquipaje) {
        this.precioBase = precioBase;
        this.precioEquipaje = precioEquipaje;
    }

    public Double getPrecioBase() {
        return precioBase;
    }

    public void setPrecioBase(Double precioBase) {
        this.precioBase = precioBase;
    }

    public Double getPrecioEquipaje() {
        return precioEquipaje;
    }

    public void setPrecioEquipaje(Double precioEquipaje) {
        this.precioEquipaje = precioEquipaje;
    }

    public Double calcularPrecioTotal(int cantidadEquipaje) {
        if (cantidadEquipaje < 0) {
            throw new IllegalArgumentException("La cantidad de equipaje no puede ser negativa.");
        }
        return precioBase + (precioEquipaje * cantidadEquipaje);
    }

    @Override
    public String toString() {
        return "Precio{" +
                "precioBase=" + precioBase +
                ", precioEquipaje=" + precioEquipaje +
                '}';
    }
}