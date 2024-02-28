package de.wwu.sopra.useradministration;

import java.util.regex.Pattern;

import de.wwu.sopra.DataProvider;
import de.wwu.sopra.PasswordHashing;
import de.wwu.sopra.UserManagementGUI;
import de.wwu.sopra.entity.User;
import de.wwu.sopra.entity.UserRole;
import javafx.scene.control.TextField;

public class EditUserCTRL {
	
	public EditUserCTRL(){
		
	}
		
		public void addUser(TextField[] textFieldsRegistration) {
			User registeredUser = new User(
					textFieldsRegistration[0].getText(),
					textFieldsRegistration[1].getText(),
					textFieldsRegistration[2].getText(),
					(int) Integer.valueOf(textFieldsRegistration[3].getText()),
					(int) Integer.valueOf(textFieldsRegistration[4].getText()),
					textFieldsRegistration[5].getText(),
					textFieldsRegistration[8].getText(),
					textFieldsRegistration[6].getText(),
					textFieldsRegistration[7].getText(),
					PasswordHashing.hashPassword(textFieldsRegistration[9].getText()),
					UserRole.CUSTOMER
			);

			DataProvider prov = DataProvider.getInstance();
			prov.addUser(registeredUser);
			UserManagementGUI.getInstance().login(registeredUser);
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
		
		public static User findUserByEmail(String email)
		{
			DataProvider prov = DataProvider.getInstance();
			User user = prov.getUser(email);
			return user;
		}
	}

