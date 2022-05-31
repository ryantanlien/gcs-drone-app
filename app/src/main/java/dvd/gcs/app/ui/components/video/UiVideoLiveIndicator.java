package dvd.gcs.app.ui.components.video;

import dvd.gcs.app.ui.api.UiPane;
import javafx.scene.layout.Pane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class UiVideoLiveIndicator extends UiPane {

    private final static String FXML = "UiVideoLiveIndicator.fxml";

    @Autowired
    public UiVideoLiveIndicator(@Qualifier("StackPane") Pane pane) {
        super(FXML, pane);
    }
}
