package com.nechaieva.gtea.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * And implementation of OrderProcessor that uses Levenshtein distances.
 */
public class LevenshteinOrderProcessor implements OrderProcessor {

    private List<String> menu;

    /**
     * The threshold for string distances.
     * Represents the number of typos the user can make before the query will be rejected.
     */
    public final int DEFAULT_SENSITIVITY = 3;

    private int maxAllowedStringLength = 100;

    public LevenshteinOrderProcessor(String[] menu) {
        loadMenu(menu);
    }

    LevenshteinOrderProcessor(ArrayList<String> menu) {
        loadMenu(menu);
    }

    @Override
    public void loadMenu(String[] menu) {
        this.menu = Arrays.asList(menu);
        Normalizer.normalizeAll(menu);
    }

    public void loadMenu(ArrayList<String> menu) {
        this.menu = new ArrayList<>(menu);
        menu.forEach(Normalizer::normalize);
    }

    /**
     * @see LevenshteinOrderProcessor#findInMenu(String, int)
     * Uses default sensitivity.
     * Declines the search and returns null if the given query
     * exceeds the {@link LevenshteinOrderProcessor#maxAllowedStringLength} setting.
     */
    @Override
    public Optional<String> findInMenu(String query) {
        return findInMenu(query, DEFAULT_SENSITIVITY);
    }

    /**
     * Given a text query passed from the user, return the string that represent
     * an existing menu item. Returns an empty Optional if there is no close enough match.
     * @param query
     * A user query.
     * @param sensitivity
     * The maximum distance where the match is still considered to be close enough.
     * @return
     * A menu item.
     */
    public Optional<String> findInMenu(String query, int sensitivity) {

        if (menu == null) {
            throw new NullPointerException("Menu is not initialized");
        }

        query = Normalizer.normalize(query);
        if(query.length() > maxAllowedStringLength || query.isEmpty()) {
            // Empty queries get ignored - there is no matching item.
            return Optional.empty();
        }

        Match match = findClosest(query);
        return Optional.ofNullable(match.getDistance() < sensitivity ?
                                        match.getMatchString() :
                                        null);
    }

    /**
     * Search for the closest match in the menu for the query.
     * @param query
     * The user query
     * @return
     * Closest match: string + distance from it to the query
     */
    private Match findClosest(String query) {
        // Probably a better approach than a straightforward iterate-through exists
        // (if we sort the menu?)
        int minStringDistance = Integer.MAX_VALUE;
        String bestMatch = "";
        for (String candidate: menu) {
            int distance = LevenshteinDistCalculator.stringDistance(query, candidate);
            if (distance < minStringDistance) {
                minStringDistance = distance;
                bestMatch = candidate;
            }
        }
        return new Match(bestMatch, minStringDistance);
    }

    public int getMaxAllowedStringLength() {
        return maxAllowedStringLength;
    }

    public void setMaxAllowedStringLength(int maxAllowedStringLength) {
        this.maxAllowedStringLength = maxAllowedStringLength;
    }

    static class Match {
        private String matchString;
        private int distance;

        Match (String match, int distance) {
            this.matchString = match;
            this.distance = distance;
        }

        public String getMatchString() {
            return matchString;
        }

        public int getDistance() {
            return distance;
        }
    }
}
