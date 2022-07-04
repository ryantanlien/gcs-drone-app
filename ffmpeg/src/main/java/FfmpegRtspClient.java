import dvd.gcs.app.videostream.ImageTransmitEvent;
import dvd.gcs.app.videostream.ImageTransmitEventListener;
import dvd.gcs.app.videostream.Pf4jStreamable;
import javafx.scene.image.Image;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.JavaFXFrameConverter;

import java.util.ArrayList;
import java.util.List;

public class FfmpegRtspClient implements Pf4jStreamable, Runnable {

    private static final String SAMPLE_RTSP = "rtsp://127.0.0.1:8554/mystream";
    //private static final String DEEPSTREAM_RTSP = "127.0.0.1:8554/drone";
    private static final int TIMEOUT = 10; //In seconds.

    private FFmpegFrameGrabber ffmpegFrameGrabber;

    List<ImageTransmitEventListener> listeners = new ArrayList<>();

    @Override
    public void init() {
        try (FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(SAMPLE_RTSP)) {
            this.ffmpegFrameGrabber = grabber;
            grabber.setOption(
                    TimeoutOption.TIMEOUT.getKey(),
                    String.valueOf((TIMEOUT * 1000000))
            ); //In microseconds;
            grabber.start();

            Frame frame = null;

            //While RTSP is still serving frames, consume and call callback
            while ((frame = grabber.grab()) != null) {
                System.out.println("Frame grabbed at " + grabber.getTimestamp());
                try(final JavaFXFrameConverter converter = new JavaFXFrameConverter()) {
                    Image image = converter.convert(frame);
                    transmitImage(image);
                }
            }
        } catch (FrameGrabber.Exception exception) {
            System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
    }

    @Override
    public void close() {
        try {
            ffmpegFrameGrabber.close();
        } catch (FrameGrabber.Exception exception) {
            System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
    }

    @Override
    public void addFrameListener(ImageTransmitEventListener listener) {
        listeners.add(listener);
    }

    @Override
    public void transmitImage(Image image) {
        ImageTransmitEvent imageTransmitEvent = new ImageTransmitEvent(this, image);
        for(ImageTransmitEventListener listener : listeners) {
            listener.receiveEvent(imageTransmitEvent);
        }
    }

    private enum TimeoutOption {
        /**
         * Depends on protocol (FTP, HTTP, RTMP, RTSP, SMB, SSH, TCP, UDP, or UNIX).
         * http://ffmpeg.org/ffmpeg-all.html
         *
         * Specific for RTSP:
         * Set socket TCP I/O timeout in microseconds.
         * http://ffmpeg.org/ffmpeg-all.html#rtsp
         */
        TIMEOUT,
        /**
         * Protocols
         *
         * Maximum time to wait for (network) read/write operations to complete,
         * in microseconds.
         *
         * http://ffmpeg.org/ffmpeg-all.html#Protocols
         */
        RW_TIMEOUT;

        public String getKey() {
            return toString().toLowerCase();
        }
    }

    @Override
    public void run() {
    }
}
