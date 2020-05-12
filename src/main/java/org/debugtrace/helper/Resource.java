// Resource.java
// (C) 2015 Masato Kokubo

package org.debugtrace.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Objects;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
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
                boolean escape = false;
                for (int index = 0; index < string.length(); ++index) {
                    char ch = string.charAt(index);
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
            String bundleName = toBundleName(baseName, locale);
            String resourceName = toResourceName(bundleName, "properties");

            try (InputStream inStream = loader.getResourceAsStream(resourceName);
                Reader streamReader = new InputStreamReader(inStream, "UTF-8");
                Reader reader = new BufferedReader(streamReader)) {
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
     *
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
     * Returns the string value of resource property.
     *
     * @param key the key of resource property
     * @return the string value of resource property
     *
     * @throws NullPointerException if <b>key</b> is null
     * @throws MissingResourceException if the property dose not found
    */
    private String get(String key1, String key2) {
    	try {
    		return get(key1);
    	}
    	catch (MissingResourceException e1) {
    		if (key2 == null)
    			throw e1;

        	try {
        		return get(key2);
        	}
        	catch (MissingResourceException e2) {
        		throw new MissingResourceException(e1.getMessage(), e1.getClassName(), e1.getKey() + ", " + e2.getKey());
        	}
    	}
    }

    /**
     * Returns the value of resource property.
     * 
     * @param <V> the type of value
     * @param key1 the key 1 of resource property
     * @param key2 the key 2 of resource property
     * @param valueConverter the function to convert string to return type
     * @param defaultValue the default value
     * @return the value of resource property (or defaultValue if not found in properties file)
     *
     * @throws NullPointerException if <b>key</b> or <b>valueConverter</b> is null
     *
     * @since 3.0.0
     */
    public <V> V getValue(String key1, String key2, Function<String, V> valueConverter, V defaultValue) {
        try {
            return valueConverter.apply(get(key1, key2));
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
     *
     * @throws NullPointerException if <b>key</b> is null
     *
     * @since 2.3.0
     */
    public String getString(String key, String defaultValue) {
        return getValue(key, null, stringConverter, defaultValue);
    }

    /**
     * Returns the string of resource property.
     * 
     * @param key1 the key 1 of resource property
     * @param key2 the key 2 of resource property
     * @param defaultValue the default value
     * @return the string value of resource property (or defaultValue if not found in properties file)
     *
     * @throws NullPointerException if <b>key</b> is null
     *
     * @since 3.0.0
     */
    public String getString(String key1, String key2, String defaultValue) {
        return getValue(key1, key2, stringConverter, defaultValue);
    }

    /**
     * Returns the int value of resource property.
     *
     * @param key the key of resource property
     * @param defaultValue the default value
     * @return the int value of resource property (or defaultValue if not found in properties file)
     *
     * @throws NullPointerException if <b>key</b> is null
     * @throws NumberFormatException if the value can not convert to int
     *
     * @since 2.3.0
     */
    public int getInt(String key, int defaultValue) {
        return getValue(key, null, Integer::parseInt, defaultValue);
    }

    /**
     * Returns the int value of resource property.
     *
     * @param key1 the key 1 of resource property
     * @param key2 the key 2 of resource property
     * @param defaultValue the default value
     * @return the int value of resource property (or defaultValue if not found in properties file)
     *
     * @throws NullPointerException if <b>key</b> is null
     * @throws NumberFormatException if the value can not convert to int
     *
     * @since 3.0.0
     */
    public int getInt(String key1, String key2, int defaultValue) {
        return getValue(key1, key2, Integer::parseInt, defaultValue);
    }


    /**
     * Returns a list created from the resource property value if it is found,
     * an empty list otherwise.
     *
     * @param <E> the type of elements of the list
     * @param key1 the key 1 of resource property
     * @param key2 the key 2 of resource property
     * @param valueConverter the function to convert string to element type
     * @return a created list (or an empty list)
     *
     * @throws NullPointerException if <b>key</b> or <b>valueConverter</b> is null
     *
     * @since 3.0.0
     */
    public <E> List<E> getList(String key1, String key2, Function<String, E> valueConverter) {
        Objects.requireNonNull(valueConverter, "valueConverter is null");

        String propertyValue = getString(key1, key2, "");
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
     * Returns a string list created from the resource property value if it is found,
     * an empty list otherwise.
     *
     * @param key the key of resource property
     * @return a created string list (or an empty list)
     *
     * @since 2.4.0
     */
    public List<String> getStrings(String key) {
        return getList(key, null, stringConverter);
    }

    /**
     * Returns a string list created from the resource property value if it is found,
     * an empty list otherwise.
     *
     * @param key1 the key 1 of resource property
     * @param key2 the key 2 of resource property
     * @return a created string list (or an empty list)
     *
     * @since 3.0.0
     */
    public List<String> getStrings(String key1, String key2) {
        return getList(key1, key2, stringConverter);
    }

// 3.0.0
    /**
     * Returns a map created from the resource property value if it is found,
     * an empty map otherwise.
     *
     * @param <K> the type of keys of map
     * @param <V> the type of values of the map
     * @param key1 the key 1 of resource property
     * @param key2 the key 2 of resource property
     * @param keyConverter the function that converts string to the key type of the map
     * @param valueConverter the function that converts string to the value type of the map
     * @return a created map (or an empty map)
     *
     * @throws NullPointerException if <b>key</b>, <b>keyConverter</b> or <b>valueConverter</b> is null
     *
     * @since 3.0.0
     */
    public <K, V> Map<K, V> getMap(String key1, String key2, Function<String, K> keyConverter, Function<String, V> valueConverter) {
        Objects.requireNonNull(keyConverter, "keyConverter is null");
        Objects.requireNonNull(valueConverter, "valueConverter is null");

        String mapKeyValue = getString(key1, key2, "");

        Map<K, V> map = new HashMap<>();

        Arrays.stream(mapKeyValue.split(","))
            .forEach(string -> {
                string = string.trim();
                if (!string.isEmpty()) {
                    String[] keyValueStr = string.split(":");
                    String keyStr   = keyValueStr.length == 2 ? keyValueStr[0].trim() : "";
                    String valueStr = keyValueStr.length == 2 ? keyValueStr[1].trim() : "";
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
     *
     * @throws NullPointerException if <b>key</b> is null
     * @throws NumberFormatException if the value can not convert to int
     *
     * @since 2.4.0
     */
    public Map<Integer, String> getIntegerKeyMap(String key) {
        return getMap(key, null, Integer::parseInt, stringConverter);
    }

    /**
     * Returns a map  (key: Integer, value: String) created from the resource property value if it is found,
     * an empty map otherwise.
     *
     * @param key1 the key 1 of resource property
     * @param key2 the key 2 of resource property
     * @return a created map (or an empty map)
     *
     * @throws NullPointerException if <b>key</b> is null
     * @throws NumberFormatException if the value can not convert to int
     *
     * @since 3.0.0
     */
    public Map<Integer, String> getIntegerKeyMap(String key1, String key2) {
        return getMap(key1, key2, Integer::parseInt, stringConverter);
    }

    /**
     * Returns a map (key: String, value: String) created from the resource property value if it is found,
     * an empty map otherwise.
     *
     * @param key the key of resource property
     * @return a created map (or an empty map)
     *
     * @throws NullPointerException if <b>key</b> is null
     *
     * @since 2.4.0
     */
    public Map<String, String> getStringKeyMap(String key) {
        return getMap(key, null,  s -> s, stringConverter);
    }

    /**
     * Returns a map (key: String, value: String) created from the resource property value if it is found,
     * an empty map otherwise.
     *
     * @param key1 the key 1 of resource property
     * @param key2 the key 2 of resource property
     * @return a created map (or an empty map)
     *
     * @throws NullPointerException if <b>key</b> is null
     *
     * @since 3.0.0
     */
    public Map<String, String> getStringKeyMap(String key1, String key2) {
        return getMap(key1, key2, s -> s, stringConverter);
    }
}
