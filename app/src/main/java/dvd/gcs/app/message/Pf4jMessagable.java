package dvd.gcs.app.message;

import org.pf4j.ExtensionPoint;

public interface Pf4jMessagable<U extends Message<?>>
        extends ExtensionPoint, MessageTransmitEventListener<U> {

    //Performs initialisation for a messaging class
    void init();

    //Performs termination of the messaging class
    void close();

    //transmit data to a data handler class
    void transmit(U message);

    //add the dataHandler that listens to the Messagable
    void addListener(MessageTransmitEventListener<U> eventListener);
}
