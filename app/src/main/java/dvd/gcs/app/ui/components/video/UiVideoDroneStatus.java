package dvd.gcs.app.ui.components.video;

import dvd.gcs.app.event.UpdateDroneStatusEvent;
import dvd.gcs.app.ui.api.UiPane;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class UiVideoDroneStatus extends UiPane implements ApplicationListener<UpdateDroneStatusEvent> {

    private final static String FXML = "UiVideoDroneStatus.fxml";

    @Autowired
    public UiVideoDroneStatus(
            StackPane stackPane) {
        super(FXML, stackPane);
    }

    private VBox getInnerVbox() {
        return (VBox) this.getRoot().getChildren().get(1);
    }

    private void updateDroneStatus(String model, String callSign) {
        Platform.runLater(() -> {
            VBox vBox = this.getInnerVbox();
            Label typeLabel = (Label) vBox.getChildren().get(0);
            Label statusLabel = (Label) vBox.getChildren().get(1);
            Label callSignLabel = (Label) vBox.getChildren().get(2);
            typeLabel.textProperty().setValue("Type: " + model);
            callSignLabel.textProperty().setValue("Callsign: " + callSign);
        });
    }

    @Override
    public void onApplicationEvent(UpdateDroneStatusEvent event) {
        this.updateDroneStatus(event.getDroneModel(), event.getDroneCallSign());
    }
}
