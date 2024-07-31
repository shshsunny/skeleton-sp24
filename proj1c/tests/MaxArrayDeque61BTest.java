import org.junit.jupiter.api.*;

import java.util.Comparator;

import deque.ArrayDeque61B;
import deque.Deque61B;
import deque.LinkedListDeque61B;
import deque.MaxArrayDeque61B;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class MaxArrayDeque61BTest {
    private static class StringLengthComparator implements Comparator<String> {
        public int compare(String a, String b) {
            return a.length() - b.length();
        }
    }

    @Test
    public void basicTest() {
        MaxArrayDeque61B<String> mad = new MaxArrayDeque61B<>(new StringLengthComparator());
        mad.addFirst("");
        mad.addFirst("2");
        mad.addFirst("fury road");
        assertThat(mad.max()).isEqualTo("fury road");
    }

    @Test
    public void addLastTestBasicWithoutToList() {
        Deque61B<String> lld1 = new ArrayDeque61B<>();

        lld1.addLast("front"); // after this call we expect: ["front"]
        lld1.addLast("middle"); // after this call we expect: ["front", "middle"]
        lld1.addLast("back"); // after this call we expect: ["front", "middle", "back"]
        assertThat(lld1).containsExactly("front", "middle", "back");
    }

    @Test
    public void equalsTest() {
        Deque61B<String> d1 = new LinkedListDeque61B<>();
        Deque61B<String> d2 = new ArrayDeque61B<>();
        assertThat(d1.equals(d2)).isTrue();
        d1.addLast("ab");
        d1.addLast("cd");
        d1.addLast("efg");

        d2.addFirst("efg");
        d2.addFirst("cd");
        assertThat(d1.equals(d2)).isFalse();
        d2.addFirst("ab");
        assertThat(d1.equals(d2)).isTrue();
    }

    @Test
    public void toStringTest() {
        Deque61B<String> d1 = new LinkedListDeque61B<>();
        Deque61B<String> d2 = new ArrayDeque61B<>();
        d1.addLast("ab");
        d1.addLast("cd");
        d1.addLast("efg");
        assertThat(d1.toString()).isEqualTo("[ab, cd, efg]");
        assertThat(d2.toString()).isEqualTo("[]");
    }
}
