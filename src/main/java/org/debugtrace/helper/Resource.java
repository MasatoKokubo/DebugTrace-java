// Resource.java
// (C) 2015 Masato Kokubo

package org.debugtrace.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Objects;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.function.Function;

/**
 * Uses this class when gets resources.
 *
 * @since 1.0.0
 * @author Masato Kokubo
 */
public class Resource {
    private ResourceBundle defaultResourceBundle;
    /**
     * Returns the default locale ResourceBundle.
     *
     * @return the default locale ResourceBundle
     */
    public ResourceBundle defaultResourceBundle() {return defaultResourceBundle;}

    private ResourceBundle userResourceBundle;
    /**
     * Returns the user defined ResourceBundle.
     *
     * @return the user defined ResourceBundle
     */
    public ResourceBundle userResourceBundle() {return userResourceBundle;}

    // A converter for string values
    private static final Function<String, String> stringConverter = string -> {
            if (string != null) {
                StringBuilder buff = new StringBuilder(string.length());
                var escape = false;
                for (var index = 0; index < string.length(); ++index) {
                    var ch = string.charAt(index);
                    if (escape) {
                        if      (ch == 't' ) buff.append('\t'); // 09 HT
                        else if (ch == 'n' ) buff.append('\n'); // 0A LF
                        else if (ch == 'r' ) buff.append('\r'); // 0D CR
                        else if (ch == 's' ) buff.append(' ' ); // 20 SPACE
                        else if (ch == '\\') buff.append('\\');
                        else                 buff.append(ch);
                        escape = false;
                    } else {
                        if (ch == '\\')
                            escape = true;
                        else
                            buff.append(ch);
                    }
                }
                string = buff.toString();
            }
            return string;
        };

