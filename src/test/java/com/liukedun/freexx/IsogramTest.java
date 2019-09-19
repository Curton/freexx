package com.liukedun.freexx;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class IsogramTest {

    @Test
    public void FixedTests() {
        assertTrue(Isogram.isIsogram("Dermatoglyphics"));
        assertTrue(Isogram.isIsogram("isogram"));
        assertFalse(Isogram.isIsogram("moose"));
        assertFalse(Isogram.isIsogram("isIsogram"));
        assertFalse(Isogram.isIsogram("aba"));
        assertFalse(Isogram.isIsogram("moOse"));
        assertTrue(Isogram.isIsogram("thumbscrewjapingly"));
        assertTrue(Isogram.isIsogram(""));
    }
}