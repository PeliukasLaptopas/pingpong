package patterns.iterator;

import java.util.List;

public class ListIterator<T> implements Iterator<T> {

    private List<T> items;
    private int index;

    public ListIterator(List<T> items) {
        this.items = items;
    }

    @Override
    public boolean hasNext() {
        return (index < items.size());
    }

    @Override
    public T next() {
        if (this.hasNext()) {
            return items.get(index++);
        }
        return null;
    }
}
