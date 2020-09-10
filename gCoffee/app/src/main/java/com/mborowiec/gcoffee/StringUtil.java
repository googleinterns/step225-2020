package com.mborowiec.gcoffee;

import org.apache.commons.text.similarity.LevenshteinDistance;

public class StringUtil {

    /**
     * Normalizes the string by removing punctuation, removing whitespaces
     * and making the string lowercase.
     * @param input input string
     * @return a normalized string
     */
    public String normalize(String input) {
        String normalized = input.replaceAll("[^a-zA-Z ]" , "")
                .replaceAll("\\s+", "")
                .toLowerCase();

        return normalized;
    }

    /**
     * Calculates the Levenshtein distance between two strings
     * @param str1 first input string
     * @param str2 second input string
     * @return distance between strings
     */
    public Integer calculateLevenshteinDistance(String str1, String str2) {
        LevenshteinDistance levenshteinDistance = new LevenshteinDistance();
        int distance = levenshteinDistance.apply(str1, str2);

        return distance;
    }
}
