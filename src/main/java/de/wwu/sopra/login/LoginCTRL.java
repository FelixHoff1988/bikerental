package de.wwu.sopra.login;

import de.wwu.sopra.AppContext;
import de.wwu.sopra.DataProvider;
import de.wwu.sopra.PasswordHashing;
import de.wwu.sopra.register.RegisterGUI;

/**
 * Steuerungsklasse für den User-Login
 */
public class LoginCTRL {
    /**
     * Standardkonstruktor
     */
    public LoginCTRL() {}

    /**
     * Handelt den User Login
     *
     * @param email E-Mail des einzuloggenden Nutzers
     * @param password Passwort des einzuloggenden Nutzers
     */
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
            AppContext.getInstance().showMessage(
                    "Deine angegebenen Daten scheinen nicht korrekt zu sein. Überprüfe bitte E-Mail und Passwort. "
                            + "Solltest du noch keine Account besitzen, kannst du auf 'Registrieren' klicken.",
                    5,
                    "#FFCCDD");
        } else {
            AppContext.getInstance().login(user);
        }
    }

    /**
     * Handelt den Klick auf den "Registrieren"-Text
     */
    public void registerUser() {
        AppContext.getInstance().changeViewNode(new RegisterGUI());
    }

    /**
     * Handelt den Klick auf den "Passwort vergessen"-Text
     */
    public void forgotPassword() {
        AppContext.getInstance().showMessage(
                "Falls Sie Ihr Passwort vergessen haben, bitte wenden Sie sich an den Kundenservice."
                        + " Die Email-Adresse hierfür lautet: admin@bikerental.de",
                5,
                "#FFCCDD");
    }
}
