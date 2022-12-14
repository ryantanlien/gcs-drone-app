package dvd.gcs.app.ui.components.video;

import dvd.gcs.app.ui.api.UiButton;
import dvd.gcs.app.ui.event.SwitchPaneEvent;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("UiBackButton")
@Lazy
@Scope("prototype")
public class UiBackButton extends UiButton {

    private static final String FXML = "UiBackButton.fxml";
    private static final String ICON = "/images/left-arrow-icon.png";


    @Autowired
    public UiBackButton(Button button) {
        super(FXML, button);
        setButtonBehavior();
        setButtonIcon();
    }

    private void setButtonBehavior() {
        this.getRoot().setOnAction(actionEvent -> {
            this.getRoot().fireEvent(new SwitchPaneEvent());
        });
    }

    private void setButtonIcon() {
        ImageView iconImage = new ImageView(UiBackButton.ICON);
        iconImage.setPreserveRatio(true);
        iconImage.setFitHeight(25);
        this.getRoot().setGraphic(iconImage);
    }
}
