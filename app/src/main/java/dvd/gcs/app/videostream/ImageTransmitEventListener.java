package dvd.gcs.app.videostream;
import java.util.EventListener;

public interface ImageTransmitEventListener extends EventListener {
    void receiveEvent(ImageTransmitEvent event);
}
