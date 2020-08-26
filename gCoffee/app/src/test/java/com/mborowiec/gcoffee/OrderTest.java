package com.mborowiec.gcoffee;

import org.junit.Test;
import static org.junit.Assert.*;

public class OrderTest {
    Order testClass = new Order();

    public String[] coffeeList = {"Latte", "Cappuccino", "Mocha", "Flat White",
            "Americano", "Espresso"};

    @Test
    public void testCheckMenuOnMenuLowerCase() {
        boolean test = testClass.checkMenu("latte", coffeeList);
        assertTrue(test);
    }

    @Test
    public void testCheckMenuOnMenuUpperCase() {
        boolean test = testClass.checkMenu("Mocha", coffeeList);
        assertTrue(test);
    }

    @Test
    public void testCheckMenuNotOnMenuLowerCase() {
        boolean test = testClass.checkMenu("white tea", coffeeList);
        assertFalse(test);
    }

    @Test
    public void testCheckMenuNotOnMenuUpperCase() {
        boolean test = testClass.checkMenu("Pizza", coffeeList);
        assertFalse(test);
    }

    @Test
    public void testCheckMenuNotFood() {
        boolean test = testClass.checkMenu("shoes", coffeeList);
        assertFalse(test);
    }

    @Test
    public void testCheckMenuNull() {
        boolean test = testClass.checkMenu(null, coffeeList);
        assertFalse(test);
    }
}