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
import com.ricdev.formulario.model.entities.Aluno;
import com.ricdev.formulario.model.exceptions.ValidationException;
import com.ricdev.formulario.model.services.AlunoService;
import com.ricdev.formulario.model.services.FuncionarioService;

import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AlunoFormController implements Initializable {

	private Aluno entity;

	private AlunoService service;

	private FuncionarioService instituicaoService;

	private List<DataChangeListener> dataChangeListeners = new ArrayList<DataChangeListener>();

	
	//dados do Aluno	
	@FXML
	private TextField txtId;
	@FXML
	private TextField txtNome;
	@FXML
	private TextField txtTelefoneAluno;
	@FXML
	private TextField txtNumMatricula;
	@FXML
	private DatePicker dpAniversario;
	@FXML
	private TextField txtNumNIS;
	
	// dados do responsavel
		
	@FXML
	private TextField txtNomeResponsavel;
	@FXML
	private TextField txtEndereco;
	@FXML
	private TextField txtTelefone;
	@FXML
	private TextField txtRg;
	@FXML
	private DatePicker dpAniversarioResponsavel;
	@FXML
	private TextField txtCpf;
	
	// ficha medica
	@FXML
	private TextField txtNomeMedico;
	@FXML
	private TextField txtCRM;
	
//	@FXML
//	private ComboBox<Funcionario> comboBoxFuncionario;

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

	//private ObservableList<Funcionario> obsList;

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

	private Aluno getFormData() {
		Aluno obj = new Aluno();
		ValidationException exception = new ValidationException("Validation error");
		
		// ========== dados Aluno ==============

		obj.setId(Utils.tryParseToInt(txtId.getText()));

		if (txtNome.getText() == null || txtNome.getText().trim().equals("")) {
			txtNome.setPromptText("");
			exception.addError("nome", "o campo não pode estar vazio");
		} else {
			obj.setNome(txtNome.getText());
		}
		
		if (txtNumMatricula.getText() == null || txtNumMatricula.getText().trim().equals("")) {
			txtNumMatricula.setPromptText("");
			exception.addError("numMatricula", "o campo não pode estar vazio");
		}else {
			obj.setNumMatricula(Utils.tryParseToInt(txtNumMatricula.getText()));
		}
		
		if (dpAniversario.getValue() == null) {
			exception.addError("aniversario", "o campo não pode estar vazio");
		} else {
			LocalDate instant = LocalDate.from(dpAniversario.getValue().atStartOfDay(ZoneId.systemDefault()));
			obj.setAniversario(LocalDate.from(instant));
		}
		
		if (txtNumNIS.getText() == null || txtNumNIS.getText().trim().equals("")) {
			txtNumNIS.setPromptText("");
			exception.addError("numNIS", "o campo não pode estar vazio");
		}else {
			obj.setNumNIS(Utils.tryParseToInt(txtNumNIS.getText()));
		}
		
		
		// ============ dados Responsavel =============
		
		if (txtNomeResponsavel.getText() == null || txtNomeResponsavel.getText().trim().equals("")) {
			txtNomeResponsavel.setPromptText("");
			exception.addError("nomeResponsavel", "o campo não pode estar vazio");
		} else {
			obj.setNomeResponsavel(txtNomeResponsavel.getText());
		}
		
		if (txtEndereco.getText() == null || txtEndereco.getText().trim().equals("")) {
			txtEndereco.setPromptText("");
			exception.addError("endereco", "o campo não pode estar vazio");
		} else {
			obj.setEndereco(txtEndereco.getText());
		}	

		if (txtTelefone.getText() == null || txtTelefone.getText().trim().equals("")) {
			txtTelefone.setPromptText("");
			exception.addError("telefone", "o campo não pode estar vazio");
		} else {
			obj.setTelefone(txtTelefone.getText());
		}
				
		if (dpAniversarioResponsavel.getValue() == null) {
			exception.addError("aniversarioResponsavel", "o campo não pode estar vazio");
		} else {
			LocalDate instant = LocalDate.from(dpAniversarioResponsavel.getValue().atStartOfDay(ZoneId.systemDefault()));
			obj.setAniversarioResponsavel(LocalDate.from(instant));
		}
		
		if (txtRg.getText() == null || txtRg.getText().trim().equals("")) {
			txtRg.setPromptText("");
			exception.addError("rg", "o campo não pode estar vazio");
		} else {
			obj.setRg(txtRg.getText());
		}
		
		if (txtCpf.getText() == null || txtCpf.getText().trim().equals("")) {
			txtCpf.setPromptText("");
			exception.addError("cpf", "o campo não pode estar vazio");
		} else {
			obj.setCpf(txtCpf.getText());
		}
		
		//============= ficha medica ==============
		
		if (txtNomeMedico.getText() == null || txtNomeMedico.getText().trim().equals("")) {
			txtNomeMedico.setPromptText("");
			exception.addError("nomeMedico", "o campo não pode estar vazio");
		} else {
			obj.setNomeMedico(txtNomeMedico.getText());
		}
		
		if (txtCRM.getText() == null || txtCRM.getText().trim().equals("")) {
			txtCRM.setPromptText("");
			exception.addError("crm", "o campo não pode estar vazio");
		} else {
			obj.setCrm(txtCRM.getText());
		}
		//obj.setFuncionario(comboBoxFuncionario.getValue());

		if (exception.getErrors().size() > 0) {
			throw exception;
		}
		return obj;
	}

	@FXML
	public void onBtCancelAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}
	
