package dvd.gcs.app;

import dvd.gcs.app.ui.UiApplication;
import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ThickDemoApplication {

	public static void main(String[] args) {
		Application.launch(UiApplication.class, args);
	}
}