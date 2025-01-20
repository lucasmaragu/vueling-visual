package model;


import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;


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

    private static List<Vuelo> vuelos = new ArrayList<>();

    public Vuelo() {
        this.tripulantes = new ArrayList<>();
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

    public static void mostrarVuelosDisponibles(List<Vuelo> vuelosDisponibles) {
        System.out.println("\n--- Vuelos disponibles ---");
        if (vuelosDisponibles.isEmpty()) {
            System.out.println("No hay vuelos disponibles en este momento.");
        } else {
            for (Vuelo vuelo : vuelosDisponibles) {
                System.out.println(vuelo);
            }
        }
    }

    public static List<Vuelo> filtrarPorDestino(Scanner scanner, List<Vuelo> vuelosDisponibles) {
        System.out.println("\n--- Destinos disponibles ---");
        List<Ubicacion> destinos = new ArrayList<>();
        for (Vuelo vuelo : vuelosDisponibles) {
            if (!destinos.contains(vuelo.getDestino())) {
                destinos.add(vuelo.getDestino());
            }
        }

        for (int i = 0; i < destinos.size(); i++) {
            System.out.println((i + 1) + ". " + destinos.get(i));
        }

        System.out.print("Seleccione el número del destino: ");
        int seleccion;

        try {
            seleccion = scanner.nextInt();
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Selección inválida. Intente nuevamente.");
            scanner.nextLine();
            return new ArrayList<>();
        }

        if (seleccion < 1 || seleccion > destinos.size()) {
            System.out.println("Selección fuera de rango. Intente nuevamente.");
            return new ArrayList<>();
        }

        Ubicacion destinoSeleccionado = destinos.get(seleccion - 1);
        List<Vuelo> vuelosFiltrados = new ArrayList<>();
        for (Vuelo vuelo : vuelosDisponibles) {
            if (vuelo.getDestino().equals(destinoSeleccionado)) {
                vuelosFiltrados.add(vuelo);
            }
        }

        return vuelosFiltrados;
    }

    public static List<Vuelo> filtrarPorFecha(Scanner scanner, List<Vuelo> vuelosDisponibles) {
        System.out.print("\nIngrese la fecha deseada para el vuelo (formato YYYY-MM-DD): ");
        String fechaInput = scanner.nextLine();
        LocalDate fechaDeseada;

        try {
            fechaDeseada = LocalDate.parse(fechaInput);
        } catch (Exception e) {
            System.out.println("Fecha inválida. Intente nuevamente.");
            return new ArrayList<>();
        }

        List<Vuelo> vuelosFiltrados = new ArrayList<>();
        for (Vuelo vuelo : vuelosDisponibles) {
            if (vuelo.getHoraSalida().toLocalDate().equals(fechaDeseada)) {
                vuelosFiltrados.add(vuelo);
            }
        }

        return vuelosFiltrados;
    }


    public static void añadirVuelo(Scanner scanner, List<Vuelo> vuelosDisponibles) {
        System.out.println("\n--- Añadir un vuelo ---");


        System.out.print("Ingrese el ID del vuelo: ");
        int idVuelo = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Ingrese el modelo del avión (Ej. A320, B737): ");
        String modeloAvion = scanner.nextLine();

        System.out.println("Seleccione la compañía aérea:");
        for (int i = 0; i < Companias.values().length; i++) {
            System.out.println((i + 1) + ". " + Companias.values()[i]);
        }
        System.out.print("Seleccione una opción: ");
        int opcionCompania = scanner.nextInt();
        scanner.nextLine();
        Companias companiaSeleccionada = Companias.values()[opcionCompania - 1];

        System.out.println("Seleccione el origen del vuelo:");
        for (int i = 0; i < Ubicacion.values().length; i++) {
            System.out.println((i + 1) + ". " + Ubicacion.values()[i]);
        }
        System.out.print("Seleccione una opción: ");
        int opcionOrigen = scanner.nextInt();
        scanner.nextLine();
        Ubicacion origenSeleccionado = Ubicacion.values()[opcionOrigen - 1];

        System.out.println("Seleccione el destino del vuelo:");
        for (int i = 0; i < Ubicacion.values().length; i++) {
            System.out.println((i + 1) + ". " + Ubicacion.values()[i]);
        }
        System.out.print("Seleccione una opción: ");
        int opcionDestino = scanner.nextInt();
        scanner.nextLine();
        Ubicacion destinoSeleccionado = Ubicacion.values()[opcionDestino - 1];

        System.out.print("Ingrese la fecha y hora de salida (formato YYYY-MM-DDTHH:MM): ");
        String horaSalidaInput = scanner.nextLine();
        LocalDateTime horaSalida = LocalDateTime.parse(horaSalidaInput);

        System.out.print("Ingrese la fecha y hora de llegada (formato YYYY-MM-DDTHH:MM): ");
        String horaLlegadaInput = scanner.nextLine();
        LocalDateTime horaLlegada = LocalDateTime.parse(horaLlegadaInput);

        System.out.print("Ingrese el precio del vuelo: ");
        double precioVuelo = scanner.nextDouble();

        System.out.print("Ingrese el precio del equipaje: ");
        double precioEquipaje = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Ingrese el país de origen: ");
        String paisOrigen = scanner.nextLine();

        System.out.print("Ingrese el número del vuelo: ");
        String numeroVuelo = scanner.nextLine();

        System.out.println("Seleccione el estado del vuelo:");
        for (int i = 0; i < EstadoVuelo.values().length; i++) {
            System.out.println((i + 1) + ". " + EstadoVuelo.values()[i]);
        }
        System.out.print("Seleccione una opción: ");
        int opcionEstadoVuelo = scanner.nextInt();
        scanner.nextLine();  // Limpiar el buffer
        EstadoVuelo estadoSeleccionado = EstadoVuelo.values()[opcionEstadoVuelo - 1];

        Vuelo nuevoVuelo = new Vuelo(idVuelo, modeloAvion, companiaSeleccionada, origenSeleccionado, destinoSeleccionado,
                horaSalida, horaLlegada, precioVuelo, precioEquipaje, paisOrigen, numeroVuelo, estadoSeleccionado);

        vuelosDisponibles.add(nuevoVuelo);

        System.out.println("Vuelo añadido con éxito:");
        System.out.println(nuevoVuelo);
    }
    public static List<Vuelo> obtenerVuelos() {
        return vuelos;
    }
}