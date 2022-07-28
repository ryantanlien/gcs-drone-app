package dvd.gcs.app.ui.components.map;

import dvd.gcs.app.event.StartDroneSearchEvent;
import dvd.gcs.app.event.StopDroneSearchEvent;
import dvd.gcs.app.event.UpdateVideoFeedEvent;
import dvd.gcs.app.event.UploadDroneMissionEvent;
import dvd.gcs.app.message.DroneCommandReplyMessage;
import dvd.gcs.app.ui.api.UiElement;
import dvd.gcs.app.ui.event.SwitchPaneEvent;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class UiDroneFeedWindow extends UiElement<TitledPane> {

    /** The associated FXML file with this component **/
    private static final String FXML = "UiDroneFeedWindow.fxml";

    /** Text of the TitledPane **/
    private final String title;

    @FXML
    private Label droneStatus;
    @FXML
    private Label droneType;
    @FXML
    private ImageView droneFeed;

    @Autowired
    public UiDroneFeedWindow(TitledPane titledPane) {
        super(FXML, titledPane);
        this.title = "Drone 1";
        TitledPane root = this.getRoot();
        root.setText(this.title);
        root.setExpanded(false);
        setOnClickBehavior();
        setUpDroneFeed();
    }

    public void setOnClickBehavior() {
        AnchorPane content = (AnchorPane) this.getRoot().getContent();
        content.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                content.fireEvent(new SwitchPaneEvent());
            }
        });
    }


    public void setUpDroneFeed() {
        this.droneFeed.fitHeightProperty().set(180);
        this.droneFeed.fitWidthProperty().set(300);
    }

    @EventListener
    public void handleStartSearchEvent(StartDroneSearchEvent event) {
        DroneCommandReplyMessage.CommandStatus commandStatus = event.getCommandStatus();
        if (commandStatus.equals(DroneCommandReplyMessage.CommandStatus.COMMAND_SUCCESS)) {
            setDroneStatus("Searching");
        } else if (commandStatus.equals(DroneCommandReplyMessage.CommandStatus.COMMAND_FAILURE)) {

        } else {
            //Command failed to send
        }
    }

    @EventListener
    public void handleStopSearchEvent(StopDroneSearchEvent event) {
        DroneCommandReplyMessage.CommandStatus commandStatus = event.getCommandStatus();
        if (commandStatus.equals(DroneCommandReplyMessage.CommandStatus.COMMAND_SUCCESS)) {
            setDroneStatus("Stopped Search");
        } else if (commandStatus.equals(DroneCommandReplyMessage.CommandStatus.COMMAND_FAILURE)) {
        } else {
            //Command failed to send
        }
    }

    @EventListener
    public void handleUploadMissionEvent(UploadDroneMissionEvent event) {
        DroneCommandReplyMessage.CommandStatus commandStatus = event.getCommandStatus();
        if (commandStatus.equals(DroneCommandReplyMessage.CommandStatus.COMMAND_SUCCESS)) {
            setDroneStatus("Uploaded Mission!");
        } else if (commandStatus.equals(DroneCommandReplyMessage.CommandStatus.COMMAND_FAILURE)) {

        } else {
        }
    }

    @EventListener
    public void updateMiniVideoFeed(UpdateVideoFeedEvent updateVideoFeedEvent) {
        Image image = updateVideoFeedEvent.getImage();
        this.droneFeed.setImage(image);
    }

    public void setDroneStatus(String status) {
        this.droneStatus.setText(status);
    }

    public void setDroneType(String droneType) {
        this.droneType.setText(droneType);
    }


}
