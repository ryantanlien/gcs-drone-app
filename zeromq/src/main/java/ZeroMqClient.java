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
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Extension
public class ZeroMqClient implements Pf4jMessagable<String>, Runnable {

    private static final List<MessageTransmitEventListener<String>> listeners = new ArrayList<>();

    private static final long SOCKET_TIMEOUT_DURATION_MS = 1;
    private static final String DJIAAPP_IP_ADDRESS = "tcp://*:5555";
    private static ZContext DJIAAPP_CONTEXT;

    private static final AtomicBoolean running = new AtomicBoolean(false);

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
        ZMQ.Socket recSocket = context.createSocket(SocketType.REP);
        recSocket.bind(DJIAAPP_IP_ADDRESS);

        //Register a poller
        ZMQ.Poller poller = context.createPoller(1);
        poller.register(recSocket);

        while (!Thread.currentThread().isInterrupted() && running.get()) {
            poller.poll(SOCKET_TIMEOUT_DURATION_MS);
            if (!poller.pollin(0)) {
                continue;
            }

            ZMsg receivedMessage = ZMsg.recvMsg(recSocket);
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

            //Need to make this function call non-blocking -> The message service will then feed this into a buffer
            this.transmit(strings);
        }
    }

    @Override
    public void close() {
        running.set(false);
        DJIAAPP_CONTEXT.destroy();
    }

    @Override
    public void transmit(Collection<String> strings) {
        MessageTransmitEvent<String> messageTransmitEvent = new MessageTransmitEvent<>(this, strings);
        for (MessageTransmitEventListener<String> listener : listeners) {
            listener.receiveEvent(messageTransmitEvent);
        }
    }

    @Override
    public void addListener(MessageTransmitEventListener<String> listener) {
        listeners.add(listener);
    }
}
