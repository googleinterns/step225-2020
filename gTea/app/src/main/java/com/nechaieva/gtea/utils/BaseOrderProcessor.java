package com.nechaieva.gtea;

import java.util.Arrays;
import java.util.Optional;

/**
 * A base implementation of OrderProcessor.
 * Fast but only works with exact queries and does not understand fuzzy ones.
 */
public class BaseOrderProcessor implements OrderProcessor {

    String[] menu;

    BaseOrderProcessor(String[] menu) {
        loadMenu(menu);
    }

    @Override
    public void loadMenu(String[] menu) {
        this.menu = menu.clone();
        Arrays.sort(menu);
    }

    @Override
    public Optional<String> findInMenu(String query) {
        query = normalize(query);
        int index = Arrays.binarySearch(menu, query);
        return Optional.ofNullable(index >= 0? menu[index] : null);
    }

    private String normalize(String query) {
        return query.toLowerCase().
                replaceAll("\\s+", " ").
                replaceAll("^[a-z ]", "");
    }
}
