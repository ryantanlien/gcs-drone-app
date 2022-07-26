package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DroneFeedWindowControllerWrapper {
    @FXML
    private Label droneStatus;
    @FXML
    private Label droneType;

    @FXML
    private void switchToDroneFeedButtonAction(ActionEvent event) {
        // implemented in actual controller class
    }
}
