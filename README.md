# Software application for DVD
 This software aims to tie together various parts of DVD into one cohesive UI  
 Uses JavaFX, Spring Boot and PF4J
 
 ## Current Development Status
 - [x] Text PF4J plugin integrations
 - [x] Set up development environment
 - [x] Set up demo application
 - [x] Integration of ZeroMQ messaging as a Plugin (to receive telemetry and send commands)
 - [x] Integration of JavaCV RTSP consumption as a Plugin
 - [x] Integration of Luciad Lightspeed as a Plugin
 - [x] Designing of UI
 - [ ] Wiring UI to inteded behavior
 
 ## Future Development Targets
 - Initializing Deepstream Docker container from gcs-app
 
 ## Installation Prequisites
 - Java 17
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
