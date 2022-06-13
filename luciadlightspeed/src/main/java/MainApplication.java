import com.luciad.view.map.TLcdMapJPanel;

import javax.swing.*;
import java.awt.*;

public class MainApplication {

    public JFrame createUI() {
        JFrame frame = new JFrame("First GXY application");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        TLcdMapJPanel view = createView();
        frame.add(view, BorderLayout.CENTER);

        return frame;
    }

    private TLcdMapJPanel createView() {
        TLcdMapJPanel view = new TLcdMapJPanel();
        return view;
    }

    public static void main(String[] args) {
        //Swing components must be created on the Event Dispatch Thread
        EventQueue.invokeLater(() -> {
            JFrame frame = new MainApplication().createUI();
            frame.setVisible(true);
        });
    }
}
