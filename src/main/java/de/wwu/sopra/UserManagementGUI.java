package de.wwu.sopra;

import de.wwu.sopra.entity.User;
import de.wwu.sopra.login.LoginGUI;
import de.wwu.sopra.register.RegisterGUI;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.function.Consumer;

/**
 * Klasse, welche die Elemente von Anmeldung und Registrierung enthält.
 */
public class UserManagementGUI extends VBox {
    /**
     * Instanz des MainGUI
     */
    private static UserManagementGUI instance;

    /**
     * Aktion die beim Aufruf von login() ausgeführt wird
     */
    private Consumer<User> loginAction;
    private Consumer<User> registerAction;
    

    /**
     * Standardkonstruktor
     */
    private UserManagementGUI() {
        this.getChildren().add(new LoginGUI());
    }

    /**
     * Rufe die Instanz des UserManagementGUI ab.
     *
     * @return Gibt die aktuelle Instanz des UserManagementGUI zurück
     */
    public static synchronized UserManagementGUI getInstance() {
        if (UserManagementGUI.instance == null)
            UserManagementGUI.instance = new UserManagementGUI();

        return instance;
    }

    /**
     * Definiert welche Methode beim Login ausgeführt werden soll.
     * @param consumer Methode, welche ausgeführt wird beim Login
     */
    public void onLogin(
            Consumer<User> consumer // Repräsentiert eine Methode der Form: void methodenname(User user) { ... }
    ) {
        this.loginAction = consumer;
    }

    /**
     * Kann aufgerufen werden, um den Login-Prozess zu starten
     *
     * @param user Nutzer, der eingeloggt wird
     */
    public void login(User user) {
        UserManagementGUI.instance = null;
        this.loginAction.accept(user);
    }

    /**
     * Ändere die haupt Anzeigenode des UserManagementGUI
     * @param node Ändert die View node
     */
    public void changeViewNode(Node node) {
    	if (!this.getChildren().isEmpty())
    		this.getChildren().removeLast();
        this.getChildren().add(node);

        
    }


}
