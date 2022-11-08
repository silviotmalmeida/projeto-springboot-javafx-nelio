package com.silviotmalmeida.app.javafx;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
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

    // injetando o contexto do spring, para permitir as demais injeções nos
    // controllers chamados a partir deste
    @Autowired
    private ApplicationContext springContext;

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

        // carrega a tela de listagem de vendedores na cena principal
        // atribuindo o controller e ações iniciais
        this.loadView("SellerList.fxml", (SellerListController controller) -> {
            controller.updateTableView();
        });

    }

    // referente método disparado pelo evento onAction do menuItemDepartment
    @FXML
    public void onMenuItemDepartmentAction() {

        // carrega a tela de listagem de departamentos na cena principal
        // atribuindo o controller e ações iniciais
        this.loadView("DepartmentList.fxml", (DepartmentListController controller) -> {
            controller.updateTableView();
        });
    }

    // referente método disparado pelo evento onAction do menuItemAbout
    @FXML
    public void onMenuItemAboutAction() {

        // carrega a tela na cena principal
        // sem controller, logo usa uma função lambda vazia
        this.loadView("About.fxml", x -> {
        });
    }

    // sobrecarregando o método de configuração da tela para inicialização
    @Override
    public void initialize(URL uri, ResourceBundle rb) {
    }

    // método que vai substituir o conteúdo da cena principal por uma tela
    // informada, preservando o menu.
    // as telas devem ser baseadas em VBox
    // caso a tela possua controller, deve-se realizar a atribuição do mesmo e
    // definição das ações de inicialização com expressões lambda
    // o atributo synchronized torna a execução sícrona nneste método
    private synchronized <T> void loadView(String absoluteName, Consumer<T> initializingAction) {

        // tratando as exceções
        try {

            // carregando a tela informada e incluindo o contexto do spring
            // as views devem ser baseadas em VBox
            FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
            loader.setControllerFactory(springContext::getBean);
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

            // obtendo o controller atribuído
            T controller = loader.getController();

            // realizando as ações iniciais definidas na expressão lambda da atribuição
            initializingAction.accept(controller);
        }
        // em caso de exceção, exibe um alerta
        catch (IOException e) {
            Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
        }
    }
}
