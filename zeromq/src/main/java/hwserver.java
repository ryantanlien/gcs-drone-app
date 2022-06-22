import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZFrame;
import org.zeromq.ZMQ;
import org.zeromq.ZMsg;

//Test server to receive command
public class hwserver {
    public static void main(String[] args2) {
        try (ZContext context = new ZContext()) {
            System.out.println("Connecting to hello world server");

            //Socket to talk to server
            ZMQ.Socket socket = context.createSocket(SocketType.REP);
            socket.bind("tcp://localhost:5556");

            ZMsg zmsg = ZMsg.recvMsg(socket);
            ZFrame zFrame;
            do {
                zFrame = zmsg.poll();
                if (zFrame == null) {
                    break;
                }
                if (!zFrame.hasData()) {
                    continue;
                }

                String data = zFrame.getString(ZMQ.CHARSET);
                System.out.println(data);

            } while (zFrame.hasMore());

            //JeroMQ API for sending multi-part messages.
            ZMsg msg = new ZMsg();
            msg.addString("COMMAND_REPLY");
            msg.addString("{" +
                    "\"droneModel\":\"Mavic\"," +
                    "\"geoFenceRadius\":0.1" +
                    "}");
            msg.addString("COMMAND_EXECUTION_SUCCESS");
            msg.send(socket);
        }
    }
}
