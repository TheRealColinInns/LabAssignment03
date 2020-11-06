public interface List<T> {
    boolean add(T item);
    T get(int index);
    void add(int index, T item) throws Exception;
    T remove(int index) throws Exception;
    int size();
}
