package dvd.gcs.app.ui.components.map;

import dvd.gcs.app.ui.api.UiPane;
import javafx.scene.layout.Pane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

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
        fillInnerParts();
    }

    /**
     * Fills the JavaFX placeholders.
     */
    private void fillInnerParts() {
        this.getRoot().getChildren().add(uiMainMapScene.getRoot());
    }
}
