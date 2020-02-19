package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import com.ricdev.formulario.MainFX;
import com.ricdev.formulario.model.services.AlunoService;
import com.ricdev.formulario.model.services.FuncionarioService;
import com.ricdev.formulario.model.services.InstituicaoService;

import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class MainViewController implements Initializable {
	
	@FXML
	private MenuItem menuItemCadInstituicao;
	@FXML
	private MenuItem menuItemCadFuncionario;
	@FXML
	private MenuItem menuItemFichaMatricula;
	@FXML
	private MenuItem menuItemFichaSaude;
	@FXML
	private MenuItem menuItemCadTurma1;
	@FXML
	private MenuItem menuItemCadTurma2;
	@FXML
	private MenuItem menuItemCadTurma3;
	@FXML
	private MenuItem menuItemCadTurma4;
	@FXML
	private MenuItem menuItemAtendimento;
	@FXML
	private MenuItem menuItemSobre;
	@FXML
	private ImageView imgPrincipal;
	
	@FXML
	public void onMenuItemCadInstituicaoAction() {
		loadView("/gui/InstituicaoList.fxml", (InstituicaoListController controller) -> {
			controller.setInstituicaoService(new InstituicaoService());
			controller.updateTableView();
		});
	}
	@FXML
	public void onMenuItemCadFuncionarioAction() {
		loadView("/gui/FuncionarioList.fxml", (FuncionarioListController controller) -> {
			controller.setFuncionarioService(new FuncionarioService());
			controller.updateTableView();
		});
	}
	@FXML
	public void onMenuItemFichaMatriculaAction() {
		System.out.println("onMenuItemFichaMatriculaAction");
		loadView("/gui/AlunoList.fxml", (AlunoListController controller) -> {
		controller.setAlunoService(new AlunoService());
		controller.updateTableView();
		});
		
		
	}
	@FXML
	public void onMenuItemFichaSaudeAction() {
		System.out.println("onMenuItemFichaSaudeAction");
	}
	@FXML
	public void onMenuItemCadTurma1Action() {
		System.out.println("onMenuItemCadTurma1Action");
	}
	@FXML
	public void onMenuItemCadTurma2Action() {
		System.out.println("onMenuItemCadTurma2Action");
	}
	@FXML
	public void onMenuItemCadTurma3Action() {
		System.out.println("onMenuItemCadTurma3Action");
	}
	@FXML
	public void onMenuItemCadTurma4Action() {
		System.out.println("onMenuItemCadTurma4Action");
	}
	
	@FXML
	public void onMenuItemAtendimentoAction() {
		System.out.println("onMenuItemAtendimentoAction");
	}
	@FXML
	public void onMenuItemSobreAction() {
		loadView("/gui/Sobre.fxml", x -> {});
		
	}	

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
	}	
	
	private synchronized <T> void loadView(String absoluteName, Consumer<T> initializingAction ) {
		try {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
		VBox newVBox = loader.load();
		
		Scene mainScene = MainFX.getMainScene();
		VBox mainVBox = (VBox)((ScrollPane) mainScene.getRoot()).getContent();		
		
		Node mainMenu = mainVBox.getChildren().get(0);
		mainVBox.getChildren().clear();
		mainVBox.getChildren().add(mainMenu);
		mainVBox.getChildren().addAll(newVBox.getChildren());
		
		T controller = loader.getController();
		initializingAction.accept(controller);
		
		}catch (IOException e) {
			Alerts.showAlert("IO Exception", "Erro ao Carregar a Tela", e.getMessage(), AlertType.ERROR);
		}
	}	
}
