# Ground Control Station(GCS) application for DVD
 This software aims to tie together various parts of DVD into one cohesive UI  
 Uses JavaFX, Spring Boot and PF4J
 
 ## Current Development Status
 - [x] Text PF4J plugin integrations
 - [x] Set up development environment
 - [x] Set up demo application
 - [x] Integration of ZeroMQ messaging as a Plugin (receive telemetry and send commands)
 - [x] Integration of JavaCV RTSP consumption as a Plugin
 - [x] Integration of Luciad Lightspeed as a Plugin
 - [x] Modification of DJIAAPP to fit current usecase (receive commands and send replies to GCS)
 - [x] Designing of UI
 - [ ] Wiring UI to inteded behavior
 
 ## Future Development Targets
 - Initializing Deepstream Docker container from gcs-app
 

 ## Developer Environment
 ### Installation Prerequisites
 - Java 17 SDK
 - JavaFX version 17
 - Gradle 7.4.2
 - rtsp-simple-server
 - ffmpeg-5.0.1-essentials
 - Bytecode JavaCV
 - Luciad Lightspeed
 
 ### Luciad Lightspeed Setup
 - Requires Network Loopback Adapter (for internet networks like DSTA)
   1. In Control Panel, double-click Add Hardware, and then click Next
   2. Click Yes, I have already connected the hardware, and then click Next.
   3. At the bottom of the Installed hardware list, click Add a new hardware device, and then click Next.
   4. Click Install the hardware that I manually select from a list, and then click Next
   5. In the Common hardware types list, click Network adapters, and then click Next.
   6. In the Manufacturers list box, click Microsoft.
   7. In the Network Adapter list box, click Microsoft Loopback Adapter, and then click Next.
   8. Click Next to start installing the drivers for your hardware.
   9. Click Finish.

## Deployment

### Prerequisites
- Java 17 SDK
- Python 3
- adb (Android Debug Bridge)
- A computer with a wifi card, GPU and running a Linux OS.

### Stand-alone GCS App Setup Instructions
1. Download the artifacts required from the releases page on GitHub. A release should contain the application file named app.jar as well as a folder called plugins. 
2. After downloading both the folder and the .jar file, place them in the same location. 
3. Move to the location directory of the above files.
4. Run the following command: `java -jar app.jar`

### Integrated GCS App Setup Instructions
1. Download the artifacts required from the releases page on GitHub. A release should contain the application file named app.jar as well as a folder called plugins. 
2. After downloading both the folder and the .jar file, place them in the same location. (Now refered to as installation directory)
3. Download receiveamqp3.py from DvdRepoMain.
4. Place receiveamqp3.py in a new folder called scripts in the installation directory.
5. Install Deepstream 6.1 by following the installation instructions outlined in deepstream-6.1 in DvdRepoMain.
6. Move to the installation directory.
7. Run the following command: `java -jar app.jar`
