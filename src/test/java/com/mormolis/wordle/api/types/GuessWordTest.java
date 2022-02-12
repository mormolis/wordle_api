package com.mormolis.wordle.api.types;

import org.junit.jupiter.api.Test;

import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class GuessWordTest {

    @Test
    void statusIsUpdatedOnlyOnce() {
        GuessWord guessWord = new GuessWord("abcdef");
        guessWord.setLetterStatusAccordingTo(new MasterWord("abcdef"));
        guessWord.setLetterStatusAccordingTo(new MasterWord("qwerty"));

        Set<Letter> allUsedInTheRightPlace = guessWord.getAllUsedInTheRightPlace();
        LinkedHashSet<Letter> expected = new LinkedHashSet<>(Set.of(new Letter('a'), new Letter('b'), new Letter('c'), new Letter('d'), new Letter('e'), new Letter('f')));
        assertTrue(expected.size() == allUsedInTheRightPlace.size() && allUsedInTheRightPlace.containsAll(expected));
    }

    @Test
    void allNonUsedLettersCanBeRetrieved() {
        GuessWord guessWord = new GuessWord("abcdef");
        guessWord.setLetterStatusAccordingTo(new MasterWord("acbplf"));

        Set<Letter> allNonUsedLetter = guessWord.getAllNonUsedLetter();
        LinkedHashSet<Letter> expected = new LinkedHashSet<>(Set.of(new Letter('e'), new Letter('d')));
        assertTrue(expected.size() == allNonUsedLetter.size() && allNonUsedLetter.containsAll(expected));
    }

    @Test
    void allUsedInTheRightPlaceLettersCanBeRetrieved() {
        GuessWord guessWord = new GuessWord("abcdef");
        guessWord.setLetterStatusAccordingTo(new MasterWord("acbplf"));

        Set<Letter> allNonUsedLetter = guessWord.getAllUsedInTheRightPlace();
        LinkedHashSet<Letter> expected = new LinkedHashSet<>(Set.of(new Letter('f'), new Letter('a')));
        assertTrue(expected.size() == allNonUsedLetter.size() && allNonUsedLetter.containsAll(expected));
    }

    @Test
    void allUsedInTheWrongPlaceLettersCanBeRetrieved() {
        GuessWord guessWord = new GuessWord("abcdaf");
        guessWord.setLetterStatusAccordingTo(new MasterWord("acbplf"));

        Set<Letter> allUsedInTheWrongPlace = guessWord.getAllUsedInTheWrongPlace();
        LinkedHashSet<Letter> expected = new LinkedHashSet<>(Set.of(new Letter('c'), new Letter('b'), new Letter('a')));
        assertTrue(expected.size() == allUsedInTheWrongPlace.size() && allUsedInTheWrongPlace.containsAll(expected));
    }
}