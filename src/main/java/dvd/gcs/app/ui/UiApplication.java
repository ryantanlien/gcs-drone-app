package dvd.gcs.app.ui;

import javafx.application.Application;
import javafx.stage.Stage;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import dvd.gcs.app.ThickDemoApplication;

@Component
public class UiApplication extends Application {

    private ConfigurableApplicationContext applicationContext;

    @Override
    public void init() {
        applicationContext = new SpringApplicationBuilder(ThickDemoApplication.class).run();
    }

    @Override
    public void start(Stage stage) {
        configureApplicationContext(applicationContext, stage);
        StageReadyEvent stageReadyEvent = applicationContext.getBean(StageReadyEvent.class);
        applicationContext.publishEvent(stageReadyEvent);
    }

    @Override
    public void stop() {
        applicationContext.stop();
    }

    public void configureApplicationContext(ConfigurableApplicationContext applicationContext, Stage stage) {
        applicationContext.getBeanFactory().registerResolvableDependency(Stage.class, stage);
    }
}
