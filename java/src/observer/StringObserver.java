package observer;

import java.util.function.Consumer;

public class StringObserver extends Observer<String> {

    public StringObserver(Subject<String> subject, Consumer<String> onUpdate) {
        super(subject, onUpdate);
    }

    @Override
    public void update() {
        onUpdate.accept(subject.getState());
    }
}
