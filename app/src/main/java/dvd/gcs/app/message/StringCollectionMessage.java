package dvd.gcs.app.message;

import java.util.Collection;

public class StringCollectionMessage extends Message<Collection<String>> {
    public StringCollectionMessage(Collection<String> message) {
        super(message);
    }
}
