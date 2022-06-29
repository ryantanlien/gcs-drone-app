package dvd.gcs.app.ui.components.map;

import dvd.gcs.app.luciadlightspeed.LuciadMap;
import dvd.gcs.app.ui.api.UiPane;
import javafx.embed.swing.SwingNode;
import javafx.scene.layout.Pane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class UiMapFeed extends UiPane {
    private static final String FXML = "UiMapFeed.fxml";

    private SwingNode mapSwingNode;

    @Autowired
    public UiMapFeed(
            @Qualifier("Pane") Pane pane,
            LuciadMap luciadMap) {
        super(FXML, pane);
        this.mapSwingNode = luciadMap.getSwingNode();
    }

    /**
     * Fills the JavaFX Pane
     */
    private void fillInnerParts() {
        this.getRoot().getChildren().add(mapSwingNode);
    }
}
