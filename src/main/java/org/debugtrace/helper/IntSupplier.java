// IntSupplier.java
// (C) 2015 Masato Kokubo

package org.debugtrace.helper;

@FunctionalInterface
public interface IntSupplier {
    public int getAsInt() throws Exception;
}
