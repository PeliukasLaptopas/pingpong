package patterns.iterator;

import java.util.Stack;

public class StackIterator<T> implements Iterator<T> {

    private Stack<T> items;
    private int index;

    public StackIterator(Stack<T> items) {
        this.items = items;
        index = items.size() - 1;
    }

    @Override
    public boolean hasNext() {
        return index >= 0;
    }

    @Override
    public T next() {
        if (hasNext()) {
            return items.get(index--);
        }
        return null;
    }
}
