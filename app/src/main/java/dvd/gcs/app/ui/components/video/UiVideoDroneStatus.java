package dvd.gcs.app.ui.components.video;

import dvd.gcs.app.ui.api.UiPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class UiVideoDroneStatus extends UiPane {

    private final static String FXML = "UiVideoDroneStatus.fxml";

    @Autowired
    public UiVideoDroneStatus(
            StackPane stackPane) {
        super(FXML, stackPane);
    }

    private VBox getInnerVbox() {
        return (VBox) this.getRoot().getChildren().get(1);
    }
}
