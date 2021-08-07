package mainControllers;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
	
	static MainController mainController = MainController.getMainController();

	public void start(Stage primaryStage) throws IOException {
		primaryStage.setTitle("Fruit Ninja"); 
		mainController.setStage(primaryStage);
		mainController.loadScene("/scenes/MainMenu.fxml");
	}
	
	public static void main(String[] args) { launch(args); }
}
