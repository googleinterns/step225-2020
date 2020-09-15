package com.nechaieva.gtea.utils;

/**
 * A class used to normalize strings for the order.
 * Makes the string lowercase, removes extra whitespace symbols,
 * removes everything that is not a letter or a space.
 */
public class Normalizer {

    private Normalizer() {
    }

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
