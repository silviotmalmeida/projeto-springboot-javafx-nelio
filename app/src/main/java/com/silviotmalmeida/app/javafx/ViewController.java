package com.silviotmalmeida.app.javafx;

import org.springframework.stereotype.Controller;

import com.silviotmalmeida.app.javafx.utils.Alerts;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import net.rgielen.fxweaver.core.FxmlView;

// controlador da tela View.fxml
@Controller
@FxmlView("View.fxml")
public class ViewController {

    // a anotação @FXML faz a ligação com os elementos da view
    // referente ao btnTest
    @FXML
    private Button btnTest;

    // construtor vazio, requerido pelo framework
    public ViewController() {

    }

    // a anotação @FXML faz a ligação com os elementos da view
    // referente método disparado pelo evento onAction do btnTest
    @FXML
    public void onBtnTestAction(Event e) {

        Alerts.showAlert("Alert title", "Alert header", "Hello!", AlertType.INFORMATION);
    }

}
