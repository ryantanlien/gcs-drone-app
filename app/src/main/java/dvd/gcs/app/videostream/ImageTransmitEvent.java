package dvd.gcs.app.videostream;

import javafx.scene.image.Image;

import java.util.EventObject;

public class ImageTransmitEvent extends EventObject {

    private final Image image;

    public ImageTransmitEvent(Object source, Image image) {
        super(source);
        this.image = image;
    }

    public Image getImage() {
        return this.image;
    }
}
