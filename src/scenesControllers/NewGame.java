package scenesControllers;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import mainControllers.GameState;
import mainControllers.MainController;

public class NewGame {

	MainController mainController = MainController.getMainController();
	GameState gameState = GameState.getGameState();
    @FXML  private AnchorPane pane;

    @FXML void arcade() throws IOException {
    	gameState.gameType = "arcade";
    	mainController.loadScene("/scenes/GamePlay.fxml");
    }

    @FXML void back() throws IOException { mainController.loadScene("/scenes/MainMenu.fxml"); }

    @FXML void classic() throws IOException {
    	gameState.gameType = "classic";
    	mainController.loadScene("/scenes/GamePlay.fxml");
    }

    @FXML  void initialize() {   }
}
