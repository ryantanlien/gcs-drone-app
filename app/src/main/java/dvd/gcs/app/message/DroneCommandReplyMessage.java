package dvd.gcs.app.message;

public class DroneCommandReplyMessage extends DroneMessage implements
        MessageVisitable<DroneMessageService.DroneMessageProcessor> {

    public enum CommandStatus {
        COMMAND_SUCCESS,
        COMMAND_FAILURE
    }

    private final CommandStatus commandStatus;

    public DroneCommandReplyMessage(DroneJson data, CommandStatus commandStatus) {
        super(data);
        this.commandStatus = commandStatus;
    }

    public CommandStatus getCommandStatus() {
        return commandStatus;
    }

    @Override
    public void accept(DroneMessageService.DroneMessageProcessor processor) {
        processor.process(this);
    }
}
