package patterns.iterator;

public interface Aggregator <T> {
    public Iterator<T> getIterator();
}
