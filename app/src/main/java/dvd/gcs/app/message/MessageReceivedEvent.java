package dvd.gcs.app.message;

import org.springframework.context.ApplicationEvent;
import org.springframework.core.ResolvableType;
import org.springframework.core.ResolvableTypeProvider;

import java.util.Collection;

public class MessageReceivedEvent<T> extends ApplicationEvent implements ResolvableTypeProvider {

    private final Collection<T> message;

    public MessageReceivedEvent(Object source, Collection<T> message) {
        super(source);
        this.message = message;
    }

    public Collection<T> getMessage() {
        return this.message;
    }

    @Override
    public ResolvableType getResolvableType() {
        return ResolvableType.forClassWithGenerics(getClass(), ResolvableType.forInstance(this.message.iterator().next()));
    }
}
