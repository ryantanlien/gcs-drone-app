package dvd.gcs.app.ui.components.map;

import dvd.gcs.app.ui.api.UiPane;
import dvd.gcs.app.ui.api.UiSwappableLayeredPane;
import dvd.gcs.app.ui.components.video.UiVideoTab;
import javafx.scene.layout.*;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * This is the base JavaFX Pane that contains the screen related to the map engine
 */
@Component("UiMapBasePane")
@Lazy
@Scope("singleton")
public class UiMapBasePane extends UiSwappableLayeredPane {

    UiMainMapScene uiMainMapScene;
    BeanFactory beanFactory;

    /**
     * Constructs a default UiMapBasePane, where the wrapped JavaFX Pane is injected
     * as a dependency by Spring via constructor dependency injection.
     */
    @Autowired
    public UiMapBasePane(
            UiMainMapScene uiMainMapScene,
            @Qualifier("Pane") Pane pane,
            BeanFactory beanFactory) {
        super(uiMainMapScene, pane);
        this.uiMainMapScene = uiMainMapScene;
        this.beanFactory = beanFactory;
        fillInnerParts();
    }

    /**
     * Fills the JavaFX Pane.
     */
    private void fillInnerParts() {
        this.uiMainMapScene.getRoot().prefHeightProperty().bind(this.getRoot().heightProperty());
        this.uiMainMapScene.getRoot().prefWidthProperty().bind(this.getRoot().widthProperty());
        this.addInnerPane();
    }

    @Override
    public UiPane swap() {
        return beanFactory.getBeanProvider(UiVideoTab.class).getIfAvailable();
    }
}
