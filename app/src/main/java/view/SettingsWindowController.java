package view;

import dvd.gcs.app.luciadlightspeed.LuciadLightspeedService;
import dvd.gcs.app.ui.components.map.UiSettingsWindow;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.NumberFormat;

public class SettingsWindowController {
    @FXML
    private TextField droneHeightTextField;
    @FXML
    private TextField droneSpeedTextField;
    @FXML
    private TextField geofenceTextField;
    @FXML
    private Label droneStatus;

    @FXML
    private void markHomeButtonAction(ActionEvent event) {
        // implemented in actual controller class
    }

    @FXML
    private void markSearchAreaButtonAction(ActionEvent event) {
        // implemented in actual controller class
    }

    @FXML
    private void markGeofenceButtonAction(ActionEvent event) {
        // implemented in actual controller class
    }

    @FXML
    private void startSearchButtonAction(ActionEvent event) {
        // implemented in actual controller class
    }

    @FXML
    private void stopSearchButtonAction(ActionEvent event) {
        // implemented in actual controller class
    }

    @FXML
    private void okayButtonAction(ActionEvent event) {
        // implemented in actual controller class
    }

    @FXML
    private void applySettingsButtonAction(ActionEvent event) {
        // implemented in actual controller class
    }

    @FXML
    private void cancelButtonAction(ActionEvent event) {
        // implemented in actual controller class
    }
}
