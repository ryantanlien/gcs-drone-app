package dvd.gcs.app.ui.components.video;

import dvd.gcs.app.ui.api.UiPane;
import javafx.scene.layout.BorderPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class UiVideoInterfaceLeft extends UiPane {

    UiVideoDroneStatus uiVideoDroneStatus;

    @Autowired
    public UiVideoInterfaceLeft(
            BorderPane borderPane,
            UiVideoDroneStatus uiVideoDroneStatus) {
        super(borderPane);
        this.uiVideoDroneStatus = uiVideoDroneStatus;
        configureProperties();
        fillInnerParts();
    }

    private void configureProperties() {
        BorderPane borderPane = (BorderPane) this.getRoot();
        borderPane.setPrefHeight(1080-26);
        borderPane.setMaxHeight(1080-26);
        borderPane.setMinHeight(0);
    }

    private void fillInnerParts() {
        BorderPane borderPane = (BorderPane) this.getRoot();
        borderPane.setBottom(uiVideoDroneStatus.getRoot());
    }

}
