public class LinkedList<T> implements List<T> {
    Node<T> head;
    Node<T> tail;
    int size;

    public LinkedList() {
        head = tail = null;
        size = 0;
    }

    @Override
    public boolean add(T item) {
        Node<T> node = new Node(item);
        if (head == null) {
            head = node;
            tail = node;
            size = 1;
        }
        else {
            tail.next = node;
            tail = node;
            size++;
        }

        return true;
    }

    @Override
    public void add(int position, T element) {
        Node node = new Node(element);

        if (head == null) {
            head = node;
            tail = node;
            size = 1;
        }
        else {
            int index = 0;
            if (position == 0) {
                node.next = head;
                head = node;
                size++;
            }
            else if (position == 1) {
                node.next = head.next;
                head.next = node;
                size++;
            }
            else {
                Node prev = null;
                for (Node cur = head; cur != null; cur = cur.next) {
                    if (index == position - 1) {
                        prev = cur;
                    }
                    else if (index == position) {
                        prev.next = node;
                        node.next = cur;
                        size++;
                    }
                }
            }
        }
    }

    @Override
    public T get(int position) {
        if (head == null || size <= position) {
            return null;
        }
        else {
            Node<T> cur = head;
            for (int i = 0; i < position; i++) {
                if (i == position) {
                    return cur.data;
                }
                cur = cur.next;
            }
            return cur.data;
        }
    }

    @Override
    public T remove(int position) {
        if (head == null || size <= position) {
            return null;
        }
        else if (position == 0) {   //Removing head
            Node<T> temp = head;
            head = head.next;
            size--;
            return temp.data;
        }
        else {
            Node<T> cur = head;
            Node<T> prev = null;

            for(int i = 0; i <= position; i++) {
                if (i == position - 1) {
                    prev = cur;
                }
                else if (i == position) {
                    if (position == size - 1) { //Removing tail
                        prev.next = null;
                        tail = prev;
                    }
                    else {
                        prev.next = cur.next;
                        cur.next = null;
                    }
                    size--;
                    return cur.data;
                }
                cur = cur.next;
            }
            size--;
            return cur.data;
        }
    }

    @Override
    public int size() {
        return this.size;
    }

    private class Node<T> {
        T data;
        Node next;

        public Node(T data) {
            this.data = data;
            next = null;
        }
    }

    @Override
    public String toString() {
        return toString(this.head);
    }

    private String toString(Node front) {
        if (front == null) {
            return "";
        }
        else {
            if (front.next != null) {
                return front.data + "->" + toString(front.next);
            }
            else {
                return "" + front.data;
            }
        }
    }
}
