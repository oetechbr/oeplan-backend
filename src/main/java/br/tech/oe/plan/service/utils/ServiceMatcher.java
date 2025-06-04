package br.tech.oe.plan.service.utils;

import org.springframework.data.domain.ExampleMatcher;

public class ServiceMatcher {

    public static final ExampleMatcher DefaultMatcher = ExampleMatcher.matchingAll()
            .withIgnoreCase()
            .withIgnoreNullValues()
            .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

    private ServiceMatcher() {
        // Prevent instantiation
    }
}
