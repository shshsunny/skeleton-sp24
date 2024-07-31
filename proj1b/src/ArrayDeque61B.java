import java.util.ArrayList;
import java.util.List;

public class ArrayDeque61B<T> implements Deque61B<T> {
    private static int RESIZING_FACTOR = 2; // the factor for both resizing-up and resizing-down
    private int size, maxSize;
    private int head; // the head position of the deque, only valid when size>0
    private T[] array;

    public ArrayDeque61B() {
        size = 0;
        maxSize = 8;
        array = (T[]) new Object[maxSize];
    }

    private boolean needResizingBeforeAdding() {
        return size == maxSize;
    }

    private boolean needResizingAfterRemoving() {
        return maxSize > 15 && (double) size / (double) maxSize <= 0.25;
    }

    private T[] getResizedArray(int newSize) {
        T[] newArray = (T[]) new Object[newSize];
        for (int i = 0; i < size; ++i)
            newArray[i] = get(i); // maxSize MUSTN'T be modified before this work!
        return newArray;
    }

    private void resizeUp() {
        if (size < maxSize) // not necessary
            return;
        array = getResizedArray(maxSize * RESIZING_FACTOR);
        maxSize = maxSize * RESIZING_FACTOR;
        head = 0;
    }

    private void resizeDown() { // make sure that size <= 0.5 * maxSize!
        if (size > maxSize / RESIZING_FACTOR)
            return;
        array = getResizedArray(maxSize / RESIZING_FACTOR);
        maxSize = maxSize / RESIZING_FACTOR;
        head = 0;
    }

    @Override
    public void addFirst(T x) {
        if (needResizingBeforeAdding())
            resizeUp();
        if (size == 0)
            array[head = 0] = x; // initialize the head
        else
            array[head = Math.floorMod(head - 1, maxSize)] = x;
        size++;
    }

    @Override
    public void addLast(T x) {
        if (needResizingBeforeAdding())
            resizeUp();
        if (size == 0)
            array[head = 0] = x; // initialize the head
        else
            array[Math.floorMod(head + size, maxSize)] = x;
        size++;
    }

    @Override
    public List<T> toList() {
        List<T> lst = new ArrayList<>();
        for (int i = 0; i < size; ++i)
            lst.add(get(i));
        return lst;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        if (isEmpty())
            return null;
        T res = get(0);
        head++;
        size--;
        if (needResizingAfterRemoving())
            resizeDown();
        return res;
    }

    @Override
    public T removeLast() {
        if (isEmpty())
            return null;
        T res = get(size - 1);
        size--;
        if (needResizingAfterRemoving())
            resizeDown();
        return res;
    }

    @Override
    public T get(int index) {
        if (!(0 <= index && index < size))
            return null;
        return array[Math.floorMod(head + index, maxSize)];
    }

    @Override
    public T getRecursive(int index) {
        throw new UnsupportedOperationException("No need to implement getRecursive for proj 1b");
    }

}