package dvd.gcs.app.ui.components.map;

import dvd.gcs.app.ui.api.UiButton;
import dvd.gcs.app.ui.api.UiElement;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
@Lazy
public class UiMainMapScene extends UiElement<StackPane> {
    private static final String FXML = "UiMainMapScene.fxml";

    GridPane gridPane;
    UiMapFeed uiMapFeed;
    UiDroneFeedWindow uiDroneFeedWindow;
    UiSettingsWindow uiSettingsWindow;
    UiMapLayersWindow uiMapLayersWindow;

    /**
     * Constructs a default UiMainMapScene, where the wrapped JavaFX StackPane is injected
     * as a dependency by Spring via constructor dependency injection.
     */
    @Autowired
    public UiMainMapScene(
            StackPane stackPane,
            GridPane gridPane,
            UiMapFeed uiMapFeed,
            UiDroneFeedWindow uiDroneFeedWindow,
            UiSettingsWindow uiSettingsWindow,
            UiMapLayersWindow uiMapLayersWindow) {
        super(FXML, stackPane);
        this.gridPane = gridPane;
        this.uiMapFeed = uiMapFeed;
        this.uiDroneFeedWindow = uiDroneFeedWindow;
        this.uiSettingsWindow = uiSettingsWindow;
        this.uiMapLayersWindow = uiMapLayersWindow;
        setUpScene();
    }

    /**
     * Sets up the map scene.
     */
    private void setUpScene() {
        ////////////////////////////////////////
        // Set the map feed as the base layer
        ////////////////////////////////////////
        uiMapFeed.getRoot().setId("MapFeed");
        this.getRoot().getChildren().add(uiMapFeed.getRoot());

        ////////////////////////////////////////
        // Set up the grid pane as the 2nd layer
        ////////////////////////////////////////
        gridPane.setId("Layer2");
        ColumnConstraints column1 = new ColumnConstraints();

        column1.setPercentWidth(75);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(25);
        gridPane.getColumnConstraints().addAll(column1, column2); // each get 50% of width

        this.getRoot().getChildren().add(gridPane);

        // Insert components into 2nd layer
        uiDroneFeedWindow.getRoot().setId("DroneFeedWindow1");
        gridPane.getChildren().add(uiDroneFeedWindow.getRoot());
        GridPane.setConstraints(uiDroneFeedWindow.getRoot(), 2, 0); // column=2 row=0

        uiMapLayersWindow.getRoot().setId("MapLayersWindow");
        gridPane.getChildren().add(uiMapLayersWindow.getRoot());
        GridPane.setConstraints(uiMapLayersWindow.getRoot(), 2, 2);

        ////////////////////////////////////////
        // Set up the settings window as the 3rd layer
        ////////////////////////////////////////
        // TODO: this is for the purposes of testing UI
        uiSettingsWindow.getRoot().setId("SettingsWindow1");
        this.getRoot().getChildren().add(uiSettingsWindow.getRoot());
    }

    // REFERENCE TO A NODE VIA CSS ID
    // Button b = new Button();
    // b.setId("B");
    // Button button = (Button) somePane.lookup("#B");
}
