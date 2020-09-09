package com.nechaieva.gtea;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Given a query that represent the user's order,
 * tries to process the order so that it can be passed back to the handler.
 * For this, it is necessary to find to what kind of menu item / items
 * it corresponds.
 */
public class OrderProcessor {

    private List<String> menu;
    public final int DEFAULT_SENSITIVITY = 3; // The threshold for string distances.

    OrderProcessor(String[] menu) {
        this.menu = Arrays.asList(menu);
    }

    OrderProcessor(ArrayList<String> menu) {
        this.menu = menu;
    }

    /**
     * @see OrderProcessor#findInMenu(String, String, int)
     * Uses default sensitivity.
     */
    public String findInMenu(String query,
                             String defaultNoCandidate) {
        return findInMenu(query, defaultNoCandidate, DEFAULT_SENSITIVITY);
    }

    /**
     * Given a text query passed from the user, return the string that represent
     * an existing menu item. Returns default value if there is no close enough match.
     * @param query
     * A user query.
     * @param defaultNoCandidate
     * What to return if there is no close enough match
     * @param sensitivity
     * The maximum distance where the match is still considered to be close enough.
     * @return
     * A menu item.
     */
    public String findInMenu(String query, String defaultNoCandidate, int sensitivity) {
        Match match = findClosest(query);
        return match.getDistance() < sensitivity ? match.getMatchString() : defaultNoCandidate;
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
            int distance = stringDistance(query, candidate);
            if (distance < minStringDistance) {
                minStringDistance = distance;
                bestMatch = candidate;
            }
        }
        return new Match(bestMatch, minStringDistance);
    }

    /**
     * Calculates the distance between two string, using Levenshtein algorithm.
     */
    private int stringDistance(String query, String goal) {
        // Levenshtein
        return 0;
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
