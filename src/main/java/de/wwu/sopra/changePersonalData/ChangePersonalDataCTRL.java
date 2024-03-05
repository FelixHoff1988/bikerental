package de.wwu.sopra.changePersonalData;

import java.util.ArrayList;
import java.util.regex.Pattern;

import de.wwu.sopra.AppContext;
import de.wwu.sopra.PasswordHashing;
import de.wwu.sopra.entity.User;
import javafx.scene.control.TextField;

/**
 * Kontrollklasse um eigene Benutzerdaten einzusehen und zu ändern
 */
public class ChangePersonalDataCTRL {

    /**
     * Instanz des AppContext
     */
    private AppContext context = AppContext.getInstance();

    /**
     * Standartkonstruktor
     */
    public ChangePersonalDataCTRL() {

    }

    /**
     * Wird bei korrekten Werten für Textfields aufgerufen, speichert angegebene
     * Daten
     * 
     * @param textFieldsRegistration Array mit den neuen Nutzerdaten
     * @param newPassword            true wenn ein neues Passwort gesetzt werden
     *                               soll
     */
    public void changeData(TextField[] textFieldsRegistration, boolean newPassword) {
        var user = context.getLoggedInUser();

        user.setFirstName(textFieldsRegistration[0].getText());

        user.setLastName(textFieldsRegistration[1].getText());

        user.setStreet(textFieldsRegistration[2].getText());

        user.setHouseNumber((int) Integer.valueOf(textFieldsRegistration[3].getText()));

        user.setPostalCode((int) Integer.valueOf(textFieldsRegistration[4].getText()));

        user.setCity(textFieldsRegistration[5].getText());

        user.setEmail(textFieldsRegistration[8].getText());

        user.setIban(textFieldsRegistration[6].getText());

        user.setBic(textFieldsRegistration[7].getText());

        if (newPassword) {
            user.setPasswordHash(PasswordHashing.hashPassword(textFieldsRegistration[9].getText()));
        }
    }

    /**
     * Gibt einen String mit der Rolle des Nutzers zurück
     * 
     * @param user angemeldeter User
     * @return String mit der aktuellen Rolle
     */
    public String getRoleString(User user) {
        var role = (String) "";
        switch (user.getRole()) {
        case CUSTOMER -> {
            role += "Kunde*in";
        }
        case ADMIN -> {
            role += "Systemadministrator*in";
        }
        case EXECUTIVE -> {
            role += "Geschäftsführer*in";
        }
        case MAINTAINER -> {
            role += "Wartungstechniker*in";
        }
        case MANAGER -> {
            role += "Stations-Manager*in";
        }
        }
        return role;
    }

    /**
     * Prüft, ob Eingabe in TextField von Registration korrekt ist
     *
     * @param regex     Regex code um Korrektheit der Eingabe eines Feldes zu
     *                  überprüfen
     * @param textField Text Field aus Registrierungsform
     * @return Gibt zurück, ob das Textfeld korrekt gesetzt wurde
     */
    public boolean testTextField(String regex, TextField textField) {
        if (!Pattern.matches(regex, textField.getText())) {
            textField.setStyle("-fx-background-color: #FFA59D;");
            return false;
        } else {
            textField.setStyle("");
            return true;
        }
    }
}
