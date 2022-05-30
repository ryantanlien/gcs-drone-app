package dvd.gcs.app.ui.components.video;

import dvd.gcs.app.ui.api.UiPane;
import javafx.scene.layout.StackPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class UiVideoDroneStats extends UiPane {

    private final static String FXML = "UiVideoDroneStats.fxml";

    @Autowired
    public UiVideoDroneStats(
            StackPane stackPane) {
        super(FXML, stackPane);
    }
}
