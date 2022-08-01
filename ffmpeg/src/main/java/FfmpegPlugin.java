import org.pf4j.Plugin;
import org.pf4j.PluginWrapper;

public class FfmpegPlugin extends Plugin {

    private final FfmpegRtspClient ffmpegRtspClient;
    private Thread clientThread;

    public FfmpegPlugin(PluginWrapper wrapper) {
        super(wrapper);
        this.ffmpegRtspClient = new FfmpegRtspClient();
    }

    @Override
    public void start() {
        Thread thread = new Thread(ffmpegRtspClient);
        this.clientThread = thread;
        thread.start();
    }

    @Override
    public void stop() {
        ffmpegRtspClient.close();
        clientThread.interrupt();
        try {
            clientThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
