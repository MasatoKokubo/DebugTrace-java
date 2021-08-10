// Contact.java
// (C) 2016 Masato Kokubo

package org.debugtrace.test;

import java.time.LocalDate;

import org.debugtrace.helper.Tuple;

public class Contact {
    public String firstName;
    public String lastName;
    public LocalDate birthday;
    public String phoneNumber;

    public Contact(String firstName, String lastName, LocalDate birthday, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.phoneNumber = phoneNumber;
    }

    public static Contact of(String firstName, String lastName, Tuple._3<Integer, Integer, Integer> birthday, String phoneNumber) {
        return new Contact(firstName, lastName,
            LocalDate.of(birthday.value1(), birthday.value2(), birthday.value3()),
            phoneNumber);
    } 
}
