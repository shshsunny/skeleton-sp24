package deque;

import java.util.Collections;
import java.util.Comparator;

public class MaxArrayDeque61B<T> extends ArrayDeque61B<T> {
    private Comparator<T> cmp;

    public MaxArrayDeque61B(Comparator<T> c) {
        cmp = c;
    }

    public T max(Comparator<T> c) {
        if (isEmpty())
            return null;
        T maxElement = get(0);
        for (int i = 1; i < size(); ++i) {
            T ithElement = get(i);
            if (c.compare(maxElement, ithElement) < 0)
                maxElement = ithElement;
        }
        return maxElement;
    }

    public T max() {
        return max(cmp);
    }
}
