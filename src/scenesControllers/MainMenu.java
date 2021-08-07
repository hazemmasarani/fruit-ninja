package scenesControllers;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import mainControllers.GameState;
import mainControllers.MainController;

public class MainMenu {

	MainController mainController = MainController.getMainController();
	GameState gameState = GameState.getGameState();
    @FXML private AnchorPane pane;

    @FXML void exit() { System.exit(0); }

    @FXML void leaderBoard() throws IOException { mainController.loadScene("/scenes/LeaderBoard.fxml"); }

    @FXML void loadGame() throws IOException {
    	gameState.gameType = "continue";
    	mainController.loadScene("/scenes/GamePlay.fxml");
    }

    @FXML void newGame() throws IOException { mainController.loadScene("/scenes/NewGame.fxml"); }

    @FXML void initialize() {  }
}
