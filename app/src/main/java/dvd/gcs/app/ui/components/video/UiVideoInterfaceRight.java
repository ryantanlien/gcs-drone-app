package dvd.gcs.app.ui.components.video;

import dvd.gcs.app.ui.api.UiPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;


@Component
@Lazy
public class UiVideoInterfaceRight extends UiPane {

    UiVideoDroneStats uiVideoDroneStats;
    UiVideoObjectDetection uiVideoObjectDetection;

    @Autowired
    public UiVideoInterfaceRight(
           BorderPane borderPane,
           UiVideoDroneStats uiVideoDroneStats,
           UiVideoObjectDetection uiVideoObjectDetection) {
        super(borderPane);
        this.uiVideoDroneStats = uiVideoDroneStats;
        this.uiVideoObjectDetection = uiVideoObjectDetection;
        fillInnerParts();
        configureProperties();
    }

    private void configureProperties() {
        BorderPane borderPane = (BorderPane) this.getRoot();
        borderPane.setPadding(new Insets(100, 0, 0, 0));
        borderPane.setPrefWidth(250.0);
        BorderPane.setAlignment(uiVideoObjectDetection.getRoot(), Pos.TOP_RIGHT);
        borderPane.setMargin(uiVideoDroneStats.getRoot(), new Insets(0, 0, 50, 0));
    }

    private void fillInnerParts() {
        BorderPane borderPane = (BorderPane) this.getRoot();
        borderPane.setTop(uiVideoDroneStats.getRoot());
        borderPane.setBottom(uiVideoObjectDetection.getRoot());
    }
}
