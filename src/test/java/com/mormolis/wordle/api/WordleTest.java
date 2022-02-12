package com.mormolis.wordle.api;

import com.mormolis.wordle.api.dictionaries.Dictionary;
import com.mormolis.wordle.api.exceptions.GameOverException;
import com.mormolis.wordle.api.exceptions.NotValidWordException;
import com.mormolis.wordle.api.keyboards.LocaleKeyboard;
import com.mormolis.wordle.api.types.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class WordleTest {
    private MasterWord masterWord;
    private Wordle wordle;
    private LocaleKeyboard keyboard;
    private Dictionary dictionary = mock(Dictionary.class);

    @BeforeEach
    void setUp() {
        given(dictionary.isValidWord(any())).willReturn(true);
        this.masterWord = new MasterWord("water");
        this.keyboard = new LocaleKeyboard(Locale.UK);
        this.wordle = new Wordle(masterWord, keyboard, dictionary);
    }

    @Test
    void wordShouldBeFiveCharacters() {
        assertThrows(NotValidWordException.class, () -> new Wordle(new MasterWord("England"), keyboard, dictionary));
    }

    @Test
    void wordShouldExistInTheDictionary() {
        given(dictionary.isValidWord(new MasterWord("Engla"))).willReturn(false);
        assertThrows(NotValidWordException.class, () -> new Wordle(new MasterWord("Engla"), keyboard, dictionary));
    }

    @Test
    void guessWordReturnsTrue() {
        assertTrue(wordle.guess(new GuessWord("water")));
    }

    @Test
    void attemptsReturnTheArrayWithTheWordWithTheRightStatusForEachLetter() {
        wordle.guess(new GuessWord("water"));
        var expectedAttempt = new GuessWord("water");
        expectedAttempt.setLetterStatusAccordingTo(masterWord);
        assertEquals(expectedAttempt, wordle.getAttempts()[0]);
    }

    @Test
    void attemptsAreCorrect() {
        var firstExpectation = new GuessWord("awtep");
        firstExpectation.setLetterStatusAccordingTo(masterWord);
        var secondExpectation = new GuessWord("water");
        secondExpectation.setLetterStatusAccordingTo(masterWord);
        wordle.guess(new GuessWord("awtep"));
        wordle.guess(new GuessWord("water"));

        assertEquals(wordle.getAttempts()[0], firstExpectation);
        assertEquals(wordle.getAttempts()[1], secondExpectation);
        assertNull(wordle.getAttempts()[2]);
    }


    @Test
    void keyboardMaintainsTheLatestStatus() {
        var firstExpectation = new GuessWord("awtep");
        firstExpectation.setLetterStatusAccordingTo(masterWord);
        var secondExpectation = new GuessWord("water");
        secondExpectation.setLetterStatusAccordingTo(masterWord);
        wordle.guess(new GuessWord("awtep"));
        wordle.guess(new GuessWord("water"));

        LinkedHashMap<Letter, Status> keyboard = wordle.getKeyboard();
        assertEquals(Status.USED_RIGHT_PLACE, keyboard.get(new Letter('a')));
    }

    @Test
    void keyboardReturnsAMapWithEachLetterAndTheirCurrentStatus() {
        wordle.guess(new GuessWord("awtep"));
        LinkedHashMap<Letter, Status> keyboard = wordle.getKeyboard();
        assertEquals(Status.USED_WRONG_PLACE, keyboard.get(new Letter('w')));
        assertEquals(Status.USED_WRONG_PLACE, keyboard.get(new Letter('a')));
        assertEquals(Status.USED_RIGHT_PLACE, keyboard.get(new Letter('t')));
        assertEquals(Status.USED_RIGHT_PLACE, keyboard.get(new Letter('e')));
        assertEquals(Status.INITIAL_STATE, keyboard.get(new Letter('r')));
        assertEquals(Status.NOT_USED, keyboard.get(new Letter('p')));
    }

    @Test
    void theGameIsOverWhenWordIsNotFoundAfterTheSixthAttempt() {
        assertThrows(GameOverException.class, () -> {
            wordle.guess(new GuessWord("lucky"));
            wordle.guess(new GuessWord("lucky"));
            wordle.guess(new GuessWord("lucky"));
            wordle.guess(new GuessWord("lucky"));
            wordle.guess(new GuessWord("lucky"));
            wordle.guess(new GuessWord("lucky"));
        });
    }
}