package mainControllers;

import scenesControllers.*;
import models.Fruit;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainController {

	/*		--		--		--		--		--		--		Singleton MainController		--		--		--		--		--		--		*/
	
	private static MainController mainController;

	private MainController() { }
	public static MainController getMainController() {
        if(mainController == null) mainController = new MainController(); 
        return mainController;
	}

	/*		--		--		--		--		--		--		Static stage		--		--		--		--		--		--		*/
	
	Stage stage;
	
	public void setStage(Stage stage) { this.stage= stage; }
	public Stage getStage() { return stage; }

	/*		--		--		--		--		--		--		Load scenes		--		--		--		--		--		--		*/

    int[] paneXYsize= {700,400};
	AnchorPane pane;
	
	public int[] getPaneXYsize() { return paneXYsize; }
	public AnchorPane getPane() { return pane; }
	public void loadScene(String fxmlName) throws IOException {
		pane = (AnchorPane)FXMLLoader.load(getClass().getResource( fxmlName ));
		Scene scene = new Scene(pane);
		stage.setScene(scene);
		stage.show();
	}

	/*		--		--		--		--		--		--		Game saving ( x m l )		--		--		--		--		--		--		*/
	
	XmlParser xmlParser = new XmlParser();
	
	public void saveGame() {
		xmlParser.saveGame();
		loadGameSuccessfullySavedImage();
	}
	public void loadGame() { xmlParser.loadGame(); }

	/*		--		--		--		--		--		--		Leader board ( x m l )		--		--		--		--		--		--		*/
	
	public void updateLeaderboard() { xmlParser.updateLeaderboard(); }
	public void getLeaderboard() { xmlParser.getLeaderboard(); }

	/*		--		--		--		--		--		--		Start game play	--		--		--		--		--		--		*/

	GamePlay gamePlay;
	GameState gameState = GameState.getGameState();
	GameFlowHandler gameFlowHandler;
	public int randomRange(int min, int max) { return (int)( min+Math.random()*(max-min) ); }
	
	public void startGame(Label livesLabel, Label scoreLabel, Label timeLabel, Label finalScoreLabel) {
		animator = new Animator();
		objectsLoader = new ObjectsLoader();
		gameFlowHandler = new GameFlowHandler();
		gameFlowHandler.startGame(livesLabel, scoreLabel, timeLabel, finalScoreLabel); 
		}
	public void pauseGame() { gameFlowHandler.pauseGame(); }
	public void playPausedGame() { gameFlowHandler.playPausedGame(); }
	public void setGamePlayController(GamePlay gamePlay) { this.gamePlay = gamePlay; }
	public GamePlay getGamePlayController() { return gamePlay; }

	/*		--		--		--		--		--		--		Load game elements		--		--		--		--		--		--		*/
	
	ObjectsLoader objectsLoader;
	
	public void loadFruitsAndBombs(int count) { objectsLoader.loadFruitsAndBombs(count); }
	public void loadBombs(int count) { objectsLoader.loadBombs(count); }
	public void loadGameSuccessfullySavedImage() { objectsLoader.loadGameSuccessfullySavedImage(); }
	public void loadMouseKnife() { objectsLoader.loadMouseKnife(); }
	public void loadMouseDragKnife() { objectsLoader.loadMouseDragKnife(); }

	/*		--		--		--		--		--		--		Animation transitions		--		--		--		--		--		--		*/
	
	Animator animator;
	
	public void throwTransition(Fruit fruit) { animator.throwTransition(fruit); }
	public void cutTransition(Fruit fruit, int direction) { animator.cutTransition(fruit, direction); }
	public void fadeShrinkTransition(ImageView object) { animator.fadeShrinkTransition(object); }
	public void fadeDownTransition(ImageView object) { animator.fadeDownTransition(object); }
	public void followMouseTransition(ImageView object) { animator.followMouseTransition( object ); }
	public void mouseTailTransition(ImageView object) { animator.mouseTailTransition( object ); }
}
