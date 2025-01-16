package model;

public class Asiento {
    private String numeroAsiento;
    private boolean disponible;

    public Asiento(String numeroAsiento) {
        this.numeroAsiento = numeroAsiento;
        this.disponible = true; // Inicialmente todos los asientos están disponibles
    }

    public String getNumeroAsiento() {
        return numeroAsiento;
    }

    public void setNumeroAsiento(String numeroAsiento) {
        this.numeroAsiento = numeroAsiento;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    @Override
    public String toString() {
        return numeroAsiento;
    }
}
