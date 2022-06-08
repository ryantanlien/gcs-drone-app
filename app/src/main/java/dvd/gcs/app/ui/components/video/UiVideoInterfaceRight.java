package dvd.gcs.app.ui.components.video;

import dvd.gcs.app.ui.api.UiPane;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class UiVideoInterfaceRight extends UiPane {

    UiVideoDroneStats uiVideoDroneStats;

    //Convert this to a BorderPane and not a VBox
    //Then set the top margin of the BorderPane
    @Autowired
    public UiVideoInterfaceRight(
           VBox vBox,
           UiVideoDroneStats uiVideoDroneStats) {
        super(vBox);
        this.uiVideoDroneStats = uiVideoDroneStats;
        fillInnerParts();
    }

    private void fillInnerParts() {
        this.getRoot().getChildren().add(uiVideoDroneStats.getRoot());
    }
}
