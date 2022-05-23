package dvd.gcs.app.ui;

import javafx.scene.control.MenuBar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class UiDynamicMenuBar extends UiElement<MenuBar> {

    private static final String FXML = "UiMenuBar.fxml";

    @Autowired
    public UiDynamicMenuBar(MenuBar menuBar) {
        super(FXML, menuBar);
    }


}
