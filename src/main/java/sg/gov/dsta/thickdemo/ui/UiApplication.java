package sg.gov.dsta.thickdemo.ui;

import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;
import sg.gov.dsta.thickdemo.ThickDemoApplication;


public class UiApplication extends Application {

    private ConfigurableApplicationContext applicationContext;

    @Override
    public void init() {
        applicationContext = new SpringApplicationBuilder(ThickDemoApplication.class).run();
    }

    @Override
    public void start(Stage stage) {
        applicationContext.publishEvent(new StageReadyEvent(stage));
    }

    @Override
    public void stop() {
        applicationContext.stop();
    }

    static class StageReadyEvent extends ApplicationEvent {
        public UiMainWindow uiMainWindow;

        public StageReadyEvent(Stage stage) {
            super(stage);
            this.uiMainWindow = new UiMainWindow(stage);
        }

        public UiMainWindow getMainWindow() {
            return this.uiMainWindow;
        }
    }
}
