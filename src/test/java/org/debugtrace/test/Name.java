// Name.java
// (C) 2016 Masato Kokubo

package org.debugtrace.test;

public class Name {
    public String first;
    public String last;

    public Name(String first, String last) {
        this.first = first;
        this.last = last;
    }

    public static Name of (String first, String last) {
        return new Name(first, last);
    }
}
 