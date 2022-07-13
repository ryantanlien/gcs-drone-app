package dvd.gcs.app.message;

public class DroneCommandReplyMessage extends DroneMessage implements
        MessageVisitable<DroneMessageService.DroneMessageProcessor> {

    public enum CommandStatus {
        COMMAND_SUCCESS,
        COMMAND_FAILURE
    }

    private final CommandStatus commandStatus;
    private final DroneCommandMessage.CommandType commandType;

    public DroneCommandReplyMessage(DroneJson data, CommandStatus commandStatus, DroneCommandMessage.CommandType commandType) {
        super(data);
        this.commandStatus = commandStatus;
        this.commandType = commandType;
    }

    public CommandStatus getCommandStatus() {
        return commandStatus;
    }

    public DroneCommandMessage.CommandType getCommandType() {
        return commandType;
    }
    @Override
    public void accept(DroneMessageService.DroneMessageProcessor processor) {
        processor.process(this);
    }
}
