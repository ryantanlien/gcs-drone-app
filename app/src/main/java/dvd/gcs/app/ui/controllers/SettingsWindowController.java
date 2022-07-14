package dvd.gcs.app.ui.controllers;

import dvd.gcs.app.ui.components.map.UiSettingsWindow;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.springframework.beans.factory.annotation.Autowired;

public class SettingsWindowController {
    @Autowired
    private static UiSettingsWindow uiSettingsWindowInstance; // TODO: may not work?

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
        // TODO: implement button functionality
    }

    @FXML
    private void stopSearchButtonAction(ActionEvent event) {
        // TODO: implement button functionality
    }

    @FXML
    private void okayButtonAction(ActionEvent event) {
        applySettings();

        // collapse the settings window
        uiSettingsWindowInstance.getRoot().setExpanded(false);
    }

    @FXML
    private void applySettingsButtonAction(ActionEvent event) {
        applySettings();
    }

    @FXML
    private void cancelButtonAction(ActionEvent event) {
        // TODO: implement button functionality
    }

    private void applySettings() {
        // TODO: implement functionality
    }
}
