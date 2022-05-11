package sg.gov.dsta.thickdemo.ui;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class UiButton extends UiElement<Button> {

    private static final String FXML = "UiButton.fxml";
    private String text;

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
