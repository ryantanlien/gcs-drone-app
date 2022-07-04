import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;

public class TestMain {

    //private static final String DEEPSTREAM_RTSP = "127.0.0.1:8554/drone";
    private static final String SAMPLE_RTSP = "rtsp://127.0.0.1:8554/mystream";
    private static final int TIMEOUT = 10; // In seconds.

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


    public static void main(String[] args) {
        try {
            FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(SAMPLE_RTSP);

            //If network disconnects, timeout in 10s
            grabber.setOption(
                    TimeoutOption.TIMEOUT.getKey(),
                    String.valueOf(TIMEOUT * 1000000)
            ); // In microseconds.
            grabber.start();

            Frame frame = null;

            //Video format is MPEG-4 H.264
            while ((frame = grabber.grab()) != null) {
                System.out.println("here");
                System.out.println("frame grabbed at " + grabber.getTimestamp());
            }
            System.out.println("loop end with frame: " + frame);

        } catch (FrameGrabber.Exception exception) {
            System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
    }
}
