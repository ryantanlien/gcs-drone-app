package dvd.gcs.app.ui.components;

import dvd.gcs.app.ui.api.UiElement;
import javafx.scene.control.TitledPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class UiSettingsWindow extends UiElement<TitledPane> {
    private static final String FXML = "UiSettingsWindow.fxml";

    private String title;

    @Autowired
    public UiSettingsWindow(TitledPane titledPane) {
        super(FXML, titledPane);
        this.title = "Settings";
        TitledPane root = this.getRoot();
        root.setText(this.title);
    }
}
