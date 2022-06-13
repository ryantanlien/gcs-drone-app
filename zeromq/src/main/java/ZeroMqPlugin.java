import org.pf4j.Plugin;
import org.pf4j.PluginWrapper;

import java.util.concurrent.atomic.AtomicBoolean;

//Future Improvements: Make sure threads are properly joined and that the ZeroMQ context and recvMsg loop can be properly exited
public class ZeroMqPlugin extends Plugin {

    private final AtomicBoolean running = new AtomicBoolean(false);
    private final ZeroMqClient zeroMqClient;
    private Thread clientThread;

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
        this.clientThread = thread;
        thread.start();
        this.running.set(true);
    }

    @Override
    public void stop() {
        zeroMqClient.close();
        clientThread.interrupt();
        this.running.set(false);
    }
}
