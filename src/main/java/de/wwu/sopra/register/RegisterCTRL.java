package de.wwu.sopra.register;

import java.util.ArrayList;
import java.util.regex.Pattern;

import de.wwu.sopra.DataProvider;
import de.wwu.sopra.PasswordHashing;
import de.wwu.sopra.UserManagementGUI;
import de.wwu.sopra.entity.Reservation;
import de.wwu.sopra.entity.User;
import de.wwu.sopra.entity.UserRole;
import javafx.scene.control.TextField;

public class RegisterCTRL {
	public RegisterCTRL() {
		
	}
	
	public void registerUser(TextField[] textFieldsRegistration) {
		for ( TextField element : textFieldsRegistration)
		{
			System.out.println(element.getText());
		}
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
		prov.addUser(registeredUser);
		UserManagementGUI.getInstance().login(registeredUser);
	}
	
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
