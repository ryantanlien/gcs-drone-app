package dvd.gcs.app.ui.api;

import javafx.scene.layout.Pane;

@FunctionalInterface
public interface Swappable<T> {
    T swap();
}
