// Contact.java
// (C) 2016 Masato Kokubo

package org.debugtrace.test;

import java.time.LocalDate;

import org.debugtrace.helper.Tuple;

public record Contact (String firstName, String lastName, LocalDate birthday, String phoneNumber) {
    public static Contact of(String firstName, String lastName, Tuple._3<Integer, Integer, Integer> birthday, String phoneNumber) {
        return new Contact(firstName, lastName,
            LocalDate.of(birthday.value1(), birthday.value2(), birthday.value3()),
            phoneNumber);
    } 
}
