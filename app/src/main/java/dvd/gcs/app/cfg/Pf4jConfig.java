package dvd.gcs.app.cfg;

import dvd.gcs.app.message.DroneMessageService;
import dvd.gcs.app.message.DroneTransmitEventListener;
import dvd.gcs.app.message.Pf4jMessagable;
import dvd.gcs.app.luciadlightspeed.LuciadMapInterface;
import javafx.embed.swing.SwingNode;

import dvd.gcs.app.videostream.Pf4jStreamable;
import dvd.gcs.app.videostream.VideoStreamService;
import org.pf4j.CompoundPluginDescriptorFinder;
import org.pf4j.DefaultPluginManager;
import org.pf4j.ManifestPluginDescriptorFinder;
import org.pf4j.PluginManager;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Component
public class Pf4jConfig {

    @Autowired
    public BeanFactory beanFactory;

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
    public void initializePlugins() {
        pluginManager.loadPlugins();
        pluginManager.startPlugins();

        //ZeroMQ extension, note that extensions can only be used before plugins are
        @SuppressWarnings("rawtypes")
        List<Pf4jMessagable> messagables = pluginManager.getExtensions(Pf4jMessagable.class);
        System.out.println("Messagables size: " + messagables.size());

        for (Pf4jMessagable messagable: messagables) {
            messagable.addListener(this
                    .beanFactory
                    .getBeanProvider(DroneTransmitEventListener.class)
                    .getIfAvailable());
            DroneMessageService droneMessageService =
                this.beanFactory.getBeanProvider(DroneMessageService.class).getIfAvailable();

            if (droneMessageService != null) {
                droneMessageService.addListener(messagable);
            }
        }

        //Ffmpeg Extension
        List<Pf4jStreamable> streamables = pluginManager.getExtensions(Pf4jStreamable.class);
        System.out.println("Streamables size: " + streamables.size());

        for (Pf4jStreamable streamable: streamables) {
            streamable.addFrameListener(this
                    .beanFactory
                    .getBean(VideoStreamService.class));
        }


        // Luciad Lightspeed extension
        List<LuciadMapInterface> luciadMaps = pluginManager.getExtensions(LuciadMapInterface.class);
        System.out.println("Luciad size: " + luciadMaps.size());
        for (LuciadMapInterface luciadLightspeedMap: luciadMaps) {
            // Load SwingNode from plugin
            SwingNode mapSwingNode = luciadLightspeedMap.getSwingNode();
            LuciadMapInterface luciadMapInterface = luciadLightspeedMap.getInstance();

            ConfigurableBeanFactory configurableBeanFactory = (ConfigurableBeanFactory) this.beanFactory;
            configurableBeanFactory.registerSingleton("LuciadSwingNode", mapSwingNode);
            configurableBeanFactory.registerSingleton("LuciadLightspeedMap", luciadMapInterface);
        }

        //Sample on how to use PF4J extensions
        List<Greeting> greetings = pluginManager.getExtensions(Greeting.class);
        System.out.println("Greeting size: " + greetings.size());
        for (Greeting greeting: greetings) {
            System.out.println(greeting.getGreeting());
        }
    }

    /**
     * Stops all started plugins, calling their stop() lifecycle method.
     */
    public void terminatePlugins() {
        pluginManager.stopPlugins();
    }
}
