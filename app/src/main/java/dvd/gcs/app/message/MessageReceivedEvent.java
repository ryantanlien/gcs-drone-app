package dvd.gcs.app.message;

import org.springframework.context.ApplicationEvent;
import org.springframework.core.ResolvableType;
import org.springframework.core.ResolvableTypeProvider;

public class MessageReceivedEvent<U extends Message<?>> extends ApplicationEvent implements ResolvableTypeProvider {

    private final U message;

    public MessageReceivedEvent(Object source, U message) {
        super(source);
        this.message = message;
    }

    public U getMessage() {
        return this.message;
    }

    @Override
    public ResolvableType getResolvableType() {
        return ResolvableType.forClassWithGenerics(getClass(), ResolvableType.forInstance(this.message));
    }
}
