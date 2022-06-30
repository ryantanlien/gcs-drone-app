package dvd.gcs.app.ui.components.map;

import dvd.gcs.app.ui.api.UiElement;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.layout.*;
import org.springframework.beans.factory.annotation.Autowired;
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

        int COLS = 2;
        int ROWS = 3;

        //Construct column constraints to resize horizontally
        ObservableList<ColumnConstraints> colConstraints = gridPane.getColumnConstraints();
        colConstraints.clear();
        for(int col = 0; col < COLS; col++){
            ColumnConstraints c = new ColumnConstraints();
            c.setHalignment(HPos.CENTER);
            c.setHgrow(Priority.ALWAYS);
            colConstraints.add(c);
        }

        //Construct row constraints to resize vertically
        ObservableList<RowConstraints> rowConstraints = gridPane.getRowConstraints();
        rowConstraints.clear();
        for(int row = 0; row < ROWS; row++){
            RowConstraints c = new RowConstraints();
            c.setValignment(VPos.CENTER);
            c.setVgrow(Priority.ALWAYS);
            rowConstraints.add(c);
        }

        ColumnConstraints column0 = colConstraints.get(0);
        column0.setPercentWidth(70);

        ColumnConstraints column1 = colConstraints.get(1);
        column1.setPercentWidth(30);

        RowConstraints row0 = rowConstraints.get(0);
        row0.setPercentHeight(33);

        RowConstraints row1 = rowConstraints.get(1);
        row1.setPercentHeight(33);

        RowConstraints row2 = rowConstraints.get(2);
        row2.setPercentHeight(33);

//        gridPane.getColumnConstraints().addAll(column0, column1);
//        gridPane.getRowConstraints().addAll(row0, row1, row2);

        // Insert components into 2nd layer
        uiDroneFeedWindow.getRoot().setId("DroneFeedWindow1");
        gridPane.getChildren().add(uiDroneFeedWindow.getRoot());
        GridPane.setConstraints(uiDroneFeedWindow.getRoot(), 1, 0); // column=1 row=0

        uiMapLayersWindow.getRoot().setId("MapLayersWindow");
        gridPane.getChildren().add(uiMapLayersWindow.getRoot());
        GridPane.setConstraints(uiMapLayersWindow.getRoot(), 1, 2);

        this.getRoot().getChildren().add(gridPane);
        StackPane.setAlignment(gridPane, Pos.CENTER);
        ////////////////////////////////////////
        // Set up the settings window as the 3rd layer
        ////////////////////////////////////////
        // TODO: this is for the purposes of testing UI
        uiSettingsWindow.getRoot().setId("SettingsWindow1");
        this.getRoot().getChildren().add(uiSettingsWindow.getRoot());
        StackPane.setAlignment(uiSettingsWindow.getRoot(), Pos.TOP_CENTER);
    }

    // REFERENCE TO A NODE VIA CSS ID
    // Button b = new Button();
    // b.setId("B");
    // Button button = (Button) somePane.lookup("#B");

//    @Override
//    public UiPane swap() {
//        return beanFactory.getBeanProvider(UiBasePanel.class).getIfAvailable();
//    }
}
