package dvd.gcs.app.videostream;

import javafx.scene.image.Image;
import org.pf4j.ExtensionPoint;

public interface Pf4jStreamable extends ExtensionPoint {

    //Performs initialisation for a video livestreaming class
    void init();

    //Performs termination of the video livestreaming class
    void close();

    //transmit frames(simple images) to the data handler class
    void transmitImage(Image image);

    //add the dataHander that listens to the Streamable for video frames
    void addFrameListener(ImageTransmitEventListener listener);

    //set config properties
    void setRtspUrl(String rtspUrl);
}
