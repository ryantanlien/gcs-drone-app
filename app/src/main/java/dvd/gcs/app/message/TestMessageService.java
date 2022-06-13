package dvd.gcs.app.message;

import java.util.ArrayList;

public class TestMessageService implements MessageTransmitEventListener<String> {

    @Override
    public void handleEvent(MessageTransmitEvent<String> event) {
        ArrayList<String> data = (ArrayList<String>) event.getData();
        System.out.println(data.get(0));
    }
}
