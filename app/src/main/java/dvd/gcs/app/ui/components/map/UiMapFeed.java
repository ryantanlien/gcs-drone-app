package dvd.gcs.app.ui.components.map;

import dvd.gcs.app.ui.api.UiPane;
import javafx.embed.swing.SwingNode;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.swing.*;

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
        this.getRoot().getChildren().add(mapSwingNode);
    }
}
