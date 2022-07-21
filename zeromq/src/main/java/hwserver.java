import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZFrame;
import org.zeromq.ZMQ;
import org.zeromq.ZMsg;

import java.util.ArrayList;

//Test server to receive command
public class hwserver {
    public static void main(String[] args2) {
        while (true) {
            try (ZContext context = new ZContext()) {
                ArrayList<String> message = new ArrayList<>();

                System.out.println("Connecting to hello world server");

                //Socket to talk to server
                ZMQ.Socket socket = context.createSocket(SocketType.REP);
                socket.bind("tcp://localhost:5557");

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
                    message.add(data);

                } while (zFrame.hasMore());

                String messageType = message.get(0);
                String commandType = message.get(1);
                String commandData = message.get(2);

                if (!messageType.equals("COMMAND")) {
                    return;
                }

                //JeroMQ API for sending multi-part messages.
                if (commandType.equals("SET_GEOFENCE")) {
                    ZMsg msg = new ZMsg();
                    msg.addString("COMMAND_REPLY");
                    msg.addString(commandType);
                    msg.addString("COMMAND_SUCCESS");
                    msg.addString("{" +
                            "\"droneCallSign\":\"Alpha\"," +
                            "\"geoFenceRadius\":" + commandData +
                            "}");
                    msg.send(socket);
                } else if (commandType.equals("SET_MAX_SPEED")) {
                    ZMsg msg = new ZMsg();
                    msg.addString("COMMAND_REPLY");
                    msg.addString(commandType);
                    msg.addString("COMMAND_SUCCESS");
                    msg.addString("{" +
                            "\"droneCallSign\":\"Alpha\"," +
                            "\"maxVelocity\":" + commandData +
                            "}");
                    msg.send(socket);
                }
            }
        }
    }
}
