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
## Testing
This assumes that you have already read the Quick Start on how to run the GCS Developer Quickstart.

 ### Testing viewing RTSP on GCS App
 - Download rtsp-simple-server and ffmpeg-5.0.1.
 - Download a video sample of a drone from a hard drive and save to a desired folder.
 - Run the `rtsp-simple-server.exe`
 - Assuming you have ffmpeg-5.0.1-essentials, navigate to the location where the video sample is installed and run this command: `ffmpeg -re -stream_loop -1 -i (insert my sample file name here)-c copy -f rtsp rtsp://localhost:8554/mystream`
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
 
 ---
 
 ### Building the app for deployment
 - Run `gradle build` from the project root.
 - Copy the built jar file into a deployment folder (of your choosing) from `app/build/libs` and rename to app.jar.
 - Update all plugins in the `/plugins` folder in project root (Follow Plugin Setup if unclear)
 - Copy the plugins folder into the deployment folder
 - Copy the all folders in the path `luciadlightspeed/src/main/resources` into the deployment folder
 - Place all files in the deployment folder into a GitHub release (optional but recommended before a field test)

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
