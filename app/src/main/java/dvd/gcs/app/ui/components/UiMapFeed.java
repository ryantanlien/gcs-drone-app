package dvd.gcs.app.ui.components;

import dvd.gcs.app.ui.api.UiPane;
import javafx.scene.layout.Pane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class UiMapFeed extends UiPane {
    private static final String FXML = "UiMapFeed.fxml"; // TODO: currently unused (?)

    @Autowired
    public UiMapFeed(Pane pane) {
        super(pane);
    }
}
