package dvd.gcs.app.ui.components.map;

import dvd.gcs.app.ui.api.UiElement;
import javafx.scene.control.TitledPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class UiMapLayersWindow extends UiElement<TitledPane> {
    private static final String FXML = "UiMapLayersWindow.fxml";

    private String title;

    @Autowired
    public UiMapLayersWindow(TitledPane titledPane) {
        super(FXML, titledPane);
        this.title = "Layers";
        TitledPane root = this.getRoot();
        root.setText(this.title);
    }
}
