package dvd.gcs.app.ui.components.video;

import dvd.gcs.app.ui.api.UiPane;
import javafx.scene.layout.HBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component("UiStatusBar")
@Lazy
public class UiStatBar extends UiPane {

    private static final String FXML = "UiStatBar.fxml";
    private final Double statusMaxValue;
    private Double statusCurrentValue;
    private Double statusRatio;

    @Autowired
    public UiStatBar(HBox root, Double statusMaxValue) {
        super(FXML, root);
        this.statusMaxValue = statusMaxValue;
    }

    private Double calculateStatusRatio() {
        return statusCurrentValue / statusMaxValue;
    }
}
