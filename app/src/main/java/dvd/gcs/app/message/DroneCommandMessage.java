package dvd.gcs.app.message;

public class DroneCommandMessage extends DroneMessage
        implements MessageVisitable<DroneMessageService.DroneMessageProcessor> {

    private final CommandType commandType;

    public enum CommandType {
        SET_GEOFENCE,
        SET_ALTITUDE,
        SET_SEARCH_PATTERN,
        SET_HOME
    }

    public DroneCommandMessage(DroneJson data, CommandType commandType) {
        super(data);
        this.commandType = commandType;
    }

    public CommandType getCommandType() {
        return commandType;
    }

    @Override
    public void accept(DroneMessageService.DroneMessageProcessor processor) {
        processor.process(this);
    }
}
