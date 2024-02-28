package de.wwu.sopra.register;

import java.util.ArrayList;
import java.util.regex.Pattern;

import de.wwu.sopra.DataProvider;
import de.wwu.sopra.PasswordHashing;
import de.wwu.sopra.UserManagementGUI;
import de.wwu.sopra.entity.Reservation;
import de.wwu.sopra.entity.User;
import de.wwu.sopra.entity.UserRole;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

public class RegisterCTRL {
	/**
	 * Kostruktor der Kontrollklasse 
	 */
	public RegisterCTRL() {
		
	}
	
	/**
	 * Wird bei korrekten Werten für Textfields aufgerufen, speichert angegebene Daten und logt User ein
	 * @param textFieldsRegistration Liste aller TextFields aus der Registration
	 */
	public void registerUser(TextField[] textFieldsRegistration) {
		User registeredUser = new User(
			textFieldsRegistration[0].getText(),
			textFieldsRegistration[1].getText(),
			textFieldsRegistration[2].getText() + " " +  textFieldsRegistration[3].getText() + " " + textFieldsRegistration[4].getText() + " " + textFieldsRegistration[5].getText(),
			textFieldsRegistration[8].getText(),
			textFieldsRegistration[6].getText(),
			textFieldsRegistration[7].getText(),
			PasswordHashing.hashPassword(textFieldsRegistration[9].getText()),
			UserRole.CUSTOMER
		);

		DataProvider prov = DataProvider.getInstance();
		Boolean emailNotExistsAlready = prov.addUser(registeredUser);
		if (emailNotExistsAlready)
		{
			UserManagementGUI.getInstance().login(registeredUser);
		}
		else
		{
			var alert = new Alert(
                    Alert.AlertType.NONE,
                    "Die angegebene E-Mail wird bereits von einem anderen Account verwendet." +
                    "Falls, du dein Passwort vergessen hast, clicke auf den Button im Anmeldefenster."+
                    "Wähle sonst eine andere E-Mail-Adresse für die Registrierung aus.",
                    ButtonType.OK);
            alert.setHeaderText("Registrierung mit dieser E-Mail nicht möglich");
            alert.show();
		}
	}
	
	/**
	 * Prüft, ob Eingabe in TextField von Registration korrekt ist
	 * @param regex Regex code um Korrektheit der Eingabe eines Feldes zu überprüfen
	 * @param textField Text Field aus Registrierungsform
	 * @return
	 */
	public boolean testTextField(String regex, TextField textField)
	{
		if(!Pattern.matches(regex, textField.getText()))
		{
			textField.setStyle("-fx-background-color: #FFA59D;");
			return false;
		}
		else
		{
			textField.setStyle("");
			return true;
		}
	}
}
