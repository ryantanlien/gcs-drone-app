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
    UiVideoInterfaceRight uiVideoInterfaceRight;

    @Autowired
    public UiVideoInterfacePanel(
            BorderPane borderPane,
            UiVideoInterfaceTop uiVideoInterfaceTop,
            UiVideoInterfaceRight uiVideoInterfaceRight) {
        super(borderPane);
        this.uiVideoInterfaceTop = uiVideoInterfaceTop;
        this.uiVideoInterfaceRight = uiVideoInterfaceRight;
        fillInnerParts();
    }

    private void fillInnerParts() {
        BorderPane borderPane = (BorderPane) this.getRoot();
        borderPane.setTop(uiVideoInterfaceTop.getRoot());
        borderPane.setRight(uiVideoInterfaceRight.getRoot());
    }
}
