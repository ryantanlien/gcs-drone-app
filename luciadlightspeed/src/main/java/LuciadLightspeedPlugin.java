import org.pf4j.Plugin;
import org.pf4j.PluginWrapper;

public class LuciadLightspeedPlugin extends Plugin {
    public LuciadMap luciadMap;

    public LuciadLightspeedPlugin(PluginWrapper wrapper) {
        super(wrapper);
    }

    @Override
    public void start() {
        System.out.println("LuciadLightspeedPlugin.start()");
        this.luciadMap = new LuciadMap();
    }

    @Override
    public void stop() {
        System.out.println("LuciadLightspeedPlugin.stop()");
        // requires anything when stopping plugin?
    }
}
