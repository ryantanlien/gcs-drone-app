package dvd.gcs.app.ui.components.map;

import dvd.gcs.app.ui.api.UiElement;
import javafx.scene.Node;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class UiSettingsWindow extends UiElement<TitledPane> {
    private static final String FXML = "UiSettingsWindow.fxml";

    private String title;
    private Node content;
    private Pane dummyPane;

    @Autowired
    public UiSettingsWindow(TitledPane titledPane) {
        super(FXML, titledPane);
        this.title = "Settings";
        TitledPane root = this.getRoot();
        root.setText(this.title);
        this.content = this.getRoot().getContent();
        Pane pane = new Pane();
        pane.setMaxHeight(0);
        dummyPane = pane;
    }
}
