package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Reservas {
    private String idReserva;
    private EstadoReserva estado;
    private String numeroReserva;
    private LocalDateTime fechaReserva;
    private LocalDateTime fechaVuelo;
    private int idVuelo;
    private List<Asiento> asientoReservado;
    private String idCliente;
    private Double totalPago;
    private List<Ticket> tickets;

    public Reservas(String idReserva, EstadoReserva estado, String numeroReserva, LocalDateTime fechaReserva, LocalDateTime fechaVuelo,
                    int idVuelo, List<Asiento> asientoReservado, String idCliente, Double totalPago, List<Ticket> tickets) {
        this.idReserva = idReserva;
        this.estado = estado;
        this.numeroReserva = numeroReserva;
        this.fechaReserva = fechaReserva;
        this.fechaVuelo = fechaVuelo;
        this.idVuelo = idVuelo;
        this.asientoReservado = asientoReservado;
        this.idCliente = idCliente;
        this.totalPago = totalPago;
        this.tickets = tickets != null ? tickets : new ArrayList<>();
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    // Métodos getter y setter
    public String getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(String idReserva) {
        this.idReserva = idReserva;
    }

    public EstadoReserva getEstado() {
        return estado;
    }

    public void setEstado(EstadoReserva estado) {
        this.estado = estado;
    }

    public String getNumeroReserva() {
        return numeroReserva;
    }

    public void agregarTicket(Ticket ticket) {
        if (tickets == null) {
            tickets = new ArrayList<>();
        }
        tickets.add(ticket);
    }

    public double calcularTotalPago() {
        double total = 0.0;
        if (tickets != null) {
            for (Ticket ticket : tickets) {
                total += ticket.calcularPrecioTotal();
            }
        }
        return total;
    }


    public void setNumeroReserva(String numeroReserva) {
        this.numeroReserva = numeroReserva;
    }

    public LocalDateTime getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(LocalDateTime fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public LocalDateTime getFechaVuelo() {
        return fechaVuelo;
    }

    public void setFechaVuelo(LocalDateTime fechaVuelo) {
        this.fechaVuelo = fechaVuelo;
    }

    public int getIdVuelo() {
        return idVuelo;
    }

    public void setIdVuelo(int idVuelo) {
        this.idVuelo = idVuelo;
    }

    public List<Asiento> getAsientoReservado() {
        return asientoReservado;
    }

    public void setAsientoReservado(List<Asiento> asientoReservado) {
        this.asientoReservado = asientoReservado;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public Double getTotalPago() {
        return totalPago;
    }

    public void setTotalPago(Double totalPago) {
        this.totalPago = calcularTotalPago();
    }
    public static void realizarReserva(Scanner scanner, List<Vuelo> vuelosDisponibles, List<Reservas> reservasRealizadas) {
        System.out.println("\n--- Realizar una reserva ---");
        System.out.println("1. Elegir destino");
        System.out.println("2. Elegir fecha");
        System.out.print("Seleccione una opción: ");
        int opcion;

        try {
            opcion = scanner.nextInt();
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Selección inválida. Intente nuevamente.");
            scanner.nextLine();
            return;
        }

        List<Vuelo> vuelosFiltrados = new ArrayList<>();
        if (opcion == 1) {
            vuelosFiltrados = Vuelo.filtrarPorDestino(scanner, vuelosDisponibles);
        } else if (opcion == 2) {
            vuelosFiltrados = Vuelo.filtrarPorFecha(scanner, vuelosDisponibles);
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
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Selección inválida. Intente nuevamente.");
            scanner.nextLine();
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

        Precio precio = new Precio(vueloSeleccionado.getPrecio(), vueloSeleccionado.getPrecioEquipaje());


        List<Ticket> tickets = new ArrayList<>();
        double precioTotalReserva = 0.0;
        for (int i = 1; i <= cantidadPersonas; i++) {
            System.out.print("Ingrese el nombre del pasajero #" + i + ": ");
            String nombrePasajero = scanner.nextLine();

            System.out.print("¿El pasajero #" + i + " lleva equipaje? (s/n): ");
            String llevaEquipajeInput = scanner.nextLine();
            boolean llevaEquipaje = llevaEquipajeInput.equalsIgnoreCase("s");


            double precioPorPersona = precio.calcularPrecioTotal(llevaEquipaje ? 1 : 0);
            precioTotalReserva += precioPorPersona;

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


            tickets.add(ticket);
        }


        System.out.println("\n--- TICKETS GENERADOS ---");
        for (Ticket ticket : tickets) {
            System.out.println(ticket);
        }

        System.out.println("\n--- Precio Total de la Reserva ---");
        System.out.println("Precio total de la reserva: " + String.format("%.2f", precioTotalReserva) + " EUR");

        System.out.println("\n--- Realizar Pago ---");
        System.out.print("Ingrese los detalles de pago (ej. número de tarjeta): ");
        String detallesPago = scanner.nextLine(); // Aquí puedes simular la entrada de detalles de pago

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

    public static void verReservas(List<Reservas> reservasRealizadas) {
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
    @Override
    public String toString() {
        return "Reserva ID: " + idReserva + ", Cliente: " + idCliente + ", Vuelo: " + idVuelo + ", Fecha de Reserva: " + fechaReserva +
                ", Estado: " + estado + ", Asientos: " + asientoReservado.size() + ", Total Pago: " + calcularTotalPago();
    }
}
