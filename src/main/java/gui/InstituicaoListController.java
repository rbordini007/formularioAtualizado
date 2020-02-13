package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.ricdev.formulario.MainFX;
import com.ricdev.formulario.connection.DbIntegrityException;
import com.ricdev.formulario.model.entities.Instituicao;
import com.ricdev.formulario.model.services.InstituicaoService;

import gui.css.ImageIcon;
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
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class InstituicaoListController implements Initializable, DataChangeListener {

	private InstituicaoService service;

	private ImageIcon iconEdit = new ImageIcon();
	private ImageIcon iconDelete = new ImageIcon();

	@FXML
	private TableView<Instituicao> tableViewInstituicao;
	@FXML
	private TableColumn<Instituicao, Integer> tableColunmId;
	@FXML
	private TableColumn<Instituicao, String> tableColunmNome;
	@FXML
	private TableColumn<Instituicao, Instituicao> tableColumnEDIT;
	@FXML
	private TableColumn<Instituicao, Instituicao> tableColumnREMOVE;

	@FXML
	private Button btNew;

	private ObservableList<Instituicao> obsList;

	@FXML
	public void onBtNewAction(ActionEvent event) {
		System.out.println("Bt New");
		Stage parentStage = Utils.currentStage(event);
		Instituicao obj = new Instituicao();
		createDialogForm(obj, "/gui/InstituicaoForm.fxml", parentStage);
	}

	// ===================== setters ===================//
	public void setInstituicaoService(InstituicaoService service) {
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

		Stage stage = (Stage) MainFX.getMainScene().getWindow();
		tableViewInstituicao.prefHeightProperty().bind(stage.heightProperty());
	}

	public void updateTableView() {
		if (service == null) {
			throw new IllegalStateException("service was null");
		}
		List<Instituicao> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewInstituicao.setItems(obsList);

		initEditButtons();
		initRemoveButtons();
	}

	private void createDialogForm(Instituicao obj, String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			InstituicaoFormController controller = loader.getController();
			controller.setInstituicao(obj);
			controller.setInstituicaoService(new InstituicaoService());
			controller.subscribeDataChangeListener(this);
			controller.updateFormData();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Entre com os dados da Intituicao");
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(false);
			dialogStage.initOwner(parentStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();

		} catch (IOException e) {
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
		tableColumnEDIT.setCellFactory(param -> new TableCell<Instituicao, Instituicao>() {
			private final Button button = new Button("", new javafx.scene.image.ImageView(iconEdit.getImgEdit()));

			@Override
			protected void updateItem(Instituicao obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(
						event -> createDialogForm(obj, "/gui/InstituicaoForm.fxml", Utils.currentStage(event)));
			}
		});
	}

	private void initRemoveButtons() {
		tableColumnREMOVE.setPrefWidth(50.0);
		tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnREMOVE.setCellFactory(param -> new TableCell<Instituicao, Instituicao>() {
			private final Button button = new Button("", new javafx.scene.image.ImageView(iconDelete.getImgDelete()));

			@Override
			protected void updateItem(Instituicao obj, boolean empty) {
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

	private void removeEntity(Instituicao obj) {
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
				Alerts.showAlert("Erro ao remover o objeto", "Você não pode Remover esta entidade!!! \n "
						+ "Alguem está Vinculada a ela!", e.getMessage(), AlertType.ERROR);
			}			
		}
	}
}
