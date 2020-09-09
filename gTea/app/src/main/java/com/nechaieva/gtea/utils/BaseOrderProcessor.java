package com.nechaieva.gtea.utils;

import java.util.Arrays;
import java.util.Optional;

/**
 * A base implementation of OrderProcessor.
 * Fast but only works with exact queries and does not understand fuzzy ones.
 */
public class BaseOrderProcessor implements OrderProcessor {

    String[] menu;

    public BaseOrderProcessor(String[] menu) {
        loadMenu(menu);
    }

    @Override
    public void loadMenu(String[] menu) {
        this.menu = menu.clone();
        Normalizer.normalizeAll(this.menu);
        Arrays.sort(this.menu);
    }

    @Override
    public Optional<String> findInMenu(String query) {
        query = Normalizer.normalize(query);
        int index = Arrays.binarySearch(menu, query);
        return Optional.ofNullable(index >= 0? menu[index] : null);
    }
}
