package dvd.gcs.app.ui.components.map;

import dvd.gcs.app.ui.api.UiElement;
import javafx.scene.Node;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
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
        root.setExpanded(false);
    }
}
