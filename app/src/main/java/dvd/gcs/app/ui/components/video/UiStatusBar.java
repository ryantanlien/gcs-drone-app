package dvd.gcs.app.ui.components.video;

import dvd.gcs.app.ui.api.UiPane;
import javafx.scene.layout.HBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component("UiStatusBar")
@Lazy
public class UiStatusBar extends UiPane {

    private static final String FXML = "UiStatusBar.fxml";
    private Double statusMaxValue;
    private Double statusCurrentValue;

    @Autowired
    public UiStatusBar(HBox root) {
        super(FXML, root);
    }
}
