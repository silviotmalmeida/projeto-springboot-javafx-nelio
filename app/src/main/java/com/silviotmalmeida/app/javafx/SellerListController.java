package com.silviotmalmeida.app.javafx;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

import com.silviotmalmeida.app.JavaFXApplication;
import com.silviotmalmeida.app.entities.Department;
import com.silviotmalmeida.app.entities.Seller;
import com.silviotmalmeida.app.javafx.listeners.DataChangeListener;
import com.silviotmalmeida.app.javafx.utils.Alerts;
import com.silviotmalmeida.app.javafx.utils.Utils;
import com.silviotmalmeida.app.services.SellerService;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxmlView;

// controlador da tela SellerList.fxml
// tem o papel de listener para a interface DataChangeListener, ou seja, é a classe que observa o evento emitido pelo SellerFormController
@Controller
@FxmlView("SellerList.fxml")
public class SellerListController implements Initializable, DataChangeListener {

    // injetando o contexto do spring, para permitir as demais injeções nos
    // controllers chamados a partir deste
    @Autowired
    private ApplicationContext springContext;

    // injetando o service da entidade Seller
    @Autowired
    private SellerService service;

    // a anotação @FXML faz a ligação com os elementos da view
    @FXML
    private TableView<Seller> tableViewSeller;

    @FXML
    private TableColumn<Seller, Integer> tableColumnId;

    @FXML
    private TableColumn<Seller, String> tableColumnName;

    @FXML
    private TableColumn<Seller, String> tableColumnEmail;

    @FXML
    private TableColumn<Seller, Instant> tableColumnBirthDate;

    @FXML
    private TableColumn<Seller, Double> tableColumnBaseSalary;

    @FXML
    private TableColumn<Seller, Department> tableColumnDepartment;

    @FXML
    private TableColumn<Seller, Seller> tableColumnEDIT;

    @FXML
    private TableColumn<Seller, Seller> tableColumnREMOVE;

    @FXML
    private Button btNew;

    // atributo para armazenar a lista de departamentos a ser renderizada na tabela
    private ObservableList<Seller> obsList;

    // referente método disparado pelo evento onAction do btNew
    @FXML
    public synchronized void onBtNewAction(ActionEvent event) {

        // obtendo o stage pai
        Stage parentStage = Utils.currentStage(event);

        // declarando um Seller vazio, pois trata-se de create
        Seller obj = new Seller();

        // criando o formulário de create
        createDialogForm(obj, "SellerForm.fxml", parentStage);
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
        tableColumnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tableColumnBirthDate.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        tableColumnBaseSalary.setCellValueFactory(new PropertyValueFactory<>("baseSalary"));
        tableColumnDepartment.setCellValueFactory(new PropertyValueFactory<>("department"));

        // formatando a exibição da data
        Utils.formatTableColumnInstant(tableColumnBirthDate, "dd/MM/yyyy");

        // formatando a exibição do salário
        Utils.formatTableColumnDouble(tableColumnBaseSalary, 2);

        // formatando a exibição do departamento
        Utils.formatTableColumnDepartment(tableColumnDepartment);

        // obtendo o stage a partir da cena
        Stage stage = (Stage) JavaFXApplication.getMainScene().getWindow();

        // ajustando a altura da tabela para acompanhar a janela
        tableViewSeller.prefHeightProperty().bind(stage.heightProperty());
    }

    // método responsável por popular a lista de departamentos a ser renderizada na
    // tabela
    public void updateTableView() {

        // se o service não estiver injetado, lança uma exceção
        if (service == null) {
            throw new IllegalStateException("Service was null");
        }

        // obtendo os dados do BD
        List<Seller> list = this.service.findAll();

        // renderizando os dados na tabela
        this.obsList = FXCollections.observableArrayList(list);
        tableViewSeller.setItems(this.obsList);
        initEditButtons();
        initRemoveButtons();
    }

    // método que vai criar uma tela diálogo com formulário
    // o atributo synchronized torna a execução sícrona nneste método
    private synchronized void createDialogForm(Seller obj, String absoluteName,
            Stage parentStage) {

        // tratando as exceções
        try {

            // carregando a tela informada e incluindo o contexto do spring
            // será criado um
            FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
            loader.setControllerFactory(springContext::getBean);
            Pane pane = loader.load();

            // obtendo o controller da tela
            SellerFormController controller = loader.getController();

            // injetando o entidade Seller e atualizando os dados do formulário, caso
            // existam
            controller.setSeller(obj);
            controller.updateFormData();

            // increvendo este objeto como listener do controller para viabilizar
            // atualização dos dados da tabela
            controller.subscribeDataChangeListener(this);

            // criando uma nova janela e configurando como modal
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Enter Seller data");
            dialogStage.setScene(new Scene(pane));
            dialogStage.setResizable(false);
            dialogStage.initOwner(parentStage);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.showAndWait();

        }
        // em caso de exceção, exibe um alerta
        catch (IOException e) {
            e.printStackTrace();
            Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(),
                    AlertType.ERROR);
        }
    }

    // sobrescrevendo o método a ser disparado pelo subject do DataChangeListener
    @Override
    public void onDataChanged() {
        updateTableView();
    }

    // método auxiliar responsável pela criação dos botões de editar
    // registros nas linhas da tabela
    private void initEditButtons() {

        // para cada linha será gerado um botão
        tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        tableColumnEDIT.setCellFactory(param -> new TableCell<Seller, Seller>() {

            // definindo o label do botão
            private final Button button = new Button("edit");

            // definindo o evento do botão
            @Override
            protected void updateItem(Seller obj, boolean empty) {
                super.updateItem(obj, empty);
                if (obj == null) {
                    setGraphic(null);
                    return;
                }
                setGraphic(button);
                button.setOnAction(
                        event -> createDialogForm(obj, "SellerForm.fxml",
                                Utils.currentStage(event)));
            }
        });
    }

    // método auxiliar responsável pela criação dos botões de remover
    // registros nas linhas da tabela
    private void initRemoveButtons() {

        // para cada linha será gerado um botão
        tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        tableColumnREMOVE.setCellFactory(param -> new TableCell<Seller, Seller>() {

            // definindo o label do botão
            private final Button button = new Button("remove");

            // definindo o evento do botão
            @Override
            protected void updateItem(Seller obj, boolean empty) {
                super.updateItem(obj, empty);
                if (obj == null) {
                    setGraphic(null);
                    return;
                }
                setGraphic(button);
                button.setOnAction(event -> removeEntity(obj));
            }
        });
    }

    // método auxiliar para lançar o alert de confirmação de exclusão,
    // bem como responsável por remover o registro
    private void removeEntity(Seller obj) {

        // exibindo o alert de confirmação
        Optional<ButtonType> result = Alerts.showConfirmation("Confirmation", "Are you sure to delete?");

        // se houver confirmação
        if (result.get() == ButtonType.OK) {

            // se o service não estiver injetado, lança uma exceção
            if (service == null) {
                throw new IllegalStateException("Service was null");
            }

            // tatando exceções
            try {

                // removendo o registro
                service.delete(obj.getId());

                // atualizando os dados da tabela
                updateTableView();

            }
            // caso ocorra exceção, exibe um alerta
            catch (Exception e) {
                Alerts.showAlert("Error removing object", null, e.getMessage(),
                        AlertType.ERROR);
            }
        }
        // senão
        else {

            // atualizando os dados da tabela
            updateTableView();
        }
    }
}