package scenesControllers;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import mainControllers.MainController;

public class LeaderBoard {

	MainController mainController = MainController.getMainController();
    @FXML private AnchorPane pane;
    @FXML private Label label;

    @FXML void back() throws IOException { mainController.loadScene("/scenes/MainMenu.fxml"); }

    @FXML void initialize() {
    	//set leader label
    }
}
