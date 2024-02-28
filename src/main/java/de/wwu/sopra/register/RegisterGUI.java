package de.wwu.sopra.register;



import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.regex.*;

import de.wwu.sopra.UserManagementGUI;
import de.wwu.sopra.login.LoginGUI;


public class RegisterGUI extends HBox {
	/**
	 * Instanz der Kontrollklasse
	 */
	private RegisterCTRL ctrl = new RegisterCTRL();
	
	/**
	 * Initialisert GUI
	 */
	public RegisterGUI(){
		init();
	}

	
	/**
	 * Initialisiert GUI 
	 */
	public void init() {
		
		// Achtung: Reihenfolge der Textfields bestimmt Backend-Funkionalität
		
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
		innerBox.add(lastNameLabel, 0, 1);
		innerBox.add(lastNameTextField, 1, 1);
		
		var spacer1 = new Pane();
		innerBox.add(spacer1, 0, 2);
		var spacer2 = new Pane();
		innerBox.add(spacer2, 0, 3);
		
		
		
		var streetLabel = new Label("Stra\u00DFe: ");
		var streetTextField = new TextField("");
		innerBox.add(streetLabel, 0, 4);
		innerBox.add(streetTextField, 1, 4);
		
		var houseNumberLabel = new Label("Hausnummer: ");
		var houseNumberTextField = new TextField("");
		innerBox.add(houseNumberLabel, 0, 5);
		innerBox.add(houseNumberTextField, 1, 5);
		
		var plzLabel = new Label("PLZ: ");
		var plzTextField = new TextField("");
		innerBox.add(plzLabel, 0, 6);
		innerBox.add(plzTextField, 1, 6);
		
		var townLabel = new Label("Stadt: ");
		var townTextField = new TextField("");
		innerBox.add(townLabel, 0, 7);
		innerBox.add(townTextField, 1, 7);
		
		var IBANLabel = new Label("IBAN: ");
		var IBANTextField = new TextField("");
		innerBox.add(IBANLabel, 0, 8);
		innerBox.add(IBANTextField, 1, 8);
		
		var BICLabel = new Label("BIC: ");
		var BICTextField = new TextField("");
		innerBox.add(BICLabel, 0, 9);
		innerBox.add(BICTextField, 1, 9);
		
		var emailLabel = new Label("E-Mail: ");
		var emailTextField = new TextField("");
		innerBox.add(emailLabel, 4, 0);
		innerBox.add(emailTextField, 5, 0);
		
		var PasswordLabel = new Label("Passwort: ");
		var PasswordTextField = new PasswordField();
		innerBox.add(PasswordLabel, 4, 1);
		innerBox.add(PasswordTextField, 5, 1);
		
		var VerPasswordLabel = new Label("Passwort bestätigen: ");
		var VerPasswordTextField = new PasswordField();
		innerBox.add(VerPasswordLabel, 4, 2);
		innerBox.add(VerPasswordTextField, 5, 2);
		
		// Buttons zum Navigieren
		var submitButton = new Button("Registrierung abschliessen");
		var goBackButton = new Button("Zurück");
		innerBox.add(submitButton, 0, 10);
		innerBox.add(goBackButton, 1, 10);
		
		
		var passwordRequirements = new Label(
				"Paswörter stimmen überein.\r\n"
				+ "Paswort beinhaltet:\r\n"
				+ "- eine Nummer (0-9)\r\n"
				+ "- einen großen Buchstaben\r\n"
				+ "- einen kleinen Buchstaben\r\n"
				+ "- ein Sonderzeichen\r\n"
				+ "- 6-18 Zeichen, ohne Leerzeichen");
		innerBox.add(passwordRequirements, 5, 3);
		
		submitButton.setOnAction(event -> {
			
			ArrayList<Boolean> list = new ArrayList<Boolean>();
			
			list.add(ctrl.testTextField("^[\\p{L} ,.'-]+$", firstNameTextField));
			list.add(ctrl.testTextField("^[\\p{L} ,.'-]+$", lastNameTextField));
			list.add(ctrl.testTextField("^[\\p{L} ,.'-]+$", streetTextField));
			list.add(ctrl.testTextField("-?\\d+\\.?\\d*", houseNumberTextField));
			list.add(ctrl.testTextField("-?\\d+\\.?\\d*", plzTextField));
			list.add(ctrl.testTextField("^[\\p{L} ,.'-]+$", townTextField));
			list.add(ctrl.testTextField("^DE[0-9]{20}$", IBANTextField));
			list.add(ctrl.testTextField("([a-zA-Z]{4})([a-zA-Z]{2})(([2-9a-zA-Z]{1})([0-9a-np-zA-NP-Z]{1}))((([0-9a-wy-zA-WY-Z]{1})([0-9a-zA-Z]{2}))|([xX]{3})|)", BICTextField));
			list.add(ctrl.testTextField("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", emailTextField));
			list.add(ctrl.testTextField("^(?=.*\\d)(?=.*[A-Z])(?=.*[a-z])(?=.*[^\\w\\d\\s:])([^\\s]){8,16}$", PasswordTextField));
			list.add(ctrl.testTextField("^(?=.*\\d)(?=.*[A-Z])(?=.*[a-z])(?=.*[^\\w\\d\\s:])([^\\s]){8,16}$", VerPasswordTextField));
			
			Boolean passwordsEqual = (PasswordTextField.getText().equals(VerPasswordTextField.getText()));
			if (!passwordsEqual)
			{
				VerPasswordTextField.setStyle("-fx-background-color: #FFA59D;");
			}
			
			list.add(passwordsEqual);
			
			if (areAllTrue(list))
			{
				TextField[] textFieldsRegistration = innerBox.getChildren().stream().filter(node -> node.getClass() == TextField.class || node.getClass() == PasswordField.class).toArray(TextField[]::new);
				ctrl.registerUser(textFieldsRegistration);
			}
			
		});
		
		goBackButton.setOnAction(event -> {
	        UserManagementGUI.getInstance().changeViewNode(new LoginGUI());
		});
		
		this.getChildren().addAll(innerBox);
		this.setAlignment(Pos.CENTER);
		VBox.setVgrow(this, Priority.ALWAYS);
		
		
		
	}
	
	/**
	 * Prüft, ob alle Elemente einer ArrayList von Typ Boolean wahr sind
	 * @param array
	 * @return
	 */
	private static boolean areAllTrue(ArrayList<Boolean> array)
	{
	    for(boolean b : array) if(!b) return false;
	    return true;
	}
}
