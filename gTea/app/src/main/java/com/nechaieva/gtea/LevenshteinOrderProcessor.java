package com.nechaieva.gtea;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * And implementation of OrderProcessor that uses Levenshtein distances.
 */
public class LevenshteinOrderProcessor implements OrderProcessor {

    private List<String> menu;
    public final int DEFAULT_SENSITIVITY = 3; // The threshold for string distances.
    private int maxAllowedStringLength = 100;

    LevenshteinOrderProcessor(String[] menu) {
        loadMenu(menu);
    }

    LevenshteinOrderProcessor(ArrayList<String> menu) {
        this.menu = new ArrayList<>(menu);
    }

    @Override
    public void loadMenu(String[] menu) {
        this.menu = Arrays.asList(menu);
    }

    /**
     * @see LevenshteinOrderProcessor#findInMenu(String, int)
     * Uses default sensitivity.
     */
    @Override
    public Optional<String> findInMenu(String query) {
        if(query.length() > maxAllowedStringLength) {
            return Optional.empty();
        }
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