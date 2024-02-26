package de.wwu.sopra.register;



import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;


public class RegisterGUI extends HBox {
	
	private RegisterCTRL controller = new RegisterCTRL();
	
	public RegisterGUI(){
		init();
	}
	
	public void init() {
		
		var innerBox = new GridPane();
		innerBox.setAlignment(Pos.CENTER);
		innerBox.setVgap(5);
		
		var firstName = new Label();
		innerBox.add(firstName, 0, 0);
		
		this.getChildren().addAll(innerBox);
		this.setAlignment(Pos.CENTER);
		VBox.setVgrow(this, Priority.ALWAYS);
		
		
		
	}

//	@Override
//	public void start(Stage primaryStage) throws Exception {
//		GridPane grid = new GridPane();
//		Scene register = new Scene(grid, 1920, 1080);
//		grid.setStyle("-fx-background-color: #CCFFEE");
//		
//		primaryStage.setScene(register);
//		primaryStage.show();
//		
//	}

}
