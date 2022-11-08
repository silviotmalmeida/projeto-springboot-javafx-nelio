package com.silviotmalmeida.app.javafx.utils;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

// classe utilitária para criação de alertas
public class Alerts {

    // método que cria um alerta simples
    public static void showAlert(String title, String header, String content, AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.show();
    }

    // método que cria um alerta de confirmação
    public static Optional<ButtonType> showConfirmation(String title, String content) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        return alert.showAndWait();
    }
}
