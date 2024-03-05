package de.wwu.sopra.useradministration;

import java.util.List;
import java.util.regex.Pattern;

import de.wwu.sopra.AppContext;
import de.wwu.sopra.DataProvider;
import de.wwu.sopra.Design;
import de.wwu.sopra.PasswordHashing;
import de.wwu.sopra.entity.User;
import de.wwu.sopra.entity.UserRole;
import javafx.scene.control.TextField;

/**
 * Controller-KLasse um User zu bearbeiten
 */
public class EditUserCTRL {

    /**
     * Constructor
     */
    public EditUserCTRL() {

    }

    /**
     * Fügt einen Nutzer anhand der Textfeld-Eingaben hinzu.
     * 
     * @param textFieldsRegistration Textfelder aus EditUserGUI
     * @param selectedRole           Ausgewählte Rolle des Users
     * @return Hinzugefügter Nutzer
     */
    public User addUser(TextField[] textFieldsRegistration, UserRole selectedRole) {
        User registeredUser = new User(textFieldsRegistration[0].getText(), textFieldsRegistration[1].getText(),
                textFieldsRegistration[2].getText(), Integer.parseInt(textFieldsRegistration[3].getText()),
                Integer.parseInt(textFieldsRegistration[4].getText()), textFieldsRegistration[5].getText(),
                textFieldsRegistration[8].getText(), textFieldsRegistration[6].getText(),
                textFieldsRegistration[7].getText(), PasswordHashing.hashPassword(textFieldsRegistration[9].getText()),
                selectedRole);

        DataProvider prov = DataProvider.getInstance();
        boolean emailNotExistsAlready = prov.addUser(registeredUser);
        if (!emailNotExistsAlready) {
            AppContext.getInstance().showMessage(
                    "Die angegebene E-Mail wird bereits von einem anderen Account verwendet."
                    + "Wähle eine andere E-Mail-Adresse für den neuen User aus.",
                    Design.DIALOG_TIME_STANDARD,
                    Design.COLOR_DIALOG_FAILURE);
            return null;
        } else {
            prov.addUser(registeredUser);
            return registeredUser;
        }
    }

    /**
     * Prüft, ob Eingabe in TextField von Registration korrekt ist
     * 
     * @param regex     Regex code um Korrektheit der Eingabe eines Feldes zu
     *                  überprüfen
     * @param textField Text Field aus Registrierungsform
     * @return true wenn Input aus EditUserGUI korrekt ist
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

    /**
     * Wenn ein Nutzer mit der E-Mail existiert gebe ihn zurück, sonst gebe null zurück.
     * 
     * @param email Email, mit der User gefunden werden soll
     * @return User falls User mit Email existiert, sonst null
     */
    public static User findUserByEmail(String email) {
        DataProvider prov = DataProvider.getInstance();
        User user = prov.getUser(email);
        return user;
    }

    /**
     * Lädt eine Liste aller User.
     * 
     * @return List aller User
     */
    public List<User> loadUsers() {
        return DataProvider.getInstance().getUsers();
    }

    /**
     * Ruft einen User anhand seiner E-Mail ab.
     * 
     * @param email Email mit der User gefunden werden soll
     * @return User, galls user mit dieser Email existiert, sonst null
     */
    public User getUserByEmail(String email) {
        return DataProvider.getInstance().getUser(email);
    }

    /**
     * Entfernt einen spezifizierten User.
     * 
     * @param user User der gelöscht werden soll
     */
    public void removeUser(User user) {
        DataProvider prov = DataProvider.getInstance();
        prov.removeUser(user);
    }
}
