package dvd.gcs.app.ui.components.map;

import dvd.gcs.app.event.*;
import dvd.gcs.app.message.DroneCommandReplyMessage;
import dvd.gcs.app.luciadlightspeed.LuciadLightspeedService;
import dvd.gcs.app.ui.api.UiElement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.EventListener;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
@Lazy
public class UiSettingsWindow extends UiElement<TitledPane> {
    private static final String FXML = "UiSettingsWindow.fxml";
    private String title = "Settings";
    private final LuciadLightspeedService luciadLightspeedServiceInstance;
    private final ApplicationEventPublisher applicationEventPublisher;

    @FXML
    private TextField droneHeightTextField;
    @FXML
    private TextField droneSpeedTextField;
    @FXML
    private TextField geofenceTextField;
    @FXML
    private Label droneStatus;
    private double droneHeight = 50.0; // TODO: get values from drone
    private double droneSpeed = 15.0;
    private double geofenceRadius = 300.0;

    @Autowired
    public UiSettingsWindow(
            TitledPane titledPane,
            LuciadLightspeedService luciadLightspeedServiceInstance,
            ApplicationEventPublisher applicationEventPublisher) {
        super(FXML, titledPane);
        this.luciadLightspeedServiceInstance = luciadLightspeedServiceInstance;
        TitledPane root = this.getRoot();
        root.setText(this.title);
        root.setExpanded(false);
        droneStatus.setText("Idle");
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @EventListener
    public void handleSetGeofenceEvent(SetGeofenceEvent event) {
        DroneCommandReplyMessage.CommandStatus commandStatus = event.getCommandStatus();
        if (commandStatus.equals(DroneCommandReplyMessage.CommandStatus.COMMAND_SUCCESS)) {

        } else if (commandStatus.equals(DroneCommandReplyMessage.CommandStatus.COMMAND_FAILURE)) {

        } else {
            //Command failed to send
        }
        luciadLightspeedServiceInstance.sendNextMessage();
    }

    @EventListener
    public void handleSetMaxSpeedEvent(SetMaxSpeedEvent event) {
        DroneCommandReplyMessage.CommandStatus commandStatus = event.getCommandStatus();
        if (commandStatus.equals(DroneCommandReplyMessage.CommandStatus.COMMAND_SUCCESS)) {

        } else if (commandStatus.equals(DroneCommandReplyMessage.CommandStatus.COMMAND_FAILURE)) {

        } else {
            //Command failed to send
        }
        luciadLightspeedServiceInstance.sendNextMessage();
    }

    @EventListener
    public void handleSetAltitudeEvent(SetAltitudeEvent event) {
        DroneCommandReplyMessage.CommandStatus commandStatus = event.getCommandStatus();
        if (commandStatus.equals(DroneCommandReplyMessage.CommandStatus.COMMAND_SUCCESS)) {

        } else if (commandStatus.equals(DroneCommandReplyMessage.CommandStatus.COMMAND_FAILURE)) {

        } else {
            //Command failed to send
        }
        luciadLightspeedServiceInstance.sendNextMessage();
    }

    @EventListener
    public void handleStartSearchEvent(StartDroneSearchEvent event) {
        DroneCommandReplyMessage.CommandStatus commandStatus = event.getCommandStatus();
        if (commandStatus.equals(DroneCommandReplyMessage.CommandStatus.COMMAND_SUCCESS)) {
            droneStatus.setText("Searching");
        } else if (commandStatus.equals(DroneCommandReplyMessage.CommandStatus.COMMAND_FAILURE)) {

        } else {
            //Command failed to send
        }
        luciadLightspeedServiceInstance.sendNextMessage();
    }

    @EventListener
    public void handleStopSearchEvent(StopDroneSearchEvent event) {
        DroneCommandReplyMessage.CommandStatus commandStatus = event.getCommandStatus();
        if (commandStatus.equals(DroneCommandReplyMessage.CommandStatus.COMMAND_SUCCESS)) {
            droneStatus.setText("Stopped Search");
        } else if (commandStatus.equals(DroneCommandReplyMessage.CommandStatus.COMMAND_FAILURE)) {

        } else {
            //Command failed to send
        }
        luciadLightspeedServiceInstance.sendNextMessage();
    }

    @EventListener
    public void handleUploadMissionEvent(UploadDroneMissionEvent event) {
        DroneCommandReplyMessage.CommandStatus commandStatus = event.getCommandStatus();
        if (commandStatus.equals(DroneCommandReplyMessage.CommandStatus.COMMAND_SUCCESS)) {

        } else if (commandStatus.equals(DroneCommandReplyMessage.CommandStatus.COMMAND_FAILURE)) {

        } else {
            //Command failed to send
        }
        luciadLightspeedServiceInstance.sendNextMessage();
    }

    @EventListener
    public void handleUpdateDroneSettingsEvent(UpdateDroneSettingsEvent event) {
        // update text fields with new values
        geofenceTextField.setText("" + event.getGeoFenceRadius());
        droneSpeedTextField.setText("" + event.getMaxVelocity());
        droneHeightTextField.setText("" + event.getMaxAltitude());

        luciadLightspeedServiceInstance.sendNextMessage();
    }

    @FXML
    private void markHomeButtonAction(ActionEvent event) {
        // TODO: implement button functionality
    }

    @FXML
    private void markSearchAreaButtonAction(ActionEvent event) {
        // TODO: implement button functionality
    }

    @FXML
    private void markGeofenceButtonAction(ActionEvent event) {
        // TODO: implement button functionality
    }

    @FXML
    private void startSearchButtonAction(ActionEvent event) {
        luciadLightspeedServiceInstance.queueStartDroneSearch();
        luciadLightspeedServiceInstance.sendNextMessage();
    }

    @FXML
    private void stopSearchButtonAction(ActionEvent event) {
        luciadLightspeedServiceInstance.queueStopDroneSearch();
        luciadLightspeedServiceInstance.sendNextMessage();
    }

    @FXML
    private void okayButtonAction(ActionEvent event) {
        applySettings();

        // collapse the settings window
        this.getRoot().setExpanded(false);
    }

    @FXML
    private void applySettingsButtonAction(ActionEvent event) {
        applySettings();
    }

    @FXML
    private void cancelButtonAction(ActionEvent event) {
        resetSettings();

        // collapse the settings window
        this.getRoot().setExpanded(false);
    }

    public boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    private void applySettings() {
        // check height
        if (isNumeric(droneHeightTextField.getText())) {
            double newHeight = Double.parseDouble(droneHeightTextField.getText());
            if (newHeight != this.droneHeight) {
                // has change
                droneHeightTextField.setText("" + droneHeight); // set to old value first, updated later
                this.droneHeight = newHeight;
                luciadLightspeedServiceInstance.queueUpdateDroneHeight(droneHeight);
            }
        } else {
            droneHeightTextField.setText("" + droneHeight);
        }

        // check speed
        if (isNumeric(droneSpeedTextField.getText())) {
            double newSpeed = Double.parseDouble(droneSpeedTextField.getText());
            if (newSpeed != this.droneHeight) {
                // has change
                droneSpeedTextField.setText("" + droneSpeed); // set to old value first, updated later
                this.droneSpeed = newSpeed;
                luciadLightspeedServiceInstance.queueUpdateDroneSpeed(droneSpeed);
            }
        } else {
            droneSpeedTextField.setText("" + droneSpeed);
        }

        // check geofence
        if (isNumeric(geofenceTextField.getText())) {
            double newGeofenceRadius = Double.parseDouble(geofenceTextField.getText());
            if (newGeofenceRadius != this.droneHeight) {
                // has change
                geofenceTextField.setText("" + geofenceRadius); // set to old value first, updated later
                this.geofenceRadius = newGeofenceRadius;
                luciadLightspeedServiceInstance.queueUpdateDroneGeofence(geofenceRadius);
            }
        } else {
            geofenceTextField.setText("" + geofenceRadius);
        }

        // attempt to start sending next message
        luciadLightspeedServiceInstance.sendNextMessage();
    }

    private void resetSettings() {
        droneHeightTextField.setText("" + droneHeight);
        droneSpeedTextField.setText("" + droneSpeed);
        geofenceTextField.setText("" + geofenceRadius);
    }
}
