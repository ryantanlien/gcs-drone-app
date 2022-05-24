package dvd.gcs.app.ui.components;

import dvd.gcs.app.ui.api.UiElement;
import javafx.scene.control.MenuBar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class UiMenuBar extends UiElement<MenuBar> {

    private static final String FXML = "UiMenuBar.fxml";

    @Autowired
    public UiMenuBar(MenuBar menuBar) {
        super(FXML, menuBar);
    }
}
