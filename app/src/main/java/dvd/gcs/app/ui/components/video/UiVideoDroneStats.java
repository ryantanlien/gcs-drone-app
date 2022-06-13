package dvd.gcs.app.ui.components.video;

import dvd.gcs.app.ui.api.UiPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class UiVideoDroneStats extends UiPane {

    private final static String FXML = "UiVideoDroneStats.fxml";
    private final UiStatBar batteryStatBar;
    private final UiStatBar heightStatBar;
    private final UiStatBar speedStatBar;

    @Autowired
    public UiVideoDroneStats(
            StackPane stackPane,
            @Qualifier("BatteryStatBar") UiStatBar batteryStatBar,
            @Qualifier("HeightStatBar") UiStatBar heightStatBar,
            @Qualifier("SpeedStatBar") UiStatBar speedStatBar) {
        super(FXML, stackPane);
        this.batteryStatBar = batteryStatBar;
        this.heightStatBar = heightStatBar;
        this.speedStatBar = speedStatBar;
        this.fillInnerVbox();
    }

    private VBox getInnerVbox() {
        return (VBox) this.getRoot().getChildren().get(1);
    }

    private void fillInnerVbox() {
        VBox vBox = this.getInnerVbox();
        vBox.getChildren().add(this.batteryStatBar.getRoot());
        vBox.getChildren().add(this.heightStatBar.getRoot());
        vBox.getChildren().add((this.speedStatBar.getRoot()));
    }
}
