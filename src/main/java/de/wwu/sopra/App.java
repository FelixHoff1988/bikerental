package de.wwu.sopra;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Die Klasse App ist der Einstiegspunkt der Anwendung und erweitert die JavaFX Application Klasse.
 */
public class App extends Application {
	/**
	 * Standardkonstruktor für die App Klasse.
	 */
	public App() {
	}

	/**
	 * Der Einstiegspunkt der Anwendung. Startet die Anwendung durch Aufruf von Application.launch().
	 * 
	 * @param args Argumente zum Starten des Programms
	 */
	public static void main(String[] args) {
		Application.launch(args);
	}

	/**
	 * Die start() Methode wird aufgerufen, wenn die Anwendung gestartet wird.
	 * Sie setzt den Titel des Hauptfensters und erstellt eine GridPane für die Benutzeroberfläche.
	 * 
	 * @param primaryStage Das Hauptfenster der Anwendung
	 * @throws Exception Wenn ein Fehler beim Starten der Anwendung auftritt
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Leihrad-\u00dcbersicht");

		var userManagementGUI = UserManagementGUI.getInstance();
		Scene scene = new Scene(userManagementGUI);

		userManagementGUI.onLogin(user -> {
			var mainGUI = MainGUI.init(user);
			scene.setRoot(mainGUI);
			mainGUI.onLogout(() -> scene.setRoot(UserManagementGUI.getInstance()));
		});

		primaryStage.setFullScreen(true);
		primaryStage.setMinWidth(1080);
		primaryStage.setMinHeight(720);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * Wird beim Stoppen der Anwendung ausgeführt.
	 * Und initialisiert die Speicherung der aktuellen Anwendungsdaten.
	 */
	@Override
	public void stop() {
		DataProvider.getInstance().saveData();
	}
}