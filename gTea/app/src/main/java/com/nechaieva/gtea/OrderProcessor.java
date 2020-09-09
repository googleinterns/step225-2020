package com.nechaieva.gtea;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrderProcessor {

    List<String> menu;
    final int SENSITIVITY = 3;
    public final String NO_CANDIDATE = "";

    OrderProcessor(String[] menu) {
        this.menu = Arrays.asList(menu);
    }

    OrderProcessor(ArrayList<String> menu) {
        this.menu = menu;
    }

    public String findInMenu(String query) {
        Match match = findClosest(query);
        return match.getDistance() < SENSITIVITY ? match.getMatchString() : NO_CANDIDATE;
    }

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

    private int stringDistance(String query, String goal) {
        // Levenshtein
        return 0;
    }

    class Match {
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
