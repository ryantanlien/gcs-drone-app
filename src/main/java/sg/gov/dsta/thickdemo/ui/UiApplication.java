package sg.gov.dsta.thickdemo.ui;

import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import sg.gov.dsta.thickdemo.ThickDemoApplication;


public class UiApplication extends Application {

    @Autowired
    FxButtonFactory fxButtonFactory;
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
    }

//    @Component
//    static class StageReadyEvent extends ApplicationEvent {
//
//        @Autowired
//        public UiMainWindow uiMainWindow;
//
//        public StageReadyEvent(Stage stage) {
//            super(stage);
//            uiMainWindow = new UiMainWindow(stage);
//        }
//
//        public UiMainWindow getMainWindow() {
//            return this.uiMainWindow;
//        }
//    }
}
