package com.nechaieva.gtea.utils;

import com.nechaieva.gtea.utils.ExactMatchOrderProcessor;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class ExactMatchOrderProcessorTest {

    private static ExactMatchOrderProcessor processor;

    @BeforeClass
    public static void setUp() throws Exception {
        String[] menu = {"Black tea", "Green tea", "Coffee", "Donuts"};
        processor = new ExactMatchOrderProcessor(menu);
    }

    @Test
    public void findExistent() {
        test("Black tea", Optional.of("black tea"));
    }

    @Test
    public void findExistentWithNormalization() {
        test("\nBlack tEA\n\n", Optional.of("black tea"));
    }

    @Test
    public void doNotFindNonexistent() {
        test("Orange tea", Optional.empty());
    }

    public void test(String userQuery, Optional<String> expected) {
        Optional<String> res = processor.findInMenu(userQuery);
        assertEquals(res, expected);

    }
}