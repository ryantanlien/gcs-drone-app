package dvd.gcs.app.ui;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.GridPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * A wrapper class around a JavaFX GridPane, to provide greater extensibility and separation of concerns in UI classes.
 * Styling is provided via a FXML file.
 *
 * This class is annotated with lazy as other components must be registered into the Spring context during runtime
 * before this component is initialized.
 */
@Component
@Lazy
public class UiBasePanel extends UiElement<GridPane> {

    private static final String FXML = "UiBasePanel.fxml";

    /** Constituent UiButton component **/
    UiButton uiButton;

    /** Constituent UiMenuBar component **/
    UiDynamicMenuBar uiResizableMenuBar;

    /** A placeholder for the JavaFXML button **/
    @javafx.fxml.FXML
    private Button buttonPlaceHolder;

    /** A placeholder for the JavaFXML menuBar **/
    @javafx.fxml.FXML
    private MenuBar menuBarPlaceHolder;


    /**
     * Constructs a default UiBasePanel, where the wrapped JavaFX GridPane and constituent UiButton are injected
     * as dependencies by Spring via constructor dependency injection.
     *
     * @param gridPane the wrapped around button obtained through dependency injection.
     * @param uiButton the constituent UiButton.
     */
    @Autowired
    public UiBasePanel(GridPane gridPane, UiButton uiButton, UiDynamicMenuBar uiResizableMenuBar) {
        super(FXML, gridPane);
        this.uiButton = uiButton;
        this.uiResizableMenuBar = uiResizableMenuBar;
        setGridPosition(uiButton, 1, 0);
        setGridPosition(uiResizableMenuBar, 0, 0);
        fillInnerParts();
    }

    /**
     * Fills the JavaFX button placeholder.
     */
    private void fillInnerParts() {
        this.getRoot().getChildren().add(uiButton.getRoot());
        this.getRoot().getChildren().add(uiResizableMenuBar.getRoot());
    }

    /**
     * Sets components within one of the grids of the wrapped GridPane.
     */
    private void setGridPosition(UiElement<? extends Node> uiElement, int row, int col) {
        Node root = uiElement.getRoot();
        GridPane.setRowIndex(root, row);
        GridPane.setColumnIndex(root, col);
    }
}
