// Supplier.java
// (C) 2015 Masato Kokubo

package org.debugtrace.helper;

@FunctionalInterface
public interface Supplier<T> {
    public T get() throws Exception;
}
