package dvd.gcs.app.ui.components;

import dvd.gcs.app.ui.api.UiPane;
import javafx.scene.layout.AnchorPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component("UiVideoInterfacePanel")
@Lazy
public class UiVideoInterfacePanel extends UiPane {

    UiButton uiButton;

    @Autowired
    public UiVideoInterfacePanel(
            AnchorPane anchorPane,
            UiButton uiButton) {
        super(anchorPane);
        this.uiButton = uiButton;
        fillInnerParts();
    }

    private void fillInnerParts() {
        this.getRoot().getChildren().add((uiButton.getRoot()));
    }
}
