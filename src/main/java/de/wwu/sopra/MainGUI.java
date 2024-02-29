package de.wwu.sopra;

import de.wwu.sopra.entity.User;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * Hauptlayout der App
 */
public class MainGUI extends VBox {
    /**
     * Instanz des MainGUI
     */
    private static MainGUI instance;

    /**
     * Aktion die beim Aufruf von logout() ausgeführt wird
     */
    private Runnable logoutAction;

    /**
     * Angemeldeter Nutzer
     */
    private final User loggedInUser;

    /**
     * Erzeugte das haupt Layout der App
     * @param user User, welcher angemeldet ist.
     */
    private MainGUI(User user) {
        this.loggedInUser = user;
    }

    /**
     * Erzeugte das haupt Layout der App
     * @param user User, welcher angemeldet ist.
     */
    public static synchronized MainGUI init(User user) {
        instance = new MainGUI(user);
        instance.getChildren().add(new NavigationGUI());
        instance.getChildren().add(new Pane());
        return instance;
    }

    /**
     * Rufe die Instanz des MainGUI ab.
     *
     * @return Gibt die aktuelle Instanz des MainGUI zurück
     */
    public static synchronized MainGUI getInstance() {
        return instance;
    }

    /**
     * Rufe den eingeloggten Nutzer ab.
     *
     * @return Eingeloggter Nutzer
     */
    public User getLoggedInUser() {
        return this.loggedInUser;
    }

    /**
     * Definiert welche Methode beim Logout ausgeführt werden soll.
     * @param consumer Methode, welche ausgeführt wird beim Logout
     */
    public void onLogout(
            Runnable consumer // Repräsentiert eine Methode der Form: void methodenname(User user) { ... }
    ) {
        this.logoutAction = consumer;
    }

    /**
     * Kann aufgerufen werden, um den Logout-Prozess zu starten
     */
    public void logout() {
        MainGUI.instance = null;
        this.logoutAction.run();
    }

    /**
     * Ändere die haupt Anzeigenode des MainGUI
     * @param node Ändert die View node
     */
    public void changeViewNode(Node node) {
        if (!this.getChildren().isEmpty())
            this.getChildren().removeLast();
        this.getChildren().add(node);
    }
}
