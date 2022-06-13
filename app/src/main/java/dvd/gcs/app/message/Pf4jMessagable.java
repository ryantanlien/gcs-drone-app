package dvd.gcs.app.message;

import org.pf4j.ExtensionPoint;

import java.util.Collection;

public interface Pf4jMessagable<T> extends ExtensionPoint {
    //Performs initialisation for a messaging class
    void init();

    //Performs termination of the messaging class
    void close();

    //transmit data to a data handler class
    void transmit(Collection<T> collection);

    //add the dataHandler that listens to the Messagable
    void addListener(MessageTransmitEventListener<T> eventListener);
}
