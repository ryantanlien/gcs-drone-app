# Ground Control Station(GCS) application for DVD
 This software aims to tie together various parts of DVD into one cohesive UI  
 Uses JavaFX, Spring Boot, PF4J and LuciadLightspeed
 
 ## Current Development Status
 - [x] Text PF4J plugin integrations
 - [x] Set up development environment
 - [x] Set up demo application
 - [x] Integration of ZeroMQ messaging as a Plugin (receive telemetry and send commands)
 - [x] Integration of JavaCV RTSP consumption as a Plugin
 - [x] Integration of Luciad Lightspeed as a Plugin
 - [x] Modification of DJIAAPP to fit current usecase (receive commands and send replies to GCS)
 - [x] Designing of UI
 - [x] Wiring UI to inteded behavior (Minimal, can improve by implementing value sliders and notifications)
 
 ## Future Development Targets
 - Running gcs-app on Linux OS
 - Register Drone on gcs-app via communication with DJIAAPP rather than to hard-code registration
 - Launching Deepstream, Simple RTSP Servers and Docker Container from gcs-app
 - Improvement of UI (Especially the Settings Window, and LuciadLightspeed custom vectors) 
 - Toggling of chase mode (Requires altering the DJIAAPP)

 ## Recommendations before embarking on Future Development Targets
 - Pay off technical debt by refactoring (especially the DroneMessageService.java switch statement, convert to command pattern)
 - Writing Javadocs
 - Write new gradle tasks to automatically set up a deployment folder and to place new plugin jars in `./plugins` folder for more development convenience.

 ## Development Environment
 ### Installation Software Prerequisites
 - Running on a Windows system (Linux deployment on the way)
 - Java 17 SDK
 - JavaFX version 17
 - Gradle 7.4.2
 - rtsp-simple-server
 - ffmpeg-5.0.1-essentials
 - Luciad Lightspeed

### Knowledge Prequisites (To start development)
 This guide assumes that you have basic working knowledge of the following technologies:
 1. Spring Boot
 2. JavaFX
 3. Git
 4. Gradle
 
 This guide recommends reading up and obtaining a minimum understanding of the following technologies:
 1. Real-time messaging systems, preferably ZeroMQ
 2. Plugin architectures, preferably PF4J
 3. Map APIs, preferably LuciadLightspeed

## Developer Quickstart
All steps in this Quickstart are ***MANDATORY*** to get gcs-app running on a developer machine.
This Quickstart guide assumes you have completed setting up the developer environment.
 
### Luciad Lightspeed Setup
#### Registering the License, requires Network Loopback Adapter (for internet networks like DSTA)
1. In Control Panel, double-click Add Hardware, and then click Next
2. Click Yes, I have already connected the hardware, and then click Next.
3. At the bottom of the Installed hardware list, click Add a new hardware device, and then click Next.
4. Click Install the hardware that I manually select from a list, and then click Next
5. In the Common hardware types list, click Network adapters, and then click Next.
6. In the Manufacturers list box, click Microsoft.
7. In the Network Adapter list box, click Microsoft Loopback Adapter, and then click Next.
8. Click Next to start installing the drivers for your hardware.
9. Click Finish.

