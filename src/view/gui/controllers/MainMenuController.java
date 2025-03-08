package view.gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MainMenuController {

    @FXML
    private Button logoutButton;
    @FXML
    private Button reservarVueloButton;
    @FXML
    private Button verReservasButton;

    // Datos de ejemplo para la aplicación
    private List<Vuelo> vuelosDisponibles;
    private List<Reservas> reservasRealizadas;

    public void initialize() {
        // Inicializar datos de ejemplo
        inicializarDatosEjemplo();
    }

    private void inicializarDatosEjemplo() {
        // Crear vuelos de ejemplo
        vuelosDisponibles = new ArrayList<>();

        // Vuelo 1: Madrid a Barcelona
        vuelosDisponibles.add(new Vuelo(
                1,
                "A320",
                Companias.IBERIA,
                Ubicacion.MADRID,
                Ubicacion.BARCELONA,
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(1).plusHours(2),
                120.0,
                30.0,
                "España",
                "T4-B12",
                EstadoVuelo.PROGRAMADO
        ));

        // Vuelo 2: Barcelona a París
        vuelosDisponibles.add(new Vuelo(
                2,
                "B737",
                Companias.AIR_FRANCE,
                Ubicacion.BARCELONA,
                Ubicacion.PARIS,
                LocalDateTime.now().plusDays(2),
                LocalDateTime.now().plusDays(2).plusHours(3),
                180.0,
                40.0,
                "España",
                "T1-C05",
                EstadoVuelo.PROGRAMADO
        ));

        // Vuelo 3: Madrid a Londres
        vuelosDisponibles.add(new Vuelo(
                3,
                "A330",
                Companias.BRITISH_AIRWAYS,
                Ubicacion.MADRID,
                Ubicacion.LONDRES,
                LocalDateTime.now().plusDays(3),
                LocalDateTime.now().plusDays(3).plusHours(3),
                200.0,
                45.0,
                "España",
                "T4-D22",
                EstadoVuelo.PROGRAMADO
        ));

        // Vuelo 4: Barcelona a Roma
        vuelosDisponibles.add(new Vuelo(
                4,
                "A319",
                Companias.ALITALIA,
                Ubicacion.BARCELONA,
                Ubicacion.ROMA,
                LocalDateTime.now().plusDays(4),
                LocalDateTime.now().plusDays(4).plusHours(2),
                150.0,
                35.0,
                "España",
                "T2-A08",
                EstadoVuelo.PROGRAMADO
        ));

        // Inicializar lista de reservas
        reservasRealizadas = new ArrayList<>();

        // Crear algunas reservas de ejemplo
        crearReservasEjemplo();
    }

    private void crearReservasEjemplo() {
        // Crear tickets para la primera reserva
        List<Ticket> tickets1 = new ArrayList<>();
        tickets1.add(new Ticket(
                "Juan Pérez",
                Companias.IBERIA,
                Ubicacion.MADRID,
                Ubicacion.BARCELONA,
                vuelosDisponibles.get(0).getHoraSalida(),
                vuelosDisponibles.get(0).getHoraLlegada(),
                120.0,
                true
        ));
        tickets1.add(new Ticket(
                "María López",
                Companias.IBERIA,
                Ubicacion.MADRID,
                Ubicacion.BARCELONA,
                vuelosDisponibles.get(0).getHoraSalida(),
                vuelosDisponibles.get(0).getHoraLlegada(),
                120.0,
                false
        ));

        // Crear la primera reserva
        Reservas reserva1 = new Reservas(
                "RES-1",
                EstadoReserva.CONFIRMADA,
                "NR-001",
                LocalDateTime.now().minusDays(2),
                vuelosDisponibles.get(0).getHoraSalida(),
                vuelosDisponibles.get(0).getIdVuelo(),
                new ArrayList<>(),
                "Cliente-001",
                240.0 + 30.0, // Precio base + equipaje
                tickets1
        );

        // Crear tickets para la segunda reserva
        List<Ticket> tickets2 = new ArrayList<>();
        tickets2.add(new Ticket(
                "Carlos Rodríguez",
                Companias.AIR_FRANCE,
                Ubicacion.BARCELONA,
                Ubicacion.PARIS,
                vuelosDisponibles.get(1).getHoraSalida(),
                vuelosDisponibles.get(1).getHoraLlegada(),
                180.0,
                true
        ));

        // Crear la segunda reserva
        Reservas reserva2 = new Reservas(
                "RES-2",
                EstadoReserva.CONFIRMADA,
                "NR-002",
                LocalDateTime.now().minusDays(1),
                vuelosDisponibles.get(1).getHoraSalida(),
                vuelosDisponibles.get(1).getIdVuelo(),
                new ArrayList<>(),
                "Cliente-002",
                180.0 + 40.0, // Precio base + equipaje
                tickets2
        );

        // Añadir las reservas a la lista
        reservasRealizadas.add(reserva1);
        reservasRealizadas.add(reserva2);
    }

    @FXML
    private void handleVerVuelos() {
        System.out.println("Ver Vuelos seleccionado");
        // Aquí puedes agregar la lógica para cambiar de escena
    }

    @FXML
    private void handleReservarVuelo() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/gui/crearReservaScene.fxml"));
            AnchorPane crearReservaView = loader.load();

            // Obtener el controlador y pasar los datos
            CrearReservaController controller = loader.getController();
            controller.setVuelosDisponibles(vuelosDisponibles);
            controller.setReservasRealizadas(reservasRealizadas);

            Scene crearReservaScene = new Scene(crearReservaView);
            Stage stage = (Stage) reservarVueloButton.getScene().getWindow();
            stage.setScene(crearReservaScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleVerReservas() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/gui/verReservasScene.fxml"));
            AnchorPane verReservasView = loader.load();

            // Obtener el controlador y pasar los datos
            VerReservasController controller = loader.getController();
            //controller.setReservasRealizadas(reservasRealizadas);

            Scene verReservasScene = new Scene(verReservasView);
            Stage stage = (Stage) verReservasButton.getScene().getWindow();
            stage.setScene(verReservasScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCerrarSesion() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/gui/loginScene.fxml"));
            AnchorPane loginView = loader.load();
            Scene loginScene = new Scene(loginView);
            Stage stage = (Stage) logoutButton.getScene().getWindow();
            stage.setScene(loginScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}