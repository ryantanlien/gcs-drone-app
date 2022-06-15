package dvd.gcs.app.message;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class DroneTelemetryMessageService implements ApplicationListener<MessageReceivedEvent<DroneTelemetryMessage>> {

    @Override
    public void onApplicationEvent(MessageReceivedEvent<DroneTelemetryMessage> event) {
        System.out.println("Spring Message Received!");
        DroneTelemetryMessage droneTelemetryMessage = event.getMessage();
        DroneJson droneJson = droneTelemetryMessage.getData();
        System.out.println(droneJson.getJson());

        //Implement business logic here
    }
}
