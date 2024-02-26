package de.wwu.sopra.register;



import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;


public class RegisterGUI extends HBox {
	
	
	public RegisterGUI(){
		init();
	}

	
	public void init() {
		
		var innerBox = new GridPane();
		innerBox.setAlignment(Pos.CENTER);
		innerBox.setVgap(5);
		
		var firstNameLabel = new Label("Vorname: ");
		var firstNameTextField = new TextField("");
		innerBox.add(firstNameLabel, 0, 0);
		innerBox.add(firstNameTextField, 1, 0);
		
		var lastNameLabel = new Label("Nachname: ");
		var lastNameTextField = new TextField("");
		innerBox.add(lastNameLabel, 0, 1);
		innerBox.add(lastNameTextField, 1, 1);
		
		var streetLabel = new Label("Stra\u00DFe: ");
		var streetTextField = new TextField("");
		innerBox.add(streetLabel, 0, 2);
		innerBox.add(streetTextField, 1, 2);
		
		var houseNumberLabel = new Label("Hausnummer: ");
		var houseNumberTextField = new TextField("");
		innerBox.add(houseNumberLabel, 0, 3);
		innerBox.add(houseNumberTextField, 1, 3);
		
		this.getChildren().addAll(innerBox);
		this.setAlignment(Pos.CENTER);
		VBox.setVgrow(this, Priority.ALWAYS);
		
		
		
	}

//	@Override
//	public void start(Stage primaryStage) throws Exception {
//		GridPane grid = new GridPane();
//		Scene register = new Scene(grid, 1920, 1080);
//		grid.setStyle("-fx-background-color: #CCFFEE");
//		
//		primaryStage.setScene(register);
//		primaryStage.show();
//		
//	}

}
