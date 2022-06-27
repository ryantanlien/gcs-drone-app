package standalone.swing;

import com.luciad.gui.TLcdImageIcon;
import com.luciad.shape.shape3D.TLcdLonLatHeightPoint;
import com.luciad.util.ILcd3DOrientationSettable;
import com.luciad.view.lightspeed.style.TLspIconStyle;
import javafx.scene.image.Image;

public class DroneIconModel extends TLcdLonLatHeightPoint implements ILcd3DOrientationSettable {
    private static int idCounter = 1;
    private int id;
    private TLspIconStyle iconStyle;
    private String pathToImage = "PLACEHOLDER"; // TODO: get drone image from internet

    public DroneIconModel() {
        this.id = idCounter;
        idCounter++;

        // Create icon style for luciad map
        TLspIconStyle iconStyle = TLspIconStyle.newBuilder().icon(new TLcdImageIcon(pathToImage)).build();
        this.iconStyle = iconStyle;
    }

    public TLspIconStyle getIconStyle() {
        return this.iconStyle;
    }

    public int getId() {
        return this.id;
    }

    // TODO: set methods below
    @Override
    public void setPitch(double v) {

    }

    @Override
    public void setRoll(double v) {

    }

    @Override
    public double getPitch() {
        return 0;
    }

    @Override
    public double getRoll() {
        return 0;
    }

    @Override
    public void setOrientation(double v) {

    }

    @Override
    public double getOrientation() {
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof DroneIconModel) {
            DroneIconModel otherIcon = (DroneIconModel) obj;
            if (this.id == otherIcon.getId()) {
                // compare id
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
