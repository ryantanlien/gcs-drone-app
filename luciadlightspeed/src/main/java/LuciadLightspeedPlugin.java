import org.pf4j.Plugin;
import org.pf4j.PluginWrapper;

public class LuciadLightspeedPlugin extends Plugin {
    public final LuciadMap luciadMap;

    public LuciadLightspeedPlugin(PluginWrapper pluginWrapper) {
        super(pluginWrapper);
        this.luciadMap = new LuciadMap();
    }
}
