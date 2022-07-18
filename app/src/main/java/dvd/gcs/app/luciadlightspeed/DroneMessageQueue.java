package dvd.gcs.app.luciadlightspeed;

import dvd.gcs.app.luciadlightspeed.event.DroneMessageDispatchEvent;

import java.util.LinkedList;
import java.util.PriorityQueue;

public class DroneMessageQueue {
    private final LinkedList<DroneMessageDispatchEvent> droneMessageDispatchEventQueue = new LinkedList<>();

    public LinkedList<DroneMessageDispatchEvent> getPriorityQueue() {
        return droneMessageDispatchEventQueue;
    }

    public void sendNextMessage() {
        DroneMessageDispatchEvent dispatchEvent = droneMessageDispatchEventQueue.poll();

        if (dispatchEvent == null) {
            // no event to dispatch
            return;
        }

        dispatchEvent.execute();
    }

    public void add(DroneMessageDispatchEvent droneMessageDispatchEvent) {
        this.droneMessageDispatchEventQueue.add(droneMessageDispatchEvent);
    }
}
