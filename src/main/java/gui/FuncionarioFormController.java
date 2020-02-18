package gui;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import com.ricdev.formulario.connection.DbException;
import com.ricdev.formulario.model.entities.Funcionario;
import com.ricdev.formulario.model.entities.Instituicao;
import com.ricdev.formulario.model.exceptions.ValidationException;
import com.ricdev.formulario.model.services.FuncionarioService;
import com.ricdev.formulario.model.services.InstituicaoService;

import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;

public class FuncionarioFormController implements Initializable {

	private Funcionario entity;

	private FuncionarioService service;

	private InstituicaoService instituicaoService;

	private List<DataChangeListener> dataChangeListeners = new ArrayList<DataChangeListener>();

	@FXML
	private TextField txtId;
	@FXML
	private TextField txtNome;
	@FXML
	private TextField txtTelefone;
	@FXML
	private DatePicker dpAniversario;
	@FXML
	private TextField txtSalario;
	@FXML
	private ComboBox<Instituicao> comboBoxInstituicao;

	@FXML
	private Label labelErrorNome;
	@FXML
	private Label labelErrorTelefone;
	@FXML
	private Label labelErrorAniversario;
	@FXML
	private Label labelErrorSalario;
	@FXML
	private Button btSalvar;
	@FXML
	private Button btCancel;
	@FXML
	private Button btClear ; //new Button("", new javafx.scene.image.ImageView(escolherIcones;

	private ObservableList<Instituicao> obsList;

	@FXML
	public void onBtSalvarAction(ActionEvent event) {
		if (entity == null) {
			throw new IllegalStateException("entity was null");
		}
		if (service == null) {
			throw new IllegalStateException("service was null");
		}
		try {
			entity = getFormData();
			service.saveOrUpdate(entity);
			notifyDataChangeListeners();
			Utils.currentStage(event).close();
		} catch (ValidationException e) {
			setErrorMessagers(e.getErrors());
		} catch (DbException e) {
			Alerts.showAlert("Error saving object", null, e.getMessage(), AlertType.ERROR);
		}
	}	

	private void notifyDataChangeListeners() {
		for (DataChangeListener listener : dataChangeListeners) {
			listener.onDataChanged();
		}
	}

	private Funcionario getFormData() {
		Funcionario obj = new Funcionario();
		ValidationException exception = new ValidationException("Validation error");

		obj.setId(Utils.tryParseToInt(txtId.getText()));

		if (txtNome.getText() == null || txtNome.getText().trim().equals("")) {
			txtNome.setPromptText("");
			exception.addError("nome", "o campo não pode estar vazio");
		} else {
			obj.setNome(txtNome.getText());
		}

		if (txtTelefone.getText() == null || txtTelefone.getText().trim().equals("")) {
			txtTelefone.setPromptText("");
			exception.addError("telefone", "o campo não pode estar vazio");
		} else {
			obj.setTelefone(txtTelefone.getText());
		}

		if (txtSalario.getText() == null || txtSalario.getText().trim().equals("")) {
			txtSalario.setPromptText("");
			exception.addError("salario", "o campo não pode estar vazio");
		} else {
			obj.setSalario(Utils.tryParseToDouble(txtSalario.getText()));
		}

		if (dpAniversario.getValue() == null) {
			exception.addError("aniversario", "o campo não pode estar vazio");
		} else {
			LocalDate instant = LocalDate.from(dpAniversario.getValue().atStartOfDay(ZoneId.systemDefault()));
			obj.setAniversario(LocalDate.from(instant));
		}

		obj.setInstituicao(comboBoxInstituicao.getValue());

		if (exception.getErrors().size() > 0) {
			throw exception;
		}

		return obj;
	}

	@FXML
	public void onBtCancelAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}
	
	@FXML
	private void onClearAction(ActionEvent event) {
		if (labelErrorNome.getText() != null) {
			labelErrorNome.setText("");
			if (txtNome.getText() == null) {
				txtNome.setPromptText("digite o nome");
			}
		}		
		if (labelErrorTelefone.getText() != null) {
			labelErrorTelefone.setText("");
			if (txtTelefone.getText() == null) {
				txtTelefone.setPromptText("digite o Telefone");
			}
		}
		if (labelErrorSalario.getText() != null) {
			labelErrorSalario.setText("");
			if (txtSalario.getText() == null || txtSalario.getText().trim().equals("")) {				
				txtSalario.setPromptText("digite o salario");
			}
		}		
	}


	// ================= Setters ====================
	public void setFuncionario(Funcionario entity) {
		this.entity = entity;
	}

	public void setServices(FuncionarioService FuncionarioService, InstituicaoService instituicaoService) {
		this.service = FuncionarioService;
		this.instituicaoService = instituicaoService;
	}

	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);
	}

	// ==============================================

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initializeNodes();

	}

	private void initializeNodes() {
		Locale.setDefault(Locale.ROOT);
		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldMaxLength(txtNome, 40);
		Constraints.setTextFieldMaxLength(txtTelefone, 15);
		Constraints.setTextFieldDouble(txtSalario);
		Utils.formatDatePicker(dpAniversario, "dd/MM/yyyy");

		initializeComboBoxInstituicao();
	}

	public void updateFormData() {
		if (entity == null) {
			throw new IllegalStateException("entity was null");
		}
		txtId.setText(String.valueOf(entity.getId()));
		txtNome.setText(entity.getNome());
		txtTelefone.setText(entity.getTelefone());
		Locale.setDefault(Locale.US);
		txtSalario.setText(String.format("%.2f", entity.getSalario()));
		if (entity.getAniversario() != null) {
			dpAniversario.setValue(entity.getAniversario());
		} else {
			dpAniversario.setValue(LocalDate.now());
		}

		if (entity.getInstituicao() == null) {
			comboBoxInstituicao.getSelectionModel().selectFirst();
		} else {
			comboBoxInstituicao.setValue(entity.getInstituicao());
		}
	}

	public void loadAssociatedObjects() {
		if (instituicaoService == null) {
			throw new IllegalStateException("InstituicaoService was null");
		}
		List<Instituicao> list = instituicaoService.findAll();
		obsList = FXCollections.observableArrayList(list);
		comboBoxInstituicao.setItems(obsList);
	}

	private void setErrorMessagers(Map<String, String> error) {
		Set<String> fields = error.keySet();

		labelErrorNome.setText(fields.contains("nome") ? error.get("nome") : "");

		labelErrorTelefone.setText(fields.contains("telefone") ? error.get("telefone") : "");

		labelErrorAniversario.setText(fields.contains("aniversario") ? error.get("aniversario") : "");

		labelErrorSalario.setText(fields.contains("salario") ? error.get("salario") : "");

	}

	private void initializeComboBoxInstituicao() {
		Callback<ListView<Instituicao>, ListCell<Instituicao>> factory = lv -> new ListCell<Instituicao>() {
			@Override
			protected void updateItem(Instituicao item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty ? "" : item.getNome());
			}
		};

		comboBoxInstituicao.setCellFactory(factory);
		comboBoxInstituicao.setButtonCell(factory.call(null));
	}
	
}
