package com.silviotmalmeida.app.javafx;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;

import com.silviotmalmeida.app.entities.Department;
import com.silviotmalmeida.app.javafx.utils.Constraints;
import com.silviotmalmeida.app.services.DepartmentService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class DepartmentFormController implements Initializable {

    // inicializando o novo objeto
    private Department entity;

    // injetando o service da entidade Department
    @Autowired
    private DepartmentService service;

    // a anotação @FXML faz a ligação com os elementos da view
    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private Label labelErrorName;

    @FXML
    private Button btSave;

    @FXML
    private Button btCancel;

    public void setDepartment(Department entity) {
        this.entity = entity;
    }

    // referente método disparado pelo evento onAction do btSave
    @FXML
    public void onBtSaveAction(ActionEvent event) {
        // if (entity == null) {
        // throw new IllegalStateException("Entity was null");
        // }
        // if (service == null) {
        // throw new IllegalStateException("Service was null");
        // }
        // try {
        // entity = getFormData();
        // service.saveOrUpdate(entity);
        // notifyDataChangeListeners();
        // Utils.currentStage(event).close();
        // }
        // catch (ValidationException e) {
        // setErrorMessages(e.getErrors());
        // }
        // catch (DbException e) {
        // Alerts.showAlert("Error saving object", null, e.getMessage(),
        // AlertType.ERROR);
        // }
    }

    // referente método disparado pelo evento onAction do btCancel
    @FXML
    public void onBtCancelAction(ActionEvent event) {
        // Utils.currentStage(event).close();
    }

    // sobrecarregando o método de configuração da tela para inicialização
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeNodes();
    }

    // método auxiliar responsável por configurar o formulário, bem como as
    // restrições dos campos
    private void initializeNodes() {
        Constraints.setTextFieldInteger(txtId);
        Constraints.setTextFieldMaxLength(txtName, 30);
    }

    public void updateFormData() {
        if (entity == null) {
            throw new IllegalStateException("Entity was null");
        }
        txtId.setText(String.valueOf(entity.getId()));
        txtName.setText(entity.getName());
    }
}