package sg.gov.dsta.thickdemo;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import sg.gov.dsta.thickdemo.ui.UiApplication;

import java.util.List;

@SpringBootApplication
public class ThickDemoApplication {

	public static void main(String[] args) {
		Application.launch(UiApplication.class, args);
//		initializePlugins();
	}

//	private static void initializePlugins() {
//		PluginManager pluginManager = new ZipPluginManager();
//
//		pluginManager.loadPlugins();
//        pluginManager.startPlugins();
//
//        List<Greeting> greetings = pluginManager.getExtensions(Greeting.class);
//        for (Greeting greeting : greetings) {
//            System.out.println(">>> " + greeting.getGreeting());
//        }
//
//        pluginManager.stopPlugins();
//        pluginManager.unloadPlugins();
//	}
}
