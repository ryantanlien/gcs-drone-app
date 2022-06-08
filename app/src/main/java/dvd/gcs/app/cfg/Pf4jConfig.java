package dvd.gcs.app.cfg;

import org.pf4j.CompoundPluginDescriptorFinder;
import org.pf4j.DefaultPluginManager;
import org.pf4j.ManifestPluginDescriptorFinder;
import org.pf4j.PluginManager;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Pf4jConfig {

    /** Relative path to the custom project plugin directory. **/
    final static Path PLUGIN_DIR = Paths.get("../plugins");

    /**
     * Sets up a configured pluginManager that changes the plugin directory via a parameter provided to constructor.
     * The default deployment directory is workingDir/plugins, but the defined plugin directory for deployment is
     * rootDir/plugins.
     */
    final static PluginManager pluginManager = new DefaultPluginManager(PLUGIN_DIR) {
            @Override
            protected CompoundPluginDescriptorFinder createPluginDescriptorFinder() {
                return new CompoundPluginDescriptorFinder()
                        .add(new ManifestPluginDescriptorFinder());
            }
    };

    /**
     * Loads and starts all enabled plugins.
     */
    public static void initializePlugins() {
        pluginManager.loadPlugins();
        pluginManager.startPlugins();

        //Sample on how to use PF4J extensionss
        List<Greeting> greetings = pluginManager.getExtensions(Greeting.class);
        System.out.println("Greeting size: " + greetings.size());
        for (Greeting greeting: greetings) {
            System.out.println(greeting.getGreeting());
        }
    }
}
