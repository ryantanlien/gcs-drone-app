import dvd.gcs.app.message.DroneCommandMessage;
import dvd.gcs.app.message.DroneCommandReplyMessage;
import dvd.gcs.app.message.DroneJson;
import dvd.gcs.app.message.DroneTelemetryMessage;
import org.zeromq.ZMsg;

import java.util.ArrayList;

//Assembles the stateless messages into messages with state to be passed to the application
public class ZeroMqMsgService {

    private static String mostRecentCommandType = "";

    public static DroneTelemetryMessage decodeTelemetryMsg(ArrayList<String> strings) throws Exception {

        int TELEMETRY_INDEX = 0;

        if (!strings.get(TELEMETRY_INDEX).equals("TELEMETRY")) {
            throw new Exception("Incorrect Message Type received! Expected: TELEMETRY | Obtained: " + strings.get(TELEMETRY_INDEX));
        }
        DroneJson droneJson = new DroneJson(strings.get(1), DroneJson.Type.TELEMETRY);
        return new DroneTelemetryMessage(droneJson);
    }

    public static DroneCommandReplyMessage decodeCommandReplyMsg(ArrayList<String> strings) throws Exception {

        int COMMAND_REPLY_INDEX = 0;
        int COMMAND_TYPE_INDEX = 1;
        int COMMAND_REPLY_STATUS_INDEX = 2;
        int COMMAND_REPLY_JSON_INDEX = 3;

        if (!strings.get(COMMAND_REPLY_INDEX).equals("COMMAND_REPLY")) {
            throw new Exception("Incorrect Message Type received! Expected: COMMAND_REPLY | Obtained: " + strings.get(COMMAND_REPLY_INDEX));
        }

        if (strings.get(COMMAND_REPLY_STATUS_INDEX).equals("COMMAND_SUCCESS")) {
            DroneJson droneJson = new DroneJson(strings.get(COMMAND_REPLY_JSON_INDEX), DroneJson.Type.COMMAND_REPLY);
            return new DroneCommandReplyMessage(
                    droneJson,
                    DroneCommandReplyMessage.CommandStatus.COMMAND_SUCCESS,
                    decodeCommandType(strings.get(COMMAND_TYPE_INDEX)));
        } else if (strings.get(COMMAND_REPLY_INDEX).equals("COMMAND_FAILURE")) {
            return new DroneCommandReplyMessage(
                    null,
                    DroneCommandReplyMessage.CommandStatus.COMMAND_FAILURE,
                    decodeCommandType(strings.get(COMMAND_TYPE_INDEX)));
        } else {
            return new DroneCommandReplyMessage(
                    null,
                    DroneCommandReplyMessage.CommandStatus.FAILED_TO_SEND,
                    decodeCommandType(strings.get(COMMAND_TYPE_INDEX)));
        }
    }

    public static ZMsg encodeCommandMsg(DroneCommandMessage droneCommandMessage) {
        ZMsg msg = new ZMsg();
        msg.addString("COMMAND");
        msg.addString(droneCommandMessage.getCommandType().toString());
        msg.addString(droneCommandMessage.getData().getJson());
        mostRecentCommandType = droneCommandMessage.getCommandType().toString();
        return msg;
    }

    private static DroneCommandMessage.CommandType decodeCommandType(String string) {
        return switch (string) {
            case "SET_GEOFENCE" -> DroneCommandMessage.CommandType.SET_GEOFENCE;
            case "SET_ALTITUDE" -> DroneCommandMessage.CommandType.SET_ALTITUDE;
            case "SET_MAX_SPEED" -> DroneCommandMessage.CommandType.SET_MAX_SPEED;
            case "UPLOAD_MISSION" -> DroneCommandMessage.CommandType.UPLOAD_MISSION;
            case "START_MISSION" -> DroneCommandMessage.CommandType.START_MISSION;
            case "STOP_MISSION" -> DroneCommandMessage.CommandType.STOP_MISSION;
            case "START_TAKEOFF" -> DroneCommandMessage.CommandType.START_TAKEOFF;
            case "START_LANDING" -> DroneCommandMessage.CommandType.START_LANDING;
            default -> null;
        };
    }

    public static DroneCommandReplyMessage getFailedToSendCommandReply() {
        return new DroneCommandReplyMessage(
                null,
                DroneCommandReplyMessage.CommandStatus.FAILED_TO_SEND,
                decodeCommandType(mostRecentCommandType));
    }
}
