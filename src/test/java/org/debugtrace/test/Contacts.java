// Point.java
// (C) 2016 Masato Kokubo

package org.debugtrace.test;

public class Contacts {
    public Contact contact1;
    public Contact contact2;
    public Contact contact3;
    public Contact contact4;

    public Contacts(Contact contact1, Contact contact2,  Contact contact3,  Contact contact4) {
        this.contact1 = contact1;
        this.contact2 = contact2;
        this.contact3 = contact3;
        this.contact4 = contact4;
    }

    public static Contacts of(Contact contact1, Contact contact2,  Contact contact3,  Contact contact4) {
        return new Contacts(contact1, contact2, contact3, contact4);
    }
}
