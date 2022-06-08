package dvd.gcs.app.cfg;

import org.pf4j.ExtensionPoint;

public interface Pf4jMessagable extends ExtensionPoint {
    //Performs initialisation for a messaging class
    void init();

    //Performs termination of the messaging class
    void close();

    //transmit data to a data handler class
    void transmit();
}
