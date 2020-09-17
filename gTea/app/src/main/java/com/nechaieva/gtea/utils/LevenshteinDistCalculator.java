package com.nechaieva.gtea.utils;

public class LevenshteinDistCalculator {

    private LevenshteinDistCalculator() {
    }

    static int[][] dp;

    /**
     * Calculates given distance between two given strings.
     * Currently, uses the Levenshtein-Damerau version of the algorithm.
     * Complexity: O(a.len * b.len)
     */
    static int stringDistance(String a, String b) {
        final int al = a.length(), bl = b.length();
        dp = new int[al][bl];

        for (int y = 0; y < al; y++) {
            dp[y][0] = y;
        }
        for (int x = 1; x < bl; x++) {
            dp[0][x] = x;
        }
        for (int y = 1; y < al; y++) {
            for (int x = 1; x < bl; x++) {
                int charEquals = a.charAt(y) != b.charAt(x) ? 1 : 0;

                dp[y][x] = min(dp[y][x-1] + 1,
                               dp[y-1][x] + 1,
                               dp[y-1][x-1] + charEquals);

                if (y > 1 && x > 1 &&
                        a.charAt(y) == b.charAt(x-1) &&
                        a.charAt(y-1) == b.charAt(x)) {
                    dp[y][x] = Math.min(dp[y][x], dp[y-2][x-2] + 1);
                }
            }
        }
        return dp[al-1][bl-1];
    }

    private static int min(int a, int b, int c) {
        return Math.min(Math.min(a, b), c);
    }

    /**
     * Prints current values of dp[]. Use it for debugging purposes.
     * @param ySize
     * current size of dp, rows
     * @param xSize
     * current size of dp, columns
     */
    private static void printArray(int ySize, int xSize) {
        for (int y = 0; y < ySize; y++) {
            for (int x = 0; x < xSize; x++) {
                System.out.print(dp[y][x] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
