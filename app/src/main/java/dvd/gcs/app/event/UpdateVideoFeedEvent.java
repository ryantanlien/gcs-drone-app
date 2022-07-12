package dvd.gcs.app.event;

import javafx.scene.image.Image;
import org.springframework.context.ApplicationEvent;

public class UpdateVideoFeedEvent extends ApplicationEvent {

    private final Image image;
    public UpdateVideoFeedEvent(Object source, Image image) {
        super(source);
        this.image = image;
    }

    public Image getImage() {
        return image;
    }
}
