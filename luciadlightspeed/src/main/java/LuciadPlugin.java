import org.pf4j.Extension;
import org.pf4j.Plugin;
import org.pf4j.PluginWrapper;

public class LuciadPlugin extends Plugin {
    public LuciadPlugin(PluginWrapper pluginWrapper) {
        super(pluginWrapper);
    }

    @Extension
    public static class LuciadMap implements LuciadMapInterface {

    }
}
