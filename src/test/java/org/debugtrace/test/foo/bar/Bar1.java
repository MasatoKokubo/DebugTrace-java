// Bar1.java
// (C) 2016 Masato Kokubo

package org.debugtrace.test.foo.bar;

public class Bar1 {
    private int value;

    public Bar1(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "value is " + value;
    }
}
