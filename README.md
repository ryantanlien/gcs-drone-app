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

An event bus implementation was chosen as Spring Boot already provides an event bus called the ApplicationEventPublisher. Using an event bus also provides flexibility in the application as multiple classes can listen in to the same event and have different behaviors based on their role. For instance, different Ui controller classes can listen in to events that are emited when the model changes, allowing them to define how to handle their own Ui components after receiving the event. The event bus also prevents needless use of composition throughout the application, instead only loosely coupling components, as they are coupled to events and the event bus instead.

### JavaFX Integration with Spring Boot
This application provides a lightweight Ui framework that integrates JavaFX and Spring Boot. The application registers any required JavaFX components as Spring Beans, and hence these classes can be handled by Spring, allowing the programmer to use all the convenience that Spring provides when writing behaviors for Ui components. 
The framework also defines useful defaults for JavaFX. Commonly, controllers for JavaFX for each component must be defined manually inside the FXML file or programatically. With the framework we provide, the controller for each JavaFX component with an FXML file is by default the Java class that instantiates it, allowing one to easily program behaviors for different Ui components and to build up a useful Ui library. 

A good example of this usage is in the `dvd.gcs.app.ui.video` package. The basis of this framework is the `UiElement` and `JavaFxConfig` class, and it is highly recommended that you examine these classes for a full understanding of the integration details.  

### Use of Plugins
PF4J provides a plugin framework for the application. This allows the application to merely define appropiate behaviors and contracts for plugins using interfaces, which plugins then provide. For instance, messaging between GCS and DJIAAPP is currently handled by a ZeroMQ (a messaging API) plugin. The API used can be changed from ZeroMQ to any other messaging API by writing a new plugin as long as the contract defined by GCS is met. This makes code re-use easy and allows rapid changes in implementation details.

Any configuration of PF4J and its plugins is done in the class `Pf4jConfig`. With the exception of the Luciad plugin, any interface that defines plugin behavior has the prefix Pf4j are -ables. For instance `Pf4jMessagable`

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
