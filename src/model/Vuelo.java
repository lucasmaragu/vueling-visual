package model;


 import java.time.Duration;
 import java.time.LocalDate;
 import java.util.ArrayList;
 import java.time.LocalDateTime;
 import java.util.List;


public class Vuelo {
    private int idVuelo;
    private String idAvion;
    private Companias companiaAerea;
    private Ubicacion origen;
    private Ubicacion destino;
    private LocalDateTime horaSalida;
    private LocalDateTime horaLlegada;
    private Double precio;
    private Double precioEquipaje;
    private String nacionalidad;
    private String puertaEmbarque;
    private EstadoVuelo estado;
    private List<Tripulante> tripulantes;


    public Vuelo() {
    }

    public Vuelo(int idVuelo, String idAvion,  Companias companiaAerea, Ubicacion origen, Ubicacion destino, LocalDateTime horaSalida, LocalDateTime horaLlegada, Double precio, Double precioEquipaje, String nacionalidad, String puertaEmbarque, EstadoVuelo estado) {
        this.idVuelo = idVuelo;
        this.idAvion = idAvion;
        this.companiaAerea = companiaAerea;
        this.origen = origen;
        this.destino = destino;
        this.horaSalida = horaSalida;
        this.horaLlegada = horaLlegada;
        this.precio = precio;
        this.precioEquipaje = precioEquipaje;
        this.nacionalidad = nacionalidad;
        this.puertaEmbarque = puertaEmbarque;
        this.estado = estado;
        this.tripulantes = new ArrayList<>();
    }

    public int getIdVuelo() {
        return idVuelo;
    }

    public void setIdVuelo(int idVuelo) {
        this.idVuelo = idVuelo;
    }

    public Companias getCompaniaAerea() {
        return companiaAerea;
    }

    public void setCompaniaAerea(Companias companiaAerea) {
        this.companiaAerea = companiaAerea;
    }

    public Ubicacion getOrigen() {
        return origen;
    }

    public void setOrigen(Ubicacion origen) {
        this.origen = origen;
    }

    public Ubicacion getDestino() {
        return destino;
    }

    public void setDestino(Ubicacion destino) {
        this.destino = destino;
    }

    public LocalDateTime getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(LocalDateTime horaSalida) {
        this.horaSalida = horaSalida;
    }

    public LocalDateTime getHoraLlegada() {
        return horaLlegada;
    }

    public void setHoraLlegada(LocalDateTime horaLlegada) {
        this.horaLlegada = horaLlegada;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Double getPrecioEquipaje() {
        return precioEquipaje;
    }

    public void setPrecioEquipaje(Double precioEquipaje) {
        this.precioEquipaje = precioEquipaje;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getPuertaEmbarque() {
        return puertaEmbarque;
    }

    public void setPuertaEmbarque(String puertaEmbarque) {
        this.puertaEmbarque = puertaEmbarque;
    }

    public EstadoVuelo getEstado() {
        return estado;
    }

    public void setEstado(EstadoVuelo estado) {
        this.estado = estado;
    }

    public List<Tripulante> getTripulantes() {
        return tripulantes;
    }

    @Override
    public String toString() {
        return "Vuelo{" +
                "idVuelo=" + idVuelo +
                ", companiaAerea='" + companiaAerea + '\'' +
                ", origen=" + origen +
                ", destino=" + destino +
                ", fechaSalida=" + horaSalida +
                ", fechaLlegada=" + horaLlegada +
                ", precio=" + precio +
                ", precioEquipaje=" + precioEquipaje +
                ", nacionalidad='" + nacionalidad + '\'' +
                ", puertaEmbarque='" + puertaEmbarque + '\'' +
                ", estado=" + estado +
                '}';
    }
    public String calcularDuracion(LocalDateTime horaSalida, LocalDateTime horaLlegada) {

        Duration duracion = Duration.between(horaSalida, horaLlegada);


        long duracionHoras = duracion.toHours();
        long duracionMinutos = duracion.toMinutes() % 60;


        return duracionHoras + " horas y " + duracionMinutos + " minutos";
    }
    public void actualizarEstado(EstadoVuelo nuevoEstado) {
        this.estado = nuevoEstado;
    }

    public void addTripulante(Tripulante tripulante) {
        this.tripulantes.add(tripulante);
    }
}
