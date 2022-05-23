package dvd.gcs.app.ui;

import javafx.scene.control.TitledPane;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class UiDroneFeedWindow extends UiElement<TitledPane> {

    /** The associated FXML file with this component **/
    private static final String FXML = "UiDroneFeedWindow.fxml";

    private String title;

    public UiDroneFeedWindow(TitledPane titledPane) {
        super(FXML, titledPane);
        this.title = "Drone 1";
        TitledPane root = this.getRoot();
        root.setText(this.title);
    }
}
