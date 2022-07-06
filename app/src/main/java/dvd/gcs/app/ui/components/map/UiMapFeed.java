package dvd.gcs.app.ui.components.map;

import dvd.gcs.app.ui.api.UiPane;
import javafx.embed.swing.SwingNode;
import javafx.scene.layout.Pane;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class UiMapFeed extends UiPane {
    private static final String FXML = "UiMapFeed.fxml";

    private SwingNode mapSwingNode;

//    @Autowired
//    BeanFactory beanFactory;

    @Autowired
    public UiMapFeed(
            @Qualifier("Pane") Pane pane,
            BeanFactory beanFactory) {
        super(FXML, pane);
        //SwingNode swingNode = (SwingNode) beanFactory.getBean("LuciadSwingNode");
        //this.mapSwingNode = swingNode;
    }

    /**
     * Fills the JavaFX Pane
     */
    private void fillInnerParts() {
        this.getRoot().getChildren().add(mapSwingNode);
    }
}
