import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZFrame;
import org.zeromq.ZMQ;
import org.zeromq.ZMsg;

import java.util.ArrayList;

//Test server to receive command
public class hwserver {

    public static void main(String[] args2) {
        ZContext methodContext = null;
        try (ZContext context = new ZContext()) {
            methodContext = context;
            ArrayList<String> message = new ArrayList<>();

            System.out.println("Connecting to client");

            //Socket to talk to server
            ZMQ.Socket socket = context.createSocket(SocketType.REP);
            socket.bind("tcp://0.0.0.0:5557");

            ZMQ.Poller poller = context.createPoller(1);
            poller.register(socket);

            while(true) {
                int result = poller.poll(1000);
                if (result == -1) {
                    break;
                }
                if (poller.pollin(0)) {
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
                    processCommandMessage(message, socket);
                }
            }
        }
    }

    public static void processCommandMessage(ArrayList<String> message, ZMQ.Socket functionalitiesSocket) {
        // TODO: Upon receive request message from GCS, do functionality
        String messageType = message.get(0);
        String commandType = message.get(1);
        String commandData = message.get(2);
        if (!messageType.equals("COMMAND")) {
            return;
        }
        if (commandType.equals("SET_MAX_SPEED")) {
            int speedToSet = Integer.parseInt(commandData);
            ZMsg zMsg = new ZMsg();
            zMsg.addString("COMMAND_REPLY");
            zMsg.addString("SET_MAX_SPEED");
            zMsg.addString("COMMAND_SUCCESS");
            zMsg.addString("{" +
                    "\"droneCallSign\":\"" + "Alpha" + "\"," +
                    "\"maxVelocity\":\"" + commandData +
                    "}");
            zMsg.send(functionalitiesSocket);
        }

        if (commandType.equals("SET_ALTITUDE")) {
            float altitudeToSet = Float.parseFloat(commandData);
            ZMsg zMsg = new ZMsg();
            zMsg.addString("COMMAND_REPLY");
            zMsg.addString("SET_ALTITUDE");
            zMsg.addString("COMMAND_SUCCESS");
            zMsg.addString("{" +
                    "\"droneCallSign\":\"" + "Alpha" + "\"," +
                    "\"maxAltitude\":\"" + commandData +
                    "}");
            zMsg.send(functionalitiesSocket);
        }

        if (commandType.equals("UPLOAD_MISSION")) {
            String json = commandData;
            ZMsg zMsg = new ZMsg();
            zMsg.addString("COMMAND_REPLY");
            zMsg.addString("UPLOAD_MISSION");
            zMsg.addString("COMMAND_FAILURE");
            zMsg.addString("{" + "}");
            zMsg.send(functionalitiesSocket);
        }

        if (commandType.equals("START_MISSION")) {
            ZMsg zMsg = new ZMsg();
            zMsg.addString("COMMAND_REPLY");
            zMsg.addString("START_MISSION");
            zMsg.addString("COMMAND_SUCCESS");
            zMsg.addString("{" + "}");
            zMsg.send(functionalitiesSocket);
        }

        if (commandType.equals("STOP_MISSION")) {
            ZMsg zMsg = new ZMsg();
            zMsg.addString("COMMAND_REPLY");
            zMsg.addString("STOP_MISSION");
            zMsg.addString("COMMAND_SUCCESS");
            zMsg.addString("{" + "}");
            zMsg.send(functionalitiesSocket);
        }

        if (commandType.equals("START_TAKEOFF")) {
            ZMsg zMsg = new ZMsg();
            zMsg.addString("COMMAND_REPLY");
            zMsg.addString("START_TAKEOFF");
            zMsg.addString("COMMAND_SUCCESS");
            zMsg.addString("{" + "}");
            zMsg.send(functionalitiesSocket);
        }

        if (commandType.equals("START_LANDING")) {
            ZMsg zMsg = new ZMsg();
            zMsg.addString("COMMAND_REPLY");
            zMsg.addString("START_LANDING");
            zMsg.addString("COMMAND_SUCCESS");
            zMsg.addString("{" + "}");
            zMsg.send(functionalitiesSocket);
        }

        if (commandType.equals("SET_GEOFENCE")) {
            int geoFenceRadius = Integer.parseInt(commandData);
            ZMsg zMsg = new ZMsg();
            zMsg.addString("COMMAND_REPLY");
            zMsg.addString("SET_GEOFENCE");
            zMsg.addString("COMMAND_SUCCESS");
            zMsg.addString("{" +
                    "\"droneCallSign\":\"" + "Alpha" + "\"," +
                    "\"geoFenceRadius\":\"" + commandData +
                    "}");
            zMsg.send(functionalitiesSocket);
        }
    }
}
