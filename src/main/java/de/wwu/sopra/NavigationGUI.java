package de.wwu.sopra;


import de.wwu.sopra.bikeadministration.editbike.EditBikeGUI;
import de.wwu.sopra.bookingProcess.BookingProcessGUI;
import de.wwu.sopra.bikemaintenance.BikeMaintenanceGUI;
import de.wwu.sopra.bikemanagement.BikeManagementGUI;
import de.wwu.sopra.biketypeadministration.EditBikeTypeGUI;
import de.wwu.sopra.changePersonalData.ChangePersonalDataGUI;
import de.wwu.sopra.stationadministration.EditStationGUI;
import de.wwu.sopra.usagehistory.UsageHistoryGUI;
import de.wwu.sopra.geofencingareaadministration.EditGeofencingAreaGUI;
import de.wwu.sopra.reportdefect.ReportGUI;
import de.wwu.sopra.reviewbusinessstatistics.ReviewBusinessStatisticsGUI;
import de.wwu.sopra.useradministration.EditUserGUI;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

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
		var user = AppContext.getInstance().getLoggedInUser();
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
			case CUSTOMER -> {
				burgerMenu.getItems();
			}
			case ADMIN -> {
				burgerMenu.getItems().addAll("Fahrräder", "FahrradTypen", "Stationen", "Geofencing-Areas", "Benutzer");
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
		
		burgerMenuEvents(burgerMenu);
	}

	/**
	 * Behandelt die Aufrufe die passieren sollen, wenn ein Item in dem Burger Menü ausgewählt wird
	 * @param burgerMenu nimmt das erstellte Burger-Menü entgegen
	 */
	public void burgerMenuEvents(ComboBox<String> burgerMenu) {
		burgerMenu.setOnAction(event -> {
			switch(burgerMenu.getValue()) {
				case "Buchen" -> {
					booking();
				}
				case "Mein Konto" -> {
					account();
				}
				case "Nutzungshistorie" -> {
					useHistory();
				}
				case "Schaden melden" -> {
					report();
				}
				case "Fahrräder" -> {
					bikes();
				}
				case "FahrradTypen" -> {
				    biketypes();
				}
				case "Stationen" -> {
					stations();
				}
				case "Geofencing-Areas" -> {
					geofencingAreas();
				}
				case "Benutzer" -> {
					users();
				}
				case "Bilanzen" -> {
					statistics();
				}
				case "Wartung" -> {
					service();
				}
				case "Fahrräder managen" -> {
					bikeManagement();
				}
				case "Abmelden" -> {
					logOut();
				}
			}
		});
	}

	/**
	 * Wechsel auf das Startfenster/Buchungsfenster
	 */
	private void booking() {
		AppContext.getInstance().changeViewNode(new BookingProcessGUI());
	}

	/**
	 * Wechsel auf die Accountübersicht
	 */
	private void account() {
		AppContext.getInstance().changeViewNode(new ChangePersonalDataGUI());
	}

	/**
	 * Wechsel auf die Nutzungshistorie
	 */
	private void useHistory() {
	    AppContext.getInstance().changeViewNode(new UsageHistoryGUI());
	}

	/**
	 * Wechsel auf das Schaden melden Formular
	 */
	private void report() {
		AppContext.getInstance().changeViewNode(new ReportGUI());
	}

	/**
	 * Wechseln auf die Fahrradliste
	 */
	private void bikes() {
	    AppContext.getInstance().changeViewNode(new EditBikeGUI());
	}

	/**
     * Wechseln auf die Fahrradliste
     */
    private void biketypes() {
        AppContext.getInstance().changeViewNode(new EditBikeTypeGUI());
        
    }
    
	/**
	 * Wechseln auf die Stationsliste
	 */
	private void stations() {
		AppContext.getInstance().changeViewNode(new EditStationGUI());
		
	}

	/**
	 * Wechseln auf die Geofencing-Area Liste
	 */
	private void geofencingAreas() {
		AppContext.getInstance().changeViewNode(new EditGeofencingAreaGUI());
		
	}

	/**
	 * Wechseln auf die Benutzerliste
	 */
	private void users() {
		AppContext.getInstance().changeViewNode(new EditUserGUI());
	}

	/**
	 * Wechseln auf die Unternehmensbilanz
	 */
	private void statistics() {
		AppContext.getInstance().changeViewNode(new ReviewBusinessStatisticsGUI());
	}

	/**
	 * Wechseln auf die Wartungs und Schadensliste
	 */
	private void service() {
		AppContext.getInstance().changeViewNode(new BikeMaintenanceGUI());
	}

	/**
	 * Wechseln auf die Umverteilungsansicht
	 */
	private void bikeManagement() {
		AppContext.getInstance().changeViewNode(new BikeManagementGUI());
	}

	/**
	 * Benutzer Abmelden und wechsel auf Anmeldefenster
	 */
	private void logOut() {
		AppContext.getInstance().logout();
	}
}