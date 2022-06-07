import org.zeromq.SocketType;
import org.zeromq.ZMQ;
import org.zeromq.ZContext;

public class hwclient {
    public static void main(String[] args2) {
        try (ZContext context = new ZContext()) {
            System.out.println("Connecting to hello world server");

            //Socket to talk to server
            ZMQ.Socket socket = context.createSocket(SocketType.REQ);
            socket.connect("tcp://localhost:5555");

            for (int requestNbr = 0; requestNbr != 10; requestNbr++) {
                String request = "hello";
                System.out.println("Sending Hello " + requestNbr);
                socket.send(request.getBytes(ZMQ.CHARSET), 0);

                byte[] reply = socket.recv(0);
                System.out.println(
                        "Received " + new String(reply, ZMQ.CHARSET) + " " + requestNbr
                );
            }
        }
    }
}
