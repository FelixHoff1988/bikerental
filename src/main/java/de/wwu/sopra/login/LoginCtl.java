package de.wwu.sopra.login;

import de.wwu.sopra.DataProvider;
import de.wwu.sopra.PasswordHashing;
import de.wwu.sopra.UserManagementGUI;
import de.wwu.sopra.entity.User;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * Steuerungsklasse für den User-Login
 */
public class LoginCtl {
    /**
     * Standardkonstruktor
     */
    public LoginCtl() {}

    public void loginUser(String email, String password) {
        var provider = DataProvider.getInstance();
        var user = provider.getUser(email);
        var validData = false;

        if (user != null) {

            var correctPassword = PasswordHashing.validatePassword(password, user.getPasswordHash());
            if (correctPassword)
                validData = true;
        }

        if (!validData) {
            var alert = new Alert(
                    Alert.AlertType.NONE,
                    "Deine angegebenen Daten scheinen nicht korrekt zu sein. Überprüfe bitte E-Mail und Passwort. " +
                    "Solltest du noch keine Account besitzen, kannst du auf 'Registrieren' klicken.",
                    ButtonType.OK);
            alert.setHeaderText("Anmeldung nicht möglich");
            alert.show();
        } else {
            UserManagementGUI.getInstance().login(user);
        }
    }
}
