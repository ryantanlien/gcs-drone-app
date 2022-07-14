package dvd.gcs.app.luciadlightspeed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class LuciadLightspeedService {
    @Autowired
    @Qualifier("LuciadLightspeedMap")
    LuciadMapInterface luciadLightspeedMap; // TODO: may not work
}
