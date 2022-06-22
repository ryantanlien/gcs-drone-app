import org.pf4j.Plugin;
import org.pf4j.PluginWrapper;

public class LuciadLightspeedPlugin extends Plugin {
    public final LuciadMap luciadMap;

    public LuciadLightspeedPlugin(PluginWrapper wrapper) {
        super(wrapper);
        this.luciadMap = new LuciadMap();
    }
}
