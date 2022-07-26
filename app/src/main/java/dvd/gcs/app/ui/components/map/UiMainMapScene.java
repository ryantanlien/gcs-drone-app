package dvd.gcs.app.ui.components.map;

import dvd.gcs.app.ui.api.UiPane;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
@Lazy
public class UiMainMapScene extends UiPane {
    private static final String FXML = "UiMainMapScene.fxml";

    BorderPane borderPane;
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
            BorderPane borderPane,
            UiMapFeed uiMapFeed,
            UiDroneFeedWindow uiDroneFeedWindow,
            UiSettingsWindow uiSettingsWindow,
            UiMapLayersWindow uiMapLayersWindow) {
        super(FXML, stackPane);
        this.borderPane = borderPane;
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
        // Set up the next pane as the next 2 layers
        ////////////////////////////////////////
        // Insert components into 2nd layer
        uiDroneFeedWindow.getRoot().setId("DroneFeedWindow1");
        this.getRoot().getChildren().add(uiDroneFeedWindow.getRoot());
        StackPane.setAlignment(uiDroneFeedWindow.getRoot(), Pos.TOP_RIGHT);

        uiMapLayersWindow.getRoot().setId("MapLayersWindow");
        this.getRoot().getChildren().add(uiMapLayersWindow.getRoot());
        StackPane.setAlignment(uiMapLayersWindow.getRoot(), Pos.BOTTOM_RIGHT);

        ////////////////////////////////////////
        // Set up the settings window as the next layer
        ////////////////////////////////////////
        uiSettingsWindow.getRoot().setId("SettingsWindow1");
        this.getRoot().getChildren().add(uiSettingsWindow.getRoot());
        StackPane.setAlignment(uiSettingsWindow.getRoot(), Pos.TOP_CENTER);

        uiMapFeed.getRoot().prefHeightProperty().bind(this.getRoot().heightProperty());
        uiMapFeed.getRoot().prefWidthProperty().bind(this.getRoot().widthProperty());
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
