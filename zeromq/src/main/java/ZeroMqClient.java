import dvd.gcs.app.message.DroneCommandMessage;
import dvd.gcs.app.message.DroneCommandReplyMessage;
import dvd.gcs.app.message.DroneMessage;
import dvd.gcs.app.message.DroneTelemetryMessage;
import dvd.gcs.app.message.MessageTransmitEvent;
import dvd.gcs.app.message.MessageTransmitEventListener;
import dvd.gcs.app.message.Pf4jMessagable;

import org.pf4j.Extension;
import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZFrame;
import org.zeromq.ZMQ;
import org.zeromq.ZMsg;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Extension
public class ZeroMqClient implements Pf4jMessagable<DroneMessage>, Runnable {

    private static final List<MessageTransmitEventListener<DroneMessage>> listeners
            = new ArrayList<>();

    private static final long SOCKET_TIMEOUT_DURATION_MS = 1;
    //this approach works as well -> controller connects to GPU laptop's subnet ipv4, but recommended to start app before DJIAAPP
    //preferred approach
    //GPU Laptop ZeroMQ -> socket.bind(tcp://*:5556) | DJIAAPP ZeroMQ -> socket.connect(tcp://[insert GPU Laptop subnet IPV4 here]:5556)]
    private static String TELEMETRY_SOCKET_PORT = "5556";
    private static String COMMAND_SOCKET_PORT = "5557";
    private static final String DJIAAPP_IP_ADDRESS_TELEMETRY = "tcp://*:" + TELEMETRY_SOCKET_PORT;
    private static final String DJIAAPP_IP_ADDRESS_COMMAND = "tcp://*:" + COMMAND_SOCKET_PORT;
    private static ZContext DJIAAPP_CONTEXT;


    private static final AtomicBoolean running = new AtomicBoolean(false);

    private final ArrayList<ZeroMqReliableRequest> resolvingRequests = new ArrayList<>();

    @Override
    public void run() {
        this.init();
    }

