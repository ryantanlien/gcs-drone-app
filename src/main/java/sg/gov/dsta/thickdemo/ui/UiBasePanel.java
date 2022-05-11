package sg.gov.dsta.thickdemo.ui;

import javafx.scene.layout.Region;

public class UiBasePanel extends UiElement<Region> {

    private static final String FXML = "UiBasePanel.fxml";

    public UiBasePanel(Region region) {
        super(FXML, region);
    }
}
