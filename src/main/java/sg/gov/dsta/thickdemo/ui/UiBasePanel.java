package sg.gov.dsta.thickdemo.ui;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

public class UiBasePanel extends UiElement<GridPane> {

    private static final String FXML = "UiBasePanel.fxml";

    private UiButton uiButton;

    @javafx.fxml.FXML
    private Button buttonPlaceHolder;

    public UiBasePanel(GridPane gridPane) {
        super(FXML, gridPane);
        fillInnerParts();
    }

    void fillInnerParts() {
        uiButton = new UiButton();
        this.getRoot().getChildren().add(uiButton.getRoot());
    }
}
