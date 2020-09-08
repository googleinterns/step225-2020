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
}