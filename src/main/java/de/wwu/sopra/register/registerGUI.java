package de.wwu.sopra.register;



import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;


public class RegisterGUI extends HBox {
	
	private RegisterCTRL controller = new RegisterCTRL();
	
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
		
		var streetNameLabel = new Label("Stra\u00DFe: ");
		var streetNameTextField = new TextField("");
		innerBox.add(streetNameLabel, 0, 2);
		innerBox.add(streetNameTextField, 1, 2);
		
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
