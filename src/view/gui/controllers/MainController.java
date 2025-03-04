package view.gui.controllers;

import model.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label errorLabel;

    private Autenticacion autenticacion;

    public MainController() {
        // Inicializar la clase de autenticación
        autenticacion = new Autenticacion();
    }

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        Usuario usuario = autenticacion.iniciarSesion(username, password);

        if (usuario != null) {
            errorLabel.setText("¡Inicio de sesión exitoso!");

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/gui/menuScene.fxml"));
                AnchorPane menuView = loader.load();
                Scene menuScene = new Scene(menuView);
                Stage stage = (Stage) usernameField.getScene().getWindow();
                stage.setScene(menuScene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            errorLabel.setText("Credenciales incorrectas.");
        }
    }


    @FXML
    private void handleRegister() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/gui/registerScene.fxml"));
            AnchorPane registerView = loader.load();
            Scene registerScene = new Scene(registerView);
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(registerScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Esto te mostrará el error si algo falla
        }
    }
}
