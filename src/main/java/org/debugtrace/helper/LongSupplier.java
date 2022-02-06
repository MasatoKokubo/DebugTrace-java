// LongSupplier.java
// (C) 2015 Masato Kokubo

package org.debugtrace.helper;

@FunctionalInterface
public interface LongSupplier {
    public long getAsLong() throws Exception;
}
