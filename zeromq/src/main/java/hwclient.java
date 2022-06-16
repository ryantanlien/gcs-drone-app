import org.zeromq.SocketType;
import org.zeromq.ZMQ;
import org.zeromq.ZContext;
import org.zeromq.ZMsg;

public class hwclient {
    public static void main(String[] args2) {
        try (ZContext context = new ZContext()) {
            System.out.println("Connecting to hello world server");

            //Socket to talk to server
            ZMQ.Socket socket = context.createSocket(SocketType.REQ);
            socket.connect("tcp://localhost:5555");


            for (int requestNbr = 0; requestNbr != 10; requestNbr++) {

                //JeroMQ API for sending multi-part messages.
                ZMsg msg = new ZMsg();
                msg.addString("TELEMETRY");
                msg.addString("{\"droneCallSign\":\"Alpha\"}");
                msg.send(socket);

                byte[] reply = socket.recv(0);
                System.out.println(
                        "Received " + new String(reply, ZMQ.CHARSET) + " " + requestNbr
                );
            }
        }
    }
}
