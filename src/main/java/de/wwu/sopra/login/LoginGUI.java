package de.wwu.sopra.login;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;

/**
 * Login Ansicht
 */
public class LoginGUI extends HBox {
    /**
     * Login Steuerungsklasse
     */
    private final LoginCTRL controller = new LoginCTRL();

    /**
     * Standardkonstruktor: Initialisiert das Basislayout
     */
    public LoginGUI() {
        init();
    }

    /**
     * Erzeugt das Layout des LoginGUI
     */
    private void init() {
        var innerBox = new GridPane();
        innerBox.setAlignment(Pos.CENTER);
        innerBox.setVgap(5);

        var emailInput = new TextField();
        emailInput.setPromptText("E-Mail");
        emailInput.setPrefWidth(400);

        var passwordInput = new PasswordField();
        passwordInput.setPromptText("Passwort");
        passwordInput.setPrefWidth(400);

        var controls = new HBox();
        controls.setPrefWidth(400);
        controls.setSpacing(10);

        var anmeldeButton = new Button();
        anmeldeButton.setText("Anmelden");
        anmeldeButton.setPrefWidth(100);
        anmeldeButton.setOnAction(event -> controller.loginUser(emailInput.getText(), passwordInput.getText()));

        var spacer = new Pane();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        var registerLink = new Hyperlink("Registrieren");
        registerLink.setOnAction(event -> controller.registerUser());

        var passwordLink = new Hyperlink("Passwort vergessen");
        passwordLink.setOnAction(event -> controller.forgotPassword());

        controls.getChildren().addAll(anmeldeButton, spacer, registerLink, passwordLink);
        innerBox.add(emailInput, 0, 0);
        innerBox.add(passwordInput, 0, 1);
        innerBox.add(controls, 0, 2);

        this.getChildren().addAll(innerBox);
        this.setAlignment(Pos.CENTER);
        VBox.setVgrow(this, Priority.ALWAYS);
    }
}
