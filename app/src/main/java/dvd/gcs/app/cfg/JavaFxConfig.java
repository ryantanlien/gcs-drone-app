package dvd.gcs.app.cfg;

import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * A Spring Configuration class that declares instances JavaFX classes as managed by Spring,
 * allowing Spring to instantiate these classes in its application context.
 */
@Configuration
public class JavaFxConfig {
    /**
     * Defines a JavaFX button to be managed by Spring.
     * It has a prototype scope thus multiple separate instances of the class can be instantiated.
     *
     * @return a JavaFX Button managed by Spring.
     */
    @Bean
    @Scope("prototype")
    public Button getButton() {
        return new Button();
    }

    /**
     * Defines a JavaFX GridPane to be managed by Spring.
     * It has a prototype scope thus multiple separate instances of the class can be instantiated.
     *
     * @return a JavaFX GridPane managed by Spring.
     */
    @Bean
    @Scope("prototype")
    public GridPane getGridPane() {
        return new GridPane();
    }

    /**
     * Defines a JavaFX MenuBar to be managed by Spring.
     * It has a prototype scope thus multiple separate instances of the class can be instantiated.
     *
     * @return a JavaFX MenuBar managed by Spring.
     */
    @Bean
    @Scope("prototype")
    public MenuBar getMenuBar() {
       return new MenuBar();
    }
  
    /**
     * Defines a JavaFX TitledPane to be managed by Spring.
     * It has a prototype scope thus multiple separate instances of the class can be instantiated.
     *
     * @return a JavaFX TitledPane managed by Spring.
     */
    @Bean
    @Scope("prototype")
    public TitledPane getTitledPane() {
        return new TitledPane();
    }

    /**
     * Defines a JavaFX Pane to be managed by Spring.
     * It has a prototype scope thus multiple separate instances of the class can be instantiated.
     *
     * @return a JavaFX Pane managed by Spring.
     */
    @Bean("Pane")
    @Scope("prototype")
    public Pane getPane() {
        return new Pane();
    }

    /**
     * Defines a JavaFX VBox to be managed by Spring.
     * It has a prototype scope thus multiple separate instances of the class can be instantiated.
     *
     * @return a JavaFX VBox managed by Spring.
     */
    @Bean("VBox")
    @Scope("prototype")
    public VBox getVBox() {
        return new VBox();
    }

    /**
     * Defines a JavaFX HBox to be managed by Spring.
     * It has a prototype scope thus multiple separate instances of the class can be instantiated.
     *
     * @return a JavaFX HBox managed by Spring.
     */
    @Bean("HBox")
    @Scope("prototype")
    public HBox getHBox() {
        return new HBox();
    }


    /**
     * Defines a JavaFX StackPane to be managed by Spring.
     * It has a prototype scope thus multiple separate instances of the class can be instantiated.
     *
     * @return a JavaFX StackPane managed by Spring.
     */
    @Bean("StackPane")
    @Scope("prototype")
    public StackPane getStackPane() {
        return new StackPane();
    }

    /**
     * Defines a JavaFX AnchorPane to be managed by Spring.
     * It has a prototype scope thus multiple separate instances of the class can be instantiated.
     *
     * @return a JavaFX AnchorPane managed by Spring.
     */
    @Bean("AnchorPane")
    @Scope("prototype")
    public AnchorPane getAnchorPane() {
        return new AnchorPane();
    }

    /**
     * Defines a JavaFX BorderPane to be managed by Spring.
     * It has a prototype scope thus multiple separate instances of the class can be instantiated.
     *
     * @return a JavaFX BorderPane managed by Spring.
     */
    @Bean("BorderPane")
    @Scope("prototype")
    public BorderPane getBorderPane() {
        return new BorderPane();
    }
}
