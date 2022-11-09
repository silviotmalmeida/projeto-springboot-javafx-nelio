package com.silviotmalmeida.app.javafx;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.silviotmalmeida.app.entities.Department;
import com.silviotmalmeida.app.entities.Seller;
import com.silviotmalmeida.app.javafx.listeners.DataChangeListener;
import com.silviotmalmeida.app.javafx.utils.Alerts;
import com.silviotmalmeida.app.javafx.utils.Constraints;
import com.silviotmalmeida.app.javafx.utils.Utils;
import com.silviotmalmeida.app.services.DepartmentService;
import com.silviotmalmeida.app.services.SellerService;
import com.silviotmalmeida.app.services.exceptions.ValidationException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.util.Callback;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import net.rgielen.fxweaver.core.FxmlView;

// controlador da tela SellerForm.fxml
// tem o papel de subject para a interface DataChangeListener, ou seja, é a classe que emite o evento a uma
// lista de listeners
@Controller
@FxmlView("SellerForm.fxml")
public class SellerFormController implements Initializable {

    // declarando a dependência com a entidade Seller
    private Seller entity;

    // injetando o service da entidade Seller
    @Autowired
    private SellerService service;

    // injetando o service da entidade Department
    @Autowired
    private DepartmentService departmentService;

    // declarando a lista de listeners a serem informados
    private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

    // a anotação @FXML faz a ligação com os elementos da view
    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtEmail;

    @FXML
    private DatePicker dpBirthDate;

    @FXML
    private TextField txtBaseSalary;

    @FXML
    private Label labelErrorName;

    @FXML
    private Label labelErrorEmail;

    @FXML
    private Label labelErrorBirthDate;

    @FXML
    private Label labelErrorBaseSalary;

    @FXML
    private ComboBox<Department> comboBoxDepartment;

    @FXML
    private Button btSave;

    @FXML
    private Button btCancel;

    // atributo para armazenar a lista de departamentos a ser renderizada no
    // combobox
    private ObservableList<Department> obsList;

    // método para injeção da dependência da entidade Seller
    public void setSeller(Seller entity) {
        this.entity = entity;
    }

    // método que inclui um listener à lista de listeners a serem informados
    public void subscribeDataChangeListener(DataChangeListener listener) {
        this.dataChangeListeners.add(listener);
    }

    // referente método disparado pelo evento onAction do btSave
    @FXML
    public void onBtSaveAction(ActionEvent event) {

        // se a entidade não estiver injetada, lança uma exceção
        if (entity == null) {
            throw new IllegalStateException("Entity was null");
        }

        // se o service não estiver injetado, lança uma exceção
        if (service == null) {
            throw new IllegalStateException("Service was null");
        }

        // se o service não estiver injetado, lança uma exceção
        if (departmentService == null) {
            throw new IllegalStateException("Department Service was null");
        }

        // tratando exceções
        try {

            // obtendo um objeto com os dados do formulário
            entity = getFormData();

            // persistindo no BD
            service.saveOrUpdate(entity);

            // notificando os listeners deste evento
            notifyDataChangeListeners();

            // fechando o formulário
            Utils.currentStage(event).close();

        }
        // caso ocorra exceção de validação, popula a label com o erro
        catch (ValidationException e) {
            setErrorMessages(e.getErrors());
        }
        // caso ocorra outra exceção, exibe um alerta
        catch (Exception e) {
            Alerts.showAlert("Error saving object", null, e.getMessage(),
                    AlertType.ERROR);
        }
    }

    // referente método disparado pelo evento onAction do btCancel
    @FXML
    public void onBtCancelAction(ActionEvent event) {

        // notificando os listeners deste evento
        notifyDataChangeListeners();

        // fechando o formulário
        Utils.currentStage(event).close();
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
        Constraints.setTextFieldMaxLength(txtName, 70);
        Constraints.setTextFieldMaxLength(txtEmail, 60);
        Constraints.setTextFieldDouble(txtBaseSalary);
        Utils.formatDatePicker(dpBirthDate, "dd/MM/yyyy");

        this.loadComboBoxDepartment();
        this.initializeComboBoxDepartment();
    }

    // método auxiliar responsável por instanciar um objeto a partir dos dados do
    // formulário
    private Seller getFormData() {

        // inicializando um objeto vazio
        Seller obj = new Seller();

        // inicializando um objeto de validação vazio
        ValidationException exception = new ValidationException("Validation error");

        // populando o objeto

        //// id
        obj.setId(Utils.tryParseToLong(txtId.getText()));

        //// name
        obj.setName(txtName.getText());
        ////// validação de nulidade ou vazio
        if (txtName.getText() == null
                || txtName.getText().trim().equals("")) {

            exception.addError("name", "Field can't be empty!");
        }

        // se existir ao menos um erro, lança a exceção
        if (exception.getErrors().size() > 0) {
            throw exception;
        }

        // retornando o objeto
        return obj;
    }

    // método auxiliar responsável por popular os dados do formulário, caso existam
    // na
    // entidade
    public void updateFormData() {

        // se a entidade não estiver injetada, lança uma exceção
        if (entity == null) {
            throw new IllegalStateException("Entity was null");
        }

        // populando o formulário
        txtId.setText(String.valueOf(entity.getId()));
        txtName.setText(entity.getName());
        txtEmail.setText(entity.getEmail());
        txtBaseSalary.setText(String.format("%.2f", entity.getBaseSalary()));

        // se a data não for vazia, carrega
        if (entity.getBirthDate() != null) {
            dpBirthDate.setValue(LocalDate.ofInstant(entity.getBirthDate(), ZoneId.systemDefault()));
        }
        // se o departamento não for vazio, carrega
        if (entity.getDepartment() != null) {
            comboBoxDepartment.setValue(entity.getDepartment());
        }
    }

    // método responsável por popular o comboBox de Departamentos
    private void loadComboBoxDepartment() {

        // se o service não estiver injetado, lança uma exceção
        if (departmentService == null) {
            throw new IllegalStateException("Department Service was null");
        }

        // obtendo os dados no BD
        List<Department> list = this.departmentService.findAll();

        // populando o componente
        this.obsList = FXCollections.observableArrayList(list);
        comboBoxDepartment.setItems(obsList);
    }

    // método responsável por customizar o combobox de departamentos, configurando
    // a forma de exibição do mesmo
    private void initializeComboBoxDepartment() {
        Callback<ListView<Department>, ListCell<Department>> factory = lv -> new ListCell<Department>() {
            @Override
            protected void updateItem(Department item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getName());
            }
        };
        comboBoxDepartment.setCellFactory(factory);
        comboBoxDepartment.setButtonCell(factory.call(null));
    }

    // método auxiliar responsável por notificar os listeners
    private void notifyDataChangeListeners() {
        for (DataChangeListener listener : this.dataChangeListeners) {
            listener.onDataChanged();
        }
    }

    // método auxiliar responsável por exibir a mensagem de erro de validação no
    // formulário
    private void setErrorMessages(Map<String, String> errors) {

        // obtendo o conjunto de chaves com erro
        Set<String> fields = errors.keySet();

        // se existir erro na chave name, preenche a label de erro
        if (fields.contains("name")) {
            labelErrorName.setText(errors.get("name"));
        }
    }    
}