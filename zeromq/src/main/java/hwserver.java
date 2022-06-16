import org.zeromq.*;

public class hwserver {
    public static void main(String[] args) throws Exception {
        try (ZContext context = new ZContext()) {
            ZMQ.Socket socket = context.createSocket(SocketType.REP);
            socket.bind("tcp://*:5555");

            while (!Thread.currentThread().isInterrupted()) {

                //JeroMQ API for receiving multi-part messages.
                ZMsg receivedMessage = ZMsg.recvMsg(socket);
                System.out.println("Number of frames in ZMsg: "+ receivedMessage.size());
                for (ZFrame f: receivedMessage) {
                    System.out.println(f.getString(ZMQ.CHARSET));
                }

                String response = "world";
                socket.send(response.getBytes(ZMQ.CHARSET), 0);
            }
        }
    }
}