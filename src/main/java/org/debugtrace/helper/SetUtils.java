// SetUtils.java
// (C) 2015 Masato Kokubo

package org.debugtrace.helper;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Has utility methods for Set.
 *
 * @since 3.0.0
 * @author Masato Kokubo
 */
public interface SetUtils {
    /**
     * Returns an unmodifiable Set containing the elements.
     *
     * @param <E> the element type
     * @param elements an array of elements
     * @return a Set
     */
    @SuppressWarnings("unchecked")
    public static <E> Set<E> of(E... elements) {
        Set<E> set = new HashSet<E>(elements.length);
        for (E element : elements)
            set.add(element);
        return Collections.unmodifiableSet(set);
    }
}
