package gui;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.ricdev.formulario.MainFX;
import com.ricdev.formulario.connection.DbIntegrityException;
import com.ricdev.formulario.model.entities.Aluno;
import com.ricdev.formulario.model.services.AlunoService;
import com.ricdev.formulario.model.services.FuncionarioService;

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
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jdk.jshell.Diag;

public class AlunoListController implements Initializable, DataChangeListener {

	private AlunoService service;

	private ImageIcones iconEdit = new ImageIcones();
	private ImageIcones iconDelete = new ImageIcones();
	
	@FXML
	private TabPane tablePaneAluno; 
	
	@FXML
	private TableView<Aluno> tableViewAluno;
	@FXML
	private TableView<Aluno> tableViewResponsavel;
	@FXML
	private TableView<Aluno> tableViewMedico;
	
	// dados do Aluno	
	@FXML
	private TableColumn<Aluno, Integer> tableColunmId;
	@FXML
	private TableColumn<Aluno, String> tableColunmNome;
	@FXML
	private TableColumn<Aluno, Integer> tableColunmNumMatricula;
	@FXML
	private TableColumn<Aluno, LocalDate> tableColunmAniversario;
	@FXML
	private TableColumn<Aluno, Integer> tableColunmNumNIS;
	
	// dados do Responsavel
	@FXML
	private TableColumn<Aluno, String> tableColunmNomeResponsavel;
	@FXML
	private TableColumn<Aluno, String> tableColunmEndereco;
	@FXML
	private TableColumn<Aluno, String> tableColunmTelefone;
	@FXML
	private TableColumn<Aluno, LocalDate> tableColunmAniverResponsavel;
	@FXML
	private TableColumn<Aluno, String> tableColunmRg;
	@FXML
	private TableColumn<Aluno, String> tableColunmCpf;
	
	// ficha medica
	@FXML
	private TableColumn<Aluno, String> tableColunmNomeMedico;
	@FXML
	private TableColumn<Aluno, String> tableColunmCRM;
	
	
	@FXML
	private TableColumn<Aluno, Aluno> tableColumnEDIT;
	@FXML
	private TableColumn<Aluno, Aluno> tableColumnREMOVE;

	@FXML
	private Button btNew;
	
	@FXML
	private Label lblHora;

	private ObservableList<Aluno> obsList;

	@FXML
	public void onBtNewAction(ActionEvent event) {
		System.out.println("Bt New");
		Stage parentStage = Utils.currentStage(event);
		Aluno obj = new Aluno();
		createDialogForm(obj, "/gui/AlunoForm.fxml", parentStage);
	}

	// ===================== setters ===================//
	public void setAlunoService(AlunoService service) {
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
		tableColunmNumMatricula.setCellValueFactory(new PropertyValueFactory<>("numMatricula"));
		
		Utils.formatTableColumnLocalDate(tableColunmAniversario, "dd/MM/yyyy");
		tableColunmAniversario.setCellValueFactory(new PropertyValueFactory<>("aniversario"));
		
		tableColunmNumNIS.setCellValueFactory(new PropertyValueFactory<>("numNIS"));
		tableColunmNomeResponsavel.setCellValueFactory(new PropertyValueFactory<>("nomeResponsavel"));
		tableColunmEndereco.setCellValueFactory(new PropertyValueFactory<>("endereco"));
		tableColunmTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
		
		Utils.formatTableColumnLocalDate(tableColunmAniverResponsavel, "dd/MM/yyyy");
		tableColunmAniverResponsavel.setCellValueFactory(new PropertyValueFactory<>("aniversarioResponsavel"));
		
		tableColunmRg.setCellValueFactory(new PropertyValueFactory<>("rg"));
		tableColunmCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
		tableColunmNomeMedico.setCellValueFactory(new PropertyValueFactory<>("nomeMedico"));
		tableColunmCRM.setCellValueFactory(new PropertyValueFactory<>("crm"));

		Stage stage = (Stage) MainFX.getMainScene().getWindow();
		tablePaneAluno.prefHeightProperty().bind(stage.heightProperty());
		//tableViewAluno.prefHeightProperty().bind(stage.heightProperty());
	}

	public void updateTableView() {
		if (service == null) {
			throw new IllegalStateException("service was null");
		}
		List<Aluno> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewAluno.setItems(obsList);
		tableViewResponsavel.setItems(obsList);
		tableViewMedico.setItems(obsList);

		initEditButtons();
		initRemoveButtons();
		dataDoDia();
	}

	private void createDialogForm(Aluno obj, String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			AlunoFormController controller = loader.getController();
			controller.setAluno(obj);
			controller.setServices(new AlunoService(), new FuncionarioService() );
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
		tableColumnEDIT.setCellFactory(param -> new TableCell<Aluno, Aluno>() {
			private final Button button = new Button("", new javafx.scene.image.ImageView(iconEdit.getImgEdit()));

			@Override
			protected void updateItem(Aluno obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(
						event -> createDialogForm(obj, "/gui/AlunoForm.fxml", Utils.currentStage(event)));
			}
		});
	}

	private void initRemoveButtons() {
		tableColumnREMOVE.setPrefWidth(50.0);
		tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnREMOVE.setCellFactory(param -> new TableCell<Aluno, Aluno>() {
			private final Button button = new Button("", new javafx.scene.image.ImageView(iconDelete.getImgDelete()));

			@Override
			protected void updateItem(Aluno obj, boolean empty) {
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

	private void removeEntity(Aluno obj) {
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
				Alerts.showAlert("Erro ao remover o objeto", "VocÃª nÃ£o pode Remover esta entidade!!! \n "
						+ "Alguem estÃ¡ Vinculada a ela!", e.getMessage(), AlertType.ERROR);
			}			
		}
	}
	
	public void dataDoDia() {
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String dia = LocalDate.now().format(dateFormatter);
		lblHora.setText(dia);
	}
}
