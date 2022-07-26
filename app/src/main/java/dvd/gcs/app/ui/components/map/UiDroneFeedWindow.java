package dvd.gcs.app.ui.components.map;

import dvd.gcs.app.ui.api.UiElement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class UiDroneFeedWindow extends UiElement<TitledPane> {

    /** The associated FXML file with this component **/
    private static final String FXML = "UiDroneFeedWindow.fxml";

    /** Text of the TitledPane **/
    private final String title;

    @FXML
    private Label droneStatus;
    @FXML
    private Label droneType;

    @Autowired
    public UiDroneFeedWindow(TitledPane titledPane) {
        super(FXML, titledPane);
        this.title = "Drone 1";
        TitledPane root = this.getRoot();
        root.setText(this.title);
        root.setExpanded(false);
    }

    public void setDroneStatus(String status) {
        this.droneStatus.setText(status);
    }

    public void setDroneType(String droneType) {
        this.droneType.setText(droneType);
    }

    @FXML
    private void switchToDroneFeedButtonAction(ActionEvent event) {
        // TODO: switch to drone feed
    }
}
