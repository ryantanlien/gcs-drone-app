package sg.gov.dsta.thickdemo.ui;

import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class StageReadyEvent extends ApplicationEvent{
    @Autowired
    public UiMainWindow uiMainWindow;

    public StageReadyEvent(Stage stage) {
        super(stage);
    }

    public UiMainWindow getMainWindow() {
        return this.uiMainWindow;
    }
}

