package dvd.gcs.app.message;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class DroneTelemetryMessageService implements ApplicationListener<MessageReceivedEvent<String>> {

    @Override
    public void onApplicationEvent(MessageReceivedEvent<String> event) {
        System.out.println("Spring Message Received!");
        ArrayList<String> arrayList = (ArrayList<String>) event.getMessage();
        arrayList.forEach(System.out::println);

        //Implement business logic here
    }
}
