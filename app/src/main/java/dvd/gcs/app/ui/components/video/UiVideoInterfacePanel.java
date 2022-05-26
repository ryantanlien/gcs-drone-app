package dvd.gcs.app.ui.components.video;

import dvd.gcs.app.ui.api.UiPane;
import javafx.scene.layout.BorderPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component("UiVideoInterfacePanel")
@Lazy
public class UiVideoInterfacePanel extends UiPane {

    UiVideoInterfaceTop uiVideoInterfaceTop;

    @Autowired
    public UiVideoInterfacePanel(
            BorderPane pane,
            UiVideoInterfaceTop uiVideoInterfaceTop) {
        super(pane);
        this.uiVideoInterfaceTop = uiVideoInterfaceTop;
        fillInnerParts();
    }

    private void fillInnerParts() {
        BorderPane borderPane = (BorderPane) this.getRoot();
        borderPane.setTop(uiVideoInterfaceTop.getRoot());
    }
}
