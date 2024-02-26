package de.wwu.sopra;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * Navigationsleiste der GUI
 */
public class NavigationGUI extends HBox {
	
	/**
	 * Konstruktor erstellt die Navigationsleiste
	 */
	public NavigationGUI() {
		build();
	}
	
	/**
	 * Erstellt die notwendigen Schaltflächen und Anzeigen und fügt sie zu einer Leiste zusammen
	 */
	public void build() {
		
		var user = MainGUI.getInstance().getLoggedInUser();
		String name = user.getFirstName() + " " + user.getLastName();
		var profileData = new Label();
		String nameAndRole = user.getFirstName() + " " + user.getLastName() + ": ";
		switch(user.getRole()) {
			case CUSTOMER -> {
				nameAndRole += "Kunde*in";
			}
			case ADMIN -> {
				nameAndRole += "Systemadministrator*in";
			}
			case EXECUTIVE -> {
				nameAndRole += "Geschäftsführer*in";
			}
			case MAINTAINER -> {
				nameAndRole += "Wartungstechniker*in";
			}
			case MANAGER -> {
				nameAndRole += "Stations-Manager*in";
			}
		}
		profileData.setText(nameAndRole);
		
		var burgerMenu = new ComboBox<String>();
		burgerMenu.getItems().addAll("Buchen", "Mein Konto", "Nutzungshistorie", "Schaden melden");
		switch(user.getRole()) {
			case ADMIN -> {
				burgerMenu.getItems().addAll("Fahrräder", "Stationen", "Geofencing-Areas", "Benutzer");
			}
			case EXECUTIVE -> {
				burgerMenu.getItems().addAll("Bilanzen");
			}
			case MAINTAINER -> {
				burgerMenu.getItems().addAll("Wartung");
			}
			case MANAGER -> {
				burgerMenu.getItems().addAll("Fahrräder managen");
			}
		}
		burgerMenu.getItems().addAll("Abmelden");
		
		var spacer = new Pane();
		HBox.setHgrow(spacer, Priority.ALWAYS);
		
		this.getChildren().addAll(profileData, spacer, burgerMenu);
		
		this.setSpacing(10);
		this.setAlignment(Pos.CENTER);
		this.setPadding(new Insets(5));
		burgerMenu.getSelectionModel().select(0);
	}
}