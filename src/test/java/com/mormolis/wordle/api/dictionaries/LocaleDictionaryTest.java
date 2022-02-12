package com.mormolis.wordle.api.dictionaries;

import com.mormolis.wordle.api.types.GuessWord;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LocaleDictionaryTest {

    @Test
    void loadsTheDictionaryAndChecksWordsValidity() {
        LocaleDictionary localeDictionary = new LocaleDictionary(Locale.UK);
        assertFalse(localeDictionary.isValidWord(new GuessWord("pouli")));
        assertTrue(localeDictionary.isValidWord(new GuessWord("words")));
    }

}