// MapUtils.java
// (C) 2015 Masato Kokubo

package org.debugtrace.helper;

import java.util.AbstractMap;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Has utility methods for Map.
 *
 * @since 3.0.0
 * @author Masato Kokubo
 */
public interface MapUtils {
    /**
     * Returns an immutable Map.Entry.
     *
     * @param <K> the type of the map keys
     * @param <V> the type of the map values
     * @param key a key 
     * @param value a value 
     * @return a Map.Entry
     */
    public static <K, V> Map.Entry<K, V> entry(K key, V value) {
        return new AbstractMap.SimpleImmutableEntry<K, V>(key, value);
    }

    /**
     * Returns an unmodifiable map containing the entries.
     *
     * @param <K> the type of the map keys
     * @param <V> the type of the map values
     * @param entries an array of Map.Entries
     * @return an immutable Map
     */
    @SafeVarargs
    public static <K, V> Map<K, V> ofEntries(Map.Entry<K, V>... entries) {
        Map<K, V> map = new HashMap<>(entries.length);
        for (Map.Entry<K, V> entry : entries)
            map.put(entry.getKey(), entry.getValue());
        return Collections.unmodifiableMap(map);
    }
}
