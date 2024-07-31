package deque;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Iterator;
//import javassist.bytecode.Descriptor.Iterator;

public class LinkedListDeque61B<T> implements Deque61B<T> {

    private class Node { // 不能设为static类，否则无法使用未定的T类型！（相当于没有外部环境）
        public Node prev, next;
        public T val;

        public Node() {
            prev = next = null;
        }

        public Node(T v) {
            prev = next = null;
            val = v;
        }
    }

    Node stl; // the sentinel node
    private int size;

    public LinkedListDeque61B() {
        stl = new Node();
        stl.prev = stl.next = stl;
        size = 0;
    }

    public void addFirst(T x) {
        Node first = new Node(x), second = stl.next;
        stl.next = first;
        first.prev = stl;
        first.next = second;
        second.prev = first;
        size++;
    }

    public void addLast(T x) {
        Node last = new Node(x), second = stl.prev;
        stl.prev = last;
        last.next = stl;
        last.prev = second;
        second.next = last;
        size++;
    }

    public List<T> toList() {
        List<T> lst = new ArrayList<>();
        Node p = stl;
        while (p.next != stl) {
            p = p.next;
            lst.add(p.val);
        }
        return lst;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public T removeFirst() {
        if (isEmpty())
            return null;
        T first_val = stl.next.val;
        Node second = stl.next.next;
        stl.next = second;
        second.prev = stl; // the reference of first is lost
        return first_val;
    }

    public T removeLast() {
        if (isEmpty())
            return null;
        T last_val = stl.prev.val;
        Node second = stl.prev.prev;
        second.next = stl;
        stl.prev = second;
        return last_val;
    }

    public T get(int index) {
        if (!(0 <= index && index < size))
            return null;
        int i = 0;
        Node p = stl;
        while (i != index) {
            p = p.next;
            i++;
        }
        return p.next.val;
    }

    public T getRecursive(int index) {
        if (!(0 <= index && index < size))
            return null;
        return getRecursiveHelper(index, stl);
    }

    private T getRecursiveHelper(int rem, Node p) {
        if (rem == 0)
            return p.next.val;
        return getRecursiveHelper(rem - 1, p.next);
    }

    // iteration
    private class LLDIterator implements Iterator<T> {
        private Node ptr;

        public LLDIterator() {
            ptr = stl;
        }

        public boolean hasNext() {
            return ptr.next != stl;
        }

        public T next() {
            if (!hasNext())
                throw new NoSuchElementException("LLD elements have been exhausted");
            ptr = ptr.next;
            return ptr.val;
        }
    }

    public Iterator<T> iterator() {
        return new LLDIterator();
    }

    // equality
    @Override
    public boolean equals(Object o) {
        return equalsHelper(o);
    }

    // stringification
    @Override
    public String toString() {
        return toList().toString();
    }
}
