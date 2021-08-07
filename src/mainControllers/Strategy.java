package mainControllers;

import javafx.scene.control.Label;

public interface Strategy {
	
	public void startGame(Label livesLabel, Label scoreLabel, Label timeLabel, Label finalScoreLabel);
	public void updateLabels();
	public void startTimer();
	public void startChecker();
	public void stopGame();
	public void pauseGame();
	public void playPausedGame();
	public void addCombo();
	public void increaseCombo();

}
