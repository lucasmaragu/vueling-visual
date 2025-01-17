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
    private static Autenticacion autenticacion = new Autenticacion();  // Instancia de Autenticación

    public static void main(String[] args) {
        // Crear algunos vuelos de ejemplo
        Vuelo vuelo1 = new Vuelo(1234, "A320", Companias.EASYJET, Ubicacion.ANDORRA, Ubicacion.BERLIN,
                LocalDateTime.of(2025, 1, 13, 10, 30), LocalDateTime.of(2025, 1, 13, 15, 45),
                180.00, 12.00, "España", "A2", EstadoVuelo.EN_VUELO);

        Vuelo vuelo2 = new Vuelo(5678, "B737", Companias.RYANAIR, Ubicacion.MADRID, Ubicacion.LONDRES,
                LocalDateTime.of(2025, 1, 14, 11, 00), LocalDateTime.of(2025, 1, 14, 12, 30),
                150.00, 10.00, "España", "R1", EstadoVuelo.EN_VUELO);

        vuelosDisponibles.add(vuelo1);
        vuelosDisponibles.add(vuelo2);

        // Autenticación antes de mostrar el menú
        if (!iniciarSesion()) {
            System.out.println("No se pudo iniciar sesión. Saliendo del sistema...");
            return; // Sale si no se puede iniciar sesión
        }

        // Menú interactivo
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
                    realizarReserva(scanner);
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

    private static boolean iniciarSesion() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("--- Iniciar sesión ---");
        System.out.print("Ingrese su ID de cliente: ");
        String idCliente = scanner.nextLine();

        System.out.print("Ingrese su contraseña: ");
        String contrasena = scanner.nextLine();

        Cliente cliente = autenticacion.iniciarSesion(idCliente, contrasena);
        return cliente != null; // Si la autenticación es exitosa, retorna true
    }

    private static void mostrarVuelosDisponibles() {
        System.out.println("\n--- Vuelos disponibles ---");
        if (vuelosDisponibles.isEmpty()) {
            System.out.println("No hay vuelos disponibles en este momento.");
        } else {
            for (Vuelo vuelo : vuelosDisponibles) {
                System.out.println(vuelo);
            }
        }
    }

    private static void realizarReserva(Scanner scanner) {
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

        List<Vuelo> vuelosFiltrados = new ArrayList<>();
        if (opcion == 1) {
            vuelosFiltrados = filtrarPorDestino(scanner);
        } else if (opcion == 2) {
            vuelosFiltrados = filtrarPorFecha(scanner);
        } else {
            System.out.println("Opción no válida. Intente nuevamente.");
            return;
        }

        if (vuelosFiltrados.isEmpty()) {
            System.out.println("No hay vuelos disponibles para la opción seleccionada.");
            return;
        }

        System.out.println("\n--- Seleccione un vuelo ---");
        for (int i = 0; i < vuelosFiltrados.size(); i++) {
            System.out.println((i + 1) + ". " + vuelosFiltrados.get(i));
        }

        System.out.print("Seleccione el número del vuelo: ");
        int seleccionVuelo;
        try {
            seleccionVuelo = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer
        } catch (Exception e) {
            System.out.println("Selección inválida. Intente nuevamente.");
            scanner.nextLine(); // Limpiar el buffer
            return;
        }

        if (seleccionVuelo < 1 || seleccionVuelo > vuelosFiltrados.size()) {
            System.out.println("Selección fuera de rango. Intente nuevamente.");
            return;
        }

        Vuelo vueloSeleccionado = vuelosFiltrados.get(seleccionVuelo - 1);

        System.out.print("Ingrese el número de personas que viajarán: ");
        int cantidadPersonas = scanner.nextInt();
        scanner.nextLine();

        System.out.print("¿Incluye equipaje? (true/false): ");
        boolean incluyeEquipaje = scanner.nextBoolean();
        scanner.nextLine();

        Precio precio = new Precio(vueloSeleccionado.getPrecio(), vueloSeleccionado.getPrecioEquipaje());
        double precioPorPersona = precio.calcularPrecioTotal(incluyeEquipaje ? 1 : 0);

        List<Ticket> tickets = new ArrayList<>();
        for (int i = 1; i <= cantidadPersonas; i++) {
            System.out.print("Ingrese el nombre del pasajero #" + i + ": ");
            String nombrePasajero = scanner.nextLine();

            // Crear un ticket para el pasajero
            Ticket ticket = new Ticket(
                    nombrePasajero,
                    vueloSeleccionado.getCompaniaAerea().toString(),  // Compañía aérea
                    vueloSeleccionado.getOrigen().toString(),         // Origen
                    vueloSeleccionado.getDestino().toString(),        // Destino
                    vueloSeleccionado.getHoraSalida(),                // Fecha de ida
                    vueloSeleccionado.getHoraLlegada(),               // Fecha de vuelta
                    precioPorPersona                                   // Precio total
            );
            tickets.add(ticket);
        }

        // Mostrar los tickets generados
        System.out.println("\n--- TICKETS GENERADOS ---");
        for (Ticket ticket : tickets) {
            System.out.println(ticket);
        }

        // Crear un objeto de reserva
        Reservas nuevaReserva = new Reservas(
                "RES-" + (reservasRealizadas.size() + 1),
                EstadoReserva.CONFIRMADA,
                "NR-" + (reservasRealizadas.size() + 1),
                LocalDateTime.now(),
                vueloSeleccionado.getHoraSalida(),
                vueloSeleccionado.getIdVuelo(),
                new ArrayList<>(),
                "Cliente-" + Math.random(),
                precioPorPersona * cantidadPersonas,
                tickets
        );

        reservasRealizadas.add(nuevaReserva);

        System.out.println("\nReserva realizada con éxito.");
        System.out.println(nuevaReserva);
    }



    private static List<Vuelo> filtrarPorDestino(Scanner scanner) {
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
            scanner.nextLine(); // Limpiar el buffer
        } catch (Exception e) {
            System.out.println("Selección inválida. Intente nuevamente.");
            scanner.nextLine(); // Limpiar el buffer
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

    private static List<Vuelo> filtrarPorFecha(Scanner scanner) {
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

    private static void verReservas() {
        System.out.println("\n--- Reservas realizadas ---");
        if (reservasRealizadas.isEmpty()) {
            System.out.println("No hay reservas realizadas.");
            return;
        }

        // Mostrar las reservas realizadas
        for (int i = 0; i < reservasRealizadas.size(); i++) {
            System.out.println((i + 1) + ". Reserva ID: " + reservasRealizadas.get(i).getIdReserva());
        }

        System.out.print("\nSeleccione el número de la reserva para ver los tickets (0 para salir): ");
        Scanner scanner = new Scanner(System.in);
        int reservaSeleccionada = scanner.nextInt();

        if (reservaSeleccionada == 0) {
            System.out.println("Volviendo al menú principal...");
            return;
        }

        if (reservaSeleccionada < 1 || reservaSeleccionada > reservasRealizadas.size()) {
            System.out.println("Selección inválida. Intente nuevamente.");
            return;
        }

        // Obtener la reserva seleccionada
        Reservas reserva = reservasRealizadas.get(reservaSeleccionada - 1);

        // Mostrar los tickets de la reserva seleccionada
        System.out.println("\n--- Tickets de la reserva: " + reserva.getIdReserva() + " ---");
        List<Ticket> tickets = reserva.getTickets();

        for (int i = 0; i < tickets.size(); i++) {
            System.out.println((i + 1) + ". Ticket de " + tickets.get(i).getNombrePasajero());
        }

        System.out.print("\nSeleccione el número del ticket que desea ver (0 para volver): ");
        int ticketSeleccionadoIndex = scanner.nextInt();

        if (ticketSeleccionadoIndex == 0) {
            System.out.println("Volviendo al menú de reservas...");
            return;
        }

        if (ticketSeleccionadoIndex < 1 || ticketSeleccionadoIndex > tickets.size()) {
            System.out.println("Selección inválida. Intente nuevamente.");
            return;
        }

        // Aquí ya no declaramos otra vez ticketSeleccionado, simplemente referenciamos el ticket
        Ticket ticketSeleccionado = tickets.get(ticketSeleccionadoIndex - 1);

        // Mostrar la información del ticket seleccionado
        System.out.println("\n--- Detalles del ticket ---");
        System.out.println("Compañía aérea: " + ticketSeleccionado.getCompaniaAerea());
        System.out.println("Origen: " + ticketSeleccionado.getOrigen());
        System.out.println("Destino: " + ticketSeleccionado.getDestino());
        System.out.println("Fecha de ida: " + ticketSeleccionado.getFechaSalida());
        System.out.println("Fecha de vuelta: " + ticketSeleccionado.getFechaLlegada());
        System.out.println("Precio total: " + ticketSeleccionado.getPrecioTotal());
    }

}