package view.gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.*;
import util.CSVUtil;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CrearReservaController {

    @FXML
    private TextField clienteField;
    @FXML
    private ComboBox<Vuelo> vueloComboBox;
    @FXML
    private Spinner<Integer> pasajerosSpinner;
    @FXML
    private Button confirmarPasajerosButton;
    @FXML
    private VBox pasajerosContainer;
    @FXML
    private Button reservarButton;
    @FXML
    private Label mensajeLabel;
    @FXML
    private Button backButton;
    @FXML
    private VBox resumenContainer;
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
    private Label duracionLabel;
    @FXML
    private Label puertaLabel;
    @FXML
    private Label precioPasajeroLabel;
    @FXML
    private Label precioEquipajeLabel;
    @FXML
    private Label precioTotalLabel;
    @FXML
    private VBox asientosContainer;
    @FXML
    private Label seleccionaVueloLabel;

    private List<Vuelo> vuelosDisponibles;
    private List<Reservas> reservasRealizadas;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private Map<Integer, List<String>> asientosOcupados = new HashMap<>(); // Mapa para almacenar asientos ocupados por vuelo
    private List<TextField> nombresPasajeros = new ArrayList<>();
    private List<ComboBox<String>> asientosPasajeros = new ArrayList<>();
    private List<CheckBox> equipajePasajeros = new ArrayList<>();

    public void initialize() {
        // Configurar el spinner de pasajeros
        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 1);
        pasajerosSpinner.setValueFactory(valueFactory);

        // Listener para actualizar el resumen cuando se selecciona un vuelo
        vueloComboBox.valueProperty().addListener((obs, oldValue, newValue) -> {
            actualizarResumen();
            if (newValue != null) {
                mostrarAsientosDisponibles(newValue);
            }
        });

        // Cargar reservas desde CSV
        reservasRealizadas = CSVUtil.cargarReservas();
    }

    public void setVuelosDisponibles(List<Vuelo> vuelos) {
        this.vuelosDisponibles = vuelos;
        vueloComboBox.getItems().clear();
        vueloComboBox.getItems().addAll(vuelos);

        // Configurar el cell factory para mostrar información relevante del vuelo
        vueloComboBox.setCellFactory(param -> new ListCell<Vuelo>() {
            @Override
            protected void updateItem(Vuelo vuelo, boolean empty) {
                super.updateItem(vuelo, empty);
                if (empty || vuelo == null) {
                    setText(null);
                } else {
                    setText(vuelo.getCompaniaAerea() + " - " +
                            vuelo.getOrigen().name() + " → " +
                            vuelo.getDestino().name() + " - " +
                            vuelo.getHoraSalida().format(formatter) + " - " +
                            String.format("%.2f €", vuelo.getPrecio()));
                }
            }
        });

        // Configurar el button cell para mostrar información relevante del vuelo seleccionado
        vueloComboBox.setButtonCell(new ListCell<Vuelo>() {
            @Override
            protected void updateItem(Vuelo vuelo, boolean empty) {
                super.updateItem(vuelo, empty);
                if (empty || vuelo == null) {
                    setText(null);
                } else {
                    setText(vuelo.getCompaniaAerea() + " - " +
                            vuelo.getOrigen().name() + " → " +
                            vuelo.getDestino().name() + " - " +
                            vuelo.getHoraSalida().format(formatter));
                }
            }
        });

        // Inicializar asientos ocupados para cada vuelo
        for (Vuelo vuelo : vuelos) {
            if (!asientosOcupados.containsKey(vuelo.getIdVuelo())) {
                asientosOcupados.put(vuelo.getIdVuelo(), new ArrayList<>());
            }
        }
    }

    public void setReservasRealizadas(List<Reservas> reservas) {
        this.reservasRealizadas = reservas;

        // Actualizar asientos ocupados basados en reservas existentes
        for (Reservas reserva : reservas) {
            int idVuelo = reserva.getIdVuelo();
            List<Asiento> asientosReservados = reserva.getAsientoReservado();

            if (!asientosOcupados.containsKey(idVuelo)) {
                asientosOcupados.put(idVuelo, new ArrayList<>());
            }

            for (Asiento asiento : asientosReservados) {
                asientosOcupados.get(idVuelo).add(asiento.getNumeroAsiento());
            }
        }
    }

    @FXML
    private void handleConfirmarPasajeros() {
        int cantidadPasajeros = pasajerosSpinner.getValue();
        Vuelo vueloSeleccionado = vueloComboBox.getValue();

        if (vueloSeleccionado == null) {
            mensajeLabel.setText("Por favor, seleccione un vuelo primero.");
            mensajeLabel.setStyle("-fx-text-fill: #FF5252;");
            return;
        }

        // Limpiar contenedores
        pasajerosContainer.getChildren().clear();
        nombresPasajeros.clear();
        asientosPasajeros.clear();
        equipajePasajeros.clear();

        // Crear campos para cada pasajero
        for (int i = 0; i < cantidadPasajeros; i++) {
            VBox pasajeroBox = new VBox(10);
            pasajeroBox.setStyle("-fx-background-color: #2A2A2A; -fx-padding: 10px; -fx-background-radius: 5px;");

            Label tituloLabel = new Label("Pasajero " + (i + 1));
            tituloLabel.setTextFill(Color.WHITE);
            tituloLabel.setStyle("-fx-font-weight: bold;");

            // Campo para el nombre
            HBox nombreBox = new HBox(10);
            Label nombreLabel = new Label("Nombre:");
            nombreLabel.setTextFill(Color.web("#b0b0b0"));
            nombreLabel.setPrefWidth(80);
            TextField nombreField = new TextField();
            nombreField.setPromptText("Nombre del pasajero");
            nombreField.setStyle("-fx-background-color: #3E3E3E; -fx-text-fill: white; -fx-prompt-text-fill: #808080;");
            nombreField.setPrefWidth(200);
            nombreBox.getChildren().addAll(nombreLabel, nombreField);
            nombresPasajeros.add(nombreField);

            // Campo para el asiento
            HBox asientoBox = new HBox(10);
            Label asientoLabel = new Label("Asiento:");
            asientoLabel.setTextFill(Color.web("#b0b0b0"));
            asientoLabel.setPrefWidth(80);
            ComboBox<String> asientoCombo = new ComboBox<>();
            asientoCombo.setPromptText("Seleccione asiento");
            asientoCombo.setStyle("-fx-background-color: #3E3E3E; -fx-text-fill: white; -fx-prompt-text-fill: #808080;");
            asientoCombo.setPrefWidth(200);

            // Llenar con asientos disponibles
            List<String> asientosDisponiblesVuelo = obtenerAsientosDisponibles(vueloSeleccionado.getIdVuelo());
            asientoCombo.getItems().addAll(asientosDisponiblesVuelo);

            asientoBox.getChildren().addAll(asientoLabel, asientoCombo);
            asientosPasajeros.add(asientoCombo);

            // Campo para el equipaje
            HBox equipajeBox = new HBox(10);
            Label equipajeLabel = new Label("Equipaje:");
            equipajeLabel.setTextFill(Color.web("#b0b0b0"));
            equipajeLabel.setPrefWidth(80);
            CheckBox equipajeCheck = new CheckBox("Incluir equipaje");
            equipajeCheck.setTextFill(Color.WHITE);
            equipajeCheck.setSelected(false);
            equipajeBox.getChildren().addAll(equipajeLabel, equipajeCheck);
            equipajePasajeros.add(equipajeCheck);

            // Listener para actualizar el precio cuando cambia el equipaje
            equipajeCheck.selectedProperty().addListener((obs, oldVal, newVal) -> {
                actualizarResumen();
            });

            pasajeroBox.getChildren().addAll(tituloLabel, nombreBox, asientoBox, equipajeBox);
            pasajerosContainer.getChildren().add(pasajeroBox);
        }

        actualizarResumen();
    }

    private List<String> obtenerAsientosDisponibles(int idVuelo) {
        List<String> todosLosAsientos = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            todosLosAsientos.add("A" + i);
        }

        List<String> asientosOcupadosVuelo = asientosOcupados.getOrDefault(idVuelo, new ArrayList<>());

        // Eliminar asientos ya seleccionados por otros pasajeros en esta reserva
        for (ComboBox<String> combo : asientosPasajeros) {
            String asientoSeleccionado = combo.getValue();
            if (asientoSeleccionado != null && !asientoSeleccionado.isEmpty()) {
                asientosOcupadosVuelo.add(asientoSeleccionado);
            }
        }

        // Filtrar asientos ocupados
        List<String> asientosDisponibles = new ArrayList<>();
        for (String asiento : todosLosAsientos) {
            if (!asientosOcupadosVuelo.contains(asiento)) {
                asientosDisponibles.add(asiento);
            }
        }

        return asientosDisponibles;
    }

    private void mostrarAsientosDisponibles(Vuelo vuelo) {
        asientosContainer.getChildren().clear();
        seleccionaVueloLabel.setVisible(false);

        List<String> asientosDisponiblesVuelo = obtenerAsientosDisponibles(vuelo.getIdVuelo());
        List<String> asientosOcupadosVuelo = asientosOcupados.getOrDefault(vuelo.getIdVuelo(), new ArrayList<>());

        Label infoLabel = new Label("Asientos para el vuelo " + vuelo.getIdVuelo() + " (" + vuelo.getOrigen() + " → " + vuelo.getDestino() + ")");
        infoLabel.setTextFill(Color.WHITE);
        infoLabel.setStyle("-fx-font-weight: bold;");
        asientosContainer.getChildren().add(infoLabel);

        // Crear una cuadrícula de asientos (10x10)
        GridPane gridPane = new GridPane();
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        gridPane.setPadding(new Insets(10));

        // Añadir etiquetas de columnas (1-10)
        for (int col = 1; col <= 10; col++) {
            Label colLabel = new Label(String.valueOf(col));
            colLabel.setTextFill(Color.web("#b0b0b0"));
            gridPane.add(colLabel, col, 0);
        }

        // Crear botones de asientos
        for (int row = 1; row <= 10; row++) {
            // Etiqueta de fila
            Label rowLabel = new Label(String.valueOf(row));
            rowLabel.setTextFill(Color.web("#b0b0b0"));
            gridPane.add(rowLabel, 0, row);

            for (int col = 1; col <= 10; col++) {
                int asientoNum = (row - 1) * 10 + col;
                String asientoId = "A" + asientoNum;

                Button asientoButton = new Button(asientoId);
                asientoButton.setPrefWidth(50);
                asientoButton.setPrefHeight(30);

                if (asientosOcupadosVuelo.contains(asientoId)) {
                    // Asiento ocupado
                    asientoButton.setStyle("-fx-background-color: #FF5252; -fx-text-fill: white;");
                    asientoButton.setDisable(true);
                } else {
                    // Asiento disponible
                    asientoButton.setStyle("-fx-background-color: #64B5F6; -fx-text-fill: white;");
                }

                gridPane.add(asientoButton, col, row);
            }
        }

        // Leyenda
        HBox leyendaBox = new HBox(20);
        leyendaBox.setAlignment(Pos.CENTER_LEFT);
        leyendaBox.setPadding(new Insets(10, 0, 0, 0));

        HBox disponibleBox = new HBox(5);
        disponibleBox.setAlignment(Pos.CENTER_LEFT);
        Region disponibleColor = new Region();
        disponibleColor.setPrefWidth(20);
        disponibleColor.setPrefHeight(20);
        disponibleColor.setStyle("-fx-background-color: #64B5F6;");
        Label disponibleLabel = new Label("Disponible");
        disponibleLabel.setTextFill(Color.WHITE);
        disponibleBox.getChildren().addAll(disponibleColor, disponibleLabel);

        HBox ocupadoBox = new HBox(5);
        ocupadoBox.setAlignment(Pos.CENTER_LEFT);
        Region ocupadoColor = new Region();
        ocupadoColor.setPrefWidth(20);
        ocupadoColor.setPrefHeight(20);
        ocupadoColor.setStyle("-fx-background-color: #FF5252;");
        Label ocupadoLabel = new Label("Ocupado");
        ocupadoLabel.setTextFill(Color.WHITE);
        ocupadoBox.getChildren().addAll(ocupadoColor, ocupadoLabel);

        leyendaBox.getChildren().addAll(disponibleBox, ocupadoBox);

        asientosContainer.getChildren().addAll(gridPane, leyendaBox);
    }

    private void actualizarResumen() {
        Vuelo vueloSeleccionado = vueloComboBox.getValue();
        int cantidadPasajeros = pasajerosSpinner.getValue();

        if (vueloSeleccionado != null) {
            companiaLabel.setText(vueloSeleccionado.getCompaniaAerea().toString());
            origenLabel.setText(vueloSeleccionado.getOrigen().name());
            destinoLabel.setText(vueloSeleccionado.getDestino().name());
            fechaSalidaLabel.setText(vueloSeleccionado.getHoraSalida().format(formatter));
            fechaLlegadaLabel.setText(vueloSeleccionado.getHoraLlegada().format(formatter));
            duracionLabel.setText(vueloSeleccionado.calcularDuracion(
                    vueloSeleccionado.getHoraSalida(), vueloSeleccionado.getHoraLlegada()));
            puertaLabel.setText(vueloSeleccionado.getPuertaEmbarque());
            precioPasajeroLabel.setText(String.format("%.2f €", vueloSeleccionado.getPrecio()));

            // Calcular precio total basado en equipaje seleccionado
            double precioTotalEquipaje = 0.0;
            int cantidadEquipaje = 0;

            for (CheckBox equipajeCheck : equipajePasajeros) {
                if (equipajeCheck.isSelected()) {
                    cantidadEquipaje++;
                }
            }

            precioTotalEquipaje = vueloSeleccionado.getPrecioEquipaje() * cantidadEquipaje;
            precioEquipajeLabel.setText(String.format("%.2f €", precioTotalEquipaje));

            double precioTotal = (vueloSeleccionado.getPrecio() * cantidadPasajeros) + precioTotalEquipaje;
            precioTotalLabel.setText(String.format("%.2f €", precioTotal));
        } else {
            companiaLabel.setText("--");
            origenLabel.setText("--");
            destinoLabel.setText("--");
            fechaSalidaLabel.setText("--");
            fechaLlegadaLabel.setText("--");
            duracionLabel.setText("--");
            puertaLabel.setText("--");
            precioPasajeroLabel.setText("--");
            precioEquipajeLabel.setText("--");
            precioTotalLabel.setText("--");
        }
    }

    @FXML
    private void handleReservar() {
        String cliente = clienteField.getText();
        Vuelo vueloSeleccionado = vueloComboBox.getValue();
        int cantidadPasajeros = pasajerosSpinner.getValue();

        if (cliente.isEmpty() || vueloSeleccionado == null) {
            mensajeLabel.setText("Por favor, complete todos los campos.");
            mensajeLabel.setStyle("-fx-text-fill: #FF5252;");
            return;
        }

        // Verificar que todos los pasajeros tengan nombre y asiento seleccionado
        boolean datosCompletos = true;
        for (int i = 0; i < cantidadPasajeros; i++) {
            if (i >= nombresPasajeros.size() || i >= asientosPasajeros.size()) {
                datosCompletos = false;
                break;
            }

            TextField nombreField = nombresPasajeros.get(i);
            ComboBox<String> asientoCombo = asientosPasajeros.get(i);

            if (nombreField.getText().isEmpty() || asientoCombo.getValue() == null) {
                datosCompletos = false;
                break;
            }
        }

        if (!datosCompletos) {
            mensajeLabel.setText("Por favor, complete los datos de todos los pasajeros y seleccione asientos.");
            mensajeLabel.setStyle("-fx-text-fill: #FF5252;");
            return;
        }

        // Crear lista de asientos reservados
        List<Asiento> asientosReservados = new ArrayList<>();
        List<Ticket> tickets = new ArrayList<>();

        for (int i = 0; i < cantidadPasajeros; i++) {
            String nombrePasajero = nombresPasajeros.get(i).getText();
            String numeroAsiento = asientosPasajeros.get(i).getValue();
            boolean llevaEquipaje = equipajePasajeros.get(i).isSelected();

            // Crear asiento
            Asiento asiento = new Asiento(numeroAsiento);
            asiento.setDisponible(false);
            asientosReservados.add(asiento);

            // Marcar asiento como ocupado
            if (!asientosOcupados.containsKey(vueloSeleccionado.getIdVuelo())) {
                asientosOcupados.put(vueloSeleccionado.getIdVuelo(), new ArrayList<>());
            }
            asientosOcupados.get(vueloSeleccionado.getIdVuelo()).add(numeroAsiento);

            // Calcular precio para este pasajero
            double precioPasajero = vueloSeleccionado.getPrecio();
            if (llevaEquipaje) {
                precioPasajero += vueloSeleccionado.getPrecioEquipaje();
            }

            // Crear ticket
            Ticket ticket = new Ticket(
                    nombrePasajero,
                    vueloSeleccionado.getCompaniaAerea(),
                    vueloSeleccionado.getOrigen(),
                    vueloSeleccionado.getDestino(),
                    vueloSeleccionado.getHoraSalida(),
                    vueloSeleccionado.getHoraLlegada(),
                    precioPasajero,
                    llevaEquipaje
            );
            tickets.add(ticket);
        }

        // Calcular precio total
        double precioTotal = 0.0;
        for (Ticket ticket : tickets) {
            precioTotal += ticket.calcularPrecioTotal();
        }

        // Crear la reserva con los tickets generados
        Reservas nuevaReserva = new Reservas(
                "RES-" + (reservasRealizadas.size() + 1),
                EstadoReserva.CONFIRMADA,
                "NR-" + String.format("%03d", reservasRealizadas.size() + 1),
                LocalDateTime.now(),
                vueloSeleccionado.getHoraSalida(),
                vueloSeleccionado.getIdVuelo(),
                asientosReservados,
                cliente,
                precioTotal,
                tickets
        );

        reservasRealizadas.add(nuevaReserva);

        // Guardar las reservas en CSV
        CSVUtil.guardarReservas(reservasRealizadas);

        mensajeLabel.setText("¡Reserva realizada con éxito!");
        mensajeLabel.setStyle("-fx-text-fill: #64B5F6;");

        // Mostrar diálogo de confirmación
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Reserva Confirmada");
        alert.setHeaderText("Reserva realizada con éxito");

        StringBuilder contenido = new StringBuilder();
        contenido.append("Se ha creado la reserva con ID: ").append(nuevaReserva.getIdReserva()).append("\n");
        contenido.append("Número de reserva: ").append(nuevaReserva.getNumeroReserva()).append("\n");
        contenido.append("Número de pasajeros: ").append(cantidadPasajeros).append("\n");
        contenido.append("Precio total: ").append(String.format("%.2f €", nuevaReserva.getTotalPago())).append("\n\n");
        contenido.append("Tickets generados:\n");

        for (int i = 0; i < tickets.size(); i++) {
            Ticket ticket = tickets.get(i);
            contenido.append("- Pasajero: ").append(ticket.getNombrePasajero()).append("\n");
            contenido.append("  Asiento: ").append(asientosReservados.get(i).getNumeroAsiento()).append("\n");
            contenido.append("  Equipaje: ").append(ticket.isEquipajeIncluido() ? "Sí" : "No").append("\n");
            contenido.append("  Precio: ").append(String.format("%.2f €", ticket.calcularPrecioTotal())).append("\n\n");
        }

        alert.setContentText(contenido.toString());

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

        // Limpiar formulario
        clienteField.clear();
        vueloComboBox.setValue(null);
        pasajerosSpinner.getValueFactory().setValue(1);
        pasajerosContainer.getChildren().clear();
        nombresPasajeros.clear();
        asientosPasajeros.clear();
        equipajePasajeros.clear();
        actualizarResumen();
    }

    @FXML
    private void handleVolver() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/gui/menuScene.fxml"));
            AnchorPane menuView = loader.load();

            // No necesitamos pasar los datos al MainMenuController
            // ya que no tiene los métodos setVuelosDisponibles y setReservasRealizadas

            Scene menuScene = new Scene(menuView);
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(menuScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            mensajeLabel.setText("Error al volver al menú principal.");
            mensajeLabel.setStyle("-fx-text-fill: #FF5252;");
        }
    }
}