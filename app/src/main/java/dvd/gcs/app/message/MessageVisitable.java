package dvd.gcs.app.message;

public interface MessageVisitable<V> {
    void accept(V visitor);
}
