package scenesControllers;

import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import mainControllers.MainController;

public class LeaderBoard {

	MainController mainController = MainController.getMainController();
    @FXML private AnchorPane pane;
    @FXML private Label classicLabel;
    @FXML private Label arcadeLabel;
    @FXML private Label classicChildLabel;
    @FXML private Label arcadeChildLabel;

    @FXML void back() throws IOException { mainController.loadScene("/scenes/MainMenu.fxml"); }

    @FXML void initialize() throws ParserConfigurationException, SAXException, IOException {
		String[] leaderBoard = mainController.getLeaderboard();
		classicLabel.setText( leaderBoard[0] );
		classicChildLabel.setText( leaderBoard[1] );
		arcadeLabel.setText( leaderBoard[2] );
		arcadeChildLabel.setText( leaderBoard[3] );
    }
}
