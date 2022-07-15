package dvd.gcs.app.ui.components.map;

import dvd.gcs.app.ui.api.UiElement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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

    @Autowired
    public UiSettingsWindow(TitledPane titledPane) {
        super(FXML, titledPane);
        TitledPane root = this.getRoot();
        root.setText(this.title);
        root.setExpanded(false);
    }

    @FXML
    private void markHomeButtonAction(ActionEvent event) {
        // TODO: implement button functionality
    }
}
