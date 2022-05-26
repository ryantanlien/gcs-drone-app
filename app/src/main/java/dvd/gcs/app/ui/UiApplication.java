package dvd.gcs.app.ui;

import dvd.gcs.app.ThickDemoApplication;
import dvd.gcs.app.cfg.Pf4jConfig;
import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

/**
 * A wrapper written around the JavaFX Application, launched by the JavaFX.
 * This class is a handler that starts up all configuration classes needed for the application to run.
 * It includes setting up the Spring Application Context as well as PF4J project configurations.
 *
 * The class also overrides lifecycle methods that handle the JavaFX application.
 */
public class UiApplication extends Application {

    /** A spring application context that can be configured. **/
    private ConfigurableApplicationContext applicationContext;

    /** Initialises the Spring Application Context, providing the default configuration **/
    @Override
    public void init() {
        applicationContext = new SpringApplicationBuilder(ThickDemoApplication.class).run();
    }

    /**
     * Provides additional configuration to the Spring Application Context and PF4J plugins.
     *
     * Then signals that the all configurations have been set, allowing Spring to inject UI beans for JavaFX to show
     * it's window.
     *
     * @param stage the stage object provided by JavaFX after initialising the JavaFX application.
     */
    @Override
    public void start(Stage stage) {
        configureApplicationContext(applicationContext, stage);
        Pf4jConfig.initializePlugins();
        StageReadyEvent stageReadyEvent = applicationContext.getBean(StageReadyEvent.class);
        applicationContext.publishEvent(stageReadyEvent);
    }

    /**
     * Ends the Spring Application context.
     */
    @Override
    public void stop() {
        applicationContext.stop();
    }

    /**
     * Registers the stage instance provided by JavaFX as a Spring Bean, so that it can be managed by Spring,
     * and injected as a dependency via @Autowire.
     * @param applicationContext
     * @param stage
     */
    public void configureApplicationContext(ConfigurableApplicationContext applicationContext, Stage stage) {
        applicationContext.getBeanFactory().registerResolvableDependency(Stage.class, stage);
    }


}
