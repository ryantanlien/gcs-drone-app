package dvd.gcs.app.ui.components.map;

import dvd.gcs.app.event.*;
import dvd.gcs.app.luciadlightspeed.DroneMessageQueue;
import dvd.gcs.app.message.DroneCommandReplyMessage;
import dvd.gcs.app.luciadlightspeed.LuciadLightspeedService;
import dvd.gcs.app.mission.MapPoint;
import dvd.gcs.app.mission.MissionWaypointBuilder;
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
    private final LuciadLightspeedService luciadLightspeedService;
    private final ApplicationEventPublisher applicationEventPublisher; // Springboot event publisher

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
    private DroneMessageQueue droneMessageQueue;

    @Autowired
    public UiSettingsWindow(
            TitledPane titledPane,
            LuciadLightspeedService luciadLightspeedServiceInstance,
            ApplicationEventPublisher applicationEventPublisher) { // TODO: may not work?

        super(FXML, titledPane);
        this.luciadLightspeedService = luciadLightspeedServiceInstance;
        TitledPane root = this.getRoot();
        root.setText(this.title);
        root.setExpanded(false);
        droneStatus.setText("Idle");
        this.applicationEventPublisher = applicationEventPublisher;
        this.droneMessageQueue = new DroneMessageQueue(applicationEventPublisher);
    }

    @EventListener
    public void handleSetGeofenceEvent(SetGeofenceEvent event) {
        DroneCommandReplyMessage.CommandStatus commandStatus = event.getCommandStatus();
        if (commandStatus.equals(DroneCommandReplyMessage.CommandStatus.COMMAND_SUCCESS)) {

        } else if (commandStatus.equals(DroneCommandReplyMessage.CommandStatus.COMMAND_FAILURE)) {

        } else {
            //Command failed to send
        }
        droneMessageQueue.sendNextMessage();
    }

    @EventListener
    public void handleSetMaxSpeedEvent(SetMaxSpeedEvent event) {
        DroneCommandReplyMessage.CommandStatus commandStatus = event.getCommandStatus();
        if (commandStatus.equals(DroneCommandReplyMessage.CommandStatus.COMMAND_SUCCESS)) {

        } else if (commandStatus.equals(DroneCommandReplyMessage.CommandStatus.COMMAND_FAILURE)) {

        } else {
            //Command failed to send
        }
        droneMessageQueue.sendNextMessage();
    }

    @EventListener
    public void handleSetAltitudeEvent(SetAltitudeEvent event) {
        DroneCommandReplyMessage.CommandStatus commandStatus = event.getCommandStatus();
        if (commandStatus.equals(DroneCommandReplyMessage.CommandStatus.COMMAND_SUCCESS)) {

        } else if (commandStatus.equals(DroneCommandReplyMessage.CommandStatus.COMMAND_FAILURE)) {

        } else {
            //Command failed to send
        }
        droneMessageQueue.sendNextMessage();
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
        droneMessageQueue.sendNextMessage();
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
        droneMessageQueue.sendNextMessage();
    }

    @EventListener
    public void handleUploadMissionEvent(UploadDroneMissionEvent event) {
        DroneCommandReplyMessage.CommandStatus commandStatus = event.getCommandStatus();
        if (commandStatus.equals(DroneCommandReplyMessage.CommandStatus.COMMAND_SUCCESS)) {

        } else if (commandStatus.equals(DroneCommandReplyMessage.CommandStatus.COMMAND_FAILURE)) {

        } else {
            //Command failed to send
        }
        droneMessageQueue.sendNextMessage();
    }

    @EventListener
    public void handleUpdateDroneSettingsEvent(UpdateDroneSettingsEvent event) {
        // update text fields with new values
        this.droneHeight = event.getMaxAltitude();
        this.droneSpeed = event.getMaxVelocity();
        this.geofenceRadius = event.getGeoFenceRadius();
        geofenceTextField.setText("" + geofenceRadius);
        droneSpeedTextField.setText("" + droneSpeed);
        droneHeightTextField.setText("" + droneHeight);

        // droneMessageQueue.sendNextMessage();
        // TODO: is this an extra drone reply on top of the other replies like SetYYYYEvents? if so then this event
        // TODO: should not send next message as it will clog up socket.
    }

    @FXML
    private void markHomeButtonAction(ActionEvent event) {
        // TODO: implement button functionality
    }

    @FXML
    private void markSearchAreaButtonAction(ActionEvent event) {
        droneMessageQueue.queueMarkSearchArea();
        applicationEventPublisher.publishEvent(new BuildMissionEvent(
                this,
                new MapPoint(0.0, 0.0),
                new MapPoint(0.0, 0.0),
                MissionWaypointBuilder.SearchPatternType.HORIZONTAL_LADDER)); // TODO: input real values

        droneMessageQueue.sendNextMessage();
    }

    @FXML
    private void markGeofenceButtonAction(ActionEvent event) {
        // TODO: implement button functionality
    }

    @FXML
    private void launchDroneButtonAction(ActionEvent event) {
        droneMessageQueue.queueLaunchDrone();
        droneMessageQueue.sendNextMessage();
    }

    @FXML
    private void landDroneButtonAction(ActionEvent event) {
        droneMessageQueue.queueLandDrone();
        droneMessageQueue.sendNextMessage();
    }

    @FXML
    private void startSearchButtonAction(ActionEvent event) {
        droneMessageQueue.queueStartDroneSearch();
        droneMessageQueue.sendNextMessage();
    }

    @FXML
    private void stopSearchButtonAction(ActionEvent event) {
        droneMessageQueue.queueStopDroneSearch();
        droneMessageQueue.sendNextMessage();
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
                // set to old value first, updated later through handling drone reply
                droneHeightTextField.setText("" + droneHeight);
                droneMessageQueue.queueUpdateDroneHeight(droneHeight);
            }
        } else {
            droneHeightTextField.setText("" + droneHeight);
        }

        // check speed
        if (isNumeric(droneSpeedTextField.getText())) {
            double newSpeed = Double.parseDouble(droneSpeedTextField.getText());
            if (newSpeed != this.droneHeight) {
                // has change
                // set to old value first, updated later through handling drone reply
                droneSpeedTextField.setText("" + droneSpeed);
                droneMessageQueue.queueUpdateDroneSpeed(droneSpeed);
            }
        } else {
            droneSpeedTextField.setText("" + droneSpeed);
        }

        // check geofence
        if (isNumeric(geofenceTextField.getText())) {
            double newGeofenceRadius = Double.parseDouble(geofenceTextField.getText());
            if (newGeofenceRadius != this.droneHeight) {
                // has change
                // set to old value first, updated later through handling drone reply
                geofenceTextField.setText("" + geofenceRadius);
                droneMessageQueue.queueUpdateDroneGeofence(geofenceRadius);
            }
        } else {
            geofenceTextField.setText("" + geofenceRadius);
        }

        // attempt to start sending next message
        droneMessageQueue.sendNextMessage();
    }

    private void resetSettings() {
        droneHeightTextField.setText("" + droneHeight);
        droneSpeedTextField.setText("" + droneSpeed);
        geofenceTextField.setText("" + geofenceRadius);
    }
}
