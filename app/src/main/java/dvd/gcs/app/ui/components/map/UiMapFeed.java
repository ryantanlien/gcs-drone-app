package dvd.gcs.app.ui.components.map;

import dvd.gcs.app.ui.api.UiPane;
import javafx.embed.swing.SwingNode;
import javafx.scene.layout.*;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class UiMapFeed extends UiPane {
    private static final String FXML = "UiMapFeed.fxml";

    private SwingNode mapSwingNode;

    @Autowired
    public UiMapFeed(
            StackPane stackPane,
            BeanFactory beanFactory) {
        super(FXML, stackPane);
        SwingNode swingNode = (SwingNode) beanFactory.getBean("LuciadSwingNode");

        this.mapSwingNode = swingNode;
        fillInnerParts();
    }

    /**
     * Fills the JavaFX Pane
     */
    private void fillInnerParts() {
//        this.getRoot().getChildren().add(mapSwingNode);
    }
}
