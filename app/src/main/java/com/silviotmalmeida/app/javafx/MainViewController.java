package com.silviotmalmeida.app.javafx;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.stereotype.Controller;

import com.silviotmalmeida.app.JavaFXApplication;
import com.silviotmalmeida.app.javafx.utils.Alerts;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import net.rgielen.fxweaver.core.FxmlView;

// controlador da tela MainView.fxml
@Controller
@FxmlView("MainView.fxml")
public class MainViewController implements Initializable {

    // a anotação @FXML faz a ligação com os elementos da view
    @FXML
    private MenuItem menuItemSeller;

    @FXML
    private MenuItem menuItemDepartment;

    @FXML
    private MenuItem menuItemAbout;

    // referente método disparado pelo evento onAction do menuItemSeller
    @FXML
    public void onMenuItemSellerAction() {

    }

    // referente método disparado pelo evento onAction do menuItemDepartment
    @FXML
    public void onMenuItemDepartmentAction() {

    }

    // referente método disparado pelo evento onAction do menuItemAbout
    @FXML
    public void onMenuItemAboutAction() {

        // carrega a tela na cena principal
        this.loadView("About.fxml");
    }

    // sobrecarregando o método de configuração da tela para inicialização
    @Override
    public void initialize(URL uri, ResourceBundle rb) {
    }

    // método que vai substituir o conteúdo da cena principal por uma tela
    // informada, preservando o menu.
    // as telas devem ser baseadas em VBox
    // o atributo synchronized torna a execução sícrona nneste método
    private synchronized void loadView(String absoluteName) {

        // tratando as exceções
        try {

            // carregando a tela informada
            // as views devem ser baseadas em VBox
            FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
            VBox newVBox = loader.load();

            // obtendo a cena principal
            Scene mainScene = JavaFXApplication.getMainScene();

            // obtendo o VBox da cena principal, a ser alterado
            VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();

            // obtendo o menu a ser preservado
            Node mainMenu = mainVBox.getChildren().get(0);

            // limpando o VBox da cena principal
            mainVBox.getChildren().clear();

            // reinserindo o menu preservado
            mainVBox.getChildren().add(mainMenu);

            // inserindo o novo conteúdo
            mainVBox.getChildren().addAll(newVBox.getChildren());
        }
        // em caso de exceção, exibe um alerta
        catch (Exception e) {
            Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
        }

    }
}
