package command;

import mainControllers.XmlParser;
import remote.Command;

public class SaveGameCommand implements Command {

	XmlParser xmlParser;

    public SaveGameCommand() {
        xmlParser = new XmlParser();
    }

    @Override
    public void execute() {
    	xmlParser.saveGame();
    }

}
