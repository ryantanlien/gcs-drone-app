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
                for (int i = 0; i < 10; i++) {
                    msg.addString("Frame" + i);
                }
                msg.send(socket);

                //JeroMQ API for simple single-part messages.
//                String request = "hello";
//                System.out.println("Sending Frame " + requestNbr);
//                socket.send(request.getBytes(ZMQ.CHARSET), 0);

                byte[] reply = socket.recv(0);
                System.out.println(
                        "Received " + new String(reply, ZMQ.CHARSET) + " " + requestNbr
                );
            }
        }
    }
}
