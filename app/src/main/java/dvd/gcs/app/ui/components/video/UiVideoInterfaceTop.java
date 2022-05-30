package dvd.gcs.app.ui.components.video;

import dvd.gcs.app.ui.api.UiPane;
import javafx.scene.layout.BorderPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class UiVideoInterfaceTop extends UiPane {

    UiBackButton uiBackButton;
    UiVideoLiveIndicator uiVideoLiveIndicator;

    @Autowired
    public UiVideoInterfaceTop(
            BorderPane borderPane,
            UiBackButton uiBackButton,
            UiVideoLiveIndicator uiVideoLiveIndicator) {
        super(borderPane);
        this.uiBackButton = uiBackButton;
        this.uiVideoLiveIndicator = uiVideoLiveIndicator;
        configureProperties();
        fillInnerParts();
        borderPane.getPrefHeight();
    }

    private void configureProperties() {
        BorderPane borderPane = (BorderPane) this.getRoot();
        borderPane.setPrefHeight(26);
        borderPane.setMaxHeight(26);
        borderPane.setPrefWidth(600);
    }

    private void fillInnerParts() {
        BorderPane borderPane = (BorderPane) this.getRoot();
        borderPane.setRight(uiVideoLiveIndicator.getRoot());
        borderPane.setLeft(uiBackButton.getRoot());
    }
}
