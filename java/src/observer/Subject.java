package observer;

import java.util.ArrayList;
import java.util.List;

public class Subject <T> {

    private List<Observer<T>> observers = new ArrayList<>();
    private T state;

    public T getState() {
        return state;
    }

    public void setState(T state) {
        this.state = state;
        notifyAllObservers();
    }

    public void attach(Observer<T> observer) {
        observers.add(observer);
    }

    public void notifyAllObservers() {
        for (Observer<T> observer : observers) {
            observer.update();
        }
    }
}
