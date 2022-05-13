package sg.gov.dsta.thickdemo.ui;

import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import sg.gov.dsta.thickdemo.ThickDemoApplication;

@Component
public class UiApplication extends Application {

    @Autowired
    FxButtonFactory fxButtonFactory;

    @Autowired
    FxGridPaneFactory fxGridPaneFactory;

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
        applicationContext.getBeanFactory().registerResolvableDependency(Button.class, fxButtonFactory);
        applicationContext.getBeanFactory().registerResolvableDependency(GridPane.class, fxGridPaneFactory);
        System.out.println(applicationContext.getBean(GridPane.class).toString());
    }
}
