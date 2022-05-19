package dvd.gcs.app;

import dvd.gcs.app.ui.UiApplication;
import javafx.application.Application;
import org.pf4j.CompoundPluginDescriptorFinder;
import org.pf4j.DefaultPluginManager;
import org.pf4j.ManifestPluginDescriptorFinder;
import org.pf4j.PluginManager;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ThickDemoApplication {

	public static void main(String[] args) {
		final PluginManager pluginManager = new DefaultPluginManager() {
			@Override
			protected CompoundPluginDescriptorFinder createPluginDescriptorFinder() {
				return new CompoundPluginDescriptorFinder()
						// Demo is using the Manifest file
						// PropertiesPluginDescriptorFinder is commented out just to avoid error log
						//.add(new PropertiesPluginDescriptorFinder())
						.add(new ManifestPluginDescriptorFinder());
			}
		};

		// load the plugins
		pluginManager.loadPlugins();

		// start (active/resolved) the plugins
		pluginManager.startPlugins();
		Application.launch(UiApplication.class, args);
	}
}
