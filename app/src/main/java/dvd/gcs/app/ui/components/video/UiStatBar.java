package dvd.gcs.app.ui.components.video;

import dvd.gcs.app.ui.api.UiPane;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class UiStatBar extends UiPane {

    private static final String FXML = "UiStatBar.fxml";
    private final Double statusMaxValue;
    private Double statusCurrentValue;
    private BigDecimal statusRatio;
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
        this.progressBar.setProgress(statusRatio.doubleValue());
    }

    private void updateStatusCurrentValue(Double updatedValue) {
        this.statusCurrentValue = updatedValue;
    }

    private void updateStatusRatio() {
        this.statusRatio = BigDecimal.valueOf(statusCurrentValue / statusMaxValue).setScale(2, RoundingMode.CEILING);
    }
}
