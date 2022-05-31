package dvd.gcs.app.ui.components.video;

import dvd.gcs.app.ui.api.UiPane;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Paint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component("UiVideoInterfacePanel")
@Lazy
public class UiVideoInterfacePanel extends UiPane {

    private final UiVideoInterfaceTop uiVideoInterfaceTop;
    private final UiVideoInterfaceRight uiVideoInterfaceRight;

    @Autowired
    public UiVideoInterfacePanel(
            BorderPane borderPane,
            UiVideoInterfaceTop uiVideoInterfaceTop,
            UiVideoInterfaceRight uiVideoInterfaceRight) {
        super(borderPane);
        this.uiVideoInterfaceTop = uiVideoInterfaceTop;
        this.uiVideoInterfaceRight = uiVideoInterfaceRight;
        setProperties();
        fillInnerParts();
    }

    private void setProperties() {
        BorderPane borderPane = (BorderPane) this.getRoot();
        borderPane.setPrefHeight(Double.MAX_VALUE);
        borderPane.setPrefWidth(Double.MAX_VALUE);
        borderPane.setMaxWidth(Double.MAX_VALUE);
        borderPane.setMaxHeight(Double.MAX_VALUE);
        borderPane.setBackground(
                new Background(
                        new BackgroundFill(Paint.valueOf("666666"),
                        new CornerRadii(0),
                        new Insets(0))));
    }

    private void fillInnerParts() {
        BorderPane borderPane = (BorderPane) this.getRoot();
        borderPane.setTop(uiVideoInterfaceTop.getRoot());
        borderPane.setRight(uiVideoInterfaceRight.getRoot());
    }
}
