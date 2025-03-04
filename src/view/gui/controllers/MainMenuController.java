package view.gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;

public class MainMenuController {

    @FXML
    private Button logoutButton;

    @FXML
    private void handleVerVuelos() {
        System.out.println("Ver Vuelos seleccionado");
        // Aquí puedes agregar la lógica para cambiar de escena
    }

    @FXML
    private void handleReservarVuelo() {
        System.out.println("Reservar Vuelo seleccionado");
        // Aquí puedes agregar la lógica para cambiar de escena
    }

    @FXML
    private void handleVerReservas() {
        System.out.println("Ver Reservas seleccionado");
        // Aquí puedes agregar la lógica para cambiar de escena
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