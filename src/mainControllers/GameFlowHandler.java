package mainControllers;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class GameFlowHandler {

	static MainController mainController;
	GameState gameState = GameState.getGameState();
	Label livesLabel;
	Label scoreLabel;
	Label timeLabel;
	Label finalScoreLabel;
	music mu=new music();
	String filePath;
	public GameFlowHandler() { mainController = MainController.getMainController(); }
	
	public void startGame(Label livesLabel, Label scoreLabel, Label timeLabel, Label finalScoreLabel) {
		this.livesLabel = livesLabel;
		this.scoreLabel = scoreLabel;
		this.timeLabel = timeLabel;
		this.finalScoreLabel = finalScoreLabel;
		filePath="disco.wav";
		mu.playMusic(filePath);
		startTimer();
    	/* TODO : game type */
		startChecker();
	}
	
	public void updateLabels() {
		livesLabel.setText( String.valueOf(gameState.livesCounter) );
		scoreLabel.setText( String.valueOf(gameState.scoreCounter) );
		timeLabel.setText( String.valueOf(gameState.timeCounter) );
		finalScoreLabel.setText( String.valueOf(gameState.scoreCounter) );
	}
	
	public void startTimer() {
		Timeline timeAdderTimeline = gameState.timeAdderTimeline;
		Timeline fruitAdderTimeline = gameState.fruitAdderTimeline;
		timeAdderTimeline.setCycleCount(Animation.INDEFINITE);
		fruitAdderTimeline.setCycleCount(Animation.INDEFINITE);
		
    	KeyFrame timeAdderKey = new KeyFrame(Duration.seconds(1), e -> {
    		gameState.timeCounter++;
    		timeLabel.setText( String.valueOf(gameState.timeCounter) );
	    });
    	KeyFrame fruitAdderKey = new KeyFrame(Duration.seconds(4), e -> {
			mainController.loadFruitsAndBombs( (gameState.timeCounter/10)+1 );
			mainController.loadBombs( gameState.timeCounter/20 );
	    });
    	
    	timeAdderTimeline.getKeyFrames().add(timeAdderKey);
    	fruitAdderTimeline.getKeyFrames().add(fruitAdderKey);
    	timeAdderTimeline.play();
    	fruitAdderTimeline.play();
	}
	
	public void startChecker() {
    	AnimationTimer checker = new AnimationTimer() {
			@Override public void handle(long arg0) {
				
				if( !gameState.mouseKnifeLoaded ) {
					gameState.mouseKnifeLoaded=true;
					mainController.loadMouseKnife();
					mainController.loadFruitsAndBombs(1);
				}
				if( gameState.mouseIsDragging ) mainController.loadMouseDragKnife();
				
				for( int i=0; i<gameState.fruits.size(); i++ ) {
					if( gameState.fruits.get( i ).isSliced() ) {
					    if( gameState.fruits.get( i ).isBomb() ) {
					    	
					    	filePath="bomb.wav";
						    mu.playMusic(filePath);
					    	gameState.livesCounter--;}
					    if( gameState.fruits.get( i ).isDenamite() ) {
					    	
					    	filePath="dynamite.wav";
						    mu.playMusic(filePath);
					    	gameState.livesCounter-=2;}
					    else { 
					    	
					    	filePath="crunch.wav";
						    mu.playMusic(filePath);
					    	gameState.scoreCounter++;}
					    gameState.fruits.remove( i );
					    i--;
					}
					else if( gameState.fruits.get( i ).isOffScreen() ) {
						if( !gameState.fruits.get( i ).isBomb() && !gameState.fruits.get( i ).isDenamite() ) {
							filePath="oh.wav";
							mu.playMusic(filePath);
							gameState.livesCounter--;}
						gameState.fruits.remove( i );
					    i--;
					}
				}
				if( gameState.livesCounter < 1 ) stopGame();
				updateLabels();
		}};
		gameState.checker = checker;
		checker.start();
	}
	
	public void stopGame() {
		gameState.livesCounter = 0;
		gameState.isGameOver=true;
		updateLabels();
		pauseGame();
		
		filePath="lose2.wav";
		mu.playMusic(filePath);
		
		mainController.getGamePlayController().showGameOver();
    	/* TODO: new high score */
		if( false ) mainController.getGamePlayController().showNewHighScore();
	}
	
	public void pauseGame() {
		gameState.timeAdderTimeline.pause();
		gameState.fruitAdderTimeline.pause();
		gameState.checker.stop();
		
		for(int i=0; i<gameState.fruits.size() ;i++) {
			if( gameState.fruits.get(i).isSliced() ) {
				gameState.fruits.get(i).getCutTransitionLeft().pause();
				gameState.fruits.get(i).getCutTransitionRight().pause();
			}
			else gameState.fruits.get(i).getThrowTransition().pause();
		}
	}
	
	public void playPausedGame() {
		for(int i=0; i<gameState.fruits.size() ;i++) {
			if( gameState.fruits.get(i).isSliced() ) {
				gameState.fruits.get(i).getCutTransitionLeft().play();
				gameState.fruits.get(i).getCutTransitionRight().play();
			}
			else gameState.fruits.get(i).getThrowTransition().play();
		}
		gameState.timeAdderTimeline.play();
		gameState.fruitAdderTimeline.play();
		gameState.checker.start();
	}
}
