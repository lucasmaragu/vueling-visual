package view.console;

import model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;




public class Main extends Application {
    private static List<Vuelo> vuelosDisponibles = new ArrayList<>();
    private static List<Reservas> reservasRealizadas = new ArrayList<>();
    private static Autenticacion autenticacion = new Autenticacion();

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Cargar el archivo FXML para la ventana principal
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/gui/loginScene.fxml"));
        AnchorPane root = loader.load();

        // Crear la escena y asignarla al escenario principal
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Sistema de Gestión de Vuelos");
        primaryStage.show();
    }

    public static void main(String[] args) {

        launch(args);
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