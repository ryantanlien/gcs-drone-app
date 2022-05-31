package dvd.gcs.app.ui.components.video;

import dvd.gcs.app.ui.api.UiPane;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
public class UiStatBar extends UiPane {

    private static final String FXML = "UiStatBar.fxml";
    private final Double statusMaxValue;
    private Double statusCurrentValue;
    private Integer statusRatio;
    private final String statusUnit;
    private final Label statusTitle;
    private final Label statusMeasure;
    private final ProgressBar progressBar;

    public UiStatBar(HBox root, Double statusMaxValue, String statusTitle, String statusMeasure, String statusUnit) {
        super(FXML, root);
        this.statusMaxValue = statusMaxValue;
        this.statusUnit = statusUnit;
        this.statusTitle = (Label) this.getRoot().getChildren().get(0);
        this.statusTitle.textProperty().set(statusTitle);
        this.statusMeasure = (Label) this.getRoot().getChildren().get(1);
        this.statusMeasure.textProperty().set(statusMeasure);
        this.progressBar = (ProgressBar) this.getRoot().getChildren().get(2);
        //Demo sake, TODO: Remove later
        this.updateStatus(75.0);
    }

    public Double getStatusMaxValueUnmodifiable() {
        return statusMaxValue;
     }

    public void updateStatus(Double updatedValue) {
        this.updateStatusCurrentValue(updatedValue);
        this.updateStatusRatio();
        this.updateProgressBar();
        this.updateStatusMeasure(updatedValue);
    }
    private void updateStatusMeasure(Double updatedValue) {
        StringBuilder stringBuilder = new StringBuilder(updatedValue.toString()).append(statusUnit);
        this.statusMeasure.textProperty().set(stringBuilder.toString());
    }

    private void updateProgressBar() {
        this.progressBar.setProgress(this.statusRatio);
    }

    private void updateStatusCurrentValue(Double updatedValue) {
        this.statusCurrentValue = updatedValue;
    }

    private void updateStatusRatio() {
        this.statusRatio = (int) (statusCurrentValue / statusMaxValue);
    }
}
