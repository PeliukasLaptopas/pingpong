package observer;

import input.InputKey;

import java.util.function.Consumer;

public class InputKeyObserver extends Observer<InputKey> {

    public InputKeyObserver(Subject<InputKey> subject, Consumer<InputKey> onUpdate) {
        super(subject, onUpdate);
    }

    @Override
    public void update() {
        onUpdate.accept(subject.getState());
    }
}