//	@FXML
//	private void onClearAction(ActionEvent event) {
//		if (labelErrorNome.getText() != null) {
//			labelErrorNome.setText("");
//			if (txtNome.getText() == null) {
//				txtNome.setPromptText("digite o nome");
//			}
//		}		
//		if (labelErrorTelefone.getText() != null) {
//			labelErrorTelefone.setText("");
//			if (txtTelefone.getText() == null) {
//				txtTelefone.setPromptText("digite o Telefone");
//			}
//		}
//		if (labelErrorSalario.getText() != null) {
//			labelErrorSalario.setText("");
//			if (txtSalario.getText() == null || txtSalario.getText().trim().equals("")) {				
//				txtSalario.setPromptText("digite o salario");
//			}
//		}		
//	}


	// ================= Setters ====================
	public void setAluno(Aluno entity) {
		this.entity = entity;
	}

	public void setServices(AlunoService AlunoService, FuncionarioService instituicaoService) {
		this.service = AlunoService;
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
		
		//============= Aluno =================
		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldMaxLength(txtNome, 40);
		Constraints.setTextFieldInteger(txtNumMatricula);
		Utils.formatDatePicker(dpAniversario, "dd/MM/yyyy");
		Constraints.setTextFieldInteger(txtNumNIS);
		
		// Responsaveis
		Constraints.setTextFieldMaxLength(txtNomeResponsavel, 40);
		Constraints.setTextFieldMaxLength(txtEndereco, 60);
		Constraints.setTextFieldMaxLength(txtTelefone, 15);
		Constraints.setTextFieldMaxLength(txtRg, 16);
		Utils.formatDatePicker(dpAniversarioResponsavel, "dd/MM/yyyy");
		Constraints.setTextFieldMaxLength(txtCpf, 16);
		
		// ficha Medica
		Constraints.setTextFieldMaxLength(txtNomeMedico, 40);
		Constraints.setTextFieldMaxLength(txtCRM, 10);
		
		//initializeComboBoxFuncionario();
	}

	public void updateFormData() {
		if (entity == null) {
			throw new IllegalStateException("entity was null");
		}
		
		// ============ Aluno ===============
		txtId.setText(String.valueOf(entity.getId()));
		txtNome.setText(entity.getNome());
		txtNumMatricula.setText(String.valueOf(entity.getNumMatricula()));
		Locale.setDefault(Locale.US);
		if (entity.getAniversario() != null) {
			dpAniversario.setValue(entity.getAniversario());
		} else {
			dpAniversario.setValue(LocalDate.now());
		}
		txtNumNIS.setText(String.valueOf(entity.getNumNIS()));
		
		// ============= responsaveis ===============
		txtNomeResponsavel.setText(entity.getNomeResponsavel());
		txtEndereco.setText(entity.getEndereco());		
		txtTelefone.setText(entity.getTelefone());
		txtRg.setText(entity.getRg());
		Locale.setDefault(Locale.US);
		if (entity.getAniversarioResponsavel() != null) {
			dpAniversarioResponsavel.setValue(entity.getAniversarioResponsavel());
		}else {
			dpAniversario.setValue(LocalDate.now());
		}		
		txtCpf.setText(entity.getCpf());
		
		// ============= Medico =================
		txtNomeMedico.setText(entity.getNomeMedico());
		txtCRM.setText(entity.getCrm());
	
//		if (entity.getFuncionario() == null) {
//			comboBoxFuncionario.getSelectionModel().selectFirst();
//		} else {
//			comboBoxFuncionario.setValue(entity.getFuncionario());
//		}
	}

	// //================== liga ComboBox ======================
//	public void loadAssociatedObjects() {
//		if (instituicaoService == null) {
//			throw new IllegalStateException("FuncionarioService was null");
//		}
//		List<Funcionario> list = instituicaoService.findAll();
//		obsList = FXCollections.observableArrayList(list);
//		comboBoxFuncionario.setItems(obsList);
//	}

	private void setErrorMessagers(Map<String, String> error) {
		Set<String> fields = error.keySet();

		labelErrorNome.setText(fields.contains("nome") ? error.get("nome") : "");

		labelErrorTelefone.setText(fields.contains("telefone") ? error.get("telefone") : "");

		labelErrorAniversario.setText(fields.contains("aniversario") ? error.get("aniversario") : "");

		labelErrorSalario.setText(fields.contains("salario") ? error.get("salario") : "");

	}

//	private void initializeComboBoxFuncionario() {
//		Callback<ListView<Funcionario>, ListCell<Funcionario>> factory = lv -> new ListCell<Funcionario>() {
//			@Override
//			protected void updateItem(Funcionario item, boolean empty) {
//				super.updateItem(item, empty);
//				setText(empty ? "" : item.getNome());
//			}
//		};
//
//		comboBoxFuncionario.setCellFactory(factory);
//		comboBoxFuncionario.setButtonCell(factory.call(null));
//	}
	
}
