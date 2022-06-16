import dvd.gcs.app.message.DroneTelemetryMessage;
import dvd.gcs.app.message.DroneJson;

import java.util.ArrayList;

//Assembles the stateless messages into
public class ZeroMqMsgService {

    public static DroneTelemetryMessage decodeTelemetryMsg(ArrayList<String> strings) throws Exception {
        if (!strings.get(0).equals("TELEMETRY")) {
            throw new Exception("Incorrect Message Type received! Expected: TELEMETRY Obtained: " + strings.get(0));
        }
        DroneJson droneJson = new DroneJson(strings.get(1), DroneJson.Type.TELEMETRY);
        return new DroneTelemetryMessage(droneJson);
    }
}
