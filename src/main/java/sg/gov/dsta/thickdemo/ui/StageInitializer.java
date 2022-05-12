package sg.gov.dsta.thickdemo.ui;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class StageInitializer implements ApplicationListener<StageReadyEvent> {

    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        UiMainWindow uiMainWindow = event.getMainWindow();
        uiMainWindow.show();
    }
}