    // A ResourceBundle.Control
    private static final ResourceBundle.Control control = new ResourceBundle.Control() {
        @Override
        public ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader loader, boolean reload)
                throws IllegalAccessException, InstantiationException, IOException {
            var bundleName = toBundleName(baseName, locale);
            var resourceName = toResourceName(bundleName, "properties");

            try (var inStream = loader.getResourceAsStream(resourceName);
                var streamReader = new InputStreamReader(inStream, "UTF-8");
                var reader = new BufferedReader(streamReader)) {
                return new PropertyResourceBundle(reader);
            }
        }
    };

    /**
     * Construct a Resource.
     *
     * @param baseClass the base class of a system ResourceBundle
     * @param userBaseName the base name of an user ResourceBundle
     */
    public Resource(Class<?> baseClass, String userBaseName) {
        Objects.requireNonNull(baseClass, "baseClass is null");
        try {
            defaultResourceBundle = ResourceBundle.getBundle(baseClass.getName(), control);
        }
        catch (Exception e) {
        }

        if (userBaseName != null) {
            try {
                userResourceBundle = ResourceBundle.getBundle(userBaseName, control);
            }
            catch (Exception e) {
            }
        }
    }

    /**
     * Returns the string value of resource property.
     *
     * @param key the key of resource property
     * @return the string value of resource property
     * @throws NullPointerException if <b>key</b> is null
     * @throws MissingResourceException if the property dose not found
    */
    private String get(String key) {
        Objects.requireNonNull(key, "key is null");

        String string = null;
        MissingResourceException e = null;

        if (userResourceBundle != null) {
            try {
                string = userResourceBundle.getString(key);
            }
            catch (MissingResourceException e2) {
                e = e2;
            }
        }

        if (string == null && defaultResourceBundle != null) {
            try {
                string = defaultResourceBundle.getString(key);
                e = null;
            }
            catch (MissingResourceException e2) {
                e = e2;
            }
        }

        if (e != null)
            throw e;

        return string;
    }

    /**
     * Returns the value of resource property.
     * 
     * @param <V> the type of value
     * @param key the key of resource property
     * @param valueConverter the function to convert string to return type
     * @param defaultValue the default value
     * @return the value of resource property (or defaultValue if not found in properties file)
     * @throws NullPointerException if <b>key</b> or <b>valueConverter</b> is null
     * @since 3.0.0
     */
    public <V> V getValue(String key, Function<String, V> valueConverter, V defaultValue) {
        try {
            return valueConverter.apply(get(key));
        }
        catch (MissingResourceException e) {
            return defaultValue;
        }
    }

    /**
     * Returns the string of resource property.
     * 
     * @param key the key of resource property
     * @param defaultValue the default value
     * @return the string value of resource property (or defaultValue if not found in properties file)
     * @throws NullPointerException if <b>key</b> is null
     * @since 2.3.0
     */
    public String getString(String key, String defaultValue) {
        return getValue(key, stringConverter, defaultValue);
    }

    /**
     * Returns the int value of resource property.
     *
     * @param key the key of resource property
     * @param defaultValue the default value
     * @return the int value of resource property (or defaultValue if not found in properties file)
     * @throws NullPointerException if <b>key</b> is null
     * @throws NumberFormatException if the value can not convert to int
     * @since 2.3.0
     */
    public int getInt(String key, int defaultValue) {
        return getValue(key, Integer::parseInt, defaultValue);
    }

    /**
     * Returns a list created from the resource property value if it is found,
     * an empty list otherwise.
     *
     * @param <E> the type of elements of the list
     * @param key the key of resource property
     * @param valueConverter the function to convert string to element type
     * @return a created list (or an empty list)
     * @throws NullPointerException if <b>key</b> or <b>valueConverter</b> is null
     * @since 3.0.0
     */
    public <E> List<E> getList(String key, Function<String, E> valueConverter) {
        Objects.requireNonNull(valueConverter, "valueConverter is null");

        var propertyValue = getString(key, "");
        List<E> list = new ArrayList<>();

        Arrays.stream(propertyValue.split(","))
            .forEach(string -> {
                string = string.trim();
                if (!string.isEmpty())
                    list.add(valueConverter.apply(string));
            });

        return list;
    }


    /**
     * Returns a set created from the resource property value if it is found,
     * an empty set otherwise.
     *
     * @param <E> the type of elements of the list
     * @param key the key of resource property
     * @param valueConverter the function to convert string to element type
     * @return a created set (or an empty set)
     * @throws NullPointerException if <b>key</b> or <b>valueConverter</b> is null
     * @since 3.1.1
     */
    public <E> Set<E> getSet(String key, Function<String, E> valueConverter) {
        Objects.requireNonNull(valueConverter, "valueConverter is null");

        var propertyValue = getString(key, "");
        Set<E> set = new HashSet<>();

        Arrays.stream(propertyValue.split(","))
            .forEach(string -> {
                string = string.trim();
                if (!string.isEmpty())
                    set.add(valueConverter.apply(string));
            });

        return set;
    }

    /**
     * Returns a string list created from the resource property value if it is found,
     * an empty list otherwise.
     *
     * @param key the key of resource property
     * @return a created string list (or an empty list)
     * @since 2.4.0
     */
    public List<String> getStrings(String key) {
        return getList(key, stringConverter);
    }

    /**
     * Returns a string set created from the resource property value if it is found,
     * an empty set otherwise.
     *
     * @param key the key of resource property
     * @return a created string set (or an empty set)
     * @since 3.1.1
     */
    public Set<String> getStringSet(String key) {
        return getSet(key, stringConverter);
    }

    /**
     * Returns a map created from the resource property value if it is found,
     * an empty map otherwise.
     *
     * @param <K> the type of keys of map
     * @param <V> the type of values of the map
     * @param key the key of resource property
     * @param keyConverter the function that converts string to the key type of the map
     * @param valueConverter the function that converts string to the value type of the map
     * @return a created map (or an empty map)
     * @throws NullPointerException if <b>key</b>, <b>keyConverter</b> or <b>valueConverter</b> is null
     * @since 3.0.0
     */
    public <K, V> Map<K, V> getMap(String key, Function<String, K> keyConverter, Function<String, V> valueConverter) {
        Objects.requireNonNull(keyConverter, "keyConverter is null");
        Objects.requireNonNull(valueConverter, "valueConverter is null");

        var mapKeyValue = getString(key, "");

        var map = new HashMap<K, V>();

        Arrays.stream(mapKeyValue.split(","))
            .forEach(string -> {
                string = string.trim();
                if (!string.isEmpty()) {
                    var keyValueStr = string.split(":");
                    var keyStr   = keyValueStr.length == 2 ? keyValueStr[0].trim() : "";
                    var valueStr = keyValueStr.length == 2 ? keyValueStr[1].trim() : "";
                    if (!keyStr.isEmpty() && !valueStr.isEmpty())
                        map.put(keyConverter.apply(keyStr), valueConverter.apply(valueStr));
                }
            });

        return map;
    }

    /**
     * Returns a map  (key: Integer, value: String) created from the resource property value if it is found,
     * an empty map otherwise.
     *
     * @param key the key of resource property
     * @return a created map (or an empty map)
     * @throws NullPointerException if <b>key</b> is null
     * @throws NumberFormatException if the value can not convert to int
     * @since 2.4.0
     */
    public Map<Integer, String> getIntegerKeyMap(String key) {
        return getMap(key, Integer::parseInt, stringConverter);
    }

    /**
     * Returns a map (key: String, value: String) created from the resource property value if it is found,
     * an empty map otherwise.
     *
     * @param key the key of resource property
     * @return a created map (or an empty map)
     * @throws NullPointerException if <b>key</b> is null
     * @since 2.4.0
     */
    public Map<String, String> getStringKeyMap(String key) {
        return getMap(key,  s -> s.toLowerCase(), stringConverter);
    }
}
