package gov.dsta.thickdemo.ui;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import gov.dsta.thickdemo.ThickDemoApplication;

public abstract class UiElement<T> {

    private final FXMLLoader fxmlLoader = new FXMLLoader();

    public static final String FXML_FILE_FOLDER = "/view/";

    public UiElement(URL fxmlFileURL) {
        loadFxmlFile(fxmlFileURL, null);
    }

    public UiElement(URL fxmlFileURL, T root) {
        loadFxmlFile(fxmlFileURL, root);
    }

    public UiElement(String fxmlFileName) {
        this(getFxmlFileURL(fxmlFileName));
    }

    public UiElement(String fxmlFileName, T root) {
        this(getFxmlFileURL(fxmlFileName), root);
    }

    public T getRoot() {
        return fxmlLoader.getRoot();
    }

    private void loadFxmlFile(URL location,  T root) {
        requireNonNull(location);
        fxmlLoader.setLocation(location);
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(root);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

    private static URL getFxmlFileURL(String fxmlFileName) {
        requireNonNull(fxmlFileName);
        String fxmlFileNameWithFolder = FXML_FILE_FOLDER + fxmlFileName;

        System.out.println(fxmlFileNameWithFolder);

        URL fxmlFileUrl = ThickDemoApplication.class.getResource(fxmlFileNameWithFolder);
        return requireNonNull(fxmlFileUrl);
    }
}
