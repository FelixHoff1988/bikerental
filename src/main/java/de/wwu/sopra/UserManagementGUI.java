package de.wwu.sopra;

import de.wwu.sopra.entity.User;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.util.function.Consumer;

/**
 * Klasse, welche die Elemente von Anmeldung und Registrierung enthält.
 */
public class UserManagementGUI extends Pane {
    /**
     * Standardkonstruktor
     */
    public UserManagementGUI() {

    }

    /**
     * Definiert welche Methode beim Login ausgeführt werden soll.
     * @param consumer Methode, welche ausgeführt wird beim Login
     */
    public void onLogin(
            Consumer<User> consumer // Repräsentiert eine Methode der Form: void methodenname(User user) { ... }
    ) {

    }

    /**
     * Ändere die haupt Anzeigenode des UserManagementGUI
     * @param node Ändert die View node
     */
    public void changeViewNode(Node node) {
        this.getChildren().removeAll();
        this.getChildren().add(node);
    }
}
