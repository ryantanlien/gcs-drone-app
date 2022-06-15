package dvd.gcs.app.message;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class DroneJson {

    public enum Type {
        TELEMETRY,
        COMMAND_REPLY,
    }

    private final String json;
    private final Type type;

    public DroneJson(String json, Type type) {
        this.json = json;
        this.type = type;
    }

    public Type getType() {
        return this.type;
    }

    public String getJson() {
        return this.json;
    }
}
