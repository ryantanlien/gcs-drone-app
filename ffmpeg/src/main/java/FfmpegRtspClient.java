import dvd.gcs.app.videostream.ImageTransmitEvent;
import dvd.gcs.app.videostream.ImageTransmitEventListener;
import dvd.gcs.app.videostream.Pf4jStreamable;
import javafx.scene.image.Image;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.JavaFXFrameConverter;
import org.pf4j.Extension;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Extension
public class FfmpegRtspClient implements Pf4jStreamable, Runnable {

    private static final String DEEPSTREAM_RTSP = "rtsp://127.0.0.1:8554/ds-gcs";
    private static final int TIMEOUT = 10; //In seconds.

    private FFmpegFrameGrabber ffmpegFrameGrabber;
    private ExecutorService imageExecutorService;

    private static final List<ImageTransmitEventListener> listeners = new ArrayList<>();

    @Override
    public void init() {
        boolean isRtspConnected = false;
        while (!Thread.interrupted() && !isRtspConnected) {
            try (FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(DEEPSTREAM_RTSP)) {

                System.out.println("Connecting to drone feed RTSP stream...");

                this.ffmpegFrameGrabber = grabber;
                grabber.setOption(
                        TimeoutOption.TIMEOUT.getKey(),
                        String.valueOf((TIMEOUT * 1000000))
                ); //In microseconds;

                //Enable hardware decoding
                grabber.setVideoCodecName("h264_cuvid"); //For H264
                /*grabber.setVideoCodecName("hevc_cuvid");*/ //For H265

                //Automatically set the number of threads
                grabber.setVideoOption("threads", "0");

                //Start the FrameGrabber
                grabber.start();
                isRtspConnected = true;
                System.out.println("Connected!");

                final JavaFXFrameConverter converter = new JavaFXFrameConverter();
                final ExecutorService imageExecutor = Executors.newSingleThreadExecutor();
                this.imageExecutorService = imageExecutor;

                //While RTSP is still serving frames, consume and call callback
                while (!Thread.interrupted()) {
                    final Frame frame = grabber.grab();
                    if (frame == null) {
                        break;
                    }
                    if (frame.image != null) {
                        final Frame imageFrame = frame.clone();
                        if (!(imageFrame.imageWidth > 0) || !(imageFrame.imageHeight > 0)) {
                            continue;
                        }
                        imageExecutor.submit(() -> {
                            final Image image = converter.convert(imageFrame);
                            imageFrame.close();
                            transmitImage(image);
                        });
                    }
                }
            } catch (FrameGrabber.Exception exception) {
                System.out.println("No response from RTSP stream, retrying operation...");
                exception.printStackTrace();
                isRtspConnected = false;
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException iException) {
                    System.out.println("Retry operation cancelled, Ffmpeg thread interrupted!");
                    iException.printStackTrace();
                }
            }
        }
    }

    @Override
    public void close() {
        try {
            ffmpegFrameGrabber.close();
            imageExecutorService.shutdown();
        } catch (FrameGrabber.Exception exception) {
            System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
    }

    @Override
    public void addFrameListener(ImageTransmitEventListener listener) {
        listeners.add(listener);
        System.out.println("FfmpegRtspClient initial listener size: " + listeners.size());
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
        this.init();
    }
}
