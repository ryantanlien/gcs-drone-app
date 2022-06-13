import org.pf4j.Plugin;
import org.pf4j.PluginWrapper;

public class ZeroMqPlugin extends Plugin {

    private final ZeroMqClient zeroMqClient;

    /**
     * Constructor to be used by plugin manager for plugin instantiation.
     * Your plugins have to provide constructor with this exact signature to
     * be successfully loaded by manager.
     *
     * @param wrapper the wrapper provided by PF4J
     */
    public ZeroMqPlugin(PluginWrapper wrapper) {
        super(wrapper);
        this.zeroMqClient = new ZeroMqClient();
    }

    @Override
    public void start() {
        Thread thread = new Thread(zeroMqClient);
        thread.start();
    }

    @Override
    public void stop() {
        zeroMqClient.close();
    }
}
