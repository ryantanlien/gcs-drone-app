package dvd.gcs.app.ui.components.video;

import dvd.gcs.app.ui.api.UiButton;
import dvd.gcs.app.ui.api.UiPane;
import dvd.gcs.app.ui.components.UiSimpleButton;
import javafx.scene.layout.Pane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component("UiVideoInterfacePanel")
@Lazy
public class UiVideoInterfacePanel extends UiPane {

    UiButton uiBackButton;

    @Autowired
    public UiVideoInterfacePanel(
            @Qualifier("AnchorPane") Pane pane,
            @Qualifier("UiBackButton") UiButton uiBackButton) {
        super(pane);
        this.uiBackButton = uiBackButton;
        fillInnerParts();
    }

    private void fillInnerParts() {
        this.getRoot().getChildren().add((uiBackButton.getRoot()));
    }
}
