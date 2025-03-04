package view.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Reservas;
import model.Vuelo;
import model.Ticket;
import model.EstadoReserva;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CrearReservaController {

    @FXML
    private TextField clienteField;
    @FXML
    private ComboBox<Vuelo> vueloComboBox;
    @FXML
    private Spinner<Integer> pasajerosSpinner;
    @FXML
    private Button reservarButton;
    @FXML
    private Label mensajeLabel;

    private List<Vuelo> vuelosDisponibles;
    private List<Reservas> reservasRealizadas;

    public void initialize() {
        pasajerosSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 1));
    }

    public void setVuelosDisponibles(List<Vuelo> vuelos) {
        this.vuelosDisponibles = vuelos;
        vueloComboBox.getItems().addAll(vuelos);
    }

    public void setReservasRealizadas(List<Reservas> reservas) {
        this.reservasRealizadas = reservas;
    }

    @FXML
    private void handleReservar() {
        String cliente = clienteField.getText();
        Vuelo vueloSeleccionado = vueloComboBox.getValue();
        int cantidadPasajeros = pasajerosSpinner.getValue();

        if (cliente.isEmpty() || vueloSeleccionado == null) {
            mensajeLabel.setText("Por favor, complete todos los campos.");
            return;
        }

        List<Ticket> tickets = new ArrayList<>();
        for (int i = 0; i < cantidadPasajeros; i++) {
            Ticket ticket = new Ticket(cliente, vueloSeleccionado.getCompaniaAerea(), vueloSeleccionado.getOrigen(),
                    vueloSeleccionado.getDestino(), vueloSeleccionado.getHoraSalida(), vueloSeleccionado.getHoraLlegada(),
                    vueloSeleccionado.getPrecio(), false);
            tickets.add(ticket);
        }

        Reservas nuevaReserva = new Reservas(
                "RES-" + (reservasRealizadas.size() + 1),
                EstadoReserva.CONFIRMADA,
                "NR-" + (reservasRealizadas.size() + 1),
                LocalDateTime.now(),
                vueloSeleccionado.getHoraSalida(),
                vueloSeleccionado.getIdVuelo(),
                new ArrayList<>(),
                cliente,
                vueloSeleccionado.getPrecio() * cantidadPasajeros,
                tickets
        );

        reservasRealizadas.add(nuevaReserva);
        mensajeLabel.setText("Reserva realizada con éxito.");
    }
}
