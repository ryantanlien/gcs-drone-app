package dvd.gcs.app.ui.components.map;

import dvd.gcs.app.event.*;
import dvd.gcs.app.message.DroneCommandReplyMessage;
import dvd.gcs.app.ui.api.UiElement;
import javafx.scene.control.TitledPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class UiSettingsWindow extends UiElement<TitledPane> {
    private static final String FXML = "UiSettingsWindow.fxml";

    private String title;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public UiSettingsWindow(TitledPane titledPane, ApplicationEventPublisher applicationEventPublisher) {
        super(FXML, titledPane);
        this.title = "Settings";
        TitledPane root = this.getRoot();
        root.setText(this.title);
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @EventListener
    public void handleSetGeofenceEvent(SetGeofenceEvent event) {
        DroneCommandReplyMessage.CommandStatus commandStatus = event.getCommandStatus();
        if (commandStatus.equals(DroneCommandReplyMessage.CommandStatus.COMMAND_SUCCESS)) {

        } else {

        }
    }

    @EventListener
    public void handleSetMaxSpeedEvent(SetMaxSpeedEvent event) {
        DroneCommandReplyMessage.CommandStatus commandStatus = event.getCommandStatus();
        if (commandStatus.equals(DroneCommandReplyMessage.CommandStatus.COMMAND_SUCCESS)) {

        } else {

        }
    }

    @EventListener
    public void handleSetAltitudeEvent(SetAltitudeEvent event) {
        DroneCommandReplyMessage.CommandStatus commandStatus = event.getCommandStatus();
        if (commandStatus.equals(DroneCommandReplyMessage.CommandStatus.COMMAND_SUCCESS)) {

        } else {

        }
    }

    @EventListener
    public void handleStartSearchEvent(StartDroneSearchEvent event) {
        DroneCommandReplyMessage.CommandStatus commandStatus = event.getCommandStatus();
        if (commandStatus.equals(DroneCommandReplyMessage.CommandStatus.COMMAND_SUCCESS)) {

        } else {

        }
    }

    @EventListener
    public void handleStopSearchEvent(StopDroneSearchEvent event) {
        DroneCommandReplyMessage.CommandStatus commandStatus = event.getCommandStatus();
        if (commandStatus.equals(DroneCommandReplyMessage.CommandStatus.COMMAND_SUCCESS)) {

        } else {

        }
    }

    @EventListener
    public void handleUpdateDroneSettingsEvent(UpdateDroneSettingsEvent event) {
        event.getGeoFenceRadius();
        event.getMaxVelocity();
        event.getMaxAltitude();
    }
}
