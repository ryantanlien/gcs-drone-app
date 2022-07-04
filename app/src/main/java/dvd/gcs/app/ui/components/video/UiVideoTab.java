package dvd.gcs.app.ui.components.video;

import dvd.gcs.app.ui.api.UiPane;
import dvd.gcs.app.ui.api.UiSwappableLayeredPane;
import dvd.gcs.app.ui.components.UiBasePanel;
import dvd.gcs.app.ui.components.map.UiMapBasePane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component("UiVideoTab")
@Lazy
public class UiVideoTab extends UiSwappableLayeredPane {

    @Autowired
    BeanFactory beanFactory;

    @Autowired
    public UiVideoTab(
            @Qualifier("UiVideoInterfacePanel") UiPane uiPane,
            @Qualifier("StackPane") Pane pane) {
        super(uiPane, pane);
        fillInnerParts();
    }

    //Need to get ImageView to fill the whole stackPane
    private void fillInnerParts() {
        ImageView imageView = new ImageView();
        super.addInnerPane();
        this.getRoot().getChildren().add(imageView);
    }

    @Override
    public UiPane swap() {
        return beanFactory.getBeanProvider(UiMapBasePane.class).getIfAvailable();
    }
}
