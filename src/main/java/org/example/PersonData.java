package org.example;

public class PersonData {
    String surname, name, middlename;
    long number;
    char gender;
    String dateOfBirth;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(surname).append(' ')
                .append(name).append(' ')
                .append(middlename).append(' ')
                .append(dateOfBirth).append(' ')
                .append(number).append(' ')
                .append(gender).append(' ');
        return sb.toString();
    }
}
