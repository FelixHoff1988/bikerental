package de.wwu.sopra;

import de.wwu.sopra.login.LoginGUI;
import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.util.Duration;

/**
 * Hauptlayout-Node der App
 */
public class AppGUI extends StackPane {
    /**
     * Zeigt an, ob die Navigationsleiste gerade angezeigt wird.
     */
    private boolean navigatorShown = false;

    /**
     * Hauptanzeige node der App
     */
    private final BorderPane mainView = new BorderPane();

    /**
     * Hier werden Nachrichten angezeigt
     */
    private final VBox messageBox = new VBox();

    /**
     * Standardkonstruktor
     */
    public AppGUI() {
        this.setAlignment(Pos.TOP_CENTER);

        VBox.setVgrow(this, Priority.NEVER);
        HBox.setHgrow(this, Priority.NEVER);

        messageBox.setSpacing(10);
        messageBox.setMinWidth(300);
        messageBox.setMaxWidth(300);
        messageBox.setMinHeight(0);
        messageBox.setMaxHeight(0);
        messageBox.setPadding(new Insets(10, 0, 0, 0));

        this.getChildren().addAll(mainView, messageBox);
        this.changeViewNode(new LoginGUI());
    }

    /**
     * Zeigt an bzw. blendet die Navigationsleiste aus.
     */
    public void toggleNavigation() {
        if (navigatorShown)
            mainView.setTop(null);
        else
            mainView.setTop(new NavigationGUI());

        this.navigatorShown = !this.navigatorShown;
    }

    /**
     * Ändere die haupt Anzeigenode des GUI
     *
     * @param node Neue haupt Node
     */
    public void changeViewNode(Node node) {
        mainView.setCenter(node);
    }

    /**
     * Zeigt eine Nachricht für eine angegebene Zeitdauer an.
     *
     * @param message Nachrichtentext
     * @param duration Anzeigedauer in Sekunden
     */
    public void showMessage(String message, int duration) {
        var messagePane = new FlowPane();
        var messageLabel = new Label(message);

        messageLabel.setWrapText(true);
        messageLabel.setMinWidth(300);
        messageLabel.setMaxWidth(300);

        messagePane.setMinWidth(300);
        messagePane.setMaxWidth(300);
        messagePane.setAlignment(Pos.CENTER_LEFT);
        messagePane.setPadding(new Insets(10));
        messagePane.setStyle("-fx-background-color: #e6e6e6;"
                + "-fx-border-radius: 10;"
                + "-fx-border-style: solid;"
                + "-fx-border-color: #bfbfbf;"
                + "-fx-background-radius: 10;"
                + "-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.5), 5, 0, 0, 0);");

        messagePane.getChildren().add(messageLabel);
        messageBox.setMaxHeight(messageBox.getMaxHeight() + messagePane.getHeight());
        messageBox.getChildren().add(messagePane);

        var pause = new PauseTransition(Duration.seconds(duration));
        pause.setOnFinished(event -> {
            messageBox.setMaxHeight(messageBox.getMaxHeight() - messagePane.getHeight());
            messageBox.getChildren().remove(messagePane);
        });
        pause.play();
    }
}
