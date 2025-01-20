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
    private static Autenticacion autenticacion = new Autenticacion();

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


        if (usuario != null) {
            if (usuario instanceof Cliente) {
                menuCliente();
            } else if (usuario instanceof PersonalAdministrativo) {
                menuAdministrativo();
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
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    Vuelo.mostrarVuelosDisponibles(vuelosDisponibles);
                    break;
                case 2:
                    Reservas.realizarReserva(scanner, vuelosDisponibles, reservasRealizadas);
                    break;
                case 3:
                    Reservas.verReservas(reservasRealizadas);
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
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    Vuelo.mostrarVuelosDisponibles(vuelosDisponibles);
                    break;
                case 2:
                    Vuelo.añadirVuelo(scanner, vuelosDisponibles);
                    break;
                case 3:
                    Reservas.verReservas(reservasRealizadas);
                    break;
                case 4:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción no válida, intente nuevamente.");
            }
        } while (opcion != 4);
    }


















}