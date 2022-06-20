package dvd.gcs.app.ui.components.video;

import dvd.gcs.app.event.UpdateDroneStatEvent;
import dvd.gcs.app.ui.api.UiPane;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class UiVideoDroneStats extends UiPane implements ApplicationListener<UpdateDroneStatEvent> {

    private final static String FXML = "UiVideoDroneStats.fxml";
    private final UiStatBar batteryStatBar;
    private final UiStatBar heightStatBar;
    private final UiStatBar speedStatBar;

    private final Label longitudeLabel;
    private final Label latitudeLabel;

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
        this.longitudeLabel = this.getLongLabel();
        this.latitudeLabel = this.getLatLabel();
    }

    private VBox getInnerVbox() {
        return (VBox) this.getRoot().getChildren().get(1);
    }

    private Label getLongLabel() {
        VBox embeddedVbox = (VBox) this.getInnerVbox().getChildren().get(2);
        Label longLabel = (Label) embeddedVbox.getChildren().get(1);
        return longLabel;
    }

    private Label getLatLabel() {
        VBox embeddedVbox = (VBox) this.getInnerVbox().getChildren().get(2);
        Label latLabel = (Label) embeddedVbox.getChildren().get(2);
        return latLabel;
    }

    private void fillInnerVbox() {
        VBox vBox = this.getInnerVbox();
        vBox.getChildren().add(this.batteryStatBar.getRoot());
        vBox.getChildren().add(this.heightStatBar.getRoot());
        vBox.getChildren().add((this.speedStatBar.getRoot()));
    }

    private void updateDroneStats(Double batteryPercent, Double height, Double speed, Double longitude, Double latitude) {
        Platform.runLater(() -> {
            this.batteryStatBar.updateStatus(batteryPercent);
            this.heightStatBar.updateStatus(height);
            this.speedStatBar.updateStatus(speed);
            this.longitudeLabel.textProperty().setValue("Lon: " + longitude);
            this.latitudeLabel.textProperty().setValue("Lat: " + latitude);
        });
    }

    @Override
    public void onApplicationEvent(UpdateDroneStatEvent event) {
        this.updateDroneStats(
                event.getBatteryPercent(),
                event.getAltitude(),
                event.getVelocity(),
                event.getLongitude(),
                event.getLatitude());
    }
}
