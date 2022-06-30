package dvd.gcs.app.message;

public abstract class Message<T> {
    private final T data;

    public Message(T data) {
        this.data = data;
    }

    public T getData() {
        return this.data;
    }
}
