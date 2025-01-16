package view.console;

import model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static List<Vuelo> vuelosDisponibles = new ArrayList<>();
    private static List<Reservas> reservasRealizadas = new ArrayList<>();

    public static void main(String[] args) {
        // Crear algunos vuelos de ejemplo con LocalDateTime
        Vuelo vuelo1 = new Vuelo(1234, "A320", Companias.EASYJET, Ubicacion.ANDORRA, Ubicacion.BERLIN,
                LocalDateTime.of(2025, 1, 13, 10, 30), LocalDateTime.of(2025, 1, 13, 15, 45), 180.00, 12.00, "España", "A2", EstadoVuelo.EN_VUELO);
        Vuelo vuelo2 = new Vuelo(5678, "B737", Companias.RYANAIR, Ubicacion.MADRID, Ubicacion.LONDRES,
                LocalDateTime.of(2025, 1, 14, 11, 00), LocalDateTime.of(2025, 1, 14, 12, 30), 150.00, 10.00, "España", "R1", EstadoVuelo.EN_VUELO);

        vuelosDisponibles.add(vuelo1);
        vuelosDisponibles.add(vuelo2);

        // Crear un menú interactivo
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n--- MENÚ DE VUELING ---");
            System.out.println("1. Ver vuelos disponibles");
            System.out.println("2. Hacer una reserva");
            System.out.println("3. Ver reservas realizadas");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer del scanner

            switch (opcion) {
                case 1:
                    mostrarVuelosDisponibles();
                    break;
                case 2:
                    menuReserva(scanner);
                    break;
                case 3:
                    verReservas();
                    break;
                case 4:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción no válida, intente nuevamente.");
            }
        } while (opcion != 4);

        scanner.close();
    }

    private static void mostrarVuelosDisponibles() {
        System.out.println("\n--- Vuelos disponibles ---");
        for (Vuelo vuelo : vuelosDisponibles) {
            System.out.println(vuelo);
        }
    }

    private static void menuReserva(Scanner scanner) {
        System.out.println("\n--- Realizar una reserva ---");
        System.out.println("1. Elegir destino");
        System.out.println("2. Elegir fecha");
        System.out.print("Seleccione una opción: ");
        int opcion;

        try {
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer
        } catch (Exception e) {
            System.out.println("Selección inválida. Intente nuevamente.");
            scanner.nextLine(); // Limpiar el buffer
            return;
        }

        switch (opcion) {
            case 1:
                elegirDestino(scanner);
                break;
            case 2:
                elegirFecha(scanner);
                break;
            default:
                System.out.println("Opción no válida. Intente nuevamente.");
        }
    }

    private static void elegirDestino(Scanner scanner) {
        System.out.println("\n--- Ubicaciones disponibles ---");
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
            scanner.nextLine(); // Limpiar el buffer
        } catch (Exception e) {
            System.out.println("Selección inválida. Intente nuevamente.");
            scanner.nextLine(); // Limpiar el buffer
            return;
        }

        if (seleccion < 1 || seleccion > destinos.size()) {
            System.out.println("Selección fuera de rango. Intente nuevamente.");
            return;
        }

        Ubicacion destinoSeleccionado = destinos.get(seleccion - 1);

        System.out.println("\n--- Vuelos disponibles hacia " + destinoSeleccionado + " ---");
        for (Vuelo vuelo : vuelosDisponibles) {
            if (vuelo.getDestino().equals(destinoSeleccionado)) {
                System.out.println("Compañía: " + vuelo.getCompaniaAerea() + ", Origen: " + vuelo.getOrigen() + ", Salida: " + vuelo.getHoraSalida() + ", Llegada: " + vuelo.getHoraLlegada());
            }
        }
    }

    private static void elegirFecha(Scanner scanner) {
        System.out.print("\nIngrese la fecha deseada para el vuelo (formato YYYY-MM-DD): ");
        String fechaInput = scanner.nextLine();
        LocalDate fechaDeseada;

        try {
            fechaDeseada = LocalDate.parse(fechaInput);
        } catch (Exception e) {
            System.out.println("Fecha inválida. Intente nuevamente.");
            return;
        }

        // Filtrar vuelos por la fecha deseada
        List<Vuelo> vuelosFiltrados = new ArrayList<>();
        for (Vuelo vuelo : vuelosDisponibles) {
            if (vuelo.getHoraSalida().toLocalDate().equals(fechaDeseada)) {
                vuelosFiltrados.add(vuelo);
            }
        }

        // Mostrar vuelos disponibles en esa fecha
        if (vuelosFiltrados.isEmpty()) {
            System.out.println("No hay vuelos disponibles para la fecha especificada.");
            return;
        }

        System.out.println("\n--- Vuelos disponibles para la fecha " + fechaDeseada + " ---");
        for (int i = 0; i < vuelosFiltrados.size(); i++) {
            Vuelo vuelo = vuelosFiltrados.get(i);
            System.out.println((i + 1) + ". Compañía: " + vuelo.getCompaniaAerea() + ", Origen: " + vuelo.getOrigen() + ", Destino: " + vuelo.getDestino() + ", Salida: " + vuelo.getHoraSalida() + ", Llegada: " + vuelo.getHoraLlegada());
        }

        // Solicitar al usuario que seleccione un vuelo
        System.out.print("Seleccione el número del vuelo que desea reservar: ");
        int seleccion;
        try {
            seleccion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer
        } catch (Exception e) {
            System.out.println("Selección inválida. Intente nuevamente.");
            scanner.nextLine(); // Limpiar el buffer
            return;
        }

        if (seleccion < 1 || seleccion > vuelosFiltrados.size()) {
            System.out.println("Selección fuera de rango. Intente nuevamente.");
            return;
        }

        Vuelo vueloSeleccionado = vuelosFiltrados.get(seleccion - 1);

        // Solicitar los datos del cliente
        System.out.print("Ingrese su ID de cliente: ");
        String idCliente = scanner.nextLine();

        // Asientos reservados (en este ejemplo se reserva un asiento por vuelo)
        List<Asiento> asientos = new ArrayList<>();
        System.out.print("Ingrese el número del asiento a reservar: ");
        String numeroAsiento = scanner.nextLine();
        Asiento asiento = new Asiento(numeroAsiento);
        asientos.add(asiento);

        // Crear la reserva con LocalDateTime
        Reservas reserva = new Reservas("R" + (reservasRealizadas.size() + 1), EstadoReserva.EN_PROCESO,
                "NR" + (reservasRealizadas.size() + 1), LocalDateTime.now(), vueloSeleccionado.getHoraSalida(),
                vueloSeleccionado.getIdVuelo(), asientos, idCliente, 200.00);

        reservasRealizadas.add(reserva);
        System.out.println("Reserva realizada exitosamente: " + reserva);
    }

    private static void verReservas() {
        System.out.println("\n--- Reservas realizadas ---");
        if (reservasRealizadas.isEmpty()) {
            System.out.println("No hay reservas realizadas.");
        } else {
            for (Reservas reserva : reservasRealizadas) {
                System.out.println(reserva);
            }
        }
    }
}
