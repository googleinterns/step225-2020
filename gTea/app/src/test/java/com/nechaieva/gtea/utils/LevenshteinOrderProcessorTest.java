package com.nechaieva.gtea.utils;

import android.graphics.Path;

import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

public class LevenshteinOrderProcessorTest {

    private static LevenshteinOrderProcessor processor;

    @Before
    public void setUp() throws Exception {
        String[] menu = {"Black tea", "Green tea", "Coffee", "Donuts"};
        processor = new LevenshteinOrderProcessor(menu);
    }

    @Test
    public void exactMatch() {
        test("coffee", Optional.of("coffee"));
    }

    @Test
    public void exactMatchWithNormalization() {
        test("COFFEE!", Optional.of("coffee"));
    }

    @Test
    public void approximateMatch() {
        test("Blacktea", Optional.of("black tea"));
    }

    @Test
    public void noMatch() {
        test("Giant crocodile", Optional.empty());
    }

    @Test
    public void lowSensitivity() {
        Optional<String> res = processor.findInMenu("Blaktea", 1);
        assertEquals(res, Optional.empty());
    }

    public void test(String userQuery, Optional<String> expected) {
        Optional<String> res = processor.findInMenu(userQuery);
        assertEquals(res, expected);
    }
}