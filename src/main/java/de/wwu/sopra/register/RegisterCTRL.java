package de.wwu.sopra.register;

import java.util.regex.Pattern;

import de.wwu.sopra.UserManagementGUI;
import javafx.scene.control.TextField;

public class RegisterCTRL {
	public RegisterCTRL() {
		
	}
	
	public void registerUser() {
		
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
