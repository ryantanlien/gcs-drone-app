package dvd.gcs.app.cfg;

import dvd.gcs.app.luciadlightspeed.LuciadMapInterface;
import dvd.gcs.app.message.Pf4jMessagable;

import dvd.gcs.app.message.TestMessageService;
import javafx.embed.swing.SwingNode;
import org.pf4j.CompoundPluginDescriptorFinder;
import org.pf4j.DefaultPluginManager;
import org.pf4j.ManifestPluginDescriptorFinder;
import org.pf4j.PluginManager;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Pf4jConfig {

    /** Relative path to the custom project plugin directory. **/
    final static Path PLUGIN_DIR = Paths.get("./plugins");

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
     * Loads and starts all enabled plugins, as well as configures them appropiately.
     */
    public static void initializePlugins() {
        pluginManager.loadPlugins();
        pluginManager.startPlugins();

        //ZeroMQ extension, note that extensions can only be used before plugins are
        @SuppressWarnings("rawtypes")
        List<Pf4jMessagable> messagables = pluginManager.getExtensions(Pf4jMessagable.class);
        System.out.println("Messagables size: " + messagables.size());

        for (Pf4jMessagable messagable: messagables) {
            messagable.addListener(new TestMessageService());
        }

        // Luciad Lightspeed extension
        List<LuciadMapInterface> luciadMaps = pluginManager.getExtensions(LuciadMapInterface.class);
        System.out.println("Luciad size: " + luciadMaps.size());
        for (LuciadMapInterface luciadLightspeedMap: luciadMaps) {
            // Load SwingNode from plugin
//            SwingNode mapSwingNode = luciadLightspeedMap.getSwingNode();
//            JavaFxConfig.updateSwingNode(mapSwingNode);

            // TODO: pass SwingNode to application
        }

        //Sample on how to use PF4J extensions
        List<Greeting> greetings = pluginManager.getExtensions(Greeting.class);
        System.out.println("Greeting size: " + greetings.size());
        for (Greeting greeting: greetings) {
            System.out.println(greeting.getGreeting());
        }
    }
}
