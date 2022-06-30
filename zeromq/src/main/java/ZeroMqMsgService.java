import dvd.gcs.app.message.DroneCommandMessage;
import dvd.gcs.app.message.DroneCommandReplyMessage;
import dvd.gcs.app.message.DroneJson;
import dvd.gcs.app.message.DroneTelemetryMessage;
import org.zeromq.ZMsg;

import java.util.ArrayList;

//Assembles the stateless messages into messages with state to be passed to the application
public class ZeroMqMsgService {

    public static DroneTelemetryMessage decodeTelemetryMsg(ArrayList<String> strings) throws Exception {
        if (!strings.get(0).equals("TELEMETRY")) {
            throw new Exception("Incorrect Message Type received! Expected: TELEMETRY | Obtained: " + strings.get(0));
        }
        DroneJson droneJson = new DroneJson(strings.get(1), DroneJson.Type.TELEMETRY);
        return new DroneTelemetryMessage(droneJson);
    }

    public static DroneCommandReplyMessage decodeCommandReplyMsg(ArrayList<String> strings) throws Exception {
        if (!strings.get(0).equals("COMMAND_REPLY")) {
            throw new Exception("Incorrect Message Type received! Expected: COMMAND_REPLY | Obtained: " + strings.get(0));
        }

        DroneJson droneJson = new DroneJson(strings.get(1), DroneJson.Type.COMMAND_REPLY);
        if (strings.get(2).equals("COMMAND_SUCCESS")) {
            return new DroneCommandReplyMessage(droneJson, DroneCommandReplyMessage.CommandStatus.COMMAND_SUCCESS);
        } else {
            return new DroneCommandReplyMessage(droneJson, DroneCommandReplyMessage.CommandStatus.COMMAND_FAILURE);
        }
    }

    public static ZMsg encodeCommandMsg(DroneCommandMessage droneCommandMessage) {
        ZMsg msg = new ZMsg();
        msg.addString("COMMAND");
        msg.addString(droneCommandMessage.getData().getJson());
        msg.addString(droneCommandMessage.getCommandType().toString());
        return msg;
    }
}
