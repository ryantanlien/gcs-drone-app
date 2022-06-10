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

@Extension
public class ZeroMqClient implements Pf4jMessagable<String>, Runnable {

    private static final List<MessageTransmitEventListener<String>> listeners = new ArrayList<>();

    private static final String DJIAAPP_IP_ADDRESS = "tcp://*:5555";
    private static ZMQ.Socket DJIAAPP_REC_SOCKET;

    @Override
    public void run() {
        this.init();
    }

    //Opens the socket to receive messages from the DJIAAPP
    @Override
    public void init() {
        try (ZContext context = new ZContext()) {
            ZMQ.Socket recSocket = context.createSocket(SocketType.REP);
            recSocket.bind(DJIAAPP_IP_ADDRESS);
            DJIAAPP_REC_SOCKET = recSocket;

            while (!Thread.currentThread().isInterrupted()) {
                ZMsg receivedMessage = ZMsg.recvMsg(DJIAAPP_REC_SOCKET);
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
                } while(zFrame.hasMore());

                //Need to make this function call non-blocking -> The message service will then feed this into a buffer
                this.transmit(strings);
            }
        }
    }

    @Override
    public void close() {
        DJIAAPP_REC_SOCKET.close();
    }

    @Override
    public void transmit(Collection<String> strings) {
        MessageTransmitEvent<String> messageTransmitEvent = new MessageTransmitEvent<>(this, strings);
        for (MessageTransmitEventListener<String> listener : listeners) {
            listener.handleEvent(messageTransmitEvent);
        }
    }

    @Override
    public void addListener(MessageTransmitEventListener<String> listener) {
        listeners.add(listener);
    }
}
