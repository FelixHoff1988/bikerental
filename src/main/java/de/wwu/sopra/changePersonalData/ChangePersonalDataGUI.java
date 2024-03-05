package de.wwu.sopra.changePersonalData;

import java.util.ArrayList;

/**
 * @author eikeg
 */

import de.wwu.sopra.AppContext;
import de.wwu.sopra.DataProvider;
import de.wwu.sopra.Design;
import de.wwu.sopra.entity.User;
import de.wwu.sopra.login.LoginGUI;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * GUI für die Bearbeitung eigener Nutzerdaten
 */
public class ChangePersonalDataGUI extends HBox {

    /**
     * Die Kontrollklasse um Aktionen auszuführen
     */
    private ChangePersonalDataCTRL ctrl = new ChangePersonalDataCTRL();
    /**
     * Instanz des AppContext
     */
    private AppContext context = AppContext.getInstance();

    /**
     * Standartkonstruktor
     */
    public ChangePersonalDataGUI() {
        var user = context.getLoggedInUser();
        init(user);
    }

    /**
     * Initialisiert die GUI-Übersicht zur bearbeitung der eigenen Nutzerdaten
     * 
     * @param user der angemeldete Nutzer
     */
    public void init(User user) {
        var innerBox = new GridPane();
        innerBox.setHgap(30);
        innerBox.setPadding(new Insets(25, 25, 25, 25));
        innerBox.setAlignment(Pos.CENTER);
        innerBox.setVgap(5);

        var firstName = user.getFirstName();
        var firstNameLabel = new Label("Vorname: ");
        var firstNameTextField = new TextField(firstName);
        innerBox.add(firstNameLabel, 0, 0);
        innerBox.add(firstNameTextField, 1, 0);

        var lastName = user.getLastName();
        var lastNameLabel = new Label("Nachname: ");
        var lastNameTextField = new TextField(lastName);
        innerBox.add(lastNameLabel, 0, 1);
        innerBox.add(lastNameTextField, 1, 1);

        var role = ctrl.getRoleString(user);
        var roleNameLabel = new Label("Rolle: ");
        var roleLabel = new Label(role);
        innerBox.add(roleNameLabel, 0, 2);
        innerBox.add(roleLabel, 1, 2);

        var spacer1 = new Pane();
        innerBox.add(spacer1, 0, 3);
        var spacer2 = new Pane();
        innerBox.add(spacer2, 0, 4);

        var street = user.getStreet();
        var streetLabel = new Label("Stra\u00DFe: ");
        var streetTextField = new TextField(street);
        innerBox.add(streetLabel, 0, 4);
        innerBox.add(streetTextField, 1, 4);

        var houseNumber = String.valueOf(user.getHouseNumber());
        var houseNumberLabel = new Label("Hausnummer: ");
        var houseNumberTextField = new TextField(houseNumber);
        innerBox.add(houseNumberLabel, 0, 5);
        innerBox.add(houseNumberTextField, 1, 5);

        var plz = String.valueOf(user.getPostalCode());
        var plzLabel = new Label("PLZ: ");
        var plzTextField = new TextField(plz);
        innerBox.add(plzLabel, 0, 6);
        innerBox.add(plzTextField, 1, 6);

        var town = user.getCity();
        var townLabel = new Label("Stadt: ");
        var townTextField = new TextField(town);
        innerBox.add(townLabel, 0, 7);
        innerBox.add(townTextField, 1, 7);

        var iban = String.valueOf(user.getIban());
        var ibanLabel = new Label("IBAN: ");
        var ibanTextField = new TextField(iban);
        innerBox.add(ibanLabel, 0, 8);
        innerBox.add(ibanTextField, 1, 8);

        var bic = user.getBic();
        var bicLabel = new Label("BIC: ");
        var bicTextField = new TextField(bic);
        innerBox.add(bicLabel, 0, 9);
        innerBox.add(bicTextField, 1, 9);

        var email = user.getEmail();
        var emailLabel = new Label("E-Mail: ");
        var emailTextField = new TextField(email);
        innerBox.add(emailLabel, 4, 0);
        innerBox.add(emailTextField, 5, 0);

        var passwordLabel = new Label("Neues Passwort: ");
        var passwordTextField = new PasswordField();
        innerBox.add(passwordLabel, 4, 1);
        innerBox.add(passwordTextField, 5, 1);

        var verPasswordLabel = new Label("Passwort bestätigen: ");
        var verPasswordTextField = new PasswordField();
        innerBox.add(verPasswordLabel, 4, 2);
        innerBox.add(verPasswordTextField, 5, 2);

        // Button zum Speichern
        var submitButton = new Button("Speichern");
        innerBox.add(submitButton, 0, 10);

        var passwordRequirements = new Label("Paswörter stimmen überein.\r\n" + "Paswort beinhaltet:\r\n"
                + "- eine Nummer (0-9)\r\n" + "- einen großen Buchstaben\r\n" + "- einen kleinen Buchstaben\r\n"
                + "- ein Sonderzeichen\r\n" + "- 6-18 Zeichen, ohne Leerzeichen");
        innerBox.add(passwordRequirements, 5, 3);

        submitButton.setOnAction(event -> {

            ArrayList<Boolean> list = new ArrayList<Boolean>();

            list.add(ctrl.testTextField("^[\\p{L} ,.'-]+$", firstNameTextField));
            list.add(ctrl.testTextField("^[\\p{L} ,.'-]+$", lastNameTextField));
            list.add(ctrl.testTextField("^[\\p{L} ,.'-]+$", streetTextField));
            list.add(ctrl.testTextField("-?\\d+\\.?\\d*", houseNumberTextField));
            list.add(ctrl.testTextField("-?\\d+\\.?\\d*", plzTextField));
            list.add(ctrl.testTextField("^[\\p{L} ,.'-]+$", townTextField));
            list.add(ctrl.testTextField("^DE[0-9]{20}$", ibanTextField));
            list.add(ctrl.testTextField(
                    "([a-zA-Z]{4})([a-zA-Z]{2})(([2-9a-zA-Z]{1})([0-9a-np-zA-NP-Z]{1}))((([0-9a-wy-zA-WY-Z]{1})([0-9a-zA-Z]{2}))|([xX]{3})|)",
                    bicTextField));
            list.add(ctrl.testTextField("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", emailTextField));

            var newPassword = false;
            if (!passwordTextField.getText().isEmpty()) {
                newPassword = true;

                list.add(ctrl.testTextField("^(?=.*\\d)(?=.*[A-Z])(?=.*[a-z])(?=.*[^\\w\\d\\s:])([^\\s]){8,16}$",
                        passwordTextField));
                list.add(ctrl.testTextField("^(?=.*\\d)(?=.*[A-Z])(?=.*[a-z])(?=.*[^\\w\\d\\s:])([^\\s]){8,16}$",
                        verPasswordTextField));

                Boolean passwordsEqual = (passwordTextField.getText().equals(verPasswordTextField.getText()));
                if (!passwordsEqual) {
                    verPasswordTextField.setStyle("-fx-background-color: #FFA59D;");
                }

            }

            if (areAllTrue(list)) {
                TextField[] textFieldsRegistration = innerBox.getChildren().stream()
                        .filter(node -> node.getClass() == TextField.class || node.getClass() == PasswordField.class)
                        .toArray(TextField[]::new);
                ctrl.changeData(textFieldsRegistration, newPassword);

                AppContext.getInstance().showMessage("Daten erfolgreich geändert.", 5, Design.COLOR_DIALOG_SUCCESS);
            }

        });

        this.getChildren().addAll(innerBox);
        this.setAlignment(Pos.CENTER);
        VBox.setVgrow(this, Priority.ALWAYS);
    }

    /**
     * Prüft, ob alle Elemente einer ArrayList von Typ Boolean wahr sind
     * 
     * @param arrayList
     * @return
     */
    private static boolean areAllTrue(ArrayList<Boolean> arrayList) {
        for (boolean b : arrayList)
            if (!b)
                return false;
        return true;
    }
}
