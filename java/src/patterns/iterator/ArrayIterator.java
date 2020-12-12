package patterns.iterator;

public class ArrayIterator<T> implements Iterator<T> {

    private T[] items;
    private int index;

    public ArrayIterator(T[] items){
        this.items = items;
    }

    @Override
    public boolean hasNext() {
        return (index < items.length);
    }

    @Override
    public T next() {
        if (this.hasNext()) {
            return items[index++];
        }
        return null;
    }
}
