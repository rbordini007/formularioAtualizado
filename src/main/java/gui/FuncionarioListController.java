package gui;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.ricdev.formulario.MainFX;
import com.ricdev.formulario.connection.DbIntegrityException;
import com.ricdev.formulario.model.entities.Funcionario;
import com.ricdev.formulario.model.services.FuncionarioService;
import com.ricdev.formulario.model.services.InstituicaoService;

import gui.css.ImageIcones;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Utils;
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
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class FuncionarioListController implements Initializable, DataChangeListener {

	private FuncionarioService service;
	
	private ImageIcones escolherIcones = new ImageIcones();
	

	@FXML
	private TableView<Funcionario> tableViewFuncionario;
	@FXML
	private TableColumn<Funcionario, Integer> tableColunmId;
	@FXML
	private TableColumn<Funcionario, String> tableColunmNome;
	@FXML
	private TableColumn<Funcionario, String> tableColunmTelefone;
	@FXML
	private TableColumn<Funcionario, LocalDate> tableColunmAniversario;
	@FXML
	private TableColumn<Funcionario, Double> tableColunmSalario;
	@FXML
	private TableColumn<Funcionario, Funcionario> tableColumnEDIT;
	@FXML
	private TableColumn<Funcionario, Funcionario> tableColumnREMOVE;

	@FXML
	private Button btNew;

	private ObservableList<Funcionario> obsList;

	@FXML
	public void onBtNewAction(ActionEvent event) {
		System.out.println("Bt New");
		Stage parentStage = Utils.currentStage(event);
		Funcionario obj = new Funcionario();
		createDialogForm(obj, "/gui/FuncionarioForm.fxml", parentStage);
	}

	// ===================== setters ===================//
	public void setFuncionarioService(FuncionarioService service) {
		this.service = service;
	}

	// ================================================//

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	private void initializeNodes() {
		tableColunmId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColunmNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		tableColunmTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
		
		Utils.formatTableColumnLocalDate(tableColunmAniversario, "dd/MM/yyyy");
		tableColunmAniversario.setCellValueFactory(new PropertyValueFactory<>("aniversario"));
		

		

		Utils.formatTableColumnDouble(tableColunmSalario, 2);
		tableColunmSalario.setCellValueFactory(new PropertyValueFactory<>("salario"));

		Stage stage = (Stage) MainFX.getMainScene().getWindow();
		tableViewFuncionario.prefHeightProperty().bind(stage.heightProperty());
	}

	public void updateTableView() {
		if (service == null) {
			throw new IllegalStateException("service was null");
		}
		List<Funcionario> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewFuncionario.setItems(obsList);

		initEditButtons();
		initRemoveButtons();
	}

	private void createDialogForm(Funcionario obj, String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			FuncionarioFormController controller = loader.getController();
			controller.setFuncionario(obj);
			controller.setServices(new FuncionarioService(), new InstituicaoService());
			controller.loadAssociatedObjects();
			controller.subscribeDataChangeListener(this);
			controller.updateFormData();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Entre com os dados da Intituicao");
			dialogStage.getIcons().add(new Image(getClass().getResourceAsStream("/gui\\css\\JavaImagem.jpg")));
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(false);
			dialogStage.initOwner(parentStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
			Alerts.showAlert("IO Exception", "Erro carregando a tela", e.getMessage(), AlertType.ERROR);
		}
	}

	@Override
	public void onDataChanged() {
		updateTableView();
	}

	private void initEditButtons() {
		tableColumnEDIT.setPrefWidth(50.0);		
		tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnEDIT.setCellFactory(param -> new TableCell<Funcionario, Funcionario>() {			
			private final Button button = new Button("", new javafx.scene.image.ImageView(escolherIcones.getImgEdit()));

			@Override
			protected void updateItem(Funcionario obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(
						event -> createDialogForm(obj, "/gui/FuncionarioForm.fxml", Utils.currentStage(event)));
			}
		});
	}

	private void initRemoveButtons() {
		tableColumnREMOVE.setPrefWidth(50.0);
		tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnREMOVE.setCellFactory(param -> new TableCell<Funcionario, Funcionario>() {			
			private final Button button = new Button("", new javafx.scene.image.ImageView(escolherIcones.getImgDelete()));

			@Override
			protected void updateItem(Funcionario obj, boolean empty) {
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

	private void removeEntity(Funcionario obj) {
		Optional<ButtonType> result = Alerts.showConfirmation("Confimação", "Tem certeza que deseja deletar?");

		if (result.get() == ButtonType.OK) {
			if (service == null) {
				throw new IllegalStateException("service was null");
			}
			try {
				service.remove(obj);
				updateTableView();
			} catch (DbIntegrityException e) {
				System.out.println(e.getMessage());
				Alerts.showAlert("Erro ao remover o objeto",
						"Você não pode Remover esta entidade!!! \n " + "Alguem está Vinculada a ela!",
						e.getMessage(), AlertType.ERROR);
			}
		}
	}
}
