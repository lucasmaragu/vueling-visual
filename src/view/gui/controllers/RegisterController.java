package view.gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Autenticacion;
import model.Cliente;

import java.io.IOException;

public class RegisterController {

    @FXML
    private TextField usernameField;
    @FXML
    private TextField nombreField;   // Necesitarás agregar estos campos en el FXML
    @FXML
    private TextField apellido1Field;
    @FXML
    private TextField apellido2Field;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label errorLabel;

    private Autenticacion autenticacion;

    public RegisterController() {
        // Inicializar la clase de autenticación
        autenticacion = new Autenticacion();
    }

    @FXML
    public void handleRegister() {
        // Obtener los datos de los campos
        String idCliente = usernameField.getText();
        String nombre = nombreField.getText();
        String apellido1 = apellido1Field.getText();
        String apellido2 = apellido2Field.getText();
        String email = emailField.getText();
        String contrasena = passwordField.getText();

        // Validación básica
        if (idCliente.isEmpty() || nombre.isEmpty() || apellido1.isEmpty() || apellido2.isEmpty() || email.isEmpty() || contrasena.isEmpty()) {
            errorLabel.setText("Por favor, completa todos los campos.");
        } else {
            // Intentar registrar al cliente
            boolean registrado = autenticacion.registrarCliente(idCliente, nombre, apellido1, apellido2, email, contrasena);
            if (registrado) {
                errorLabel.setText("Registro exitoso.");
                errorLabel.setTextFill(javafx.scene.paint.Color.GREEN); // Solo asegúrate de tener acceso a javafx.scene.paint.Color
            } else {
                errorLabel.setText("Error en el registro. El ID de usuario ya está en uso.");
                errorLabel.setTextFill(javafx.scene.paint.Color.RED);
            }
        }
    }

    @FXML
    private void handleBackToLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/gui/loginScene.fxml"));
            AnchorPane loginView = loader.load();
            Scene loginScene = new Scene(loginView);
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(loginScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