#### Obtaining files needed to run LuciadLightspeed
1. LuciadLightspeed requires .shp files to render the map, as well as several dependencies and license files. These files can be located in the shared hard drive.
2. LuciadLightspeed dependencies cannot be obtained from online as they are DSTA proprietary material, and must be obtained from the hard drive or Huei Rong (DSTA permanent staff)
3. .shp files can be obtained online by following step 4 onwards.
4. Download .shp.zip files for Malaysia-Singapore and Brunei at the following [link](https://download.geofabrik.de/asia.html)
5. Place these files at the path `luciadlightspeed/src/main/resources/singapore-msia-brunei`
6. Download these files from [here](https://drive.google.com/drive/folders/1rlqtaWWYmTvVnZtkKfjdBp1yLu_CmS28) and place in the path `luciadlightspeed/src/main/resources/singapore-shp`
 
### Plugin Setup
This step assumes you already have LuciadLightspeed set up correctly.
1. Enable annotation processing in your IDE. (PF4J builds plugin jars with a file called extensions.idx that is needed to run the plugin jars at application runtime)
2. Run gradle.build. 
3. After building successfully, in each submodule `luciadlightspeed`, `zeromq` and `ffmpeg`, under the `build/libs` folder there will be a fat jar built containing all the class files of each plugin and their dependencies.
4. Copy these jar files to the plugins folder of the project root. This step and folder structure is ***NON-NEGOTIABLE*** until you have a complete understanding of PF4J and how to configure it.

### Running the app
1. Run `ThickDemoApplication.java`OR
2. `gradle run` from the project root does not work unless you alter the variable `PLUGIN_DIR` in `Pf4jConfig.java` to `../plugins`. 
 
***WARNING***: Step 2 will cause the deployment jar to fail if you do build one after this step. Change `PLUGIN_DIR` back to `./plugins` before deployment.
 
---
 
## Software Architecture and Design
 
### High-level Overview
This application is built using a traditional model-view controller design (MVC) with an event bus implementation. The view is handled by JavaFX where as the model and the controller are handled by Spring Boot. PF4J extensions and their classes live outside the Spring Application Context and are not handled by Spring. However, interface classes that define the functionality of plugin classes are indeed handled by Spring and can be treated like any other Spring handled class. 

![Architecture of GCS](https://github.com/ryantanlien/gcs-app/blob/safe/docs/architecture.png) 

An event bus implementation was chosen as Spring Boot already provides an event bus called the ApplicationEventPublisher. Using an event bus also provides flexibility in the application as multiple classes can listen in to the same event and have different behaviors based on their role. For instance, different Ui controller classes can listen in to events that are emited when the model changes, allowing them to define how to handle their own Ui components after receiving the event. The event bus also prevents needless use of composition throughout the application, instead only loosely coupling components, as they are coupled to events and the event bus instead.

### JavaFX Integration with Spring Boot
This application provides a lightweight Ui framework that integrates JavaFX and Spring Boot. The application registers any required JavaFX components as Spring Beans, and hence these classes can be handled by Spring, allowing the programmer to use all the convenience that Spring provides when writing behaviors for Ui components. 
The framework also defines useful defaults for JavaFX. Commonly, controllers for JavaFX for each component must be defined manually inside the FXML file or programatically. With the framework we provide, the controller for each JavaFX component with an FXML file is by default the Java class that instantiates it, allowing one to easily program behaviors for different Ui components and to build up a useful Ui library. 

A good example of this usage is in the `dvd.gcs.app.ui.video` package. The basis of this framework is the `UiElement` and `JavaFxConfig` class, and it is highly recommended that you examine these classes for a full understanding of the integration details.  

<details>
 
  <summary>
   <ul>UiElement.java</ul>
  </summary>
 
``` Java
package dvd.gcs.app.ui.api;

import dvd.gcs.app.ThickDemoApplication;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.net.URL;

import static java.util.Objects.requireNonNull;

public abstract class UiElement<T> {

    private final FXMLLoader fxmlLoader = new FXMLLoader();

    public static final String FXML_FILE_FOLDER = "/view/";

    public UiElement(URL fxmlFileURL) {
        loadFxmlFile(fxmlFileURL, null);
    }

    public UiElement(URL fxmlFileURL, T root) {
        loadFxmlFile(fxmlFileURL, root);
    }

    public UiElement(String fxmlFileName) {
        this(getFxmlFileURL(fxmlFileName));
    }

    public UiElement(String fxmlFileName, T root) {
        this(getFxmlFileURL(fxmlFileName), root);
    }

    public T getRoot() {
        return fxmlLoader.getRoot();
    }

    private void loadFxmlFile(URL location,  T root) {
        requireNonNull(location);
        fxmlLoader.setLocation(location);
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(root);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

    private static URL getFxmlFileURL(String fxmlFileName) {
        requireNonNull(fxmlFileName);
        String fxmlFileNameWithFolder = FXML_FILE_FOLDER + fxmlFileName;

        System.out.println(fxmlFileNameWithFolder);

        URL fxmlFileUrl = ThickDemoApplication.class.getResource(fxmlFileNameWithFolder);
        return requireNonNull(fxmlFileUrl);
    }
}
```
</details>

<details>
 
 <summary>
  <ul>JavaFxConfig.java</ul> 
 </summary>
 
``` Java
package dvd.gcs.app.cfg;

import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * A Spring Configuration class that declares instances JavaFX classes as managed by Spring,
 * allowing Spring to instantiate these classes in its application context.
 */
@Configuration
public class JavaFxConfig {
    /**
     * Defines a JavaFX button to be managed by Spring.
     * It has a prototype scope thus multiple separate instances of the class can be instantiated.
     *
     * @return a JavaFX Button managed by Spring.
     */
    @Bean
    @Scope("prototype")
    public Button getButton() {
        return new Button();
    }

    /**
     * Defines a JavaFX GridPane to be managed by Spring.
     * It has a prototype scope thus multiple separate instances of the class can be instantiated.
     *
     * @return a JavaFX GridPane managed by Spring.
     */
    @Bean
    @Scope("prototype")
    public GridPane getGridPane() {
        return new GridPane();
    }

    /**
     * Defines a JavaFX MenuBar to be managed by Spring.
     * It has a prototype scope thus multiple separate instances of the class can be instantiated.
     *
     * @return a JavaFX MenuBar managed by Spring.
     */
    @Bean
    @Scope("prototype")
    public MenuBar getMenuBar() {
       return new MenuBar();
    }
  
    /**
     * Defines a JavaFX TitledPane to be managed by Spring.
     * It has a prototype scope thus multiple separate instances of the class can be instantiated.
     *
     * @return a JavaFX TitledPane managed by Spring.
     */
    @Bean
    @Scope("prototype")
    public TitledPane getTitledPane() {
        return new TitledPane();
    }

    /**
     * Defines a JavaFX Pane to be managed by Spring.
     * It has a prototype scope thus multiple separate instances of the class can be instantiated.
     *
     * @return a JavaFX Pane managed by Spring.
     */
    @Bean("Pane")
    @Scope("prototype")
    public Pane getPane() {
        return new Pane();
    }

    /**
     * Defines a JavaFX VBox to be managed by Spring.
     * It has a prototype scope thus multiple separate instances of the class can be instantiated.
     *
     * @return a JavaFX VBox managed by Spring.
     */
    @Bean("VBox")
    @Scope("prototype")
    public VBox getVBox() {
        return new VBox();
    }

    /**
     * Defines a JavaFX HBox to be managed by Spring.
     * It has a prototype scope thus multiple separate instances of the class can be instantiated.
     *
     * @return a JavaFX HBox managed by Spring.
     */
    @Bean("HBox")
    @Scope("prototype")
    public HBox getHBox() {
        return new HBox();
    }


    /**
     * Defines a JavaFX StackPane to be managed by Spring.
     * It has a prototype scope thus multiple separate instances of the class can be instantiated.
     *
     * @return a JavaFX StackPane managed by Spring.
     */
    @Bean("StackPane")
    @Scope("prototype")
    public StackPane getStackPane() {
        return new StackPane();
    }

    /**
     * Defines a JavaFX AnchorPane to be managed by Spring.
     * It has a prototype scope thus multiple separate instances of the class can be instantiated.
     *
     * @return a JavaFX AnchorPane managed by Spring.
     */
    @Bean("AnchorPane")
    @Scope("prototype")
    public AnchorPane getAnchorPane() {
        return new AnchorPane();
    }

    /**
     * Defines a JavaFX BorderPane to be managed by Spring.
     * It has a prototype scope thus multiple separate instances of the class can be instantiated.
     *
     * @return a JavaFX BorderPane managed by Spring.
     */
    @Bean("BorderPane")
    @Scope("prototype")
    public BorderPane getBorderPane() {
        return new BorderPane();
    }
}
```
</details>

### Use of Plugins
PF4J provides a plugin framework for the application. This allows the application to merely define appropiate behaviors and contracts for plugins using interfaces, which plugins then provide. For instance, messaging between GCS and DJIAAPP is currently handled by a ZeroMQ (a messaging API) plugin. The API used can be changed from ZeroMQ to any other messaging API by writing a new plugin as long as the contract defined by GCS is met. This makes code re-use easy and allows rapid changes in implementation details.

Any configuration of PF4J and its plugins is done in the class `Pf4jConfig`. With the exception of the Luciad plugin, any interface that defines plugin behavior has the prefix Pf4j and postfix -ables. For instance, `Pf4jMessagable`.

### ZeroMQ messaging contract
As mentioned in the Use of Plugins section, this application uses ZeroMQ to communicate to the other components of Drone versus Drone, namely the DJIAAPP and the PID controller. Here we will record and define the two messaging contracts used for the communication with DJIAAPP. For all messaging contracts, data is sent as strings in a multi-part message. 

#### Telemetry
For GCS to receive telemetry from DJIAAPP, we use a pub-sub implementation and assume that DJIAAPP sends an infinite stream of data. DJIAAPP acts as the publisher and GCS as the subscriber.

The messaging contract is as follows:
1. Part 1: Message Type
2. Part 2: Telemetry Data

Message Type is "TELEMETRY" whereas Telemetry Data is a JSON string representing drone attributes and settings.

#### Commands
For DJIAAPP to receive commands from GCS, we use a reliable-request reply implementation known as the Lazy Pirate or Simple Pirate pattern. Every request sent to DJIAAPP from GCS must be replied to by DJIAAPP, or the command fails. DJIAAPP acts as the server receiving requests while GCS acts as the client sending requests. 

The messaging contract from GCS to DJIAAPP is as follows:
1. Part 1: Message Type
2. Part 2: Command Type
3. Part 3: Command Data

Message Type is "COMMAND".

Command Type is a string representing the command sent from GCS to DJIAAPP eg: "START_TAKEOFF"

Command Data is a JSON string representing drone attributes to be set.

The messaging contract from DJIAAPP to GCS is as follows:
1. Part 1: Message Type
2. Part 2: Command Type
3. Part 3: Command Status
4. Part 4: Command Data

Message Type is "COMMAND_REPLY".

Command Type is a string representing the command sent from GCS to DJIAAPP eg: "START_TAKEOFF"

Command Status is a string representing the status of the processing of the command by DJIAAPP. This takes the form "COMMAND_SUCCESS" OR "COMMAND_FAILURE" OR "FAILED_TO_SEND"

Command Data is a JSON string representing drone attributes to be set.
 
<details>
 <summary>
  ZeroMqMsgService.java
 </summary>

``` Java
import dvd.gcs.app.message.DroneCommandMessage;
import dvd.gcs.app.message.DroneCommandReplyMessage;
import dvd.gcs.app.message.DroneJson;
import dvd.gcs.app.message.DroneTelemetryMessage;
import org.zeromq.ZMsg;

import java.util.ArrayList;

//Assembles the stateless messages into messages with state to be passed to the application
public class ZeroMqMsgService {

    private static String mostRecentCommandType = "";

    public static DroneTelemetryMessage decodeTelemetryMsg(ArrayList<String> strings) throws Exception {

        int TELEMETRY_INDEX = 0;

        if (!strings.get(TELEMETRY_INDEX).equals("TELEMETRY")) {
            throw new Exception("Incorrect Message Type received! Expected: TELEMETRY | Obtained: " + strings.get(TELEMETRY_INDEX));
        }
        DroneJson droneJson = new DroneJson(strings.get(1), DroneJson.Type.TELEMETRY);
        return new DroneTelemetryMessage(droneJson);
    }

    public static DroneCommandReplyMessage decodeCommandReplyMsg(ArrayList<String> strings) throws Exception {

        int COMMAND_REPLY_INDEX = 0;
        int COMMAND_TYPE_INDEX = 1;
        int COMMAND_REPLY_STATUS_INDEX = 2;
        int COMMAND_REPLY_JSON_INDEX = 3;

        if (!strings.get(COMMAND_REPLY_INDEX).equals("COMMAND_REPLY")) {
            throw new Exception("Incorrect Message Type received! Expected: COMMAND_REPLY | Obtained: " + strings.get(COMMAND_REPLY_INDEX));
        }

        if (strings.get(COMMAND_REPLY_STATUS_INDEX).equals("COMMAND_SUCCESS")) {
            DroneJson droneJson = new DroneJson(strings.get(COMMAND_REPLY_JSON_INDEX), DroneJson.Type.COMMAND_REPLY);
            return new DroneCommandReplyMessage(
                    droneJson,
                    DroneCommandReplyMessage.CommandStatus.COMMAND_SUCCESS,
                    decodeCommandType(strings.get(COMMAND_TYPE_INDEX)));
        } else if (strings.get(COMMAND_REPLY_INDEX).equals("COMMAND_FAILURE")) {
            return new DroneCommandReplyMessage(
                    null,
                    DroneCommandReplyMessage.CommandStatus.COMMAND_FAILURE,
                    decodeCommandType(strings.get(COMMAND_TYPE_INDEX)));
        } else {
            return new DroneCommandReplyMessage(
                    null,
                    DroneCommandReplyMessage.CommandStatus.FAILED_TO_SEND,
                    decodeCommandType(strings.get(COMMAND_TYPE_INDEX)));
        }
    }

    public static ZMsg encodeCommandMsg(DroneCommandMessage droneCommandMessage) {
        ZMsg msg = new ZMsg();
        msg.addString("COMMAND");
        msg.addString(droneCommandMessage.getCommandType().toString());
        msg.addString(droneCommandMessage.getData().getJson());
        mostRecentCommandType = droneCommandMessage.getCommandType().toString();
        return msg;
    }

    private static DroneCommandMessage.CommandType decodeCommandType(String string) {
        return switch (string) {
            case "SET_GEOFENCE" -> DroneCommandMessage.CommandType.SET_GEOFENCE;
            case "SET_ALTITUDE" -> DroneCommandMessage.CommandType.SET_ALTITUDE;
            case "SET_MAX_SPEED" -> DroneCommandMessage.CommandType.SET_MAX_SPEED;
            case "UPLOAD_MISSION" -> DroneCommandMessage.CommandType.UPLOAD_MISSION;
            case "START_MISSION" -> DroneCommandMessage.CommandType.START_MISSION;
            case "STOP_MISSION" -> DroneCommandMessage.CommandType.STOP_MISSION;
            case "START_TAKEOFF" -> DroneCommandMessage.CommandType.START_TAKEOFF;
            case "START_LANDING" -> DroneCommandMessage.CommandType.START_LANDING;
            default -> null;
        };
    }

    public static DroneCommandReplyMessage getFailedToSendCommandReply() {
        return new DroneCommandReplyMessage(
                null,
                DroneCommandReplyMessage.CommandStatus.FAILED_TO_SEND,
                decodeCommandType(mostRecentCommandType));
    }
}
```
</details>


---
 
## Testing
This assumes that you have already read the Quick Start on how to run the GCS Developer Quickstart.

### Testing viewing RTSP on GCS App
- Download rtsp-simple-server and ffmpeg-5.0.1.
- Download a video sample of a drone from a hard drive and save to a desired folder.
- Run the `rtsp-simple-server.exe`
- Assuming you have ffmpeg-5.0.1-essentials, navigate to the location where the video sample is installed and run this command: `ffmpeg -re -stream_loop -1 -i (insert  sample file name here)-c copy -f rtsp rtsp://localhost:8554/mystream`
- Configure the GCS app.properties file, changing the field `deepstream-url` to `rtsp://localhost:8554/mystream`
- Run GCS and view RTSP output in the Drone Feed window.
 
#### Configuration for viewing RTSP
- When downloading rtsp-simple-server, the program will be placed in a zip with a .exe and a .yml file.
- Configuration such as what URL the video will be streamed to will be done in the .yml file.


### Testing sending Telemetry or Command Messages via ZeroMQ
- Classes `hwserver.java` and `hwclient.java` are for you to test sending telemetry and command messages to local servers that mimick the `DJIAAPP` behavior.
- Configure the GCS app.properties file, changing the property `app.djiaapp-ip` to `localhost`
- Run `hwserver.java` or `hwclient.java`
- Send messages with the GCS app by interacting with the Ui or by configuring `AutoTestClass.java` that publishes events to send messages on app start-up.
- Check output of `hwserver.java` or `hwclient.java`to confirm that messages have been sent correctly.
 
### Building the app for deployment
- Run `gradle build` from the project root.
- Copy the built jar file into a deployment folder (of your choosing) from `app/build/libs` and rename to app.jar.
- Update all plugins in the `/plugins` folder in project root (Follow Plugin Setup if unclear)
- Copy the plugins folder into the deployment folder
- Copy the all folders in the path `luciadlightspeed/src/main/resources` into the deployment folder
- Place all files in the deployment folder into a GitHub release (optional but recommended before a field test)

---

## Deployment

### Prerequisites
- Java 17 SDK
- A computer with a wifi card, GPU and running a Windows OS.

### Stand-alone GCS App Setup Instructions
1. Download the artifacts required from the releases page on GitHub. A release should contain the application file named app.jar as well as a folder called plugins. 
2. After downloading both the folder and the .jar file, place them in the same location. 
3. Move to the location directory of the above files.
5. Run the following command: `java -jar app.jar`
6. A file named `app.properties` with default values will be created after the first run. Configure the property `app.deepstream-url` to the RTSP server that Deepstream publishes to.

### Integrated GCS App Setup Instructions
1. Download the artifacts required from the releases page on GitHub. A release should contain the application file named app.jar as well as a folder called plugins. 
2. After downloading both the folder and the .jar file, place them in the same location. (Now refered to as installation directory)
3. Download receiveamqp3.py from DvdRepoMain.
4. Place receiveamqp3.py in a new folder called scripts in the installation directory.
5. Install Deepstream 6.1 by following the installation instructions outlined in deepstream-6.1 in DvdRepoMain.
6. Move to the installation directory.
7. Run the following command: `java -jar app.jar`
