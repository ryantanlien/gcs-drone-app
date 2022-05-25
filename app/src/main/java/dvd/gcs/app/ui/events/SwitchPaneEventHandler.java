package dvd.gcs.app.ui.events;

import javafx.event.EventHandler;

public class SwitchPaneEventHandler implements EventHandler<SwitchPaneEvent> {
    @Override
    public void handle(SwitchPaneEvent event) {
        System.out.println("SwitchPaneEvent received");
    }
}
