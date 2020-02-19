package com.ricdev.formulario;

import java.io.IOException;
import java.util.Locale;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.stage.Stage;

@SpringBootApplication
public class MainFX extends Application {

	private static Scene mainScene;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/MainView.fxml"));
			ScrollPane scrollPane = loader.load();
			
			scrollPane.setFitToHeight(true);
			scrollPane.setFitToWidth(true);
			
			mainScene = new Scene(scrollPane);
			primaryStage.setScene(mainScene);			
			primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/gui\\css\\JavaImagem.jpg")));
			primaryStage.setTitle("Formulario do Instituto");
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Scene getMainScene() {
		return mainScene;
	}

	public static void main(String[] args) {
		Locale.setDefault(Locale.ROOT);
		launch(args);		
	}
}
