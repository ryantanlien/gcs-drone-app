package sg.gov.dsta.thickdemo.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.net.URL;

public class UiButton extends UiElement<Button> {

    private static final String FXML = "UiButton.fxml";
    private String text;

    public UiButton(URL fxmlFileURL) {
        super(fxmlFileURL);
        this.text = "Button";
    }

    public UiButton(String fxmlFileName) {
        super(fxmlFileName);
    }

    @javafx.fxml.FXML
    protected void handleButtonAction(ActionEvent event) {
        this.text = "Button clicked";
    }
}
