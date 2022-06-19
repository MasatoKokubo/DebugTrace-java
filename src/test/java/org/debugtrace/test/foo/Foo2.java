// Foo2.java
// (C) 2016 Masato Kokubo

package org.debugtrace.test.foo;

public class Foo2 {
    private int value;

    public Foo2(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "value is " + value;
    }
}
