package dvd.gcs.app.ui;

import dvd.gcs.app.ui.components.UiMainWindow;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * A listener class that listens for a StageReadyEvent, and shows the JavaFX main window after all configurations have
 * been set.
 */
@Component
@Lazy
public class StageInitializer implements ApplicationListener<StageReadyEvent> {

    /**
     * Shows the JavaFX main window upon receiving a StageReadyEvent.
     *
     * @param event a StageReadyEvent that is emitted after completing all application configurations.
     */
    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        UiMainWindow uiMainWindow = event.getMainWindow();
        uiMainWindow.show();
        System.out.println("JavaFX Stage MaxWidth: " + uiMainWindow.getRoot().getWidth());
        System.out.println("JavaFX Stage MaxHeight: " + uiMainWindow.getRoot().getHeight());
    }
}
