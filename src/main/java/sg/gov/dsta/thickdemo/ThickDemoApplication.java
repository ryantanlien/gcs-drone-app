package sg.gov.dsta.thickdemo;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import sg.gov.dsta.thickdemo.ui.UiApplication;

@SpringBootApplication
public class ThickDemoApplication {

	public static void main(String[] args) {
		Application.launch(UiApplication.class, args);
	}
}
