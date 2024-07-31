import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class ArrayDeque61BTest {

    // test with small numbers of elements

    @Test
    /**
     * In this test, we have three different assert statements that verify that
     * addFirst works correctly.
     */
    public void addFirstTestBasic() {
        Deque61B<String> ad1 = new ArrayDeque61B<>();

        ad1.addFirst("back"); // after this call we expect: ["back"]
        assertThat(ad1.toList()).containsExactly("back").inOrder();

        ad1.addFirst("middle"); // after this call we expect: ["middle", "back"]
        assertThat(ad1.toList()).containsExactly("middle", "back").inOrder();

        ad1.addFirst("front"); // after this call we expect: ["front", "middle", "back"]
        assertThat(ad1.toList()).containsExactly("front", "middle", "back").inOrder();

        /*
         * Note: The first two assertThat statements aren't really necessary. For
         * example, it's hard
         * to imagine a bug in your code that would lead to ["front"] and ["front",
         * "middle"] failing,
         * but not ["front", "middle", "back"].
         */
    }

    @Test
    /**
     * In this test, we use only one assertThat statement. IMO this test is just as
     * good as addFirstTestBasic.
     * In other words, the tedious work of adding the extra assertThat statements
     * isn't worth it.
     */
    public void addLastTestBasic() {
        Deque61B<String> ad1 = new ArrayDeque61B<>();

        ad1.addLast("front"); // after this call we expect: ["front"]
        ad1.addLast("middle"); // after this call we expect: ["front", "middle"]
        ad1.addLast("back"); // after this call we expect: ["front", "middle", "back"]
        assertThat(ad1.toList()).containsExactly("front", "middle", "back").inOrder();
    }

    @Test
    /** This test performs interspersed addFirst and addLast calls. */
    public void addFirstAndAddLastTest() {
        Deque61B<Integer> ad1 = new ArrayDeque61B<>();

        /*
         * I've decided to add in comments the state after each call for the convenience
         * of the
         * person reading this test. Some programmers might consider this excessively
         * verbose.
         */
        ad1.addLast(0); // [0]
        ad1.addLast(1); // [0, 1]
        ad1.addFirst(-1); // [-1, 0, 1]
        ad1.addLast(2); // [-1, 0, 1, 2]
        ad1.addFirst(-2); // [-2, -1, 0, 1, 2]

        assertThat(ad1.toList()).containsExactly(-2, -1, 0, 1, 2).inOrder();
    }

    // Below, you'll write your own tests for ArrayDeque61B.
    @Test
    public void isEmptyTest() {
        Deque61B<Integer> ad1 = new ArrayDeque61B<>();
        assertThat(ad1.isEmpty()).isTrue();
        // assertThat(ad1.toList()).containsExactly().inOrder();
        ad1.addFirst(1);
        assertThat(ad1.isEmpty()).isFalse();
        ad1.addFirst(2);
        assertThat(ad1.isEmpty()).isFalse();
        // assertThat(ad1.toList()).containsExactly(1).inOrder();
    }

    @Test
    public void removeFirstAndLastTest() {
        Deque61B<String> ad1 = new ArrayDeque61B<>();
        ad1.addLast("arrow");
        ad1.addLast("and");
        ad1.addLast("apple");
        ad1.addLast("are");
        ad1.addLast("arbitrary");
        String res = ad1.removeFirst();
        assertThat(ad1.toList()).containsExactly("and", "apple", "are", "arbitrary").inOrder();
        assertThat(res).isEqualTo("arrow");
        res = ad1.removeFirst();
        assertThat(ad1.toList()).containsExactly("apple", "are", "arbitrary").inOrder();
        assertThat(res).isEqualTo("and");
        res = ad1.removeLast();
        assertThat(ad1.toList()).containsExactly("apple", "are").inOrder();
        assertThat(res).isEqualTo("arbitrary");

        ad1.removeFirst();
        ad1.removeFirst();
        res = ad1.removeFirst();
        assertThat(ad1.toList()).containsExactly().inOrder();
        assertThat(res).isEqualTo(null);

        res = ad1.removeLast();
        assertThat(ad1.toList()).containsExactly().inOrder();
        assertThat(res).isEqualTo(null);
    }

    @Test
    public void getTest() {
        Deque61B<String> ad1 = new ArrayDeque61B<>();
        ad1.addLast("arrow");
        ad1.addLast("and");
        ad1.addLast("apple");
        ad1.addLast("are");
        ad1.addLast("arbitrary");
        String res = ad1.get(1);
        assertThat(ad1.toList()).containsExactly("arrow", "and", "apple", "are", "arbitrary").inOrder();
        assertThat(res).isEqualTo("and");
        res = ad1.get(5);
        assertThat(res).isEqualTo(null);
        res = ad1.get(-1);
        assertThat(res).isEqualTo(null);
    }

    // test the resizing ability with batches of elements

    @Test
    public void testResizingAbility() {
        Deque61B<Integer> ad1 = new ArrayDeque61B<>();
        for (int i = 0; i < 100000; ++i) {
            ad1.addLast(i);
            if (i % 100 == 0)
                assertThat(ad1.get(i)).isEqualTo(i);
        }
        for (int i = 0; i < 100000; ++i) {
            int actual = ad1.removeFirst();
            if (i % 100 == 0)
                assertThat(actual).isEqualTo(i);
        }
    }
}
