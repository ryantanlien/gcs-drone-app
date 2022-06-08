import org.zeromq.*;

public class hwserver {
    public static void main(String[] args) throws Exception {
        try (ZContext context = new ZContext()) {
            ZMQ.Socket socket = context.createSocket(SocketType.REP);
            socket.bind("tcp://*:5555");

            while (!Thread.currentThread().isInterrupted()) {

                //JeroMQ API for receiving multi-part messages.
                ZMsg receivedMessage = ZMsg.recvMsg(socket);
                for (ZFrame f: receivedMessage) {
                    System.out.println(f.getString(ZMQ.CHARSET));
                }

//                JeroMQ API for receiving simple single-part messages.
//                byte[] reply = socket.recv(0);
//                System.out.println(
//                        "Received " + ":  ["+ new String(reply, ZMQ.CHARSET) + "]"
//                );

                String response = "world";
                socket.send(response.getBytes(ZMQ.CHARSET), 0);
            }
        }
    }
}
