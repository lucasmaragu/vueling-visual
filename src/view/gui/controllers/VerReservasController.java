package view.gui.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.*;

import util.CSVUtil; // Añadir este import al principio del archivo

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class VerReservasController {

    @FXML
    private Button backButton;
    @FXML
    private ListView<Reservas> reservasListView;
    @FXML
    private VBox detallesReservaPanel;
    @FXML
    private Label seleccionaReservaLabel;
    @FXML
    private VBox infoReservaContainer;
    @FXML
    private Label idReservaLabel;
    @FXML
    private Label numeroReservaLabel;
    @FXML
    private Label clienteLabel;
    @FXML
    private Label fechaReservaLabel;
    @FXML
    private Label fechaVueloLabel;
    @FXML
    private Label estadoLabel;
    @FXML
    private Label precioTotalLabel;
    @FXML
    private ListView<Ticket> ticketsListView;
    @FXML
    private VBox detallesTicketPanel;
    @FXML
    private Label seleccionaTicketLabel;
    @FXML
    private ScrollPane ticketScrollPane;
    @FXML
    private VBox ticketInfoContainer;
    @FXML
    private Label pasajeroLabel;
    @FXML
    private Label companiaLabel;
    @FXML
    private Label origenLabel;
    @FXML
    private Label destinoLabel;
    @FXML
    private Label fechaSalidaLabel;
    @FXML
    private Label fechaLlegadaLabel;
    @FXML
    private Label precioBaseLabel;
    @FXML
    private Label equipajeLabel;
    @FXML
    private Label precioTicketLabel;
    @FXML
    private Button imprimirTicketButton;


    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    // Modificar el método initialize() para cargar las reservas desde CSV
    public void initialize() {
        // Configurar el cell factory para la lista de reservas
        reservasListView.setCellFactory(param -> new ListCell<Reservas>() {
            @Override
            protected void updateItem(Reservas reserva, boolean empty) {
                super.updateItem(reserva, empty);
                if (empty || reserva == null) {
                    setText(null);
                    setStyle("-fx-text-fill: white;");
                } else {
                    setText("Reserva " + reserva.getNumeroReserva() + " - " +
                            reserva.getFechaVuelo().format(formatter) + " - " +
                            reserva.getTickets().size() + " pasajero(s)");
                    setStyle("-fx-text-fill: white;");
                }
            }
        });

        // Listener para cuando se selecciona una reserva
        reservasListView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        mostrarDetallesReserva(newValue);
                    }
                }
        );

        // Configurar el cell factory para la lista de tickets
        ticketsListView.setCellFactory(param -> new ListCell<Ticket>() {
            @Override
            protected void updateItem(Ticket ticket, boolean empty) {
                super.updateItem(ticket, empty);
                if (empty || ticket == null) {
                    setText(null);
                    setStyle("-fx-text-fill: white;");
                } else {
                    setText("Ticket de " + ticket.getNombrePasajero());
                    setStyle("-fx-text-fill: white;");
                }
            }
        });

        // Listener para cuando se selecciona un ticket
        ticketsListView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        mostrarDetallesTicket(newValue);
                    }
                }
        );

        // Cargar reservas desde CSV
        cargarReservas();
    }

    // Añadir método para cargar reservas
    private void cargarReservas() {
        List<Reservas> reservas = CSVUtil.cargarReservas();

        if (reservas != null && !reservas.isEmpty()) {
            reservasListView.setItems(FXCollections.observableArrayList(reservas));
        } else {
            // Mostrar mensaje de que no hay reservas
            seleccionaReservaLabel.setText("No hay reservas realizadas");
        }
    }

    private void mostrarDetallesReserva(Reservas reserva) {
        // Ocultar el mensaje de selección y mostrar los detalles
        seleccionaReservaLabel.setVisible(false);
        infoReservaContainer.setVisible(true);

        // Actualizar los labels con la información de la reserva
        idReservaLabel.setText(reserva.getIdReserva());
        numeroReservaLabel.setText(reserva.getNumeroReserva());
        clienteLabel.setText(reserva.getIdCliente());
        fechaReservaLabel.setText(reserva.getFechaReserva().format(formatter));
        fechaVueloLabel.setText(reserva.getFechaVuelo().format(formatter));
        estadoLabel.setText(reserva.getEstado().toString());
        precioTotalLabel.setText(String.format("%.2f €", reserva.getTotalPago()));

        // Cargar los tickets en la lista
        ticketsListView.setItems(FXCollections.observableArrayList(reserva.getTickets()));

        // Ocultar los detalles del ticket si estaban visibles
        seleccionaTicketLabel.setVisible(true);
        ticketScrollPane.setVisible(false);
    }

    private void mostrarDetallesTicket(Ticket ticket) {
        // Ocultar el mensaje de selección y mostrar los detalles
        seleccionaTicketLabel.setVisible(false);
        ticketScrollPane.setVisible(true);

        // Actualizar los labels con la información del ticket
        pasajeroLabel.setText(ticket.getNombrePasajero());
        companiaLabel.setText(ticket.getCompaniaAerea().toString());
        origenLabel.setText(ticket.getOrigen().toString());
        destinoLabel.setText(ticket.getDestino().toString());
        fechaSalidaLabel.setText(ticket.getFechaSalida().format(formatter));
        fechaLlegadaLabel.setText(ticket.getFechaLlegada().format(formatter));
        precioBaseLabel.setText(String.format("%.2f €", ticket.getPrecioBase()));
        equipajeLabel.setText(ticket.isEquipajeIncluido() ? "Incluido" : "No incluido");
        precioTicketLabel.setText(String.format("%.2f €", ticket.calcularPrecioTotal()));
    }

    @FXML
    private void handleImprimirTicket() {
        Ticket ticketSeleccionado = ticketsListView.getSelectionModel().getSelectedItem();
        if (ticketSeleccionado != null) {
            // Aquí se implementaría la lógica para imprimir el ticket
            // Por ahora, solo mostraremos un diálogo de confirmación

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Imprimir Ticket");
            alert.setHeaderText("Impresión de Ticket");
            alert.setContentText("El ticket de " + ticketSeleccionado.getNombrePasajero() +
                    " se ha enviado a la impresora.");

            // Personalizar el diálogo para modo oscuro
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.setStyle("-fx-background-color: #1E1E1E; -fx-text-fill: white;");
            dialogPane.getStyleClass().add("dark-alert");
            dialogPane.lookupButton(ButtonType.OK).setStyle("-fx-background-color: #64B5F6; -fx-text-fill: white;");

            // Aplicar estilo a todos los labels en el diálogo
            dialogPane.lookupAll(".label").forEach(node -> {
                ((Label) node).setStyle("-fx-text-fill: white;");
            });

            alert.showAndWait();
        }
    }

    @FXML
    private void handleVolver() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/gui/menuScene.fxml"));
            AnchorPane menuView = loader.load();
            Scene menuScene = new Scene(menuView);
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(menuScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error al volver al menú principal");
            alert.setContentText("Ha ocurrido un error al intentar volver al menú principal.");

            // Personalizar el diálogo para modo oscuro
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.setStyle("-fx-background-color: #1E1E1E; -fx-text-fill: white;");
            dialogPane.getStyleClass().add("dark-alert");
            dialogPane.lookupButton(ButtonType.OK).setStyle("-fx-background-color: #64B5F6; -fx-text-fill: white;");

            // Aplicar estilo a todos los labels en el diálogo
            dialogPane.lookupAll(".label").forEach(node -> {
                ((Label) node).setStyle("-fx-text-fill: white;");
            });

            alert.showAndWait();
        }
    }
}