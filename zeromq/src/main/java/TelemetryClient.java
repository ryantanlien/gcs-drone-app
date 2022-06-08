import org.zeromq.*;

public class TelemetryClient {

    private static final int TELEMETRY_MESSAGE_SIZE = 4;
    private static final String DJIAPP_IP_ADDRESS = "tcp://*:5555";
    private static String altitudeString;
    private static String longString;
    private static String latString;

    private static String velocityString;

    public static void main(String[] args) {
        TelemetryClient.init();
    }

    //Opens the socket to receive messages from the DJIAPP
    public static void init() {
        try (ZContext context = new ZContext()) {
            ZMQ.Socket recSocket = context.createSocket(SocketType.REP);
            recSocket.bind(DJIAPP_IP_ADDRESS);

            while (!Thread.currentThread().isInterrupted()) {
                ZMsg receivedMessage = ZMsg.recvMsg(recSocket);
                int numberOfFrames = receivedMessage.size();
                if (numberOfFrames == TELEMETRY_MESSAGE_SIZE) {
                    System.out.println("All telemetry components received!");
                } else {
                    System.out.println("Error: Missing telemetry components!");
                }

                ZFrame altitudeFrame = receivedMessage.pop();
                if (altitudeFrame != null) {
                    TelemetryClient.altitudeString = altitudeFrame.getString(ZMQ.CHARSET);
                    System.out.println("Altitude Telemetry Received: " + TelemetryClient.altitudeString);
                } else {
                    System.out.println("Error: Missing altitude telemetry!");
                }

                ZFrame longFrame = receivedMessage.pop();
                if (longFrame != null) {
                    TelemetryClient.longString = longFrame.getString(ZMQ.CHARSET);
                    System.out.println("Longitude Telemetry Received: " + TelemetryClient.longString);
                } else {
                    System.out.println("Error: Missing longitude telemetry!");
                }

                ZFrame latFrame = receivedMessage.pop();
                if (latFrame != null) {
                    TelemetryClient.latString = latFrame.getString(ZMQ.CHARSET);
                    System.out.println("Latitude Telemetry Received: " + TelemetryClient.latString);
                } else {
                    System.out.println("Error: Missing latitude telemetry!");
                }

                ZFrame velocityFrame = receivedMessage.pop();
                if (velocityFrame != null) {
                    TelemetryClient.velocityString = velocityFrame.getString(ZMQ.CHARSET);
                    System.out.println("Velocity Telemetry Received: " + TelemetryClient.velocityString);
                } else {
                    System.out.println("Error: Missing velocity telemetry!");
                }
            }
        }
    }
}
