package observer;

import java.util.function.Consumer;

public abstract class Observer <T> {

    protected Subject<T> subject;
    protected Consumer<T> onUpdate;

    protected Observer(Subject<T> subject, Consumer<T> onUpdate) {
        this.subject = subject;
        this.onUpdate = onUpdate;
        this.subject.attach(this);
    }

    public abstract void update();
}
