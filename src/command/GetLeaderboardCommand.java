package command;

import mainControllers.GameState;
import mainControllers.XmlParser;
import remote.Command;

public class GetLeaderboardCommand implements Command {

	XmlParser xmlParser;
	GameState gameState = GameState.getGameState();

    public GetLeaderboardCommand() {
        xmlParser = new XmlParser();
    }

    @Override
    public void execute() {
    	gameState.returnedGameList = xmlParser.getLeaderboard();
    }

}
