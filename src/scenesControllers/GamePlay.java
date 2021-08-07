package scenesControllers;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import mainControllers.GameState;
import mainControllers.MainController;

public class GamePlay {

	MainController mainController = MainController.getMainController();
	GameState gameState = GameState.getGameState();
    @FXML private AnchorPane pane;
    @FXML private Label livesLabel;
    @FXML private Label scoreLabel;
    @FXML private Label timeLabel;
    @FXML private Label finalScoreLabel;
    @FXML private ImageView dimView;
    @FXML private ImageView gameOverButton;
    @FXML private ImageView backToMainButton;
    @FXML private ImageView leaderBoardButton;
    @FXML private ImageView gameOverGlow;
    @FXML private ImageView backToMainGlow;
    @FXML private ImageView leaderBoardGlow;
    @FXML private ImageView menuExitButton;
    @FXML private ImageView menuContinueButton;
    @FXML private ImageView menuSaveButton;
    @FXML private ImageView menuContinueGlow;
    @FXML private ImageView menuSaveGlow;
    @FXML private ImageView menuExitGlow;
    @FXML private ImageView FinalScore;
    @FXML private ImageView FinalScoreGlow;
    @FXML private ImageView finalScoreBackground;
    @FXML private ImageView  newHighScore;

    @FXML void backToMain() throws IOException { mainController.loadScene("/scenes/MainMenu.fxml"); }

    @FXML void leaderBoard() throws IOException { mainController.loadScene("/scenes/leaderBoard.fxml"); }
    
    @FXML void menu() {
    	mainController.pauseGame();
    	showMenu();
    }

    @FXML void menuContinue() {
    	hideLayers();
    	mainController.playPausedGame();
    	/* TODO: append play Fruits */
    }

    @FXML void menuExit() throws IOException { mainController.loadScene("/scenes/MainMenu.fxml"); }

    @FXML void menuSave() {
    	/* TODO: save state */
    }
    
    public void hideLayers() {
        dimView.setVisible(false);
        gameOverButton.setVisible(false);
        backToMainButton.setVisible(false);
        leaderBoardButton.setVisible(false);
        leaderBoardGlow.setVisible(false);
        gameOverGlow.setVisible(false);
        backToMainGlow.setVisible(false);
        menuExitButton.setVisible(false);
        menuContinueButton.setVisible(false);
        menuSaveButton.setVisible(false);
        menuContinueGlow.setVisible(false);
        menuSaveGlow.setVisible(false);
        menuExitGlow.setVisible(false);
        FinalScore.setVisible(false);
        finalScoreLabel.setVisible(false);
        FinalScoreGlow.setVisible(false);
        finalScoreBackground.setVisible(false);
        newHighScore.setVisible(false);
    }
    
    public void showGameOver() {
        dimView.setVisible(true);
        dimView.toFront();
        gameOverButton.setVisible(true);
        gameOverButton.toFront();
        backToMainButton.setVisible(true);
        backToMainButton.toFront();
        leaderBoardButton.setVisible(true);
        leaderBoardButton.toFront();
        finalScoreBackground.setVisible(true);
        finalScoreBackground.toFront();
        FinalScore.setVisible(true);
        FinalScore.toFront();
        leaderBoardGlow.setVisible(true);
        leaderBoardGlow.toFront();
        gameOverGlow.setVisible(true);
        gameOverGlow.toFront();
        backToMainGlow.setVisible(true);
        backToMainGlow.toFront();
        FinalScoreGlow.setVisible(true);
        FinalScoreGlow.toFront();
        finalScoreLabel.setVisible(true);
        finalScoreLabel.toFront();
    }
    
    public void showNewHighScore() {
      newHighScore.setVisible(true);
      newHighScore.toFront();
    }
    
    public void showMenu() {
        dimView.setVisible(true);
        dimView.toFront();
        menuExitButton.setVisible(true);
        menuExitButton.toFront();
        menuContinueButton.setVisible(true);
        menuContinueButton.toFront();
        menuSaveButton.setVisible(true);
        menuSaveButton.toFront();
        menuContinueGlow.setVisible(true);
        menuContinueGlow.toFront();
        menuSaveGlow.setVisible(true);
        menuSaveGlow.toFront();
        menuExitGlow.setVisible(true);
        menuExitGlow.toFront();
    }
    
    public void setOnMouseDragging() {
    	pane.setOnDragDetected(e -> pane.startFullDrag());
    	pane.setOnMouseMoved(e -> {
    		gameState.mouseXY[0] = e.getX();
    		gameState.mouseXY[1] = e.getY();
		});
    	pane.setOnMouseDragged(e -> {
    		gameState.mouseIsDragging=true;
			gameState.mouseXY[0] = e.getX();
			gameState.mouseXY[1] = e.getY();
		});
    	pane.setOnMouseReleased(e -> { gameState.mouseIsDragging=false; });
    }

    @FXML void initialize() {
    	setOnMouseDragging();
    	hideLayers();
    	mainController.setGamePlayController( this );
    	mainController.startGame(livesLabel, scoreLabel, timeLabel, finalScoreLabel);
    	/* TODO: start game */
    }
}
