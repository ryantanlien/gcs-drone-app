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
    private final String title = "Settings";
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
    private double droneHeight = 50.0;
    private double droneHeightSent = 50.0;
    private double droneSpeed = 15.0;
    private double droneSpeedSent = 15.0;
    private double geofenceRadius = 300.0;
    private double geofenceRadiusSent = 300.0;
    private final DroneMessageQueue droneMessageQueue;

    @Autowired
    public UiSettingsWindow(
            TitledPane titledPane,
            LuciadLightspeedService luciadLightspeedService,
            ApplicationEventPublisher applicationEventPublisher) {
        super(FXML, titledPane);
        this.luciadLightspeedService = luciadLightspeedService;
        this.applicationEventPublisher = applicationEventPublisher;
        this.droneMessageQueue = new DroneMessageQueue(applicationEventPublisher);

        TitledPane root = this.getRoot();
        root.setText(this.title);
        root.setExpanded(false);
        updateStatus("Idle");
    }

    @EventListener
    public void handleSetGeofenceEvent(SetGeofenceEvent event) {
        DroneCommandReplyMessage.CommandStatus commandStatus = event.getCommandStatus();
        if (commandStatus.equals(DroneCommandReplyMessage.CommandStatus.COMMAND_SUCCESS)) {
            geofenceRadius = geofenceRadiusSent;
            geofenceTextField.setText("" + geofenceRadius);
        } else if (commandStatus.equals(DroneCommandReplyMessage.CommandStatus.COMMAND_FAILURE)) {
            //Command sent but failed, reset values
            geofenceRadiusSent = geofenceRadius;
        } else {
            //Command failed to send, reset values
            geofenceRadiusSent = geofenceRadius;
        }
        droneMessageQueue.sendNextMessage();
    }

    @EventListener
    public void handleSetMaxSpeedEvent(SetMaxSpeedEvent event) {
        DroneCommandReplyMessage.CommandStatus commandStatus = event.getCommandStatus();
        if (commandStatus.equals(DroneCommandReplyMessage.CommandStatus.COMMAND_SUCCESS)) {
            droneSpeed = droneSpeedSent;
            droneSpeedTextField.setText("" + droneSpeed);
        } else if (commandStatus.equals(DroneCommandReplyMessage.CommandStatus.COMMAND_FAILURE)) {
            //Command sent but failed, reset values
            droneSpeedSent = droneSpeed;
        } else {
            //Command failed to send, reset values
            droneSpeedSent = droneSpeed;
        }
        droneMessageQueue.sendNextMessage();
    }

    @EventListener
    public void handleSetAltitudeEvent(SetAltitudeEvent event) {
        DroneCommandReplyMessage.CommandStatus commandStatus = event.getCommandStatus();
        if (commandStatus.equals(DroneCommandReplyMessage.CommandStatus.COMMAND_SUCCESS)) {
            droneHeight = droneHeightSent;
            droneHeightTextField.setText("" + droneHeight);
        } else if (commandStatus.equals(DroneCommandReplyMessage.CommandStatus.COMMAND_FAILURE)) {
            //Command sent but failed, reset values
            droneHeightSent = droneHeight;
        } else {
            //Command failed to send, reset values
            droneHeightSent = droneHeight;
        }
        droneMessageQueue.sendNextMessage();
    }

    @EventListener
    public void handleStartSearchEvent(StartDroneSearchEvent event) {
        DroneCommandReplyMessage.CommandStatus commandStatus = event.getCommandStatus();
        if (commandStatus.equals(DroneCommandReplyMessage.CommandStatus.COMMAND_SUCCESS)) {
            updateStatus("Searching");
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
            updateStatus("Stopped Search");
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
            updateStatus("Uploaded Mission!");
        } else if (commandStatus.equals(DroneCommandReplyMessage.CommandStatus.COMMAND_FAILURE)) {

        } else {
            //Command failed to send
        }
        droneMessageQueue.sendNextMessage();
    }

    @EventListener
    public void handleUpdateDroneSettingsEvent(UpdateDroneSettingsEvent event) {
        // update values
        this.droneHeight = event.getMaxAltitude();
        this.droneSpeed = event.getMaxVelocity();
        this.geofenceRadius = event.getGeoFenceRadius();

        // reset values tracking sent values
        this.droneHeightSent = droneHeight;
        this.droneSpeedSent = droneSpeed;
        this.geofenceRadiusSent = geofenceRadius;
    }

    @FXML
    private void markHomeButtonAction(ActionEvent event) {
        // TODO: implement button functionality
    }

    @FXML
    private void markSearchAreaButtonAction(ActionEvent event) {
        luciadLightspeedService.drawNewSearchArea();
    }

    @FXML
    private void saveSearchAreaButtonAction(ActionEvent event) {
        double minX = luciadLightspeedService.getSearchAreaMinX();
        double minY = luciadLightspeedService.getSearchAreaMinY();
        double maxX = luciadLightspeedService.getSearchAreaMaxX();
        double maxY = luciadLightspeedService.getSearchAreaMaxY();

        if (minX == -1) {
            // search area not drawn
            return;
        }

        // TODO: check if values are correct
        applicationEventPublisher.publishEvent(new BuildMissionEvent(
                this,
                new MapPoint(minY, minX),
                new MapPoint(maxY, maxX),
                MissionWaypointBuilder.SearchPatternType.HORIZONTAL_LADDER));

        droneMessageQueue.queueSaveSearchArea();
        droneMessageQueue.sendNextMessage();
    }

    @FXML
    private void markGeofenceButtonAction(ActionEvent event) {
        // TODO: implement button functionality
    }

    @FXML
    private void toggleChaseButtonAction(ActionEvent event) {
        // TODO: IMPLEMENT
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

    private void updateStatus(String newStatus) {
        droneStatus.setText(newStatus);
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
                droneHeightSent = newHeight;
                droneHeightTextField.setText("" + droneHeight);
                droneMessageQueue.queueUpdateDroneHeight(newHeight);
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
                droneSpeedSent = newSpeed;
                droneSpeedTextField.setText("" + droneSpeed);
                droneMessageQueue.queueUpdateDroneSpeed(newSpeed);
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
                geofenceRadiusSent = newGeofenceRadius;
                geofenceTextField.setText("" + geofenceRadius);
                droneMessageQueue.queueUpdateDroneGeofence(newGeofenceRadius);
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
