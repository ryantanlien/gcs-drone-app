import dvd.gcs.app.videostream.ImageTransmitEvent;
import dvd.gcs.app.videostream.ImageTransmitEventListener;
import javafx.scene.image.Image;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.JavaFXFrameConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestMain {

    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
          final String SAMPLE_RTSP = "rtsp://localhost:8554/mystream";
        //private static final String DEEPSTREAM_RTSP = "127.0.0.1:8554/drone";
          final int TIMEOUT = 10; //In seconds.

            try (FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(SAMPLE_RTSP)) {
                grabber.start();

                final ExecutorService imageExecutor = Executors.newSingleThreadExecutor();

                //While RTSP is still serving frames, consume and call callback
                while (!Thread.interrupted()) {
                    final Frame frame = grabber.grab();
                    if (frame == null) {
                        break;
                    }
                    System.out.println("here");
                    if (frame.image != null) {
                        final Frame imageFrame = frame.clone();
                        System.out.println("here1");
                        /*System.out.println("Frame grabbed and cloned at " + grabber.getTimestamp());*/
                        if (!(imageFrame.imageWidth > 0) || !(imageFrame.imageHeight > 0)) {
                            continue;
                        }
                        imageExecutor.submit(() -> {
                            System.out.println("here2");
                    /*final Image image = converter.convert(imageFrame);
                    imageFrame.close();*/
                        });
                    }
                }
            } catch (FrameGrabber.Exception exception) {
                System.out.println(exception.getMessage());
                exception.printStackTrace();
            }
        });
        thread.start();
    }
}
