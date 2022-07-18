package dvd.gcs.app.ui.components.map;

import dvd.gcs.app.ui.api.UiElement;
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
    private String title;

    @Autowired
    public UiDroneFeedWindow(TitledPane titledPane) {
        super(FXML, titledPane);
        this.title = "Drone 1";
        TitledPane root = this.getRoot();
        root.setText(this.title);
        root.setExpanded(false);
    }
}
