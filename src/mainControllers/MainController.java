package mainControllers;

import remote.*;
import command.*;
import models.*;
import scenesControllers.*;
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
	Strategy Strategy;
	
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
	
	RemoteControl remoteControl = new RemoteControl();
	Command loadGameCommand = new LoadGameCommand();
	Command saveGameCommand = new SaveGameCommand();
	Command isNewHighScoreCommand = new IsNewHighScoreCommand();
	Command getLeaderboardCommand = new GetLeaderboardCommand();
	Command addToLeaderboardCommand = new AddToLeaderboardCommand();
	
	public void saveGame() {
		remoteControl.addCommand( saveGameCommand );
		remoteControl.pushButton();
		loadGameSuccessfullySavedImage();
	}
	public void loadGame() {
		remoteControl.addCommand( loadGameCommand );
		remoteControl.pushButton();
	}

	/*		--		--		--		--		--		--		Leader board ( x m l )		--		--		--		--		--		--		*/
	
	public boolean isNewHighScore() {
		remoteControl.addCommand( isNewHighScoreCommand );
		remoteControl.pushButton();
		return gameState.returnedHighScoreBool;
	}
	public void addToLeaderboard() {
		remoteControl.addCommand( addToLeaderboardCommand );
		remoteControl.pushButton();
		}
	public String[] getLeaderboard() {
		remoteControl.addCommand( getLeaderboardCommand );
		remoteControl.pushButton();
		return gameState.returnedGameList;
		}

	/*		--		--		--		--		--		--		Start game play	--		--		--		--		--		--		*/

	GamePlay gamePlay;
	GameState gameState = GameState.getGameState();
	Strategy gameFlowHandler;
	public int randomRange(int min, int max) { return (int)( min+Math.random()*(max-min) ); }
	
	public void startGame(Label livesLabel, Label scoreLabel, Label timeLabel, Label finalScoreLabel) {
		animator = new Animator();
		objectsLoader = new ObjectsLoader();
		gameFlowHandler = new GameFlowHandler();
		gameFlowHandler.startGame(livesLabel, scoreLabel, timeLabel, finalScoreLabel); 
		}
	public void pauseGame() { gameFlowHandler.pauseGame(); }
	public void playPausedGame() { gameFlowHandler.playPausedGame(); }
	public void stopGame() { gameFlowHandler.stopGame(); }
	public void addCombo() { gameFlowHandler.addCombo(); }
	public void setGamePlayController(GamePlay gamePlay) { this.gamePlay = gamePlay; }
	public GamePlay getGamePlayController() { return gamePlay; }

	/*		--		--		--		--		--		--		Load game elements		--		--		--		--		--		--		*/
	
	ObjectsLoader objectsLoader;

	public void loadFruitsAndBombs(int count) { objectsLoader.loadFruitsAndBombs(count); }
	public void loadFruits(int count) { objectsLoader.loadFruits(count); }
	public void loadBombs(int count) { objectsLoader.loadBombs(count); }
	public void loadGameSuccessfullySavedImage() { objectsLoader.loadGameSuccessfullySavedImage(); }
	public void loadComboImage() { objectsLoader.loadComboImage(); }
	public void loadMouseKnife() { objectsLoader.loadMouseKnife(); }
	public void loadMouseDragKnife() { objectsLoader.loadMouseDragKnife(); }

	/*		--		--		--		--		--		--		Animation transitions		--		--		--		--		--		--		*/
	
	Animator animator;
	
	public void throwTransition(ObjThrow fruit) { animator.throwTransition(fruit); }
	public void cutTransition(ObjThrow fruit, int direction) { animator.cutTransition(fruit, direction); }
	public void fadeTransition(ObjThrow fruit) { animator.fadeTransition( fruit ); }
	public void fadeShrinkTransition(ImageView object) { animator.fadeShrinkTransition(object); }
	public void fadeEnlargeTransition(ObjThrow fruit) { animator.fadeEnlargeTransition(fruit); }
	public void fadeDownTransition(ImageView object) { animator.fadeDownTransition(object); }
	public void fadeUpTransition(ImageView object) { animator.fadeUpTransition(object); }
	public void followMouseTransition(ImageView object) { animator.followMouseTransition( object ); }
	public void mouseTailTransition(ImageView object) { animator.mouseTailTransition( object ); }
}
