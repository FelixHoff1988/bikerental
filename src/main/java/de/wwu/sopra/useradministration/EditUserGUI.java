package de.wwu.sopra.useradministration;




import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import de.wwu.sopra.DataProvider;
import de.wwu.sopra.PasswordHashing;
import de.wwu.sopra.PasswordHashing.PasswordHash;
import de.wwu.sopra.entity.User;
import de.wwu.sopra.entity.UserRole;
import de.wwu.sopra.register.RegisterCTRL;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;



/**
 * GUI-Klasse zum Editieren eines Benutzers. (als Admin)
 */
public class EditUserGUI extends HBox {
	
	private EditUserCTRL ctrl = new EditUserCTRL();
	private RegisterCTRL registerCTRL = new RegisterCTRL();

	
	 /**
     * Konstruktor.
     */
	public EditUserGUI(){
		init();
	}
	
	  /**
     * Initialisiert das GUI-Layout für die Benutzer-Editierung.
     */
	public void init() {
		
		TableView<User> tableView = new TableView<User>();

        TableColumn<User, String> column1 = new TableColumn<>("Vorname");
        column1.setCellValueFactory(data -> {
        	return new ReadOnlyStringWrapper(data.getValue().getFirstName());
        });
        	
    	TableColumn<User, String> column2 = new TableColumn<>("Nachname");
    	column2.setCellValueFactory(data -> {
        	return new ReadOnlyStringWrapper(data.getValue().getLastName());
        });
        
        TableColumn<User, String> column3 = new TableColumn<>("Strasse");
        column3.setCellValueFactory(data -> {
        	return new ReadOnlyStringWrapper(data.getValue().getStreet());
        });
        
        TableColumn<User, String> column4 = new TableColumn<>("Hausnummer");
        column4.setCellValueFactory(data -> {
        	return new ReadOnlyStringWrapper(Integer.toString(data.getValue().getHouseNumber()));
        });
        
        TableColumn<User, String> column5 = new TableColumn<>("PLZ");
        column5.setCellValueFactory(data -> {
        	return new ReadOnlyStringWrapper(Integer.toString(data.getValue().getPostalCode()));
        });
        
        TableColumn<User, String> column6 = new TableColumn<>("Stadt");
        column6.setCellValueFactory(data -> {
        	return new ReadOnlyStringWrapper(data.getValue().getCity());
        });
        
        TableColumn<User, String> column7 = new TableColumn<>("IBAN");
        column7.setCellValueFactory(data -> {
        	return new ReadOnlyStringWrapper(data.getValue().getIban());
        });
        
        TableColumn<User, String> column8 = new TableColumn<>("BIC");
        column8.setCellValueFactory(data -> {
        	return new ReadOnlyStringWrapper(data.getValue().getBic());
        });
        
        TableColumn<User, String> column9 = new TableColumn<>("E-Mail");
        column9.setCellValueFactory(data -> {
        	return new ReadOnlyStringWrapper(data.getValue().getEmail());
        });
        
        TableColumn<User, String> column10 = new TableColumn<>("Role");
        column10.setCellValueFactory(data -> {
        	return new ReadOnlyStringWrapper(data.getValue().getRole().toString());
        });
        
        tableView.getColumns().add(column1);
        tableView.getColumns().add(column2);
        tableView.getColumns().add(column3);
        tableView.getColumns().add(column4);
        tableView.getColumns().add(column5);
        tableView.getColumns().add(column6);
        tableView.getColumns().add(column7);
        tableView.getColumns().add(column8);
        tableView.getColumns().add(column9);
        tableView.getColumns().add(column10);
        
        
        ObservableList<User> users = FXCollections.observableArrayList(ctrl.loadUsers());
        tableView.setItems(users);
        
		
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
		
		var streetLabel = new Label("Straße: ");
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
		
		// Passwoerter setzten
		var PasswordLabel = new Label("Passwort: ");
		var PasswordTextField = new PasswordField();
		innerBox.add(PasswordLabel, 4, 1);
		innerBox.add(PasswordTextField, 5, 1);
		
		// Buttons zum Navigieren
		var submitButton = new Button("User Speichern");
		var newButton = new Button("Neu");
		var deleteButton = new Button("Löschen");
		
		innerBox.add(new Label(), 0, 10);
		innerBox.add(submitButton, 5, 12);
		innerBox.add(deleteButton, 4, 12);
		innerBox.add(newButton, 0, 12);
		
		Label successionLabel = new Label("Änderung erfolgreich");
		successionLabel.setVisible(false);
		innerBox.add(successionLabel, 0, 12);
		
		
		var passwordRequirements = new Label(
				"- eine Nummer (0-9)\r\n"
				+ "- einen großen Buchstaben\r\n"
				+ "- einen kleinen Buchstaben\r\n"
				+ "- ein Sonderzeichen\r\n"
				+ "- 6-18 Zeichen, ohne Leerzeichen");
		innerBox.add(passwordRequirements, 5, 3);
		
		ComboBox<UserRole> comboBox = new ComboBox<UserRole>(); 

        comboBox.getItems().add(UserRole.CUSTOMER);
        comboBox.getItems().add(UserRole.MAINTAINER);
        comboBox.getItems().add(UserRole.MANAGER);
        comboBox.getItems().add(UserRole.EXECUTIVE);
        comboBox.getItems().add(UserRole.ADMIN);
        innerBox.add(new Label("Rolle: "), 4, 4);
        innerBox.add(comboBox, 5, 4);
		
		newButton.setOnAction(event -> {
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
			list.add(comboBox.getValue() != null);
			
			if (areAllTrue(list))
			{
				TextField[] textFieldsRegistration = innerBox.getChildren().stream().filter(node -> node.getClass() == TextField.class || node.getClass() == PasswordField.class).toArray(TextField[]::new);
				UserRole selectedRole = comboBox.getValue();
				// change this function from add User to change User
				User addedUser = ctrl.addUser(textFieldsRegistration, selectedRole);
				if(addedUser != null)
				{
					users.add(addedUser);
					tableView.setItems(users);
				}
			}
		});
		
		deleteButton.setOnAction(event -> {
            User selectedUser = tableView.getSelectionModel().getSelectedItem();
            users.remove(selectedUser);
            ctrl.removeUser(selectedUser);
            tableView.setItems(users);
        });
		
		submitButton.setOnAction(value ->  {
			ArrayList<Boolean> list = new ArrayList<Boolean>();
			
			User selectedUser = tableView.getSelectionModel().getSelectedItem();
			
			list.add(ctrl.testTextField("^[\\p{L} ,.'-]+$", firstNameTextField));
			list.add(ctrl.testTextField("^[\\p{L} ,.'-]+$", lastNameTextField));
			list.add(ctrl.testTextField("^[\\p{L} ,.'-]+$", streetTextField));
			list.add(ctrl.testTextField("-?\\d+\\.?\\d*", houseNumberTextField));
			list.add(ctrl.testTextField("-?\\d+\\.?\\d*", plzTextField));
			list.add(ctrl.testTextField("^[\\p{L} ,.'-]+$", townTextField));
			list.add(ctrl.testTextField("^DE[0-9]{20}$", IBANTextField));
			list.add(ctrl.testTextField("([a-zA-Z]{4})([a-zA-Z]{2})(([2-9a-zA-Z]{1})([0-9a-np-zA-NP-Z]{1}))((([0-9a-wy-zA-WY-Z]{1})([0-9a-zA-Z]{2}))|([xX]{3})|)", BICTextField));
			list.add(ctrl.testTextField("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", emailTextField));
			if(!PasswordTextField.getText().isBlank())
			{
				list.add(ctrl.testTextField("^(?=.*\\d)(?=.*[A-Z])(?=.*[a-z])(?=.*[^\\w\\d\\s:])([^\\s]){8,16}$", PasswordTextField));
			}
			if(!(emailTextField.getText().equals(selectedUser.getEmail()))) {
				list.add(ctrl.getUserByEmail(emailTextField.getText()) == null);
			}
			list.add(comboBox.getValue() != null);
			
			
			
			if (areAllTrue(list))
			{
				int index = users.indexOf(selectedUser);
				selectedUser.setFirstName(firstNameTextField.getText());
				selectedUser.setLastName(lastNameTextField.getText());
				selectedUser.setStreet(streetTextField.getText());
				selectedUser.setHouseNumber((int) Integer.valueOf(houseNumberTextField.getText()));
				selectedUser.setPostalCode((int) Integer.valueOf(plzTextField.getText()));
				selectedUser.setCity(townTextField.getText());
				selectedUser.setIban(IBANTextField.getText());
				selectedUser.setBic(BICTextField.getText());
				selectedUser.setEmail(emailTextField.getText());
				selectedUser.setRole(comboBox.getValue());
				
				if(!PasswordTextField.getText().isBlank())
				{
					selectedUser.setPasswordHash(PasswordHashing.hashPassword(PasswordTextField.getText()));
				}
				
				successionLabel.setVisible(true);
				users.set(index, selectedUser);
				tableView.setItems(users);
			}
        });
		
		tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
            	successionLabel.setVisible(false);
            	firstNameTextField.setText(newSelection.getFirstName());
            	lastNameTextField.setText(newSelection.getLastName());
            	streetTextField.setText(newSelection.getStreet());
            	houseNumberTextField.setText(String.valueOf(newSelection.getHouseNumber()));
            	plzTextField.setText(String.valueOf(newSelection.getPostalCode()));
            	townTextField.setText(newSelection.getCity());
            	IBANTextField.setText(newSelection.getIban());
            	BICTextField.setText(newSelection.getBic());
            	emailTextField.setText(newSelection.getEmail());
            	PasswordTextField.setText("");
            	comboBox.setValue(newSelection.getRole());
            }
        });
		
		VBox vbox = new VBox(innerBox, tableView);
		vbox.setFillWidth(true);
		
		this.getChildren().addAll(vbox);
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
