package com.nechaieva.gtea.utils;

/**
 * A class used to normalize strings for the order.
 */
public class Normalizer {
    public static String normalize(String query) {
        return query.toLowerCase().
                replaceAll("\\s+", " ").
                replaceAll("\\s$", "").
                replaceAll("^\\s", "").
                replaceAll("[^a-z ]", "");
    }

    public static void normalizeAll(String[] menu) {
        for(int i = 0; i < menu.length; i++) {
            menu[i] = Normalizer.normalize(menu[i]);
        }
    }
}
