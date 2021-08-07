package command;

import mainControllers.GameState;
import mainControllers.XmlParser;
import remote.Command;

public class IsNewHighScoreCommand implements Command {

	XmlParser xmlParser;
	GameState gameState = GameState.getGameState();

    public IsNewHighScoreCommand() {
        xmlParser = new XmlParser();
    }

    @Override
    public void execute() {
    	gameState.returnedHighScoreBool = xmlParser.isNewHighScore();
    }

}
