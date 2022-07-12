package dvd.gcs.app.ui.components.map;

import dvd.gcs.app.ui.api.UiPane;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * This is the base JavaFX Pane that contains the screen related to the map engine
 */
@Component
@Lazy
@Scope("singleton")
public class UiMapBasePane extends UiPane {

    UiMainMapScene uiMainMapScene;

    /**
     * Constructs a default UiMapBasePane, where the wrapped JavaFX Pane is injected
     * as a dependency by Spring via constructor dependency injection.
     */
    @Autowired
    public UiMapBasePane(
            @Qualifier("Pane") Pane pane,
            UiMainMapScene uiMainMapScene) {
        super(pane);
        this.uiMainMapScene = uiMainMapScene;
        setConfigurations();
        fillInnerParts();
    }

    private void setConfigurations() {
        Pane pane = (Pane) this.getRoot();
        pane.setPrefWidth(1920);
        pane.setPrefHeight(1080-26);
        pane.setMaxWidth(Double.MAX_VALUE);
        pane.setMaxHeight(Double.MAX_VALUE);
    }

    /**
     * Fills the JavaFX Pane.
     */
    private void fillInnerParts() {
        this.getRoot().getChildren().add(uiMainMapScene.getRoot());
    }
}
