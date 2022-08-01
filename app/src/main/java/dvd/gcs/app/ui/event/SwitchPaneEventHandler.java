package dvd.gcs.app.ui.event;

import dvd.gcs.app.ui.api.Swappable;
import dvd.gcs.app.ui.api.UiPane;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class SwitchPaneEventHandler implements EventHandler<SwitchPaneEvent> {

    UiPane parentPane;
    Swappable<UiPane> swappablePane;
    Swappable<UiPane> otherSwappablePane;
    Swappable<UiPane> currentPane;

    public SwitchPaneEventHandler(UiPane parentPane,
                                  Swappable<UiPane> swappablePane,
                                  Swappable<UiPane> otherSwappablePane) {
        UiPane currentPane = (UiPane) swappablePane;

        //Enforce that the parentPane must be a parent of the swappablePane
        if (!currentPane.getRoot().getParent().equals(parentPane.getRoot())) {
            throw new RuntimeException();
        }

        this.parentPane = parentPane;
        this.swappablePane = swappablePane;
        this.otherSwappablePane = otherSwappablePane;
        this.currentPane = this.swappablePane;
    }

    @Override
    public void handle(SwitchPaneEvent event) {
        UiPane currentPane = (UiPane) this.currentPane;
        ObservableList<Node> childrenList = parentPane.getRoot().getChildren();

        if (childrenList.contains(currentPane.getRoot())) {
            childrenList.remove(currentPane.getRoot());
            childrenList.add(this.currentPane.swap().getRoot());
            currentPane.getRoot().prefHeightProperty().bind(parentPane.getRoot().heightProperty());
            currentPane.getRoot().prefWidthProperty().bind(parentPane.getRoot().widthProperty());
        }

        if(this.currentPane == swappablePane) {
            this.currentPane = otherSwappablePane;
        } else {
            this.currentPane = swappablePane;
        }
    }
}
