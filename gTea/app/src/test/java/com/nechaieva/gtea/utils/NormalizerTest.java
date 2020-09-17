package com.nechaieva.gtea.utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class NormalizerTest {

    @Test
    public void spaces() {
        assertEquals("black tea", Normalizer.normalize("black  \t tea"));
    }

    @Test
    public void spacesBegin() {
        assertEquals("black tea", Normalizer.normalize("\nblack   tea"));
    }

    @Test
    public void spacesEnd() {
        assertEquals("black tea", Normalizer.normalize("black   tea\n"));
    }

    @Test
    public void textCase() {
        assertEquals("black tea", Normalizer.normalize("BlacK tEA"));
    }

    @Test
    public void simultaneousCorrections() {
        assertEquals("black tea", Normalizer.normalize("\nBlack tEA\n\n"));
    }

    @Test
    public void normalizeAll() {
        String[] arr = {"Black\ttea", "Green  tea"};
        Normalizer.normalizeAll(arr);
        assertArrayEquals(new String[]{"black tea", "green tea"}, arr);
    }
}