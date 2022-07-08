package dvd.gcs.app.ui.components.video;

import dvd.gcs.app.event.UpdateVideoFeedEvent;
import dvd.gcs.app.ui.api.UiPane;
import dvd.gcs.app.ui.api.UiSwappableLayeredPane;
import dvd.gcs.app.ui.components.map.UiMapBasePane;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component("UiVideoTab")
@Lazy
public class UiVideoTab extends UiSwappableLayeredPane implements ApplicationListener<UpdateVideoFeedEvent> {

    @Autowired
    BeanFactory beanFactory;

    private ImageView imageView;

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
        imageView.setFitHeight(super.uiPane.getRoot().getHeight());
        this.imageView = imageView;
        this.getRoot().getChildren().add(imageView);
        super.addInnerPane();
    }

    private void updateVideoFeed(Image image) {
        this.imageView.setImage(image);
    }

    @Override
    public UiPane swap() {
        return beanFactory.getBeanProvider(UiMapBasePane.class).getIfAvailable();
    }

    @Override
    public void onApplicationEvent(UpdateVideoFeedEvent event) {
        this.updateVideoFeed(event.getImage());
    }
}
