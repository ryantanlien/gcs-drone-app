package standalone.swing;

import com.luciad.gui.TLcdImageIcon;
import com.luciad.shape.shape3D.TLcdLonLatHeightPoint;
import com.luciad.util.ILcd3DOrientationSettable;
import com.luciad.view.lightspeed.style.TLspIconStyle;

import java.awt.*;

public class DroneIconModel extends TLcdLonLatHeightPoint implements ILcd3DOrientationSettable {
    private Image icon;
    private int id;
    private TLspIconStyle iconStyle;
    private double pitch;
    private double roll;
    private double orientation; // yaw


    public DroneIconModel(int id, double longitude, double latitude, double height, Image icon) {
        super(longitude, latitude, height);
        this.id = id;
        this.icon = icon;

        // Create icon style for luciad map
        TLspIconStyle iconStyle = TLspIconStyle.newBuilder().icon(new TLcdImageIcon(icon)).build();
        this.iconStyle = iconStyle;
    }

    public TLspIconStyle getIconStyle() {
        return this.iconStyle;
    }

    public int getId() {
        return this.id;
    }

    public void setPosition(double newLongitude, double newLatitude, double newHeight) {
        this.move3D(newLongitude, newLatitude, newHeight);
    }

    @Override
    public void setPitch(double v) {
        this.pitch = v;
    }

    @Override
    public void setRoll(double v) {
        this.roll = v;
    }

    @Override
    public double getPitch() {
        return this.pitch;
    }

    @Override
    public double getRoll() {
        return this.roll;
    }

    @Override
    public void setOrientation(double v) {
        this.orientation = v;
    }

    @Override
    public double getOrientation() {
        return this.orientation;
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
