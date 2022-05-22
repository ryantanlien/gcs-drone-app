package dvd.gcs.welcome;

import org.pf4j.Plugin;
import org.pf4j.PluginWrapper;

/**
 * Sample plugin class that extends interface provided by PF4J in order to handle the lifecycle of the plugin
 */
public class WelcomePlugin extends Plugin {

    /**
     * Constructs the sample plugin
     * @param wrapper the wrapper provided by PF4J
     */
    public WelcomePlugin(PluginWrapper wrapper) {
        super(wrapper);
    }

    @Override
    public void start() {
        System.out.println("WelcomePlugin.start()");
    }

    @Override
    public void stop() {
        System.out.println("WelcomePlugin.stop()");
    }

    @Override
    public void delete() {
        System.out.println("WelcomePlugin.delete()");
    }
}
