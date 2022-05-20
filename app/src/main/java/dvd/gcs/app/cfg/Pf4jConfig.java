package dvd.gcs.app.cfg;

import org.pf4j.CompoundPluginDescriptorFinder;
import org.pf4j.DefaultPluginManager;
import org.pf4j.ManifestPluginDescriptorFinder;
import org.pf4j.PluginManager;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Pf4jConfig {
    final static Path PLUGIN_DIR = Paths.get("../plugins");

    //Set up a configured pluginManager that changes the plugin directory
    //The default deployment directory is workingDir/plugins, but the defined plugin dir for deployment is
    //rootDir/plugins
    final static PluginManager pluginManager = new DefaultPluginManager(PLUGIN_DIR) {
            @Override
            protected CompoundPluginDescriptorFinder createPluginDescriptorFinder() {
                return new CompoundPluginDescriptorFinder()
                        .add(new ManifestPluginDescriptorFinder());
            }
    };

    public static void initializePlugins() {
        pluginManager.loadPlugins();
        pluginManager.startPlugins();
    }
}
