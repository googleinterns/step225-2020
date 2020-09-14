package com.nechaieva.gtea.utils;

public class LevenshteinDistCalculator {
    static int[][] dp;

    /**
     * Calculates given distance between two given strings.
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
            }
        }
        printArray(al, bl);
        return dp[al-1][bl-1];
    }

    private static int min(int a, int b, int c) {
        return Math.min(Math.min(a, b), c);
    }

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
