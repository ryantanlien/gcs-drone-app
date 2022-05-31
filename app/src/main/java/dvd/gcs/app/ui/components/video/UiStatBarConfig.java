package dvd.gcs.app.ui.components.video;

import javafx.scene.layout.HBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
@Lazy
public class UiStatBarConfig {

    @Bean("BatteryStatBar")
    @Autowired
    public UiStatBar getBatteryStatBar(HBox hBox) {
        return new UiStatBar(hBox, 100.0, "Battery", "100", "%");
    }

    @Bean("HeightStatBar")
    @Autowired
    public UiStatBar getHeightStatBar(HBox hBox) {
        return new UiStatBar(hBox, 100.0, "Height", "100", "m");
    }

    @Bean("SpeedStatBar")
    @Autowired
    public UiStatBar getSpeedStatBar(HBox hBox) {
        return new UiStatBar(hBox, 100.0, "Speed", "100", "m/s");
    }
}
