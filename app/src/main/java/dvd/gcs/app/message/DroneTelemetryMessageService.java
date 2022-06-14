package dvd.gcs.app.message;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class DroneTelemetryMessageService implements ApplicationListener<MessageReceivedEvent<StringCollectionMessage>> {

    @Override
    public void onApplicationEvent(MessageReceivedEvent<StringCollectionMessage> event) {
        System.out.println("Spring Message Received!");
        StringCollectionMessage stringCollectionMessage = event.getMessage();
        ArrayList<String> arrayList = (ArrayList<String>) stringCollectionMessage.getData();
        arrayList.forEach(System.out::println);

        //Implement business logic here
    }
}
