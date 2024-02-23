package de.wwu.sopra;

import de.wwu.sopra.entity.User;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

import java.util.function.Consumer;

/**
 * Hauptlayout der App
 */
public class MainGUI extends GridPane {
    /**
     * Erzeugte das haupt Layout der App
     * @param user User, welcher angemeldet ist.
     */
    public MainGUI(User user) {

    }

    /**
     * Definiert welche Methode beim Logout ausgeführt werden soll.
     * @param consumer Methode, welche ausgeführt wird beim Logout
     */
    public void onLogout(
            Consumer<Void> consumer // Repräsentiert eine Methode der Form: void methodenname(User user) { ... }
    ) {

    }

    /**
     * Ändere die haupt Anzeigenode des MainGUI
     * @param node Ändert die View node
     */
    public void changeViewNode(Node node) {
        this.add(node, 0, 1);
    }
}
