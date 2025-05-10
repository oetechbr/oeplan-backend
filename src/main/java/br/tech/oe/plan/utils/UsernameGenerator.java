package br.tech.oe.plan.utils;

public class UsernameGenerator {

    public static String fromFullName(String firstName, String lastName) {
        if (firstName == null || lastName == null) return null;
        return (firstName + "-" + lastName).toLowerCase().replaceAll("[^a-z0-9.]", "");
    }
}
