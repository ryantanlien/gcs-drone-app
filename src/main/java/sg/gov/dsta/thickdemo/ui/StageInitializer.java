package sg.gov.dsta.thickdemo.ui;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class StageInitializer implements ApplicationListener<UiApplication.StageReadyEvent> {

    @Override
    public void onApplicationEvent(UiApplication.StageReadyEvent event) {
        UiMainWindow uiMainWindow = event.getMainWindow();
        uiMainWindow.show();
    }
}
