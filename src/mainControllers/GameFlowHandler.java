package mainControllers;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.util.Duration;

public class GameFlowHandler implements Strategy {

	static MainController mainController;
	GameState gameState = GameState.getGameState();
	music music=new music();
	Label livesLabel;
	Label scoreLabel;
	Label timeLabel;
	Label finalScoreLabel;
	
	public GameFlowHandler() { mainController = MainController.getMainController(); }
	
	public void startGame(Label livesLabel, Label scoreLabel, Label timeLabel, Label finalScoreLabel) {
		this.livesLabel = livesLabel;
		this.scoreLabel = scoreLabel;
		this.timeLabel = timeLabel;
		this.finalScoreLabel = finalScoreLabel;

		music.playMusic("start");
		if( gameState.gameType.equals("continue") ) mainController.loadGame();
		startTimer();
		startChecker();
	}
	
	public void updateLabels() {
		scoreLabel.setText( String.valueOf(gameState.scoreCounter) );
		timeLabel.setText( String.valueOf(gameState.timeCounter) );
		finalScoreLabel.setText( String.valueOf(gameState.scoreCounter) );
		if( gameState.gameType.equals("classic") ) livesLabel.setText( String.valueOf(gameState.livesCounter) );
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
			if( gameState.gameType.equals("classic") ) {
				if( gameState.timeCounter < 59 ) {
					mainController.loadFruitsAndBombs( (gameState.timeCounter/10)+1 );
					mainController.loadBombs( gameState.timeCounter/20 );
				}
				else {
					mainController.loadFruitsAndBombs( (gameState.timeCounter/20)+1 );
					mainController.loadBombs( gameState.timeCounter/40 );
				}
			}
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
					mainController.loadFruits(1);
					if( gameState.gameType.equals("arcade") ) livesLabel.setText("  -");
				}
				if( gameState.mouseIsDragging ) mainController.loadMouseDragKnife();
				
				for( int i=0; i<gameState.fruits.size(); i++ ) {
					if( gameState.fruits.get( i ).isSliced() ) {
					    if( gameState.fruits.get( i ).isBomb() ) {
							music.playMusic("bomb");
					    	gameState.livesCounter--;
							gameState.scoreCounter--;
					    	gameState.combo--;
							addCombo();
					    }
					    if( gameState.fruits.get( i ).isDenamite() ) {
							music.playMusic("dynamite");
					    	gameState.combo--;
							addCombo();
					    	stopGame();
					    	return;
					    }
					    else {
							music.playMusic("slice");
					    	gameState.scoreCounter++;
					    	gameState.combo++;
					    }
					    gameState.fruits.remove( i );
					    i--;
					}
					else if( gameState.fruits.get( i ).isOffScreen() ) {
						if( !gameState.fruits.get( i ).isBomb() && !gameState.fruits.get( i ).isDenamite() ) {
							music.playMusic("miss");
							gameState.livesCounter--;
							gameState.scoreCounter--;
						}
						gameState.fruits.remove( i );
					    i--;
					}
				}
				
				if( gameState.gameType.equals("classic") ) {
					if( gameState.livesCounter < 1 ) {
				    	stopGame();
				    	return;
					}
				}
				else if( gameState.gameType.equals("arcade") ) {
					if( gameState.timeCounter > 59 ) {
				    	stopGame();
				    	return;
					}
					else if( gameState.fruits.size()==0 ) mainController.loadFruits( (gameState.timeCounter/2)+1 );
				}
				
				if( mainController.isNewHighScore( ) ) mainController.getGamePlayController().showNewHighScore();
				
				if( gameState.combo<2 ) mainController.loadComboImage();
				else if( gameState.combo<5 ) increaseCombo();
				else addCombo();

				if( gameState.scoreCounter<0 ) gameState.scoreCounter=0;
				updateLabels();
		}};
		gameState.checker = checker;
		checker.start();
	}
	
	public void addCombo() {
		increaseCombo();
		mainController.fadeUpTransition( gameState.comboImage );
		gameState.scoreCounter+=(gameState.combo*gameState.combo)-gameState.combo;
		gameState.combo=0;
	}
	
	public void increaseCombo() {
		gameState.comboImage.setImage( new Image("file:imageLibrary/text/combos/"+gameState.combo+".png") );
	}
	
	public void stopGame() {
		pauseGame();
		gameState.livesCounter = 0;
		gameState.isGameOver=true;
		updateLabels();
		mainController.getGamePlayController().showGameOver();
		if( !gameState.isExitting ) {
			music.playMusic("end");
			if( mainController.isNewHighScore( ) ) mainController.getGamePlayController().showNewHighScore();
			mainController.addToLeaderboard( );
		}
	}
	
	public void pauseGame() {
		gameState.timeAdderTimeline.pause();
		gameState.fruitAdderTimeline.pause();
		gameState.checker.stop();
		
		for(int i=0; i<gameState.fruits.size() ;i++) {
			if( gameState.fruits.get(i).isSliced() ) {
				if( !gameState.fruits.get(i).isBomb() && !gameState.fruits.get(i).isDenamite() ) {
					gameState.fruits.get(i).getCutTransitionLeft().pause();
					gameState.fruits.get(i).getCutTransitionRight().pause();
				}
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
