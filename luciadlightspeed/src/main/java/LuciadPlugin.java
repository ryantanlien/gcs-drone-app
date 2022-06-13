import dvd.gcs.app.luciadlightspeed.LuciadMapInterface;

import javafx.embed.swing.SwingNode;
import org.pf4j.Extension;
import org.pf4j.Plugin;
import org.pf4j.PluginWrapper;

public class LuciadPlugin extends Plugin {
    public LuciadPlugin(PluginWrapper pluginWrapper) {
        super(pluginWrapper);
    }

    @Extension
    public static class LuciadMap implements LuciadMapInterface {
        private SwingNode swingNode;

        @Override
        public SwingNode getSwingNode() {
            return swingNode;
        }
    }
}
