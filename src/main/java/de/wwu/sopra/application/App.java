package de.wwu.sopra.application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Einstiegspunkt in die Anwendung
 */
public class App extends Application {
	/**
	 * Standardkonstruktor
	 */
	public App() {
	}

	/**
	 * Einstiegspunkt der Anwendung
	 * 
	 * @param args Argumente zum Programmstart
	 */
	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Leihrad-\u00dcbersicht");

		GridPane gridPane = new GridPane();

		Scene scene = new Scene(gridPane);
		primaryStage.setScene(scene);
		primaryStage.show();

	}
}
