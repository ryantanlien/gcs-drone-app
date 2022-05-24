package dvd.gcs.app.ui.components;

import dvd.gcs.app.ui.api.UiLayeredPane;
import dvd.gcs.app.ui.api.UiPane;
import javafx.scene.layout.Pane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Lazy
@Scope("prototype")
public class UiVideoTab extends UiLayeredPane {

    @Autowired
    public UiVideoTab(
            @Qualifier("UiInterfacePanel") UiPane uiPane,
            @Qualifier("StackPane") Pane pane) {
        super(uiPane, pane);
    }
}
