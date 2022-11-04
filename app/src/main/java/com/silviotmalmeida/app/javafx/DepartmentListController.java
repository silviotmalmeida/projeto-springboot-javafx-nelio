package com.silviotmalmeida.app.javafx;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

import com.silviotmalmeida.app.JavaFXApplication;
import com.silviotmalmeida.app.entities.Department;
import com.silviotmalmeida.app.javafx.utils.Alerts;
import com.silviotmalmeida.app.javafx.utils.Utils;
import com.silviotmalmeida.app.services.DepartmentService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

// import java.io.IOException;
// import java.net.URL;
// import java.util.List;
// import java.util.Optional;
// import java.util.ResourceBundle;

// import application.Main;
// import db.DbIntegrityException;
// import gui.listeners.DataChangeListener;
// import gui.util.Alerts;
// import gui.util.Utils;
// import javafx.beans.property.ReadOnlyObjectWrapper;
// import javafx.collections.FXCollections;
// import javafx.collections.ObservableList;
// import javafx.event.ActionEvent;
// import javafx.fxml.FXML;
// import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
// import javafx.scene.Scene;
// import javafx.scene.control.Alert.AlertType;
// import javafx.scene.control.Button;
// import javafx.scene.control.ButtonType;
// import javafx.scene.control.TableCell;
// import javafx.scene.control.TableColumn;
// import javafx.scene.control.TableView;
// import javafx.scene.control.cell.PropertyValueFactory;
// import javafx.scene.layout.Pane;
// import javafx.stage.Modality;
// import javafx.stage.Stage;
// import model.entities.Department;
// import model.services.DepartmentService;
import net.rgielen.fxweaver.core.FxmlView;

// controlador da tela DepartmentList.fxml
@Controller
@FxmlView("DepartmentList.fxml")
public class DepartmentListController implements Initializable {

    // injetando o contexto do spring, para permitir as demais injeções nos
    // controllers chamados a partir deste
    @Autowired
    private ApplicationContext springContext;

    // injetando o service da entidade Department
    @Autowired
    private DepartmentService service;

    // a anotação @FXML faz a ligação com os elementos da view
    @FXML
    private TableView<Department> tableViewDepartment;

    @FXML
    private TableColumn<Department, Integer> tableColumnId;

    @FXML
    private TableColumn<Department, String> tableColumnName;

    @FXML
    private TableColumn<Department, Department> tableColumnEDIT;

    @FXML
    private TableColumn<Department, Department> tableColumnREMOVE;

    @FXML
    private Button btNew;

    // atributo para armazenar a lista de departamentos a ser renderizada na tabela
    private ObservableList<Department> obsList;

    // referente método disparado pelo evento onAction do btNew
    @FXML
    public synchronized void onBtNewAction(ActionEvent event) {
        Stage parentStage = Utils.currentStage(event);
        Department obj = new Department();
        createDialogForm(obj, "DepartmentForm.fxml", parentStage);
    }

    // sobrecarregando o método de configuração da tela para inicialização
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // inicializando a tabela
        initializeNodes();
    }

    // método auxiliar responsável por configurar a tabela
    private void initializeNodes() {

        // definindo a ligação das colunas da tabela com os atributos da entidade
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));

        // obtendo o stage a partir da cena
        Stage stage = (Stage) JavaFXApplication.getMainScene().getWindow();

        // ajustando a altura da tabela para acompanhar a janela
        tableViewDepartment.prefHeightProperty().bind(stage.heightProperty());
    }

    // método responsável por popular a lista de departamentos a ser renderizada na
    // tabela
    public void updateTableView() {

        // se o service não estiver injetado, lança uma exceção
        if (service == null) {
            throw new IllegalStateException("Service was null");
        }

        // obtendo os dados do BD
        List<Department> list = this.service.findAll();

        // renderizando os dados na tabela
        this.obsList = FXCollections.observableArrayList(list);
        tableViewDepartment.setItems(this.obsList);
        // initEditButtons();
        // initRemoveButtons();
    }

    private void createDialogForm(Department obj, String absoluteName, Stage parentStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
            loader.setControllerFactory(springContext::getBean);
            Pane pane = loader.load();

            // DepartmentFormController controller = loader.getController();
            // controller.setDepartment(obj);
            // controller.updateFormData();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Enter Department data");
            dialogStage.setScene(new Scene(pane));
            dialogStage.setResizable(false);
            dialogStage.initOwner(parentStage);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(),
                    AlertType.ERROR);
        }
    }

    // @Override
    // public void onDataChanged() {
    // updateTableView();
    // }

    // private void initEditButtons() {
    // tableColumnEDIT.setCellValueFactory(param -> new
    // ReadOnlyObjectWrapper<>(param.getValue()));
    // tableColumnEDIT.setCellFactory(param -> new TableCell<Department,
    // Department>() {
    // private final Button button = new Button("edit");

    // @Override
    // protected void updateItem(Department obj, boolean empty) {
    // super.updateItem(obj, empty);
    // if (obj == null) {
    // setGraphic(null);
    // return;
    // }
    // setGraphic(button);
    // button.setOnAction(
    // event -> createDialogForm(obj, "/gui/DepartmentForm.fxml",
    // Utils.currentStage(event)));
    // }
    // });
    // }

    // private void initRemoveButtons() {
    // tableColumnREMOVE.setCellValueFactory(param -> new
    // ReadOnlyObjectWrapper<>(param.getValue()));
    // tableColumnREMOVE.setCellFactory(param -> new TableCell<Department,
    // Department>() {
    // private final Button button = new Button("remove");

    // @Override
    // protected void updateItem(Department obj, boolean empty) {
    // super.updateItem(obj, empty);
    // if (obj == null) {
    // setGraphic(null);
    // return;
    // }
    // setGraphic(button);
    // button.setOnAction(event -> removeEntity(obj));
    // }
    // });
    // }

    // private void removeEntity(Department obj) {
    // Optional<ButtonType> result = Alerts.showConfirmation("Confirmation", "Are
    // you sure to delete?");

    // if (result.get() == ButtonType.OK) {
    // if (service == null) {
    // throw new IllegalStateException("Service was null");
    // }
    // try {
    // service.remove(obj);
    // updateTableView();
    // }
    // catch (DbIntegrityException e) {
    // Alerts.showAlert("Error removing object", null, e.getMessage(),
    // AlertType.ERROR);
    // }
    // }
    // }
}