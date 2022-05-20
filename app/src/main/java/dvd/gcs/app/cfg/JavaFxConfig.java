package dvd.gcs.app.cfg;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class JavaFxConfig {

    @Bean
    @Scope("prototype")
    public Button getButton() {
        return new Button();
    }

    @Bean
    @Scope("prototype")
    public GridPane getGridPane() {
        return new GridPane();
    }
}
