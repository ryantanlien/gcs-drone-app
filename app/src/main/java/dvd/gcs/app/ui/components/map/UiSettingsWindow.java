package dvd.gcs.app.ui.components.map;

import dvd.gcs.app.luciadlightspeed.LuciadLightspeedService;
import dvd.gcs.app.ui.api.UiElement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
@Lazy
public class UiSettingsWindow extends UiElement<TitledPane> {
    private static final String FXML = "UiSettingsWindow.fxml";
    private String title = "Settings";
    private final LuciadLightspeedService luciadLightspeedServiceInstance;

    @FXML
    private TextField droneHeightTextField;
    @FXML
    private TextField droneSpeedTextField;
    @FXML
    private TextField geofenceTextField;
    private double droneHeight = 50.0; // TODO: get values from drone
    private double droneSpeed = 15.0;
    private double geofenceRadius = 300.0;

    @Autowired
    public UiSettingsWindow(
            TitledPane titledPane,
            LuciadLightspeedService luciadLightspeedServiceInstance) { // TODO: may not work?
        super(FXML, titledPane);
        this.luciadLightspeedServiceInstance = luciadLightspeedServiceInstance;
        TitledPane root = this.getRoot();
        root.setText(this.title);
        root.setExpanded(false);
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
        luciadLightspeedServiceInstance.startDroneSearch();
    }

    @FXML
    private void stopSearchButtonAction(ActionEvent event) {
        luciadLightspeedServiceInstance.stopDroneSearch();
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
        if (isNumeric(droneHeightTextField.getText())) {
            this.droneHeight = Double.parseDouble(droneHeightTextField.getText());
        } else {
            droneHeightTextField.setText("" + droneHeight);
        }

        if (isNumeric(droneSpeedTextField.getText())) {
            this.droneSpeed = Double.parseDouble(droneSpeedTextField.getText());
        } else {
            droneSpeedTextField.setText("" + droneSpeed);
        }

        if (isNumeric(geofenceTextField.getText())) {
            this.geofenceRadius = Double.parseDouble(geofenceTextField.getText());
        } else {
            geofenceTextField.setText("" + geofenceRadius);
        }

        luciadLightspeedServiceInstance.updateDrone(droneSpeed, droneHeight, geofenceRadius);
    }

    private void resetSettings() {
        droneHeightTextField.setText("" + droneHeight);
        droneSpeedTextField.setText("" + droneSpeed);
        geofenceTextField.setText("" + geofenceRadius);
    }
}
