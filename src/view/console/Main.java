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
        Scanner scanner = new Scanner(System.in);
        Vuelo vuelo1 = new Vuelo(1234, "A320", Companias.EASYJET, Ubicacion.ANDORRA, Ubicacion.BERLIN,
                LocalDateTime.of(2025, 1, 13, 10, 30), LocalDateTime.of(2025, 1, 13, 15, 45),
                180.00, 12.00, "España", "A2", EstadoVuelo.EN_VUELO);

        Vuelo vuelo2 = new Vuelo(5678, "B737", Companias.RYANAIR, Ubicacion.MADRID, Ubicacion.LONDRES,
                LocalDateTime.of(2025, 1, 14, 11, 00), LocalDateTime.of(2025, 1, 14, 12, 30),
                150.00, 10.00, "España", "R1", EstadoVuelo.EN_VUELO);

        vuelosDisponibles.add(vuelo1);
        vuelosDisponibles.add(vuelo2);

        Usuario usuario = iniciarSesion(scanner);

        // Si el usuario fue autenticado correctamente
        if (usuario != null) {
            // Verificar el tipo de usuario y mostrar el menú correspondiente
            if (usuario instanceof Cliente) {
                menuCliente();  // Llamar al menú para cliente
            } else if (usuario instanceof PersonalAdministrativo) {
                menuAdministrativo();  // Llamar al menú para personal administrativo
            }
        } else {
            System.out.println("No se pudo iniciar sesión. Saliendo del sistema...");
        }

        scanner.close();


    }
    private static Usuario iniciarSesion(Scanner scanner) {
        System.out.println("--- Iniciar sesión ---");
        System.out.print("Ingrese su ID de cliente: ");
        String idCliente = scanner.nextLine();

        System.out.print("Ingrese su contraseña: ");
        String contrasena = scanner.nextLine();

        // Llamar al método de autenticación para verificar el login y obtener el usuario
        return autenticacion.iniciarSesion(idCliente, contrasena);
    }

    private static void menuCliente() {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n--- MENÚ CLIENTE ---");
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
    }

    private static void menuAdministrativo() {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n--- MENÚ ADMINISTRATIVO ---");
            System.out.println("1. Ver vuelos disponibles");
            System.out.println("2. Añadir un vuelo");
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
                    añadirVuelo(scanner);
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
    }
    private static void añadirVuelo(Scanner scanner) {
        System.out.println("\n--- Añadir un vuelo ---");

        // Solicitar datos del vuelo
        System.out.print("Ingrese el ID del vuelo: ");
        int idVuelo = scanner.nextInt();
        scanner.nextLine();  // Limpiar el buffer

        System.out.print("Ingrese el modelo del avión (Ej. A320, B737): ");
        String modeloAvion = scanner.nextLine();

        System.out.println("Seleccione la compañía aérea:");
        for (int i = 0; i < Companias.values().length; i++) {
            System.out.println((i + 1) + ". " + Companias.values()[i]);
        }
        System.out.print("Seleccione una opción: ");
        int opcionCompania = scanner.nextInt();
        scanner.nextLine();  // Limpiar el buffer
        Companias companiaSeleccionada = Companias.values()[opcionCompania - 1];

        System.out.println("Seleccione el origen del vuelo:");
        for (int i = 0; i < Ubicacion.values().length; i++) {
            System.out.println((i + 1) + ". " + Ubicacion.values()[i]);
        }
        System.out.print("Seleccione una opción: ");
        int opcionOrigen = scanner.nextInt();
        scanner.nextLine();  // Limpiar el buffer
        Ubicacion origenSeleccionado = Ubicacion.values()[opcionOrigen - 1];

        System.out.println("Seleccione el destino del vuelo:");
        for (int i = 0; i < Ubicacion.values().length; i++) {
            System.out.println((i + 1) + ". " + Ubicacion.values()[i]);
        }
        System.out.print("Seleccione una opción: ");
        int opcionDestino = scanner.nextInt();
        scanner.nextLine();  // Limpiar el buffer
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
        scanner.nextLine(); // Limpiar el buffer

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

        // Crear el nuevo vuelo
        Vuelo nuevoVuelo = new Vuelo(idVuelo, modeloAvion, companiaSeleccionada, origenSeleccionado, destinoSeleccionado,
                horaSalida, horaLlegada, precioVuelo, precioEquipaje, paisOrigen, numeroVuelo, estadoSeleccionado);

        // Añadir el nuevo vuelo a la lista de vuelos disponibles
        vuelosDisponibles.add(nuevoVuelo);

        System.out.println("Vuelo añadido con éxito:");
        System.out.println(nuevoVuelo);
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
        scanner.nextLine(); // Consumir el salto de línea

        Precio precio = new Precio(vueloSeleccionado.getPrecio(), vueloSeleccionado.getPrecioEquipaje());


        List<Ticket> tickets = new ArrayList<>();
        double precioTotalReserva = 0.0;
        for (int i = 1; i <= cantidadPersonas; i++) {
            System.out.print("Ingrese el nombre del pasajero #" + i + ": ");
            String nombrePasajero = scanner.nextLine();

            System.out.print("¿El pasajero #" + i + " lleva equipaje? (s/n): ");
            String llevaEquipajeInput = scanner.nextLine();
            boolean llevaEquipaje = llevaEquipajeInput.equalsIgnoreCase("s");

            // Calcular el precio total para el pasajero
            double precioPorPersona = precio.calcularPrecioTotal(llevaEquipaje ? 1 : 0);
            precioTotalReserva += precioPorPersona;
            // Crear un ticket para el pasajero
            Ticket ticket = new Ticket(
                    nombrePasajero,
                    vueloSeleccionado.getCompaniaAerea(),
                    vueloSeleccionado.getOrigen(),
                    vueloSeleccionado.getDestino(),
                    vueloSeleccionado.getHoraSalida(),
                    vueloSeleccionado.getHoraLlegada(),
                    precioPorPersona,
                    llevaEquipaje
            );

            // Agregar el ticket a la lista
            tickets.add(ticket);
        }

        // Mostrar los tickets generados
        System.out.println("\n--- TICKETS GENERADOS ---");
        for (Ticket ticket : tickets) {
            System.out.println(ticket);
        }

        // Mostrar el precio total de la reserva
        System.out.println("\n--- Precio Total de la Reserva ---");
        System.out.println("Precio total de la reserva: " + String.format("%.2f", precioTotalReserva) + " EUR");

        // Apartado para realizar el pago
        System.out.println("\n--- Realizar Pago ---");
        System.out.print("Ingrese los detalles de pago (ej. número de tarjeta): ");
        String detallesPago = scanner.nextLine(); // Aquí puedes simular la entrada de detalles de pago

        // Confirmar el pago
        System.out.print("¿Desea confirmar el pago de " + String.format("%.2f", precioTotalReserva) + " EUR? (s/n): ");
        String confirmacionPago = scanner.nextLine();

        if (confirmacionPago.equalsIgnoreCase("s")) {
            // Si el pago es exitoso, crear una nueva reserva
            Reservas nuevaReserva = new Reservas(
                    "RES-" + (reservasRealizadas.size() + 1),
                    EstadoReserva.CONFIRMADA,
                    "NR-" + (reservasRealizadas.size() + 1),
                    LocalDateTime.now(),
                    vueloSeleccionado.getHoraSalida(),
                    vueloSeleccionado.getIdVuelo(),
                    new ArrayList<>(),
                    "Cliente-" + Math.random(),
                    precioTotalReserva,
                    tickets
            );

            reservasRealizadas.add(nuevaReserva);

            System.out.println("\nReserva realizada con éxito.");
            System.out.println(nuevaReserva);
        } else {
            System.out.println("El pago ha sido cancelado. No se ha realizado la reserva.");
        }
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

        // Mostrar la información de la reserva seleccionada
        System.out.println("\n--- Información de la reserva: " + reserva.getIdReserva() + " ---");
        System.out.println("Número de reserva: " + reserva.getNumeroReserva());
        System.out.println("Fecha y hora de la reserva: " + reserva.getFechaReserva());
        System.out.println("Vuelo: " + reserva.getIdVuelo());
        System.out.println("Precio total: " + String.format("%.2f", reserva.getTotalPago()));

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

        // Referenciar el ticket seleccionado
        Ticket ticketSeleccionado = tickets.get(ticketSeleccionadoIndex - 1);

        // Mostrar la información del ticket seleccionado
        System.out.println("\n--- Detalles del ticket ---");
        System.out.println("Compañía aérea: " + ticketSeleccionado.getCompaniaAerea());
        System.out.println("Origen: " + ticketSeleccionado.getOrigen());
        System.out.println("Destino: " + ticketSeleccionado.getDestino());
        System.out.println("Fecha de ida: " + ticketSeleccionado.getFechaSalida());
        System.out.println("Fecha de vuelta: " + ticketSeleccionado.getFechaLlegada());
        System.out.println("Precio total: " + String.format("%.2f", ticketSeleccionado.calcularPrecioTotal()));
    }

}