package gov.dsta.thickdemo.ui;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class UiBasePanel extends UiElement<GridPane> {

    private static final String FXML = "UiBasePanel.fxml";

    UiButton uiButton;

    @javafx.fxml.FXML
    private Button buttonPlaceHolder;

    @Autowired
    public UiBasePanel(GridPane gridPane, UiButton uiButton) {
        super(FXML, gridPane);
        this.uiButton = uiButton;
        fillInnerParts();
    }

    void fillInnerParts() {
        this.getRoot().getChildren().add(uiButton.getRoot());
    }
}
