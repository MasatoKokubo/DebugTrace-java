// BooleanSupplier.java
// (C) 2015 Masato Kokubo

package org.debugtrace.helper;

@FunctionalInterface
public interface BooleanSupplier {
    public boolean getAsBoolean() throws Exception;
}
