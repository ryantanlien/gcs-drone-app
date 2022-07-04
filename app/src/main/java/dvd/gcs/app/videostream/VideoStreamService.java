package dvd.gcs.app.videostream;

import dvd.gcs.app.event.UpdateVideoFeedEvent;
import javafx.scene.image.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class VideoStreamService implements ImageTransmitEventListener {

    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void receiveEvent(ImageTransmitEvent event) {
        Image image = event.getImage();
        UpdateVideoFeedEvent updateVideoFeedEvent = new UpdateVideoFeedEvent(this, image);
        applicationEventPublisher.publishEvent(updateVideoFeedEvent);
    }
}
