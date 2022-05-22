package dvd.gcs.app;

import dvd.gcs.app.ui.UiApplication;
import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main class that launches the JavaFX application and configures the Spring Boot Container
 */
@SpringBootApplication
public class ThickDemoApplication {

	/**
	 * Launches and sets up the JavaFX application.
	 */
	public static void main(String[] args) {
		Application.launch(UiApplication.class, args);
	}
}