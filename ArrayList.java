public class ArrayList<T> implements List<T> {
    int size;
    T[] array;

    public ArrayList(int capacity) {
        array = (T[]) new Object[capacity];
        size = 0;
    }

    @Override
    public boolean add(T element) {
        if (array[array.length - 1] != null) {
            grow_array();
        }
        if (array[array.length - 1] == null) {
            size++;
            array[size - 1] = element;
        }
        return true;
    }

    @Override
    public void add(int index, T element) throws Exception {
        if (index >= array.length || index < 0) {
            throw new Exception("Error: Out of Range");
        }
        if (array[array.length - 1] != null) {
            grow_array();
        }
        if (index < array.length && array[array.length - 1] == null) {
            for (int i = array.length - 1; i > index; i--) {
                array[i] = array[i - 1];
            }
            array[index] = element;
        }
    }

    public void grow_array() {
        T[] array2 = (T[]) new Object[array.length * 2];
        for (int i = 0; i < array.length; i++) {
            array2[i] = array[i];
        }
        array = array2;
    }

    @Override
    public T get(int index) {
        return array[index];
    }

    @Override
    public T remove(int index) throws Exception {
        T removed = array[index];

        if (index >= size || index < 0) {
            throw new Exception("Error: Specified Index Out Of Range");
        } else {
            for (int i = index; i < array.length - 1; i++) {
                array[i] = array[i + 1];
            }
            array[array.length - 1] = null;
            size--;
            return removed;
        }
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public String toString() {
        String output = "";
        output += '[';
        for (int i = 0; i < array.length - 1; i++) {
            output += array[i] + ", ";
        }
        output += array[array.length - 1];
        output += ']';
        return output;
    }
}
