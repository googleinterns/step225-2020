package com.nechaieva.gtea;

import com.nechaieva.gtea.utils.ExactMatchOrderProcessor;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class ExactMatchOrderProcessorTest {

    private static ExactMatchOrderProcessor processor;

    @BeforeClass
    public static void setUp() throws Exception {
        String[] menu = {"Black tea", "Green tea", "Coffee", "Donuts"};
        processor = new ExactMatchOrderProcessor(menu);
    }

    @Test
    public void findExistent() {
        String userQuery = "Black tea";
        Optional<String> res = processor.findInMenu(userQuery);
        assertEquals(res.orElse("null"), "black tea");
    }

    @Test
    public void findExistentWithNormalization() {
        String userQuery = "\nBlack tEA\n\n";
        Optional<String> res = processor.findInMenu(userQuery);
        assertEquals(res.orElse("null"), "black tea");
    }

    @Test
    public void doNotFindNonexistent() {
        String userQuery = "Orange tea";
        Optional<String> res = processor.findInMenu(userQuery);
        assertFalse(res.isPresent());
    }
}