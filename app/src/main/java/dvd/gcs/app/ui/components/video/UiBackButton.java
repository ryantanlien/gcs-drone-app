package dvd.gcs.app.ui.components.video;

import dvd.gcs.app.ui.api.UiButton;
import dvd.gcs.app.ui.events.SwitchPaneEvent;
import javafx.scene.control.Button;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component("UiBackButton")
@Lazy
public class UiBackButton extends UiButton {

    @Autowired
    public UiBackButton(Button button) {
        super(button);
        setButtonBehavior();
    }

    //Need to add switching of panes behavior here
    private void setButtonBehavior() {
        this.getRoot().setOnAction(actionEvent -> {
            this.getRoot().fireEvent(new SwitchPaneEvent());
        });
    }

}
