package dvd.gcs.app.ui.components.video;

import dvd.gcs.app.ui.api.UiPane;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class UiVideoObjectDetection extends UiPane {

    private final static String FXML = "UiVideoObjectDetection.fxml";
    private final static int VBOX_CHILD_INDEX = 1;


    @Autowired
    public UiVideoObjectDetection(StackPane stackPane) {
        super(FXML, stackPane);
        this.addDetectionLabel("Object 1");
    }

    //Tightly coupled to the view
    private void addDetectionLabel(String labelName) {
        VBox vBox = (VBox) this.getRoot().getChildren().get(VBOX_CHILD_INDEX);
        VBox innerVbox = (VBox) vBox.getChildren().get(VBOX_CHILD_INDEX);

        Label newLabel = new Label(labelName);

        //Configure Detection Label Properties
        newLabel.getStyleClass().add("video-label");
        newLabel.setPrefWidth(250.0);
        newLabel.setAlignment(Pos.CENTER);
        newLabel.setFont(new Font("Arial", 24.0));

        innerVbox.getChildren().add(newLabel);
    }
}
