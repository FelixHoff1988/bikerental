package de.wwu.sopra;

import de.wwu.sopra.entity.User;
import de.wwu.sopra.login.LoginGUI;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

/**
 * Verwaltet im globalen Kontext der App wichtige Funktionen
 */
public class AppContext {
    /**
     * Singleton Instanz des AppContext
     */
    private static AppContext instance;
    /**
     * Eingeloggter Nutzer
     */
    private User loggedInUser;
    /**
     * GUI der App
     */
    private final AppGUI appGui;

    /**
     * Konstruktor: Setzt die gui
     *
     * @param gui GUI der App
     */
    private AppContext(AppGUI gui) {
        this.appGui = gui;
    }

    /**
     * Erstellt die Singleton-Instanz des AppContext
     *
     * @param gui GUI der App
     */
    public static synchronized void create(AppGUI gui) {
        if (AppContext.instance == null)
            AppContext.instance = new AppContext(gui);
    }

    /**
     * Ruft die Instanz des AppContext ab.
     *
     * @return Singleton-Instanz des AppContext
     */
    public static synchronized AppContext getInstance() {
        return AppContext.instance;
    }

    /**
     * Ruft den aktuell eingeloggten Nutzer ab.
     *
     * @return Aktuell eingeloggter Nutzer (null, wenn keiner eingeloggt)
     */
    public User getLoggedInUser() {
        return this.loggedInUser;
    }

    /**
     * Loggt einen Nutzer ein.
     *
     * @param user Einzuloggender Nutzer
     */
    public void login(User user) {
        this.loggedInUser = user;
        this.appGui.toggleNavigation();
        this.appGui.changeViewNode(new Pane());
    }

    /**
     * Loggt den aktuellen Nutzer aus.
     */
    public void logout() {
        this.loggedInUser = null;
        this.appGui.toggleNavigation();
        this.appGui.changeViewNode(new LoginGUI());
    }

    /**
     * Ändert die aktuell angezeigte GUI.
     *
     * @param node GUI auf die gewechselt werden soll
     */
    public void changeViewNode(Node node) {
        this.appGui.changeViewNode(node);
    }

    /**
     * Zeigt eine Nachricht auf in der App an, ohne einen Popup zu verwenden.
     *
     * @param text Nachrichtentext
     * @param duration Anzeigedauer in Sekunden
     * @param color Hintergrundfarbe (kann null sein)
     */
    public void showMessage(String text, int duration, String color) {
        if (color == null)
            color = "#e6e6e6";
        this.appGui.showMessage(text, duration, color);
    }
}
