package com.mborowiec.gcoffee;

import org.junit.Test;
import static org.junit.Assert.*;

public class OrderTest {
    Order testClass = new Order();

    public String[] coffeeList = {"Latte", "Cappuccino", "Mocha", "Flat White",
            "Americano", "Espresso"};

    @Test
    public void testCheckMenuOnMenuLowerCase() {
        boolean test = testClass.menuContainsItem("latte", coffeeList);
        assertTrue(test);
    }

    @Test
    public void testCheckMenuOnMenuUpperCase() {
        boolean test = testClass.menuContainsItem("Mocha", coffeeList);
        assertTrue(test);
    }

    @Test
    public void testCheckMenuNotOnMenuLowerCase() {
        boolean test = testClass.menuContainsItem("white tea", coffeeList);
        assertFalse(test);
    }

    @Test
    public void testCheckMenuNotOnMenuUpperCase() {
        boolean test = testClass.menuContainsItem("Pizza", coffeeList);
        assertFalse(test);
    }

    @Test
    public void testCheckMenuNotFood() {
        boolean test = testClass.menuContainsItem("shoes", coffeeList);
        assertFalse(test);
    }

    @Test
    public void testCheckMenuNull() {
        boolean test = testClass.menuContainsItem(null, coffeeList);
        assertFalse(test);
    }

    @Test
    public void testFindMatchExactMatch() {
        String testString = testClass.findMatch("latte", coffeeList);
        assertEquals("latte", testString);
    }

    @Test
    public void testFindMatchWhiteSpaceMissing() {
        String testString = testClass.findMatch("flatwhite", coffeeList);
        assertEquals("Flat White", testString);
    }

    @Test
    public void testFindMatchOneMiddleLetterMissing() {
        String testString = testClass.findMatch("capuccino", coffeeList);
        assertEquals("Cappuccino", testString);
    }

    @Test
    public void testFindMatchLastLetterMissing() {
        String testString = testClass.findMatch("american", coffeeList);
        assertEquals("Americano", testString);
    }

    @Test
    public void testFindMatchLetterAndWhiteSpaceMissing() {
        String testString = testClass.findMatch("fatwhite", coffeeList);
        assertEquals("Flat White", testString);
    }

    @Test
    public void testFindMatchLetterMissing() {
        String testString = testClass.findMatch("mcha", coffeeList);
        assertEquals("Mocha", testString);
    }

    @Test
    public void testFindMatchLetterWithAccent() {
        String testString = testClass.findMatch("latté", coffeeList);
        assertEquals("Latte", testString);
    }

    @Test
    public void testFindMatchNull() {
        String testString = testClass.findMatch(null, coffeeList);
        assertEquals(null, testString);
    }

    @Test
    public void testFindMatchEmptyString() {
        String testString = testClass.findMatch("", coffeeList);
        assertEquals(null, testString);
    }

    @Test
    public void testFindMatchWhitespace() {
        String testString = testClass.findMatch(" ", coffeeList);
        assertEquals(null, testString);
    }

    //The case not handled properly yet - returns latte, as this is "closest" to
    // black tea according to Levenshtein distance
    @Test
    public void testFindMatchNotOnMenu() {
        String testString = testClass.findMatch("black tea", coffeeList);
        assertEquals(null, testString);
    }
}