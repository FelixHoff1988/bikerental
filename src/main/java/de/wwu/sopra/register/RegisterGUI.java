package de.wwu.sopra.register;



import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.regex.*;


public class RegisterGUI extends HBox {
	private RegisterCTRL ctrl = new RegisterCTRL();
	
	public RegisterGUI(){
		init();
	}

	
	public void init() {
		
		var innerBox = new GridPane();
		innerBox.setHgap(30);
		innerBox.setPadding(new Insets(25, 25, 25, 25));
		innerBox.setAlignment(Pos.CENTER);
		innerBox.setVgap(5);
		
		var firstNameLabel = new Label("Vorname: ");
		var firstNameTextField = new TextField("");
		innerBox.add(firstNameLabel, 0, 0);
		innerBox.add(firstNameTextField, 1, 0);
		
		var lastNameLabel = new Label("Nachname: ");
		var lastNameTextField = new TextField("");
		innerBox.add(lastNameLabel, 4, 0);
		innerBox.add(lastNameTextField, 5, 0);
		
		var streetLabel = new Label("Stra\u00DFe: ");
		var streetTextField = new TextField("");
		innerBox.add(streetLabel, 0, 2);
		innerBox.add(streetTextField, 1, 2);
		
		var houseNumberLabel = new Label("Hausnummer: ");
		var houseNumberTextField = new TextField("");
		innerBox.add(houseNumberLabel, 4, 2);
		innerBox.add(houseNumberTextField, 5, 2);
		
		var plzLabel = new Label("PLZ: ");
		var plzTextField = new TextField("");
		innerBox.add(plzLabel, 0, 4);
		innerBox.add(plzTextField, 1, 4);
		
		var townLabel = new Label("Stadt: ");
		var townTextField = new TextField("");
		innerBox.add(townLabel, 4, 4);
		innerBox.add(townTextField, 5, 4);
		
		var emailLabel = new Label("e-Mail: ");
		var emailTextField = new TextField("");
		innerBox.add(emailLabel, 0, 5);
		innerBox.add(emailTextField, 1, 5);
		
		var mobilLabel = new Label("Mobil: ");
		var mobilTextField = new TextField("");
		innerBox.add(mobilLabel, 4, 5);
		innerBox.add(mobilTextField, 5, 5);
		
		var IBANLabel = new Label("IBAN: ");
		var IBANTextField = new TextField("");
		innerBox.add(IBANLabel, 0, 8);
		innerBox.add(IBANTextField, 1, 8);
		
		var BICLabel = new Label("BIC: ");
		var BICTextField = new TextField("");
		innerBox.add(BICLabel, 4, 6);
		innerBox.add(BICTextField, 5, 6);
		
		var PasswordLabel = new Label("Passwort: ");
		var PasswordTextField = new TextField("");
		innerBox.add(PasswordLabel, 0, 6);
		innerBox.add(PasswordTextField, 1, 6);
		
		var VerPasswordLabel = new Label("Passwort bestätigen: ");
		var VerPasswordTextField = new TextField("");
		innerBox.add(VerPasswordLabel, 0, 7);
		innerBox.add(VerPasswordTextField, 1, 7);
		
		
		var submitButton = new Button("Registrierung abschliessen");
		innerBox.add(submitButton, 5, 8);
		
		submitButton.setOnAction(event -> {
			
			ArrayList<Boolean> list = new ArrayList<Boolean>();
			
			list.add(ctrl.testTextField("^[\\p{L} ,.'-]+$", firstNameTextField));
			list.add(ctrl.testTextField("^[\\p{L} ,.'-]+$", lastNameTextField));
			list.add(ctrl.testTextField("^[\\p{L} ,.'-]+$", streetTextField));
			list.add(ctrl.testTextField("-?\\d+\\.?\\d*", houseNumberTextField));
			list.add(ctrl.testTextField("-?\\d+\\.?\\d*", plzTextField));
			list.add(ctrl.testTextField("^[\\p{L} ,.'-]+$", townTextField));
			list.add(ctrl.testTextField("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", emailTextField));
			list.add(ctrl.testTextField("^(?![ -])(?!.*[- ]$)(?!.*[- ]{2})[0-9- ]+$", mobilTextField));
			list.add(ctrl.testTextField("^DE[0-9]{20}$", IBANTextField));
			list.add(ctrl.testTextField("([a-zA-Z]{4})([a-zA-Z]{2})(([2-9a-zA-Z]{1})([0-9a-np-zA-NP-Z]{1}))((([0-9a-wy-zA-WY-Z]{1})([0-9a-zA-Z]{2}))|([xX]{3})|)", BICTextField));
			
			if (areAllTrue(list))
			{
				TextField[] textFieldsRegistration = innerBox.getChildren().stream().filter(node -> node.getClass() == TextField.class).toArray(TextField[]::new);
			}
			
		});
		
		this.getChildren().addAll(innerBox);
		this.setAlignment(Pos.CENTER);
		VBox.setVgrow(this, Priority.ALWAYS);
		
		
		
	}
	
	private static boolean areAllTrue(ArrayList<Boolean> array)
	{
	    for(boolean b : array) if(!b) return false;
	    return true;
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
