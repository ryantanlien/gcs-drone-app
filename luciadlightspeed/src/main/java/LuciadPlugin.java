import org.pf4j.Plugin;
import org.pf4j.PluginWrapper;

public class LuciadPlugin extends Plugin {
    public final LuciadMap luciadMap;

    public LuciadPlugin(PluginWrapper pluginWrapper) {
        super(pluginWrapper);
        this.luciadMap = new LuciadMap();
    }
}
