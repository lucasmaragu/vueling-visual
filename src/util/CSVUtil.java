package util;

import model.*;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CSVUtil {

    private static final String RESERVAS_FILE = "reservas.csv";
    private static final String TICKETS_FILE = "tickets.csv";
    private static final String ASIENTOS_FILE = "asientos.csv";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // Guardar reservas en CSV
    public static void guardarReservas(List<Reservas> reservas) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(RESERVAS_FILE))) {
            // Escribir encabezado
            writer.println("idReserva,estado,numeroReserva,fechaReserva,fechaVuelo,idVuelo,idCliente,totalPago");

            // Escribir datos de reservas
            for (Reservas reserva : reservas) {
                writer.println(
                        reserva.getIdReserva() + "," +
                                reserva.getEstado() + "," +
                                reserva.getNumeroReserva() + "," +
                                reserva.getFechaReserva().format(formatter) + "," +
                                reserva.getFechaVuelo().format(formatter) + "," +
                                reserva.getIdVuelo() + "," +
                                reserva.getIdCliente() + "," +
                                reserva.getTotalPago()
                );

                // Guardar tickets asociados a esta reserva
                guardarTickets(reserva.getTickets(), reserva.getIdReserva());

                // Guardar asientos asociados a esta reserva
                guardarAsientos(reserva.getAsientoReservado(), reserva.getIdReserva());
            }

            System.out.println("Reservas guardadas correctamente en " + RESERVAS_FILE);
        } catch (IOException e) {
            System.err.println("Error al guardar reservas: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Guardar tickets en CSV
    private static void guardarTickets(List<Ticket> tickets, String idReserva) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(TICKETS_FILE, true))) {
            // Si el archivo no existe, escribir encabezado
            File file = new File(TICKETS_FILE);
            if (!file.exists() || file.length() == 0) {
                writer.println("idReserva,nombrePasajero,companiaAerea,origen,destino,fechaSalida,fechaLlegada,precioBase,equipajeIncluido");
            }

            // Escribir datos de tickets
            for (Ticket ticket : tickets) {
                writer.println(
                        idReserva + "," +
                                ticket.getNombrePasajero() + "," +
                                ticket.getCompaniaAerea() + "," +
                                ticket.getOrigen() + "," +
                                ticket.getDestino() + "," +
                                ticket.getFechaSalida().format(formatter) + "," +
                                ticket.getFechaLlegada().format(formatter) + "," +
                                ticket.getPrecioBase() + "," +
                                ticket.isEquipajeIncluido()
                );
            }
        } catch (IOException e) {
            System.err.println("Error al guardar tickets: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Guardar asientos en CSV
    private static void guardarAsientos(List<Asiento> asientos, String idReserva) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ASIENTOS_FILE, true))) {
            // Si el archivo no existe, escribir encabezado
            File file = new File(ASIENTOS_FILE);
            if (!file.exists() || file.length() == 0) {
                writer.println("idReserva,numeroAsiento,disponible");
            }

            // Escribir datos de asientos
            for (Asiento asiento : asientos) {
                writer.println(
                        idReserva + "," +
                                asiento.getNumeroAsiento() + "," +
                                asiento.isDisponible()
                );
            }
        } catch (IOException e) {
            System.err.println("Error al guardar asientos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Cargar reservas desde CSV
    public static List<Reservas> cargarReservas() {
        List<Reservas> reservas = new ArrayList<>();
        File file = new File(RESERVAS_FILE);

        if (!file.exists()) {
            System.out.println("El archivo de reservas no existe. Se devolverá una lista vacía.");
            return reservas;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(RESERVAS_FILE))) {
            // Saltar la línea de encabezado
            String line = reader.readLine();

            // Leer datos
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 8) {
                    String idReserva = data[0];
                    EstadoReserva estado = EstadoReserva.valueOf(data[1]);
                    String numeroReserva = data[2];
                    LocalDateTime fechaReserva = LocalDateTime.parse(data[3], formatter);
                    LocalDateTime fechaVuelo = LocalDateTime.parse(data[4], formatter);
                    int idVuelo = Integer.parseInt(data[5]);
                    String idCliente = data[6];
                    double totalPago = Double.parseDouble(data[7]);

                    // Cargar tickets asociados a esta reserva
                    List<Ticket> tickets = cargarTickets(idReserva);

                    // Cargar asientos asociados a esta reserva
                    List<Asiento> asientos = cargarAsientos(idReserva);

                    // Crear y añadir la reserva
                    Reservas reserva = new Reservas(
                            idReserva, estado, numeroReserva, fechaReserva, fechaVuelo,
                            idVuelo, asientos, idCliente, totalPago, tickets
                    );
                    reservas.add(reserva);
                }
            }

            System.out.println("Se cargaron " + reservas.size() + " reservas desde " + RESERVAS_FILE);
        } catch (IOException e) {
            System.err.println("Error al cargar reservas: " + e.getMessage());
            e.printStackTrace();
        }

        return reservas;
    }

    // Cargar tickets desde CSV
    private static List<Ticket> cargarTickets(String idReserva) {
        List<Ticket> tickets = new ArrayList<>();
        File file = new File(TICKETS_FILE);

        if (!file.exists()) {
            return tickets;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(TICKETS_FILE))) {
            // Saltar la línea de encabezado
            String line = reader.readLine();

            // Leer datos
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 9 && data[0].equals(idReserva)) {
                    String nombrePasajero = data[1];
                    Companias companiaAerea = Companias.valueOf(data[2]);
                    Ubicacion origen = Ubicacion.valueOf(data[3]);
                    Ubicacion destino = Ubicacion.valueOf(data[4]);
                    LocalDateTime fechaSalida = LocalDateTime.parse(data[5], formatter);
                    LocalDateTime fechaLlegada = LocalDateTime.parse(data[6], formatter);
                    double precioBase = Double.parseDouble(data[7]);
                    boolean equipajeIncluido = Boolean.parseBoolean(data[8]);

                    // Crear y añadir el ticket
                    Ticket ticket = new Ticket(
                            nombrePasajero, companiaAerea, origen, destino,
                            fechaSalida, fechaLlegada, precioBase, equipajeIncluido
                    );
                    tickets.add(ticket);
                }
            }
        } catch (IOException e) {
            System.err.println("Error al cargar tickets: " + e.getMessage());
            e.printStackTrace();
        }

        return tickets;
    }

    // Cargar asientos desde CSV
    private static List<Asiento> cargarAsientos(String idReserva) {
        List<Asiento> asientos = new ArrayList<>();
        File file = new File(ASIENTOS_FILE);

        if (!file.exists()) {
            return asientos;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(ASIENTOS_FILE))) {
            // Saltar la línea de encabezado
            String line = reader.readLine();

            // Leer datos
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 3 && data[0].equals(idReserva)) {
                    String numeroAsiento = data[1];
                    boolean disponible = Boolean.parseBoolean(data[2]);

                    // Crear y añadir el asiento
                    Asiento asiento = new Asiento(numeroAsiento);
                    asiento.setDisponible(disponible);
                    asientos.add(asiento);
                }
            }
        } catch (IOException e) {
            System.err.println("Error al cargar asientos: " + e.getMessage());
            e.printStackTrace();
        }

        return asientos;
    }
}