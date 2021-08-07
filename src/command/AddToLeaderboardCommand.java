package command;

import mainControllers.XmlParser;
import remote.Command;

public class AddToLeaderboardCommand implements Command {

	XmlParser xmlParser;

    public AddToLeaderboardCommand() {
        xmlParser = new XmlParser();
    }

    @Override
    public void execute() {
    	xmlParser.addToLeaderboard();
    }

}
