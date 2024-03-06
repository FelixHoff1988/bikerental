package de.wwu.sopra;

import de.wwu.sopra.bookingProcess.BookingProcessService;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Duration;

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
	 */
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("BikeRental.de");
		DataProvider.getInstance();
		var bookingService = new BookingProcessService();
		bookingService.setPeriod(Duration.seconds(60));
		bookingService.setDelay(Duration.ZERO);
		bookingService.start();
		var simulationService = new SimulationService();
		simulationService.setPeriod(Duration.seconds(10));
		simulationService.setDelay(Duration.seconds(10));
		simulationService.start();

		var gui = new AppGUI();
		AppContext.create(gui);
		Scene scene = new Scene(gui);

		primaryStage.setMaximized(true);
		primaryStage.setMinWidth(1080);
		primaryStage.setMinHeight(720);
		primaryStage.setScene(scene);
		primaryStage.getIcons().add(new Image("file:icon.png"));
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