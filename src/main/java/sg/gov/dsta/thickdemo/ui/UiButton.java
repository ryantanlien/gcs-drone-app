package sg.gov.dsta.thickdemo.ui;

import javafx.scene.control.Button;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class UiButton extends UiElement<Button> {

    private static final String FXML = "UiButton.fxml";
    private String text;

    //need to make button managed by Spring
    public UiButton() {
        super(FXML, new Button());
        this.text = "Button";
        Button root = this.getRoot();
        root.setText(this.text);
        root.setOnAction(e -> {
            root.setText("Button Clicked");
        });
    }
}
