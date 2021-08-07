package command;

import mainControllers.XmlParser;
import remote.Command;

public class LoadGameCommand implements Command {

	XmlParser xmlParser;

    public LoadGameCommand() {
        xmlParser = new XmlParser();
    }

    @Override
    public void execute() {
    	xmlParser.loadGame();
    }

}
