// ListUtils.java
// (C) 2015 Masato Kokubo

package org.debugtrace.helper;

@FunctionalInterface
public interface DoubleSupplier {
    public double getAsDouble() throws Exception;
}
