package mainControllers;

import java.util.ArrayList;
import java.util.List;
import javafx.animation.AnimationTimer;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import models.ObjThrow;

public class GameState {

	public String gameType;
	public boolean isGameOver=false;
	public boolean isExitting=false;
	
	AnimationTimer checker;
	Timeline timeAdderTimeline = new Timeline();
	Timeline fruitAdderTimeline = new Timeline();

	public double[] mouseXY = {0,0};
	public boolean mouseKnifeLoaded=false;
	public boolean mouseIsDragging=false;
	public int combo=0;
	public ImageView comboImage;
	
	int livesCounter=5;
	public int scoreCounter=0;
	int timeCounter=0;
	List<ObjThrow> fruits = new ArrayList<ObjThrow>();

	public boolean returnedHighScoreBool;
	public String[] returnedGameList;
	
	private GameState() {
	}

	/*		--		--		--		--		--		--		Singleton GameState	--		--		--		--		--		--		*/
	
	private static GameState gameState;
	public static GameState getGameState() {
        if(gameState == null) gameState = new GameState();
        return gameState;
	}

	/*		--		--		--		--		--		--		Reset GameState	--		--		--		--		--		--		*/
	
	public void resetGameState() {
		isGameOver=false;
		isExitting=false;
		timeAdderTimeline = new Timeline();
		fruitAdderTimeline = new Timeline();
		mouseKnifeLoaded=false;
		mouseIsDragging=false;
		combo=0;
	    livesCounter=5;
	    scoreCounter=0;
	    timeCounter=0;
		fruits = new ArrayList<ObjThrow>();
	}
}