    //Opens the socket to receive messages from the DJIAAPP
    @Override
    public void init() {

        running.set(true);

        //Create context
        ZContext context = new ZContext();
        DJIAAPP_CONTEXT = context;

        //Create receiving socket
        ZMQ.Socket subscriber = context.createSocket(SocketType.SUB);
        subscriber.bind(DJIAAPP_IP_ADDRESS_TELEMETRY);
        subscriber.subscribe("");

        //Register a poller
        ZMQ.Poller poller = context.createPoller(1);
        poller.register(subscriber);

        //Define messaging behavior
        while (!Thread.currentThread().isInterrupted() && running.get()) {
            poller.poll(SOCKET_TIMEOUT_DURATION_MS);
            if (!poller.pollin(0)) {
                continue;
            }

            //Most frequent operation which is to get telemetry
            if (poller.pollin(0)) {
                ZMsg receivedMessage = ZMsg.recvMsg(subscriber);
                ZFrame zFrame;
                ArrayList<String> strings = new ArrayList<>();

                do {
                    zFrame = receivedMessage.poll();
                    if (zFrame == null) {
                        break;
                    }
                    if (!zFrame.hasData()) {
                        continue;
                    }

                    String data = zFrame.getString(ZMQ.CHARSET);
                    System.out.println(data);
                    strings.add(data);

                } while (zFrame.hasMore());

                //TODO: Change exception here later
                try {
                    DroneTelemetryMessage droneTelemetryMessage = ZeroMqMsgService.decodeTelemetryMsg(strings);
                    this.transmit(droneTelemetryMessage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void close() {
        running.set(false);
        resolvingRequests.forEach(request -> request.running.set(false));
        DJIAAPP_CONTEXT.destroy();
    }

    @Override
    public void transmit(DroneMessage droneMessage) {
        MessageTransmitEvent<DroneMessage> messageTransmitEvent
                = new MessageTransmitEvent<>(this, droneMessage);

        for (MessageTransmitEventListener<DroneMessage> listener : listeners) {
            listener.receiveEvent(messageTransmitEvent);
        }
    }

    @Override
    public void addListener(MessageTransmitEventListener<DroneMessage> listener) {
        listeners.add(listener);
    }

    @Override
    public void setTelemetryPort(String port) {
        TELEMETRY_SOCKET_PORT = port;
    }

    @Override
    public void setCommandPort(String port) {
        COMMAND_SOCKET_PORT = port;
    }

    //Transmit commands
    @Override
    public void receiveEvent(MessageTransmitEvent<DroneMessage> event) {
        DroneCommandMessage droneCommandMessage =  (DroneCommandMessage) event.getMessage();
        ZMsg request = ZeroMqMsgService.encodeCommandMsg(droneCommandMessage);

        ZeroMqReliableRequest zeroMqReliableRequest = new ZeroMqReliableRequest(request);
        resolvingRequests.add(zeroMqReliableRequest);

        Thread thread = new Thread(zeroMqReliableRequest);
        thread.start();
    }

    private class ZeroMqReliableRequest implements Runnable {

        private static final long RELIABLE_SOCKET_TIMEOUT_DURATION_MS = 1000;
        private static final int REQUEST_RETRIES = 3;
        protected final AtomicBoolean running = new AtomicBoolean(false);

        private final ZMsg request;

        public ZeroMqReliableRequest(ZMsg request) {
            this.request = request;
        }

        @Override
        public void run() {

            this.running.set(true);

            //Create sending socket
            ZMQ.Socket senSocket = DJIAAPP_CONTEXT.createSocket(SocketType.REQ);
            System.out.println("Connecting to DJIAAPP command socket...");
            senSocket.connect(DJIAAPP_IP_ADDRESS_COMMAND);
            System.out.println("Connected!");
            ZMQ.Poller poller = DJIAAPP_CONTEXT.createPoller(1);
            poller.register(senSocket);

            int retriesLeft = REQUEST_RETRIES;
            while (!Thread.currentThread().isInterrupted() && running.get() && retriesLeft > 0) {

                boolean isExpectingReply = true;
                this.request.send(senSocket);

                while(isExpectingReply) {
                    int result = poller.poll(RELIABLE_SOCKET_TIMEOUT_DURATION_MS);

                    if (result == -1) {
                        break; //Interrupted
                    }

                    //Infrequent operation to send commands to DJIAAPP
                    if (poller.pollin(0)) {
                        ZMsg receivedMessage = ZMsg.recvMsg(senSocket);
                        ZFrame zFrame;
                        ArrayList<String> strings = new ArrayList<>();

                        do {
                            zFrame = receivedMessage.poll();
                            if (zFrame == null) {
                                break;
                            }
                            if (!zFrame.hasData()) {
                                continue;
                            }

                            String data = zFrame.getString(ZMQ.CHARSET);
                            strings.add(data);

                        } while (zFrame.hasMore());

                        isExpectingReply = false;
                        retriesLeft = 0;

                        //TODO: Change exception here later
                        try {
                            DroneCommandReplyMessage droneCommandReplyMessage = ZeroMqMsgService.decodeCommandReplyMsg(strings);
                            ZeroMqClient.this.transmit(droneCommandReplyMessage);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (retriesLeft-- == 0) {
                        System.out.println("Disconnected from DJIAAPP, abandoning operation...");
                        break;
                    } else {
                        System.out.println("No response from DJIAAPP, retrying operation...");
                        poller.unregister(senSocket);
                        DJIAAPP_CONTEXT.destroySocket(senSocket);

                        ZMQ.Socket newSocket = DJIAAPP_CONTEXT.createSocket(SocketType.REQ);

                        //Note the use of connect here instead of bind
                        newSocket.connect(DJIAAPP_IP_ADDRESS_COMMAND);
                        poller.register(newSocket, ZMQ.Poller.POLLIN);
                    }
                }
            }
            resolvingRequests.remove(this);
        }
    }
}
