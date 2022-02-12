package com.mormolis.wordle.api.types;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MasterWordTest {

    @Test
    void returnsTrueWhenTheWordContainsALetter() {
        MasterWord masterWord = new MasterWord("masterWord");
        assertTrue(masterWord.contains(new Letter('w')));
    }

    @Test
    void returnsFalseWhenTheWordContainsALetter() {
        MasterWord masterWord = new MasterWord("masterWord");
        assertTrue(masterWord.contains(new Letter('s')));
    }

    @Test
    void returnsTheLetterOnSpecificIndex() {
        MasterWord masterWord = new MasterWord("masterWord");
        assertEquals(new Letter('m'), masterWord.charAt(0));
        assertEquals(new Letter('a'), masterWord.charAt(1));
        assertEquals(new Letter('s'), masterWord.charAt(2));
        assertEquals(new Letter('t'), masterWord.charAt(3));
        assertEquals(new Letter('e'), masterWord.charAt(4));
        assertEquals(new Letter('r'), masterWord.charAt(5));
        assertEquals(new Letter('w'), masterWord.charAt(6));
        assertEquals(new Letter('o'), masterWord.charAt(7));
        assertEquals(new Letter('r'), masterWord.charAt(8));
        assertEquals(new Letter('d'), masterWord.charAt(9));
    }
}