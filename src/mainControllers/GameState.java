package mainControllers;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.animation.Timeline;
import models.Fruit;

public class GameState {

	public String gameType;
	public boolean isGameOver=false;
	
	AnimationTimer checker;
	Timeline timeAdderTimeline = new Timeline();
	Timeline fruitAdderTimeline = new Timeline();

	public double[] mouseXY = {0,0};
	public boolean mouseKnifeLoaded=false;
	public boolean mouseIsDragging=false;
	
	int livesCounter=5;
	int scoreCounter=0;
	int timeCounter=0;
	List<Fruit> fruits = new ArrayList<Fruit>();
	
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
		timeAdderTimeline = new Timeline();
		fruitAdderTimeline = new Timeline();
		mouseKnifeLoaded=false;
		mouseIsDragging=false;
	    livesCounter=5;
	    scoreCounter=0;
	    timeCounter=0;
		fruits = new ArrayList<Fruit>();
	}
}
