package com.nechaieva.gtea.utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class LevenshteinDistCalculatorTest {

    @Test
    public void testLetterMissing() {
        test( 1,"hello", "hell");
    }

    @Test
    public void testLetterAdded() {
        test( 1,"hell", "hello");
    }

    @Test
    public void testLetterChanged() {
        test( 1,"hell", "hall");
    }

    @Test
    public void testLetterSwapped() {
        test(1, "swap", "sawp");
    }

    @Test
    public void testClassicalExample() {
        test( 6,"polynomial", "exponential");
    }

    private void test(int dist, String a, String b) {
        assertEquals( dist, LevenshteinDistCalculator.stringDistance(a, b));
    }
}