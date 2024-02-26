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
		innerBox.add(lastNameLabel, 2, 0);
		innerBox.add(lastNameTextField, 3, 0);
		
		var streetLabel = new Label("Stra\u00DFe: ");
		var streetTextField = new TextField("");
		innerBox.add(streetLabel, 0, 2);
		innerBox.add(streetTextField, 1, 2);
		
		var houseNumberLabel = new Label("Hausnummer: ");
		var houseNumberTextField = new TextField("");
		innerBox.add(houseNumberLabel, 2, 2);
		innerBox.add(houseNumberTextField, 3, 2);
		
		var plzLabel = new Label("PLZ: ");
		var plzTextField = new TextField("");
		innerBox.add(plzLabel, 0, 4);
		innerBox.add(plzTextField, 1, 4);
		
		var townLabel = new Label("Stadt: ");
		var townTextField = new TextField("");
		innerBox.add(townLabel, 2, 4);
		innerBox.add(townTextField, 3, 4);
		
		var emailLabel = new Label("e-Mail: ");
		var emailTextField = new TextField("");
		innerBox.add(emailLabel, 0, 5);
		innerBox.add(emailTextField, 1, 5);
		
		var mobilLabel = new Label("Mobil: ");
		var mobilTextField = new TextField("");
		innerBox.add(mobilLabel, 2, 5);
		innerBox.add(mobilTextField, 3, 5);
		
		var IBANLabel = new Label("IBAN: ");
		var IBANTextField = new TextField("");
		innerBox.add(IBANLabel, 0, 6);
		innerBox.add(IBANTextField, 1, 6);
		
		var BICLabel = new Label("BIC: ");
		var BICTextField = new TextField("");
		innerBox.add(BICLabel, 2, 6);
		innerBox.add(BICTextField, 3, 6);
		
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
